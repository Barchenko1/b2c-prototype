package com.b2c.prototype.service.processor.item.base;

import com.b2c.prototype.dao.item.IItemDataOptionDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.ItemDataOptionDto;
import com.b2c.prototype.modal.dto.response.ResponseItemDataOptionDto;
import com.b2c.prototype.modal.dto.update.ItemDataOptionDtoUpdate;
import com.b2c.prototype.modal.entity.item.ItemDataOption;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.item.IItemDataOptionService;
import com.b2c.prototype.service.supplier.ISupplierService;

import java.util.List;

import static com.b2c.prototype.util.UniqueIdUtil.getUUID;

public class ItemDataOptionService implements IItemDataOptionService {

    private final IEntityOperationDao entityOperationDao;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;

    public ItemDataOptionService(IItemDataOptionDao IItemDataOptionDao,
                                 ITransformationFunctionService transformationFunctionService,
                                 ISupplierService supplierService) {
        this.entityOperationDao = new EntityOperationDao(IItemDataOptionDao);
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
    }

    @Override
    public void saveItemDataOption(ItemDataOptionDto itemDataOptionDto) {
        entityOperationDao.executeConsumer(session -> {
            ItemDataOption itemDataOption = transformationFunctionService.getEntity(
                    ItemDataOption.class,
                    itemDataOptionDto);
            itemDataOption.setArticularId(getUUID());
            session.merge(itemDataOption);
        });
    }

    @Override
    public void updateItemDataOption(ItemDataOptionDtoUpdate itemDataOptionDtoUpdate) {
        entityOperationDao.executeConsumer(session -> {
            ItemDataOption itemDataOption = entityOperationDao.getEntity(
                    supplierService.parameterStringSupplier("articularId", itemDataOptionDtoUpdate.getSearchField()));
            ItemDataOption updateItemDataOption = transformationFunctionService.getEntity(
                    ItemDataOption.class,
                    itemDataOptionDtoUpdate.getNewEntity());
            updateItemDataOption.setArticularId(itemDataOption.getArticularId());
            updateItemDataOption.setId(itemDataOption.getId());
            session.merge(updateItemDataOption);
        });
    }

    @Override
    public void deleteItemDataOption(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntityByParameter(
                supplierService.parameterStringSupplier("articularId", oneFieldEntityDto.getValue()));
    }

    @Override
    public ResponseItemDataOptionDto getResponseItemDataOptionDto(OneFieldEntityDto oneFieldEntityDto) {
        return entityOperationDao.getEntityDto(
                supplierService.parameterStringSupplier("articularId", oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(ItemDataOption.class, ResponseItemDataOptionDto.class));
    }

    @Override
    public List<ResponseItemDataOptionDto> getResponseItemDataOptionDtoList() {
        return entityOperationDao.getEntityDtoList(
                transformationFunctionService.getTransformationFunction(ItemDataOption.class, ResponseItemDataOptionDto.class));

    }

    @Override
    public List<ResponseItemDataOptionDto> getResponseItemDataOptionDtoFiltered() {
        return entityOperationDao.getEntityDtoList(
                transformationFunctionService.getTransformationFunction(ItemDataOption.class, ResponseItemDataOptionDto.class));
    }

    @Override
    public List<ResponseItemDataOptionDto> getResponseItemDataOptionDtoSorted(String sortType) {
        return entityOperationDao.getEntityDtoList(
                transformationFunctionService.getTransformationFunction(ItemDataOption.class, ResponseItemDataOptionDto.class));
    }
}
