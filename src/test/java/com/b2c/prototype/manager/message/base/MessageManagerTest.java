package com.b2c.prototype.manager.message.base;

import com.b2c.prototype.modal.dto.payload.message.MessageDto;
import com.b2c.prototype.modal.dto.payload.message.MessageTemplateDto;
import com.b2c.prototype.modal.dto.payload.message.ResponseMessageOverviewDto;
import com.b2c.prototype.modal.dto.payload.message.ResponseMessagePayloadDto;
import com.b2c.prototype.modal.entity.message.Message;
import com.b2c.prototype.modal.entity.message.MessageBox;
import com.b2c.prototype.modal.entity.message.MessageStatus;
import com.b2c.prototype.modal.entity.message.MessageTemplate;
import com.b2c.prototype.modal.entity.message.MessageType;


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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MessageManagerTest {
    @InjectMocks
    private MessageManager messageManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveUpdateMessage_shouldInvokeMergeOnSession() {
        MessageDto messageDto = MessageDto.builder()
                .messageTemplate(MessageTemplateDto.builder()
                        .sender("test@domain.com")
                        .title("Test Message")
                        .message("This is a test.")
                        .receivers(Collections.singletonList("receiver@domain.com"))
                        .build())
                .build();
        String userId = "userId";
        String messageId = "uniqId";
        MessageBox messageBox = mock(MessageBox.class);
        Message existingMessage = mock(Message.class);
        Message newMessage = mock(Message.class);


        Session session = mock(Session.class);
        NativeQuery<MessageBox> query = mock(NativeQuery.class);

        when(messageBox.getMessages()).thenReturn(Set.of(existingMessage));
//        when(existingMessage.getMessageUniqNumber()).thenReturn("123");

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            consumer.accept(session);
            return null;
        });

        messageManager.updateMessage(messageId, messageDto);

        verify(messageBox).addMessage(newMessage);
        verify(session).merge(messageBox);
    }

    @Test
    void deleteMessage_shouldRemoveMessageFromMessageBoxAndMerge() {
        String userId = "uniqId";
        String messageId = "123";

        MessageBox messageBox = mock(MessageBox.class);
        Message existingMessage = mock(Message.class);

        Session session = mock(Session.class);
        NativeQuery<MessageBox> query = mock(NativeQuery.class);

//        when(session.createNativeQuery(any(String.class), eq(MessageBox.class))).thenReturn(query);

//        when(queryService.getQueryEntity(eq(query), any(Supplier.class))).thenReturn(messageBox);
        when(messageBox.getMessages()).thenReturn(Set.of(existingMessage));
//        when(existingMessage.getMessageUniqNumber()).thenReturn("123");

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            consumer.accept(session);
            return null;
        });

//        messageManager.deleteMessage(userId, messageId);

        verify(messageBox).removeMessage(existingMessage);
        verify(session).merge(messageBox);
    }

    @Test
    void cleanUpMessagesByUserId_shouldRemoveMessageFromMessageBoxAndMerge() {
        String uniqueId = "uniqueId";

        MessageBox messageBox = mock(MessageBox.class);
        Message existingMessage = mock(Message.class);

        Session session = mock(Session.class);
        NativeQuery<MessageBox> query = mock(NativeQuery.class);

//        when(queryService.getQueryEntity(eq(query), any(Supplier.class))).thenReturn(messageBox);
        when(messageBox.getMessages()).thenReturn(Set.of(existingMessage));

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            consumer.accept(session);
            return null;
        });

        messageManager.cleanUpMessagesByUserId(uniqueId);

        verify(messageBox).removeMessage(existingMessage);
        verify(session).merge(messageBox);
    }

    @Test
    void getMessageOverviewBySenderEmail_shouldReturnList() {
        String senderEmail = "sender@domain.com";

        Function<Message, ResponseMessageOverviewDto> transformationFunction = message -> ResponseMessageOverviewDto.builder()
                .build();
//        when(messageDao.getNamedQueryEntityList("", parameter))
//                .thenReturn(Collections.singletonList(getMessage()));

        List<ResponseMessageOverviewDto> result = messageManager.getMessageOverviewBySenderEmail(senderEmail);

        assertEquals("sender@domain.com", result.get(0).getSender());
    }

    @Test
    void getMessageOverviewByReceiverEmail_shouldReturnList() {
        String receiverEmail = "receiver@domain.com";

        Function<Message, ResponseMessageOverviewDto> transformationFunction = message -> ResponseMessageOverviewDto.builder()
                .build();
//        when(messageDao.getNamedQueryEntityList("", parameter))
//                .thenReturn(Collections.singletonList(getMessage()));

        List<ResponseMessageOverviewDto> result = messageManager.getMessageOverviewByReceiverEmail(receiverEmail);

        assertEquals("sender@domain.com", result.get(0).getSender());
    }

    @Test
    void getMessagePayloadDto_shouldReturnPayload() {
        String uniqueId = "messageUniqNumber1";

        Function<Message, ResponseMessagePayloadDto> transformationFunction = message -> ResponseMessagePayloadDto.builder()
                .message(message.getMessageTemplate().getMessage())
                .build();
        Message testMessage = getMessage();
        ResponseMessagePayloadDto expectedResponseMessagePayloadDto = getResponseMessagePayloadDto();

        ResponseMessagePayloadDto result = messageManager.getMessagePayloadDto("", uniqueId);

        assertEquals("Message Content", result.getMessage());
    }

    private Message getMessage() {
        MessageType messageType = mock(MessageType.class);
        MessageStatus messageStatus = mock(MessageStatus.class);
        return Message.builder()
                .messageTemplate(MessageTemplate.builder()
                        .title("Message Content")
                        .message("Message Content")
                        .build())
                .status(messageStatus)
                .build();
    }

    private ResponseMessagePayloadDto getResponseMessagePayloadDto() {
        return ResponseMessagePayloadDto.builder()
                .message("Message Content")
                .build();
    }

}
