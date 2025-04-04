package com.b2c.prototype.processor.user;

import com.b2c.prototype.manager.message.IMessageManager;
import com.b2c.prototype.modal.constant.MessageStatusEnum;
import com.b2c.prototype.modal.dto.payload.message.MessageDto;
import com.b2c.prototype.modal.dto.payload.message.MessageTemplateDto;
import com.b2c.prototype.modal.dto.payload.message.ResponseMessageOverviewDto;
import com.b2c.prototype.modal.dto.payload.message.ResponseMessagePayloadDto;

import java.util.List;
import java.util.Map;

public class MessageProcess implements IMessageProcess {

    private final IMessageManager messageManager;

    public MessageProcess(IMessageManager messageManager) {
        this.messageManager = messageManager;
    }

    @Override
    public void saveMessage(Map<String, String> requestParams, MessageTemplateDto messageTemplateDto) {
        MessageDto messageDto = MessageDto.builder()
                .messageTemplate(messageTemplateDto)
                .status("unread")
                .build();
        messageManager.saveMessage(messageDto);
    }

    @Override
    public void updateMessage(Map<String, String> requestParams, MessageDto messageDto) {
        String messageId = requestParams.get("messageId");
        messageManager.updateMessage(messageId, messageDto);
    }

    @Override
    public void changeMessageStatus(Map<String, String> requestParams) {
        String userId = requestParams.get("userId");
        String messageId = requestParams.get("messageId");
        String status = requestParams.get("status");
        MessageStatusEnum statusEnum = MessageStatusEnum.valueOf(status.toUpperCase());
        messageManager.changeMessageStatus(userId, messageId, statusEnum);
    }

    @Override
    public void deleteMessage(Map<String, String> requestParams) {
        String userId = requestParams.get("userId");
        String messageId = requestParams.get("messageId");
        if (userId != null && messageId != null) {
            messageManager.deleteMessageByUserId(userId, messageId);
        }
        if (userId == null && messageId != null) {
            messageManager.deleteMessage(messageId);
        }
    }

    @Override
    public void cleanUpMessagesByUserId(Map<String, String> requestParams) {
        String userId = requestParams.get("userId");
        messageManager.cleanUpMessagesByUserId(userId);
    }

    @Override
    public List<ResponseMessageOverviewDto> getMessageOverviewBySenderEmail(Map<String, String> requestParams) {
        String userId = requestParams.get("userId");
        String email = requestParams.get("email");
        String sender = requestParams.get("sender");
        return messageManager.getMessageOverviewBySenderEmail(sender);
    }

    @Override
    public List<ResponseMessageOverviewDto> getMessageOverviewByReceiverEmail(Map<String, String> requestParams) {
        String email = requestParams.get("email");
        String receiver = requestParams.get("receiver");
        return messageManager.getMessageOverviewByReceiverEmail(receiver);
    }

    @Override
    public List<ResponseMessageOverviewDto> getMessageOverviewListByUserId(Map<String, String> requestParams) {
        String userId = requestParams.get("userId");
        return messageManager.getMessageOverviewListByUserId(userId);
    }

    @Override
    public ResponseMessagePayloadDto getMessagePayloadDto(Map<String, String> requestParams) {
        String userId = requestParams.get("userId");
        String messageId = requestParams.get("messageId");
        return messageManager.getMessagePayloadDto(userId, messageId);
    }
}
