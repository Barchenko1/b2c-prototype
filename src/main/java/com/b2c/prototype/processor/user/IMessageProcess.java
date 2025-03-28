package com.b2c.prototype.processor.user;

import com.b2c.prototype.modal.dto.payload.user.MessageDto;
import com.b2c.prototype.modal.dto.payload.user.MessageTemplateDto;
import com.b2c.prototype.modal.dto.response.ResponseMessageOverviewDto;
import com.b2c.prototype.modal.dto.response.ResponseMessagePayloadDto;

import java.util.List;
import java.util.Map;

public interface IMessageProcess {
    void saveMessage(Map<String, String> requestParams, MessageTemplateDto messageTemplateDto);
    void updateMessage(Map<String, String> requestParams, MessageDto messageDto);
    void changeMessageStatus(Map<String, String> requestParams);
    void deleteMessage(Map<String, String> requestParams);
    void cleanUpMessagesByUserId(Map<String, String> requestParams);

    List<ResponseMessageOverviewDto> getMessageOverviewBySenderEmail(Map<String, String> requestParams);
    List<ResponseMessageOverviewDto> getMessageOverviewByReceiverEmail(Map<String, String> requestParams);
    List<ResponseMessageOverviewDto> getMessageOverviewListByUserId(Map<String, String> requestParams);
    ResponseMessagePayloadDto getMessagePayloadDto(Map<String, String> requestParams);
}
