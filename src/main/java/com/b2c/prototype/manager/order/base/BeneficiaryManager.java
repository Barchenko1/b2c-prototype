package com.b2c.prototype.manager.order.base;

import com.b2c.prototype.dao.order.IBeneficiaryDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.searchfield.BeneficiaryArrayDtoSearchField;
import com.b2c.prototype.modal.dto.payload.BeneficiaryDto;
import com.b2c.prototype.modal.dto.searchfield.BeneficiarySearchFieldOrderNumberDto;
import com.b2c.prototype.modal.entity.order.OrderArticularItem;
import com.b2c.prototype.modal.entity.order.Beneficiary;
import com.b2c.prototype.service.common.EntityOperationManager;
import com.b2c.prototype.service.common.IEntityOperationManager;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.order.IBeneficiaryManager;
import com.b2c.prototype.service.query.ISearchService;
import com.b2c.prototype.service.supplier.ISupplierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.b2c.prototype.util.Constant.ORDER_ID;

public class BeneficiaryManager implements IBeneficiaryManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeneficiaryManager.class);

    private final IEntityOperationManager entityOperationDao;
    private final ISearchService searchService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;

    public BeneficiaryManager(IBeneficiaryDao beneficiaryDao,
                              ISearchService searchService,
                              ITransformationFunctionService transformationFunctionService,
                              ISupplierService supplierService) {
        this.entityOperationDao = new EntityOperationManager(beneficiaryDao);
        this.searchService = searchService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
    }

    @Override
    public void saveUpdateContactInfoByOrderId(BeneficiaryArrayDtoSearchField contactInfoArrayDtoSearchField) {
        entityOperationDao.executeConsumer(session -> {
            OrderArticularItem orderItemDataOption = searchService.getEntity(
                    OrderArticularItem.class,
                    supplierService.parameterStringSupplier(ORDER_ID, contactInfoArrayDtoSearchField.getSearchField()));

            List<Beneficiary> existingBenefits = orderItemDataOption.getBeneficiaries();
            List<Beneficiary> newBeneficiaryList = Arrays.stream(contactInfoArrayDtoSearchField.getNewEntityArray())
                    .map(contactInfoDto ->
                            transformationFunctionService.getEntity(Beneficiary.class, contactInfoDto))
                    .toList();

            IntStream.range(0, Math.min(existingBenefits.size(), newBeneficiaryList.size()))
                    .forEach(i -> newBeneficiaryList.get(i).setId(existingBenefits.get(i).getId()));

            orderItemDataOption.setBeneficiaries(
                    Stream.concat(newBeneficiaryList.stream(), existingBenefits.stream().skip(newBeneficiaryList.size()))
                            .limit(newBeneficiaryList.size())
                            .toList()
            );

            session.merge(orderItemDataOption);
        });
    }

    @Override
    public void deleteContactInfoByOrderId(BeneficiarySearchFieldOrderNumberDto beneficiarySearchFieldOrderNumberDto) {
        entityOperationDao.executeConsumer(session -> {
            OrderArticularItem orderItemDataOption = searchService.getEntity(
                    OrderArticularItem.class,
                    supplierService.parameterStringSupplier(ORDER_ID, beneficiarySearchFieldOrderNumberDto.getValue()));
            Beneficiary beneficiary = orderItemDataOption.getBeneficiaries()
                    .get(beneficiarySearchFieldOrderNumberDto.getOrderNumber());
            session.remove(beneficiary);
        });
    }

    @Override
    public List<BeneficiaryDto> getContactInfoListByOrderId(OneFieldEntityDto oneFieldEntityDto) {
        return searchService.getSubEntityDtoList(
                OrderArticularItem.class,
                supplierService.parameterStringSupplier(ORDER_ID, oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(OrderArticularItem.class, BeneficiaryDto.class));
    }
}
