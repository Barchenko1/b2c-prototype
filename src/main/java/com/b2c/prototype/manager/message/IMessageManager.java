package com.b2c.prototype.manager.message;

import com.b2c.prototype.modal.dto.payload.MessageDto;
import com.b2c.prototype.modal.dto.response.ResponseMessageOverviewDto;
import com.b2c.prototype.modal.dto.response.ResponseMessagePayloadDto;

import java.util.List;

public interface IMessageManager {
    void saveMessage(String userId, MessageDto messageDto);
    void updateMessage(String userId, String messageId, MessageDto messageDto);
    void deleteMessage(String userId, String messageId);
    void cleanUpMessagesByUserId(String userId);

    List<ResponseMessageOverviewDto> getMessageOverviewBySenderEmail(String senderEmail);
    List<ResponseMessageOverviewDto> getMessageOverviewByReceiverEmail(String receiverEmail);
    ResponseMessagePayloadDto getMessagePayloadDto(String userId, String messageId);
}
