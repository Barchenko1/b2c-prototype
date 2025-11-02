package com.b2c.prototype.manager.message.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.constant.MessageStatusEnum;
import com.b2c.prototype.modal.dto.payload.message.MessageDto;
import com.b2c.prototype.modal.dto.payload.message.ResponseMessageOverviewDto;
import com.b2c.prototype.modal.dto.payload.message.ResponseMessagePayloadDto;
import com.b2c.prototype.modal.entity.message.Message;
import com.b2c.prototype.modal.entity.message.MessageBox;
import com.b2c.prototype.modal.entity.message.MessageStatus;
import com.b2c.prototype.modal.entity.message.MessageTemplate;
import com.b2c.prototype.manager.message.IMessageManager;
import com.b2c.prototype.transform.userdetails.IUserDetailsTransformService;
import com.nimbusds.jose.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.USER_ID;
import static com.b2c.prototype.util.Constant.KEY;

@Service
public class MessageManager implements IMessageManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageManager.class);

    private final IGeneralEntityDao generalEntityDao;
    private final IUserDetailsTransformService userDetailsTransformService;

    public MessageManager(IGeneralEntityDao generalEntityDao,
                          IUserDetailsTransformService userDetailsTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.userDetailsTransformService = userDetailsTransformService;
    }

    @Override
    @Transactional
    public void saveMessage(MessageDto messageDto) {
//        List<MessageBox> messageBoxes = generalEntityDao.findEntityList(
//                "MessageBox.findByEmailListWithMessages",
//                Pair.of("emails", messageDto.getMessageTemplate().getReceivers()));
//        Message message = userDetailsTransformService.mapMessageDtoToMessage(messageDto);
//        messageBoxes.forEach(message::addMessageBox);
//        generalEntityDao.mergeEntity(message);
    }

    @Override
    public void updateMessage(String messageId, MessageDto messageDto) {
        MessageBox messageBox = generalEntityDao.findEntity(
                "MessageBox.findByUserIdWithMessages",
                Pair.of(USER_ID, messageId));
        generalEntityDao.findEntity("MessageBox.findByUserIdWithMessages",
                Pair.of(USER_ID, messageId));
        Message newMessage = userDetailsTransformService.mapMessageDtoToMessage(messageDto);
        Message message = getExistingMessage(messageBox, messageId);
        MessageTemplate messageTemplate = message.getMessageTemplate();
        messageTemplate.setTitle(newMessage.getMessageTemplate().getTitle());
        messageTemplate.setMessage(newMessage.getMessageTemplate().getMessage());
        message.setStatus(newMessage.getStatus());

        generalEntityDao.mergeEntity(messageBox);
    }

    @Override
    public void changeMessageStatus(String userId, String messageId, MessageStatusEnum status) {
        MessageBox messageBox = generalEntityDao.findEntity(
                "MessageBox.findByUserIdWithMessages",
                Pair.of(USER_ID, messageId));
        Message message = getExistingMessage(messageBox, messageId);
        MessageStatus messageStatus = generalEntityDao.findEntity(
                "MessageStatus.findByKey",
                Pair.of(KEY, status.getValue()));
        message.setStatus(messageStatus);
        generalEntityDao.mergeEntity(message);
    }

    @Override
    @Transactional
    public void deleteMessage(String messageId) {
        Optional<MessageTemplate> optionalMessageTemplate = generalEntityDao.findOptionEntity(
                "MessageTemplate.findByMessageId",
                Pair.of("messageUniqNumber", messageId));

        optionalMessageTemplate.ifPresent(generalEntityDao::removeEntity);
    }

    @Override
    @Transactional
    public void deleteMessageByUserId(String userId, String messageId) {
        MessageBox messageBox = generalEntityDao.findEntity(
                "MessageBox.findByUserIdWithMessages",
                Pair.of(USER_ID, userId));

        Message message = getExistingMessage(messageBox, messageId);
        messageBox.removeMessage(message);
        if (message.getBoxes().isEmpty()) {
            generalEntityDao.removeEntity(message);
        }
//            session.merge(messageBox);
    }

    @Override
    public void cleanUpMessagesByUserId(String userId) {
        MessageBox messageBox = generalEntityDao.findEntity(
                "MessageBox.findByUserIdWithMessages",
                Pair.of(USER_ID, userId));

        List<Message> messagesToProcess = new ArrayList<>(messageBox.getMessages());
        List<Message> messagesToDelete = new ArrayList<>();

        for (Message message : messagesToProcess) {
            messageBox.removeMessage(message);
            if (message.getBoxes().isEmpty()) {
                messagesToDelete.add(message);
            }
        }
        messagesToProcess.forEach(generalEntityDao::removeEntity);
        generalEntityDao.mergeEntity(messageBox);
    }

    @Override
    public List<ResponseMessageOverviewDto> getMessageOverviewBySenderEmail(String senderEmail) {
        List<Message> messages = generalEntityDao.findEntityList(
                "Message.findMessageBySender",
                Pair.of("sender", senderEmail));

        return messages.stream()
                .map(userDetailsTransformService::mapMessageToResponseMessageOverviewDto)
                .toList();
    }

    @Override
    public List<ResponseMessageOverviewDto> getMessageOverviewByReceiverEmail(String receiverEmail) {
        List<Message> messages = generalEntityDao.findEntityList(
                "Message.findMessageByReceiver",
                Pair.of("receiver", receiverEmail));

        return messages.stream()
                .map(userDetailsTransformService::mapMessageToResponseMessageOverviewDto)
                .toList();
    }

    @Override
    public List<ResponseMessageOverviewDto> getMessageOverviewListByUserId(String userId) {
        MessageBox messageBox = generalEntityDao.findEntity(
                "MessageBox.findByUserIdWithMessages",
                Pair.of(USER_ID, userId));
        return messageBox.getMessages().stream()
                .map(userDetailsTransformService::mapMessageToResponseMessageOverviewDto)
                .toList();
    }

    @Override
    public ResponseMessagePayloadDto getMessagePayloadDto(String userId, String messageId) {
        MessageBox messageBox = generalEntityDao.findEntity(
                "MessageBox.findByUserId",
                Pair.of(USER_ID, userId));
        Message message = getExistingMessage(messageBox, messageId);
        ResponseMessagePayloadDto responseMessagePayloadDto =
                userDetailsTransformService.mapResponseMessagePayloadDtoToMessage(message);

        return responseMessagePayloadDto;
    }

    private Message getExistingMessage(MessageBox messageBox, String uniqMessageId) {
        return messageBox.getMessages().stream()
//                .filter(message -> message.getMessageUniqId().equals(uniqMessageId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Message not found"));
    }
}