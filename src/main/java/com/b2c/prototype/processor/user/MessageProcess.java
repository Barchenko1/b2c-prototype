package com.b2c.prototype.processor.user;

import com.b2c.prototype.manager.message.IMessageManager;
import com.b2c.prototype.modal.dto.payload.MessageDto;
import com.b2c.prototype.modal.dto.response.ResponseMessageOverviewDto;
import com.b2c.prototype.modal.dto.response.ResponseMessagePayloadDto;

import java.util.List;
import java.util.Map;

public class MessageProcess implements IMessageProcess {

    private final IMessageManager messageManager;

    public MessageProcess(IMessageManager messageManager) {
        this.messageManager = messageManager;
    }

    @Override
    public void saveMessage(Map<String, String> requestParams, MessageDto messageDto) {
        String userId = requestParams.get("userId");
        messageManager.saveMessage(userId, messageDto);
    }

    @Override
    public void updateMessage(Map<String, String> requestParams, MessageDto messageDto) {
        String userId = requestParams.get("userId");
        String messageId = requestParams.get("messageId");
        messageManager.updateMessage(userId, messageId, messageDto);
    }

    @Override
    public void deleteMessage(Map<String, String> requestParams) {
        String userId = requestParams.get("userId");
        String messageId = requestParams.get("messageId");
        messageManager.deleteMessage(userId, messageId);
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
        return messageManager.getMessageOverviewBySenderEmail(email);
    }

    @Override
    public List<ResponseMessageOverviewDto> getMessageOverviewByReceiverEmail(Map<String, String> requestParams) {
        String email = requestParams.get("email");
        return messageManager.getMessageOverviewByReceiverEmail(email);
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
