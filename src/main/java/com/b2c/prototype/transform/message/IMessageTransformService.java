package com.b2c.prototype.transform.message;

import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.dto.payload.message.MessageDto;
import com.b2c.prototype.modal.dto.payload.message.ResponseMessageOverviewDto;
import com.b2c.prototype.modal.dto.payload.message.ResponseMessagePayloadDto;
import com.b2c.prototype.modal.entity.message.Message;
import com.b2c.prototype.modal.entity.message.MessageStatus;
import com.b2c.prototype.modal.entity.message.MessageType;

public interface IMessageTransformService {
    MessageType mapConstantPayloadDtoToMessageType(ConstantPayloadDto constantPayloadDto);
    ConstantPayloadDto mapMessageTypeToConstantPayloadDto(MessageType messageType);
    MessageStatus mapConstantPayloadDtoToMessageStatus(ConstantPayloadDto constantPayloadDto);
    ConstantPayloadDto mapMessageStatusToConstantPayloadDto(MessageStatus messageStatus);

    Message mapMessageDtoToMessage(MessageDto messageDto);
    ResponseMessageOverviewDto mapMessageToResponseMessageOverviewDto(Message message);
    ResponseMessagePayloadDto mapResponseMessagePayloadDtoToMessage(Message message);
}
