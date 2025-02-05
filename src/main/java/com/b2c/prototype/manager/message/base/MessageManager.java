package com.b2c.prototype.manager.message.base;

import com.b2c.prototype.dao.message.IMessageDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.delete.MultipleFieldsSearchDtoDelete;
import com.b2c.prototype.modal.dto.response.ResponseMessageOverviewDto;
import com.b2c.prototype.modal.dto.response.ResponseMessagePayloadDto;
import com.b2c.prototype.modal.dto.update.MessageDtoUpdate;
import com.b2c.prototype.modal.entity.message.Message;
import com.b2c.prototype.modal.entity.message.MessageBox;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.message.IMessageManager;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.b2c.prototype.service.query.IQueryService;
import com.b2c.prototype.service.supplier.ISupplierService;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.b2c.prototype.util.Constant.USER_ID;
import static com.b2c.prototype.util.Query.SELECT_MESSAGEBOX_BY_USER_ID;

public class MessageManager implements IMessageManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageManager.class);

    private final IEntityOperationDao entityOperationDao;
    private final IQueryService queryService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;

    public MessageManager(IMessageDao messageDao,
                          IQueryService queryService,
                          ITransformationFunctionService transformationFunctionService,
                          ISupplierService supplierService) {
        this.entityOperationDao = new EntityOperationDao(messageDao);
        this.queryService = queryService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
    }

    @Override
    public void saveUpdateMessage(MessageDtoUpdate messageDtoUpdate) {
        entityOperationDao.executeConsumer(session -> {
            NativeQuery<MessageBox> query = session.createNativeQuery(SELECT_MESSAGEBOX_BY_USER_ID, MessageBox.class);
            MessageBox messageBox = queryService.getQueryEntity(
                    query,
                    supplierService.parameterStringSupplier(USER_ID, messageDtoUpdate.getMainSearchField()));
            Message existingMessage = getExistingMessage(messageBox, messageDtoUpdate.getInnerSearchField());
            Message newMessage = transformationFunctionService
                    .getEntity(Message.class, messageDtoUpdate.getNewEntity());
            if (existingMessage != null) {
                newMessage.setId(existingMessage.getId());
            }
            messageBox.addMessage(newMessage);
            session.merge(messageBox);
        });
    }

    @Override
    public void deleteMessage(MultipleFieldsSearchDtoDelete multipleFieldsSearchDtoDelete) {
        entityOperationDao.executeConsumer(session -> {
            NativeQuery<MessageBox> query = session.createNativeQuery(SELECT_MESSAGEBOX_BY_USER_ID, MessageBox.class);
            MessageBox messageBox = queryService.getQueryEntity(
                    query,
                    supplierService.parameterStringSupplier(USER_ID, multipleFieldsSearchDtoDelete.getMainSearchField()));
            Message existingMessage = getExistingMessage(messageBox, multipleFieldsSearchDtoDelete.getInnerSearchField());
            messageBox.removeMessage(existingMessage);
            session.merge(messageBox);
        });
    }

    @Override
    public void cleanUpMessagesByUserId(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.executeConsumer(session -> {
            NativeQuery<MessageBox> query = session.createNativeQuery(SELECT_MESSAGEBOX_BY_USER_ID, MessageBox.class);
            MessageBox messageBox = queryService.getQueryEntity(
                    query,
                    supplierService.parameterStringSupplier(USER_ID, oneFieldEntityDto.getValue()));
            messageBox.getMessages().forEach(message -> {
                messageBox.removeMessage(message);
                session.remove(message);
                session.merge(messageBox);
            });
        });
    }

    @Override
    public List<ResponseMessageOverviewDto> getMessageOverviewBySenderEmail(OneFieldEntityDto oneFieldEntityDto) {
        return entityOperationDao.getSubEntityGraphDtoList("",
                supplierService.parameterStringSupplier("sender", oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(Message.class, ResponseMessageOverviewDto.class));
    }

    @Override
    public List<ResponseMessageOverviewDto> getMessageOverviewByReceiverEmail(OneFieldEntityDto oneFieldEntityDto) {
        return entityOperationDao.getSubEntityGraphDtoList("",
                supplierService.parameterStringSupplier("receiver", oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(Message.class, ResponseMessageOverviewDto.class));
    }

    @Override
    public ResponseMessagePayloadDto getMessagePayloadDto(OneFieldEntityDto oneFieldEntityDto) {
        return entityOperationDao.getEntityGraphDto("",
                supplierService.parameterStringSupplier("messageUniqNumber", oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(Message.class, ResponseMessagePayloadDto.class)
        );
    }

    private Message getExistingMessage(MessageBox messageBox, String uniqMessageName) {
        return messageBox.getMessages().stream()
                .filter(message -> message.getMessageUniqNumber().equals(uniqMessageName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Message not found"));
    }
}