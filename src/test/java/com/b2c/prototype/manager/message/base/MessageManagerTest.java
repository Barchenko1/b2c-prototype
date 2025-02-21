package com.b2c.prototype.manager.message.base;


import com.b2c.prototype.dao.message.IMessageDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.delete.MultipleFieldsSearchDtoDelete;
import com.b2c.prototype.modal.dto.payload.MessageDto;
import com.b2c.prototype.modal.dto.response.ResponseMessageOverviewDto;
import com.b2c.prototype.modal.dto.response.ResponseMessagePayloadDto;
import com.b2c.prototype.modal.dto.update.MessageDtoUpdate;
import com.b2c.prototype.modal.entity.message.Message;
import com.b2c.prototype.modal.entity.message.MessageBox;
import com.b2c.prototype.modal.entity.message.MessageStatus;
import com.b2c.prototype.modal.entity.message.MessageType;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.query.ISearchService;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.finder.parameter.Parameter;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.b2c.prototype.util.Constant.USER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MessageManagerTest {

    @Mock
    private IMessageDao messageDao;
    @Mock
    private ISearchService queryService;
    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @Mock
    private ISupplierService supplierService;
    @InjectMocks
    private MessageManager messageManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveUpdateMessage_shouldInvokeMergeOnSession() {
        MessageDto messageDto = MessageDto.builder()
                .sender("test@domain.com")
                .title("Test Message")
                .message("This is a test.")
                .receivers(Collections.singletonList("receiver@domain.com"))
                .build();
        MessageDtoUpdate messageDtoUpdate = MessageDtoUpdate.builder()
                .mainSearchField("uniqId")
                .innerSearchField("123")
                .newEntity(messageDto)
                .build();
        MessageBox messageBox = mock(MessageBox.class);
        Message existingMessage = mock(Message.class);
        Message newMessage = mock(Message.class);

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> supplier = () -> parameter;
        Session session = mock(Session.class);
        NativeQuery<MessageBox> query = mock(NativeQuery.class);

        when(session.createNativeQuery(any(String.class), eq(MessageBox.class))).thenReturn(query);
        when(supplierService.parameterStringSupplier(USER_ID, messageDtoUpdate.getMainSearchField()))
                .thenReturn(supplier);
        when(queryService.getQueryEntity(eq(query), any(Supplier.class))).thenReturn(messageBox);
        when(transformationFunctionService.getEntity(eq(Message.class), eq(messageDto))).thenReturn(newMessage);
        when(messageBox.getMessages()).thenReturn(Set.of(existingMessage));
        when(existingMessage.getMessageUniqNumber()).thenReturn("123");

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            consumer.accept(session);
            return null;
        }).when(messageDao).executeConsumer(any());

        messageManager.saveUpdateMessage(messageDtoUpdate);

        verify(messageBox).addMessage(newMessage);
        verify(session).merge(messageBox);
    }

    @Test
    void deleteMessage_shouldRemoveMessageFromMessageBoxAndMerge() {
        MultipleFieldsSearchDtoDelete multipleFieldsSearchDtoDelete = MultipleFieldsSearchDtoDelete.builder()
                .mainSearchField("uniqueId")
                .innerSearchField("123")
                .build();

        MessageBox messageBox = mock(MessageBox.class);
        Message existingMessage = mock(Message.class);
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> supplier = () -> parameter;
        Session session = mock(Session.class);
        NativeQuery<MessageBox> query = mock(NativeQuery.class);

        when(session.createNativeQuery(any(String.class), eq(MessageBox.class))).thenReturn(query);
        when(supplierService.parameterStringSupplier(USER_ID, multipleFieldsSearchDtoDelete.getMainSearchField()))
                .thenReturn(supplier);
        when(queryService.getQueryEntity(eq(query), any(Supplier.class))).thenReturn(messageBox);
        when(messageBox.getMessages()).thenReturn(Set.of(existingMessage));
        when(existingMessage.getMessageUniqNumber()).thenReturn("123");

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            consumer.accept(session);
            return null;
        }).when(messageDao).executeConsumer(any());

        messageManager.deleteMessage(multipleFieldsSearchDtoDelete);

        verify(messageBox).removeMessage(existingMessage);
        verify(session).merge(messageBox);
    }

    @Test
    void cleanUpMessagesByUserId_shouldRemoveMessageFromMessageBoxAndMerge() {
        OneFieldEntityDto oneFieldEntityDto = OneFieldEntityDto.builder()
                .value("uniqueId")
                .build();

        MessageBox messageBox = mock(MessageBox.class);
        Message existingMessage = mock(Message.class);
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> supplier = () -> parameter;
        Session session = mock(Session.class);
        NativeQuery<MessageBox> query = mock(NativeQuery.class);

        when(session.createNativeQuery(any(String.class), eq(MessageBox.class))).thenReturn(query);
        when(supplierService.parameterStringSupplier(USER_ID, oneFieldEntityDto.getValue()))
                .thenReturn(supplier);
        when(queryService.getQueryEntity(eq(query), any(Supplier.class))).thenReturn(messageBox);
        when(messageBox.getMessages()).thenReturn(Set.of(existingMessage));

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            consumer.accept(session);
            return null;
        }).when(messageDao).executeConsumer(any());

        messageManager.cleanUpMessagesByUserId(oneFieldEntityDto);

        verify(messageBox).removeMessage(existingMessage);
        verify(session).merge(messageBox);
    }

    @Test
    void getMessageOverviewBySenderEmail_shouldReturnList() {
        OneFieldEntityDto oneFieldEntityDto = OneFieldEntityDto.builder()
                .value("sender@domain.com")
                .build();

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> supplier = () -> parameter;
        Function<Message, ResponseMessageOverviewDto> transformationFunction = message -> ResponseMessageOverviewDto.builder()
                .sender(message.getSender())
                .receivers(message.getReceivers())
                .build();
        when(supplierService.parameterStringSupplier("sender", oneFieldEntityDto.getValue()))
                .thenReturn(supplier);
        when(transformationFunctionService.getTransformationFunction(Message.class, ResponseMessageOverviewDto.class))
                .thenReturn(transformationFunction);

        when(messageDao.getEntityList(parameter))
                .thenReturn(Collections.singletonList(getMessage()));

        List<ResponseMessageOverviewDto> result = messageManager.getMessageOverviewBySenderEmail(oneFieldEntityDto);

        assertEquals("sender@domain.com", result.get(0).getSender());
        assertEquals("receiver@domain.com", result.get(0).getReceivers().get(0));
    }

    @Test
    void getMessageOverviewByReceiverEmail_shouldReturnList() {
        OneFieldEntityDto oneFieldEntityDto = OneFieldEntityDto.builder()
                .value("reciever@domain.com")
                .build();

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> supplier = () -> parameter;
        Function<Message, ResponseMessageOverviewDto> transformationFunction = message -> ResponseMessageOverviewDto.builder()
                .sender(message.getSender())
                .receivers(message.getReceivers())
                .build();
        when(supplierService.parameterStringSupplier("receiver", oneFieldEntityDto.getValue()))
                .thenReturn(supplier);
        when(transformationFunctionService.getTransformationFunction(Message.class, ResponseMessageOverviewDto.class))
                .thenReturn(transformationFunction);

        when(messageDao.getEntityList(parameter))
                .thenReturn(Collections.singletonList(getMessage()));

        List<ResponseMessageOverviewDto> result = messageManager.getMessageOverviewByReceiverEmail(oneFieldEntityDto);

        assertEquals("sender@domain.com", result.get(0).getSender());
        assertEquals("receiver@domain.com", result.get(0).getReceivers().get(0));
    }

    @Test
    void getMessagePayloadDto_shouldReturnPayload() {
        OneFieldEntityDto oneFieldEntityDto = OneFieldEntityDto.builder()
                .value("messageUniqNumber1")
                .build();

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> supplier = () -> parameter;
        Function<Message, ResponseMessagePayloadDto> transformationFunction = message -> ResponseMessagePayloadDto.builder()
                .message(message.getMessage())
                .build();
        Message testMessage = getMessage();
        ResponseMessagePayloadDto expectedResponseMessagePayloadDto = getResponseMessagePayloadDto();

        when(supplierService.parameterStringSupplier("messageUniqNumber", oneFieldEntityDto.getValue()))
                .thenReturn(supplier);
        when(transformationFunctionService.getTransformationFunction(Message.class, ResponseMessagePayloadDto.class))
                .thenReturn(transformationFunction);
        when(messageDao.getEntityGraph(anyString(), eq(parameter))).thenReturn(testMessage);

        ResponseMessagePayloadDto result = messageManager.getMessagePayloadDto(oneFieldEntityDto);

        assertEquals("Message Content", result.getMessage());
    }

    private Message getMessage() {
        MessageType messageType = mock(MessageType.class);
        MessageStatus messageStatus = mock(MessageStatus.class);
        return Message.builder()
                .sender("sender@domain.com")
                .receivers(Collections.singletonList("receiver@domain.com"))
                .title("Message Content")
                .message("Message Content")
                .type(messageType)
                .status(messageStatus)
                .build();
    }

    private ResponseMessagePayloadDto getResponseMessagePayloadDto() {
        return ResponseMessagePayloadDto.builder()
                .message("Message Content")
                .build();
    }

}
