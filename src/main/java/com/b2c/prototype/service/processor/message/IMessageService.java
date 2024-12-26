package com.b2c.prototype.service.processor.message;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.delete.MultipleFieldsSearchDtoDelete;
import com.b2c.prototype.modal.dto.request.MessageDto;
import com.b2c.prototype.modal.dto.response.ResponseMessageOverviewDto;
import com.b2c.prototype.modal.dto.response.ResponseMessagePayloadDto;
import com.b2c.prototype.modal.dto.update.MessageDtoUpdate;

import java.util.List;

public interface IMessageService {
    void saveUpdateMessage(MessageDtoUpdate messageDtoUpdate);
    void deleteMessage(MultipleFieldsSearchDtoDelete multipleFieldsSearchDtoDelete);
    void cleanUpMessagesByUserId(OneFieldEntityDto oneFieldEntityDto);

    List<ResponseMessageOverviewDto> getMessageOverviewBySenderEmail(OneFieldEntityDto oneFieldEntityDto);
    List<ResponseMessageOverviewDto> getMessageOverviewByReceiverEmail(OneFieldEntityDto oneFieldEntityDto);
    ResponseMessagePayloadDto getMessagePayloadDto(OneFieldEntityDto oneFieldEntityDto);
}
