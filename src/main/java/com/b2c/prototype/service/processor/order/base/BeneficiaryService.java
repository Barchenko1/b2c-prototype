package com.b2c.prototype.service.processor.order.base;

import com.b2c.prototype.dao.order.IBeneficiaryDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.searchfield.BeneficiaryArrayDtoSearchField;
import com.b2c.prototype.modal.dto.payload.BeneficiaryDto;
import com.b2c.prototype.modal.dto.searchfield.BeneficiarySearchFieldOrderNumberDto;
import com.b2c.prototype.modal.entity.order.OrderItemData;
import com.b2c.prototype.modal.entity.order.Beneficiary;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.order.IBeneficiaryService;
import com.b2c.prototype.service.processor.query.IQueryService;
import com.b2c.prototype.service.supplier.ISupplierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.b2c.prototype.util.Constant.ORDER_ID;

public class BeneficiaryService implements IBeneficiaryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeneficiaryService.class);

    private final IEntityOperationDao entityOperationDao;
    private final IQueryService queryService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;

    public BeneficiaryService(IBeneficiaryDao beneficiaryDao,
                              IQueryService queryService,
                              ITransformationFunctionService transformationFunctionService,
                              ISupplierService supplierService) {
        this.entityOperationDao = new EntityOperationDao(beneficiaryDao);
        this.queryService = queryService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
    }

    @Override
    public void saveUpdateContactInfoByOrderId(BeneficiaryArrayDtoSearchField contactInfoArrayDtoSearchField) {
        entityOperationDao.executeConsumer(session -> {
            OrderItemData orderItemData = queryService.getEntity(
                    OrderItemData.class,
                    supplierService.parameterStringSupplier(ORDER_ID, contactInfoArrayDtoSearchField.getSearchField()));

            List<Beneficiary> existingBenefits = orderItemData.getBeneficiaries();
            List<Beneficiary> newBeneficiaryList = Arrays.stream(contactInfoArrayDtoSearchField.getNewEntityArray())
                    .map(contactInfoDto ->
                            transformationFunctionService.getEntity(Beneficiary.class, contactInfoDto))
                    .toList();

            IntStream.range(0, Math.min(existingBenefits.size(), newBeneficiaryList.size()))
                    .forEach(i -> newBeneficiaryList.get(i).setId(existingBenefits.get(i).getId()));

            orderItemData.setBeneficiaries(
                    Stream.concat(newBeneficiaryList.stream(), existingBenefits.stream().skip(newBeneficiaryList.size()))
                            .limit(newBeneficiaryList.size())
                            .toList()
            );

            session.merge(orderItemData);
        });
    }

    @Override
    public void deleteContactInfoByOrderId(BeneficiarySearchFieldOrderNumberDto beneficiarySearchFieldOrderNumberDto) {
        entityOperationDao.executeConsumer(session -> {
            OrderItemData orderItemData = queryService.getEntity(
                    OrderItemData.class,
                    supplierService.parameterStringSupplier(ORDER_ID, beneficiarySearchFieldOrderNumberDto.getValue()));
            Beneficiary beneficiary = orderItemData.getBeneficiaries()
                    .get(beneficiarySearchFieldOrderNumberDto.getOrderNumber());
            session.remove(beneficiary);
        });
    }

    @Override
    public List<BeneficiaryDto> getContactInfoListByOrderId(OneFieldEntityDto oneFieldEntityDto) {
        return queryService.getSubEntityDtoList(
                OrderItemData.class,
                supplierService.parameterStringSupplier(ORDER_ID, oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(OrderItemData.class, BeneficiaryDto.class));
    }
}
