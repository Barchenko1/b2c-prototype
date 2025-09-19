package com.b2c.prototype.transform.message;

import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.dto.payload.message.MessageDto;
import com.b2c.prototype.modal.dto.payload.message.ResponseMessageOverviewDto;
import com.b2c.prototype.modal.dto.payload.message.ResponseMessagePayloadDto;
import com.b2c.prototype.modal.entity.message.Message;
import com.b2c.prototype.modal.entity.message.MessageStatus;
import com.b2c.prototype.modal.entity.message.MessageType;
import org.springframework.stereotype.Service;

@Service
public class MessageTransformService implements IMessageTransformService {

    @Override
    public MessageType mapConstantPayloadDtoToMessageType(ConstantPayloadDto constantPayloadDto) {
        return null;
    }

    @Override
    public ConstantPayloadDto mapMessageTypeToConstantPayloadDto(MessageType messageType) {
        return null;
    }

    @Override
    public MessageStatus mapConstantPayloadDtoToMessageStatus(ConstantPayloadDto constantPayloadDto) {
        return null;
    }

    @Override
    public ConstantPayloadDto mapMessageStatusToConstantPayloadDto(MessageStatus messageStatus) {
        return null;
    }

    @Override
    public Message mapMessageDtoToMessage(MessageDto messageDto) {
        return null;
    }

    @Override
    public ResponseMessageOverviewDto mapMessageToResponseMessageOverviewDto(Message message) {
        return null;
    }

    @Override
    public ResponseMessagePayloadDto mapResponseMessagePayloadDtoToMessage(Message message) {
        return null;
    }


}
