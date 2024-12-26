package com.b2c.prototype.service.processor.userprofile.basic;

import com.b2c.prototype.dao.user.IContactInfoDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.ContactInfoArrayDtoSearchField;
import com.b2c.prototype.modal.dto.request.ContactInfoDto;
import com.b2c.prototype.modal.dto.request.ContactInfoDtoSearchField;
import com.b2c.prototype.modal.dto.request.ContactInfoSearchFieldOrderNumberDto;
import com.b2c.prototype.modal.entity.order.OrderItemData;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.UserProfile;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.query.IQueryService;
import com.b2c.prototype.service.processor.userprofile.IContactInfoService;
import com.b2c.prototype.service.supplier.ISupplierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ContactInfoService implements IContactInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactInfoService.class);

    private final IEntityOperationDao entityOperationDao;
    private final IQueryService queryService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;

    public ContactInfoService(IContactInfoDao contactInfoDao,
                              IQueryService queryService,
                              ITransformationFunctionService transformationFunctionService,
                              ISupplierService supplierService) {
        this.entityOperationDao = new EntityOperationDao(contactInfoDao);
        this.queryService = queryService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
    }

    @Override
    public void saveUpdateContactInfoByUserId(ContactInfoDtoSearchField contactInfoDtoSearchField) {
        entityOperationDao.executeConsumer(session -> {
            UserProfile userProfile = queryService.getEntity(
                    UserProfile.class,
                    supplierService.parameterStringSupplier("user_id", contactInfoDtoSearchField.getSearchField()));
            ContactInfo newContactInfo = transformationFunctionService
                    .getEntity(ContactInfo.class, contactInfoDtoSearchField.getNewEntity());
            ContactInfo contactInfo = userProfile.getContactInfo();
            if (contactInfo != null) {
                newContactInfo.setId(contactInfo.getId());
            }
            userProfile.setContactInfo(newContactInfo);
            session.merge(userProfile);
        });
    }

    @Override
    public void saveUpdateContactInfoByOrderId(ContactInfoArrayDtoSearchField contactInfoArrayDtoSearchField) {
        entityOperationDao.executeConsumer(session -> {
            OrderItemData orderItemData = queryService.getEntity(
                    OrderItemData.class,
                    supplierService.parameterStringSupplier("order_id", contactInfoArrayDtoSearchField.getSearchField()));

            List<ContactInfo> existingBenefits = orderItemData.getBeneficiaries();
            List<ContactInfo> newContactInfoList = Arrays.stream(contactInfoArrayDtoSearchField.getNewEntityArray())
                    .map(contactInfoDto ->
                            transformationFunctionService.getEntity(ContactInfo.class, contactInfoDto))
                    .toList();

            IntStream.range(0, Math.min(existingBenefits.size(), newContactInfoList.size()))
                    .forEach(i -> newContactInfoList.get(i).setId(existingBenefits.get(i).getId()));

            orderItemData.setBeneficiaries(
                    Stream.concat(newContactInfoList.stream(), existingBenefits.stream().skip(newContactInfoList.size()))
                            .limit(newContactInfoList.size())
                            .toList()
            );

            session.merge(orderItemData);
        });

    }

    @Override
    public void deleteContactInfoByUserId(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntity(
                supplierService.entityFieldSupplier(
                        UserProfile.class,
                        "user_id",
                        oneFieldEntityDto.getValue(),
                        transformationFunctionService.getTransformationFunction(UserProfile.class, ContactInfo.class))
        );
    }

    @Override
    public void deleteContactInfoByOrderId(ContactInfoSearchFieldOrderNumberDto contactInfoSearchFieldOrderNumberDto) {
        entityOperationDao.executeConsumer(session -> {
            OrderItemData orderItemData = queryService.getEntity(
                    OrderItemData.class,
                    supplierService.parameterStringSupplier("order_id", contactInfoSearchFieldOrderNumberDto.getValue()));
            ContactInfo contactInfo = orderItemData.getBeneficiaries()
                    .get(contactInfoSearchFieldOrderNumberDto.getOrderNumber());
            session.remove(contactInfo);
        });
    }

    @Override
    public List<ContactInfoDto> getContactInfoListByOrderId(OneFieldEntityDto oneFieldEntityDto) {
        return queryService.getSubEntityDtoList(
                OrderItemData.class,
                supplierService.parameterStringSupplier("order_id", oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(OrderItemData.class, ContactInfoDto.class));
    }

    @Override
    public ContactInfoDto getContactInfoByUserId(OneFieldEntityDto oneFieldEntityDto) {
        return queryService.getEntityDto(
                UserProfile.class,
                supplierService.parameterStringSupplier("user_id", oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(UserProfile.class, ContactInfoDto.class));
    }

}
