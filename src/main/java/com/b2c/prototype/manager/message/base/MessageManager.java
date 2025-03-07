package com.b2c.prototype.manager.message.base;

import com.b2c.prototype.dao.message.IMessageDao;
import com.b2c.prototype.modal.dto.payload.MessageDto;
import com.b2c.prototype.modal.dto.response.ResponseMessageOverviewDto;
import com.b2c.prototype.modal.dto.response.ResponseMessagePayloadDto;
import com.b2c.prototype.modal.entity.message.Message;
import com.b2c.prototype.modal.entity.message.MessageBox;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.message.IMessageManager;
import com.b2c.prototype.service.query.ISearchService;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.manager.common.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.b2c.prototype.util.Constant.USER_ID;
import static com.b2c.prototype.util.Query.SELECT_MESSAGEBOX_BY_USER_ID;

public class MessageManager implements IMessageManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageManager.class);

    private final IEntityOperationManager entityOperationDao;
    private final ISearchService searchService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;
    private final IParameterFactory parameterFactory;

    public MessageManager(IMessageDao messageDao,
                          ISearchService searchService,
                          ITransformationFunctionService transformationFunctionService,
                          ISupplierService supplierService,
                          IParameterFactory parameterFactory) {
        this.entityOperationDao = new EntityOperationManager(messageDao);
        this.searchService = searchService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveMessage(String userId, MessageDto messageDto) {

    }

    @Override
    public void updateMessage(String userId, String messageId, MessageDto messageDto) {
        entityOperationDao.executeConsumer(session -> {
            NativeQuery<MessageBox> query = session.createNativeQuery(SELECT_MESSAGEBOX_BY_USER_ID, MessageBox.class);
            MessageBox messageBox = searchService.getQueryEntity(
                    query,
                    supplierService.parameterStringSupplier(USER_ID, userId));
            Message existingMessage = getExistingMessage(messageBox, messageId);
            Message newMessage = transformationFunctionService
                    .getEntity(Message.class, messageDto);
            if (existingMessage != null) {
                newMessage.setId(existingMessage.getId());
            }
            messageBox.addMessage(newMessage);
            session.merge(messageBox);
        });
    }

    @Override
    public void deleteMessage(String userId, String messageId) {
        entityOperationDao.executeConsumer(session -> {
            NativeQuery<MessageBox> query = session.createNativeQuery(SELECT_MESSAGEBOX_BY_USER_ID, MessageBox.class);
            MessageBox messageBox = searchService.getQueryEntity(
                    query,
                    supplierService.parameterStringSupplier(USER_ID, userId));
            Message existingMessage = getExistingMessage(messageBox, messageId);
            messageBox.removeMessage(existingMessage);
            session.merge(messageBox);
        });
    }

    @Override
    public void cleanUpMessagesByUserId(String userId) {
        entityOperationDao.executeConsumer(session -> {
            NativeQuery<MessageBox> query = session.createNativeQuery(SELECT_MESSAGEBOX_BY_USER_ID, MessageBox.class);
            MessageBox messageBox = searchService.getQueryEntity(
                    query,
                    supplierService.parameterStringSupplier(USER_ID, userId));
            messageBox.getMessages().forEach(message -> {
                messageBox.removeMessage(message);
                session.remove(message);
                session.merge(messageBox);
            });
        });
    }

    @Override
    public List<ResponseMessageOverviewDto> getMessageOverviewBySenderEmail(String senderEmail) {
        return entityOperationDao.getSubGraphEntityDtoList(
                "",
                parameterFactory.createStringParameter("sender", senderEmail),
                transformationFunctionService.getTransformationFunction(Message.class, ResponseMessageOverviewDto.class));
    }

    @Override
    public List<ResponseMessageOverviewDto> getMessageOverviewByReceiverEmail(String receiverEmail) {
        return entityOperationDao.getSubGraphEntityDtoList(
                "",
                parameterFactory.createStringParameter("receiver", receiverEmail),
                transformationFunctionService.getTransformationFunction(Message.class, ResponseMessageOverviewDto.class));
    }

    @Override
    public ResponseMessagePayloadDto getMessagePayloadDto(String userId, String messageId) {
        return entityOperationDao.getGraphEntityDto(
                "",
                parameterFactory.createStringParameter("messageUniqNumber", messageId),
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