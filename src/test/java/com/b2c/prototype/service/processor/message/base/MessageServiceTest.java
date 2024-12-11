package com.b2c.prototype.service.processor.message.base;

import com.b2c.prototype.dao.cashed.IEntityCachedMap;
import com.b2c.prototype.dao.message.IMessageDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.MessageDto;
import com.b2c.prototype.modal.dto.response.ResponseMessageOverviewDto;
import com.b2c.prototype.modal.dto.response.ResponseMessagePayloadDto;
import com.b2c.prototype.modal.dto.update.MessageDtoUpdate;
import com.b2c.prototype.modal.entity.message.Message;
import com.b2c.prototype.modal.entity.message.MessageStatus;
import com.b2c.prototype.modal.entity.message.MessageType;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MessageServiceTest {

    @Mock
    private IParameterFactory parameterFactory;

    @Mock
    private IMessageDao messageDao;

    @Mock
    private IEntityCachedMap entityCachedMap;

    @InjectMocks
    private MessageService messageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveMessage_shouldInvokeSaveEntity() {
        MessageDto messageDto = MessageDto.builder()
                .sender("test@domain.com")
                .title("Test Message")
                .message("This is a test.")
                .receivers(Collections.singletonList("receiver@domain.com"))
                .build();
        MessageStatus messageStatus = mock(MessageStatus.class);
        MessageType messageType = mock(MessageType.class);

        when(entityCachedMap.getEntity(eq(MessageStatus.class), any(), any())).thenReturn(messageStatus);
        when(entityCachedMap.getEntity(eq(MessageType.class), any(), any())).thenReturn(messageType);

        messageService.saveMessage(messageDto);

        ArgumentCaptor<Supplier<Message>> captor = ArgumentCaptor.forClass(Supplier.class);
        verify(messageDao).saveEntity(captor.capture());

        Message message = captor.getValue().get();
        assertEquals(messageStatus, message.getStatus());
        assertEquals(messageType, message.getType());
        assertEquals(messageDto.getMessage(), message.getMessage());
        assertEquals(messageDto.getSender(), message.getSender());
        assertEquals(messageDto.getTitle(), message.getTitle());
        assertEquals(messageDto.getReceivers(), message.getReceivers());
    }

    @Test
    void updateMessage_shouldInvokeUpdateEntity() {
        MessageDtoUpdate messageDtoUpdate = new MessageDtoUpdate();
        messageDtoUpdate.setSearchField("uniqueId");
        messageDtoUpdate.setNewEntityDto(MessageDto.builder()
                .message("Updated Message")
                .build());
        Message existingMessage = new Message();
        existingMessage.setId(1L);

        // Act
        messageService.updateMessage(messageDtoUpdate);

        // Assert
        verify(messageDao).updateEntity(any(Consumer.class));

    }

    @Test
    void deleteMessageBySenderEmail_shouldInvokeDeleteEntity() {
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto("sender@domain.com");

        Parameter parameter = mock(Parameter.class);
        when(parameterFactory.createStringParameter("sender", oneFieldEntityDto.getValue()))
                .thenReturn(parameter);

        messageService.deleteMessageBySenderEmail(oneFieldEntityDto);

        verify(messageDao).findEntityAndDelete(parameter);
    }

    @Test
    void deleteMessageByReceiverEmail_shouldInvokeDeleteEntity() {
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto("receiver@domain.com");

        Parameter parameter = mock(Parameter.class);
        when(parameterFactory.createStringParameter("receiver", oneFieldEntityDto.getValue()))
                .thenReturn(parameter);

        messageService.deleteMessageByReceiverEmail(oneFieldEntityDto);

        verify(messageDao).findEntityAndDelete(parameter);
    }

    @Test
    void getMessageOverviewBySenderEmail_shouldReturnList() {
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto("sender@domain.com");
        when(messageDao.getEntityList(any())).thenReturn(Collections.singletonList(getMessage()));

        List<ResponseMessageOverviewDto> result = messageService.getMessageOverviewBySenderEmail(oneFieldEntityDto);

        assertEquals("sender@domain.com", result.get(0).getSender());
    }

    @Test
    void getMessageOverviewByReceiverEmail_shouldReturnList() {
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto("receiver@domain.com");
        when(messageDao.getEntityList(any())).thenReturn(Collections.singletonList(getMessage()));

        List<ResponseMessageOverviewDto> result = messageService.getMessageOverviewByReceiverEmail(oneFieldEntityDto);

        assertEquals("sender@domain.com", result.get(0).getSender());
        assertEquals("receiver@domain.com", result.get(0).getReceivers().get(0));
    }

    @Test
    void getMessagePayloadDto_shouldReturnPayload() {
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto("messageUniqNumber1");

        Parameter parameter = mock(Parameter.class);
        Message message = getMessage();
        when(parameterFactory.createStringParameter("messageUniqNumber", "messageUniqNumber1"))
                .thenReturn(parameter);
        when(messageDao.getEntity(parameter)).thenReturn(message);

        ResponseMessagePayloadDto result = messageService.getMessagePayloadDto(oneFieldEntityDto);

        assertEquals("Message Content", result.getMessage());
    }

    @Test
    void getMessageOverviewDto_shouldReturnMessageDto() {
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto("messageUniqNumber1");
        Message message = getMessage();
        Parameter parameter = mock(Parameter.class);
        when(parameterFactory.createStringParameter("messageUniqNumber", "messageUniqNumber1"))
                .thenReturn(parameter);
        when(messageDao.getEntity(parameter)).thenReturn(message);

        ResponseMessageOverviewDto result = messageService.getMessageOverviewDto(oneFieldEntityDto);

        assertEquals("Message Content", result.getTitle());
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

}
