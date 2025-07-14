package com.b2c.prototype.manager.message.base;

import com.b2c.prototype.dao.message.IMessageBoxDao;
import com.b2c.prototype.modal.constant.MessageStatusEnum;
import com.b2c.prototype.modal.dto.payload.message.MessageDto;
import com.b2c.prototype.modal.dto.payload.message.ResponseMessageOverviewDto;
import com.b2c.prototype.modal.dto.payload.message.ResponseMessagePayloadDto;
import com.b2c.prototype.modal.entity.message.Message;
import com.b2c.prototype.modal.entity.message.MessageBox;
import com.b2c.prototype.modal.entity.message.MessageStatus;
import com.b2c.prototype.modal.entity.message.MessageTemplate;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.b2c.prototype.manager.message.IMessageManager;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.common.ITransactionEntityDao;
import com.tm.core.process.dao.query.IQueryService;
import com.tm.core.process.dao.IFetchHandler;
import com.tm.core.process.manager.common.ITransactionEntityOperationManager;
import com.tm.core.process.manager.common.operator.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;
import com.tm.core.process.manager.common.operator.TransactionEntityOperationManager;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.USER_ID;
import static com.b2c.prototype.util.Constant.VALUE;

public class MessageManager implements IMessageManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageManager.class);

    private final ITransactionEntityOperationManager entityOperationManager;
    private final IFetchHandler fetchHandler;
    private final IQueryService queryService;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;

    public MessageManager(ITransactionEntityDao messageBoxDao,
                          IFetchHandler fetchHandler,
                          IQueryService queryService,
                          ITransformationFunctionService transformationFunctionService,
                          IParameterFactory parameterFactory) {
        this.entityOperationManager = new TransactionEntityOperationManager(messageBoxDao);
        this.fetchHandler = fetchHandler;
        this.queryService = queryService;
        this.transformationFunctionService = transformationFunctionService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveMessage(MessageDto messageDto) {
        entityOperationManager.executeConsumer(session -> {
            Map<String, List<?>> map = new HashMap<>(){{
                put("emails", messageDto.getMessageTemplate().getReceivers());
            }};
            List<MessageBox> messageBoxes = queryService.getNamedQueryEntityMap(
                    session,
                    MessageBox.class,
                    "MessageBox.findByEmailListWithMessages",
                    map);
            Message message = transformationFunctionService.getEntity((Session) session, Message.class, messageDto);
            messageBoxes.forEach(message::addMessageBox);
            session.merge(message);
        });
    }

    @Override
    public void updateMessage(String messageId, MessageDto messageDto) {
        entityOperationManager.executeConsumer(session -> {
            MessageBox messageBox = queryService.getNamedQueryEntity(
                    session,
                    MessageBox.class,
                    "MessageBox.findByUserIdWithMessages",
                    parameterFactory.createStringParameter(USER_ID, messageId));
            Message newMessage = transformationFunctionService.getEntity((Session) session, Message.class, messageDto);
            Message message = getExistingMessage(messageBox, messageId);
            MessageTemplate messageTemplate = message.getMessageTemplate();
            messageTemplate.setSender(newMessage.getMessageTemplate().getSender());
            messageTemplate.setReceivers(newMessage.getMessageTemplate().getReceivers());
            messageTemplate.setTitle(newMessage.getMessageTemplate().getTitle());
            messageTemplate.setMessage(newMessage.getMessageTemplate().getMessage());
            messageTemplate.setSendSystem("APP");
            messageTemplate.setDateOfSend(newMessage.getMessageTemplate().getDateOfSend());
            messageTemplate.setType(newMessage.getMessageTemplate().getType());
            message.setStatus(newMessage.getStatus());

            session.merge(messageBox);
        });
    }

    @Override
    public void changeMessageStatus(String userId, String messageId, MessageStatusEnum status) {
        entityOperationManager.executeConsumer(session -> {
            MessageBox messageBox = queryService.getNamedQueryEntity(
                    session,
                    MessageBox.class,
                    "MessageBox.findByUserIdWithMessages",
                    parameterFactory.createStringParameter(USER_ID, messageId));
            Message message = getExistingMessage(messageBox, messageId);
            MessageStatus messageStatus = queryService.getNamedQueryEntity(
                    session,
                    MessageStatus.class,
                    "MessageStatus.findByValue",
                    parameterFactory.createStringParameter(VALUE, status.getValue()));
            message.setStatus(messageStatus);
            session.merge(message);
        });
    }

    @Override
    public void deleteMessage(String messageId) {
        entityOperationManager.executeConsumer(session -> {;
            Optional<MessageTemplate> optionalMessageTemplate = queryService.getNamedQueryOptionalEntity(
                    session,
                    MessageTemplate.class,
                    "MessageTemplate.findByMessageId",
                    parameterFactory.createStringParameter("messageUniqNumber", messageId));

            optionalMessageTemplate.ifPresent(session::remove);
        });
    }

    @Override
    public void deleteMessageByUserId(String userId, String messageId) {
        entityOperationManager.executeConsumer(session -> {
            MessageBox messageBox = queryService.getNamedQueryEntity(
                    session,
                    MessageBox.class,
                    "MessageBox.findByUserIdWithMessages",
                    parameterFactory.createStringParameter(USER_ID, userId));

            Message message = getExistingMessage(messageBox, messageId);
            messageBox.removeMessage(message);
            if (message.getBoxes().isEmpty()) {
                session.remove(message);
            }
//            session.merge(messageBox);
        });
    }

    @Override
    public void cleanUpMessagesByUserId(String userId) {
        entityOperationManager.executeConsumer(session -> {
            MessageBox messageBox = queryService.getNamedQueryEntity(
                    session,
                    MessageBox.class,
                    "MessageBox.findByUserIdWithMessages",
                    parameterFactory.createStringParameter(USER_ID, userId));

            List<Message> messagesToProcess = new ArrayList<>(messageBox.getMessages());
            List<Message> messagesToDelete = new ArrayList<>();

            for (Message message : messagesToProcess) {
                messageBox.removeMessage(message);
                if (message.getBoxes().isEmpty()) {
                    messagesToDelete.add(message);
                }
            }
            messagesToProcess.forEach(session::remove);
            session.merge(messageBox);
        });
    }

    @Override
    public List<ResponseMessageOverviewDto> getMessageOverviewBySenderEmail(String senderEmail) {
        List<Message> messages = fetchHandler.getNamedQueryEntityListClose(
                Message.class,
                "Message.findMessageBySender",
                parameterFactory.createStringParameter("sender", senderEmail));

        return messages.stream()
                .map(transformationFunctionService.getTransformationFunction(Message.class, ResponseMessageOverviewDto.class))
                .toList();
    }

    @Override
    public List<ResponseMessageOverviewDto> getMessageOverviewByReceiverEmail(String receiverEmail) {
        List<Message> messages = fetchHandler.getNamedQueryEntityListClose(
                Message.class,
                "Message.findMessageByReceiver",
                parameterFactory.createStringParameter("receiver", receiverEmail));

        return messages.stream()
                .map(transformationFunctionService.getTransformationFunction(Message.class, ResponseMessageOverviewDto.class))
                .toList();
    }

    @Override
    public List<ResponseMessageOverviewDto> getMessageOverviewListByUserId(String userId) {
        MessageBox messageBox = fetchHandler.getNamedQueryEntityClose(
                MessageBox.class,
                "MessageBox.findByUserIdWithMessages",
                parameterFactory.createStringParameter(USER_ID, userId)
        );
        return messageBox.getMessages().stream()
                .map(transformationFunctionService.getTransformationFunction(Message.class, ResponseMessageOverviewDto.class))
                .toList();
    }

    @Override
    public ResponseMessagePayloadDto getMessagePayloadDto(String userId, String messageId) {
        MessageBox messageBox = fetchHandler.getNamedQueryEntityClose(
                MessageBox.class,
                "MessageBox.findByUserId",
                parameterFactory.createStringParameter(USER_ID, userId));
        Message message = getExistingMessage(messageBox, messageId);
        ResponseMessagePayloadDto responseMessagePayloadDto =
                transformationFunctionService.getEntity(ResponseMessagePayloadDto.class, message);

        return responseMessagePayloadDto;
    }

    private Message getExistingMessage(MessageBox messageBox, String uniqMessageId) {
        return messageBox.getMessages().stream()
                .filter(message -> message.getMessageTemplate().getMessageUniqNumber().equals(uniqMessageId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Message not found"));
    }
}