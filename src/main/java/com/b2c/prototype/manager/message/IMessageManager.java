package com.b2c.prototype.manager.message;

import com.b2c.prototype.modal.constant.MessageStatusEnum;
import com.b2c.prototype.modal.dto.payload.message.MessageDto;
import com.b2c.prototype.modal.dto.payload.message.ResponseMessageOverviewDto;
import com.b2c.prototype.modal.dto.payload.message.ResponseMessagePayloadDto;

import java.util.List;

public interface IMessageManager {
    void saveMessage(MessageDto messageDto);
    void updateMessage(String messageId, MessageDto messageDto);
    void changeMessageStatus(String userId, String messageId, MessageStatusEnum status);
    void deleteMessage(String messageId);
    void deleteMessageByUserId(String userId, String messageId);
    void cleanUpMessagesByUserId(String userId);

    List<ResponseMessageOverviewDto> getMessageOverviewBySenderEmail(String senderEmail);
    List<ResponseMessageOverviewDto> getMessageOverviewByReceiverEmail(String receiverEmail);
    List<ResponseMessageOverviewDto> getMessageOverviewListByUserId(String userId);
    ResponseMessagePayloadDto getMessagePayloadDto(String userId, String messageId);
}
