package com.b2c.prototype.service.processor.message.base;

import com.b2c.prototype.dao.cashed.IEntityCachedMap;
import com.b2c.prototype.dao.message.IMessageDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.MessageDto;
import com.b2c.prototype.modal.dto.response.ResponseMessageOverviewDto;
import com.b2c.prototype.modal.dto.response.ResponseMessagePayloadDto;
import com.b2c.prototype.modal.dto.update.MessageDtoUpdate;
import com.b2c.prototype.modal.entity.message.Message;
import com.b2c.prototype.modal.entity.message.MessageStatus;
import com.b2c.prototype.modal.entity.message.MessageType;
import com.b2c.prototype.service.processor.message.IMessageService;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.b2c.prototype.util.UniqueIdUtil.getUUID;

public class MessageService implements IMessageService {

    private final IParameterFactory parameterFactory;
    private final IEntityOperationDao entityOperationDao;
    private final IMessageDao messageDao;
    private final IEntityCachedMap entityCachedMap;

    public MessageService(IParameterFactory parameterFactory,
                          IMessageDao messageDao,
                          IEntityCachedMap entityCachedMap) {
        this.parameterFactory = parameterFactory;
        this.entityOperationDao = new EntityOperationDao(messageDao);
        this.messageDao = messageDao;
        this.entityCachedMap = entityCachedMap;
    }

    @Override
    public void saveMessage(MessageDto messageDto) {
        entityOperationDao.saveEntity(messageSupplier(messageDto));
    }

    @Override
    public void updateMessage(MessageDtoUpdate messageDtoUpdate) {
        entityOperationDao.updateEntity(session -> {
            Message existMessage = messageDao.getEntity(
                    messageUniqNumberParameterSupplier(messageDtoUpdate.getSearchField()).get());
//            Message existMessage = entityOperationDao.getEntity(
//                    Message.class,
//                    messageUniqNumberParameterSupplier(messageDtoUpdate.getSearchField())
//            );
            Message newMessage = mapToEntityFunction().apply(messageDtoUpdate.getNewEntityDto());
            newMessage.setId(existMessage.getId());
            session.merge(newMessage);
        });
    }

    @Override
    public void deleteMessageBySenderEmail(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntityByParameter(senderParameterSupplier(oneFieldEntityDto.getValue()));
    }

    @Override
    public void deleteMessageByReceiverEmail(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntityByParameter(receiverParameterSupplier(oneFieldEntityDto.getValue()));
    }

    @Override
    public List<ResponseMessageOverviewDto> getMessageOverviewBySenderEmail(OneFieldEntityDto oneFieldEntityDto) {
        return entityOperationDao.getSubEntityDtoList(
                () -> parameterFactory.createParameterArray(senderParameterSupplier(oneFieldEntityDto.getValue()).get()),
                mapToResponseMessageOverviewDtoFunction());
    }

    @Override
    public List<ResponseMessageOverviewDto> getMessageOverviewByReceiverEmail(OneFieldEntityDto oneFieldEntityDto) {
        return entityOperationDao.getSubEntityDtoList(
                () -> parameterFactory.createParameterArray(receiverParameterSupplier(oneFieldEntityDto.getValue()).get()),
                mapToResponseMessageOverviewDtoFunction());
    }

    @Override
    public ResponseMessagePayloadDto getMessagePayloadDto(OneFieldEntityDto oneFieldEntityDto) {
        return entityOperationDao.getEntityDto(
                messageUniqNumberParameterSupplier(oneFieldEntityDto.getValue()),
                mapToResponseMessagePayloadDtoFunction()
        );
    }

    @Override
    public ResponseMessageOverviewDto getMessageOverviewDto(OneFieldEntityDto oneFieldEntityDto) {
        return entityOperationDao.getEntityDto(
                messageUniqNumberParameterSupplier(oneFieldEntityDto.getValue()),
                mapToResponseMessageOverviewDtoFunction());
    }

    private Function<MessageDto, Message> mapToEntityFunction() {
        MessageStatus messageStatus = entityCachedMap.getEntity(
                MessageStatus.class,
                "value",
                "value");
        MessageType messageType = entityCachedMap.getEntity(
                MessageType.class,
                "value",
                "value");
        return (messageDto) -> Message.builder()
                .sender(messageDto.getSender())
                .title(messageDto.getTitle())
                .message(messageDto.getMessage())
                .messageUniqNumber(getUUID())
                .dateOfSend(System.currentTimeMillis())
                .receivers(messageDto.getReceivers())
                .subscribe("subscribe")
                .type(messageType)
                .status(messageStatus)
                .build();
    }

    private Function<Message, ResponseMessageOverviewDto> mapToResponseMessageOverviewDtoFunction() {
        return (message) -> ResponseMessageOverviewDto.builder()
                .sender(message.getSender())
                .title(message.getTitle())
                .dateOfSend(message.getDateOfSend())
                .receivers(message.getReceivers())
                .subscribe(message.getSubscribe())
                .type(message.getType().getValue())
                .status(message.getStatus().getValue())
                .build();
    }

    private Function<Message, ResponseMessagePayloadDto> mapToResponseMessagePayloadDtoFunction() {
        return (message) -> ResponseMessagePayloadDto.builder()
                .message(message.getMessage())
                .build();
    }

    private Supplier<Message> messageSupplier(MessageDto messageDto) {
        return () -> mapToEntityFunction().apply(messageDto);
    }

    private Supplier<Parameter> senderParameterSupplier(String value) {
        return () -> parameterFactory.createStringParameter(
                "sender", value
        );
    }

    private Supplier<Parameter> receiverParameterSupplier(String value) {
        return () -> parameterFactory.createStringParameter(
                "receiver", value
        );
    }

    private Supplier<Parameter> messageUniqNumberParameterSupplier(String value) {
        return () -> parameterFactory.createStringParameter(
                "messageUniqNumber", value
        );
    }

    private Supplier<Parameter[]> parameterArraySupplier(String value) {
        return () -> parameterFactory.createParameterArray(
                senderParameterSupplier(value).get()
        );
    }
}
