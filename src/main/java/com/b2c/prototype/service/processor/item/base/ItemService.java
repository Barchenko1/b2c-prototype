package com.b2c.prototype.service.processor.item.base;

import com.b2c.prototype.dao.item.IItemDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.ItemDto;
import com.b2c.prototype.modal.dto.update.ItemDtoUpdate;
import com.b2c.prototype.modal.entity.item.Item;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.processor.IAsyncProcessor;
import com.b2c.prototype.processor.Task;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.item.IItemService;
import com.b2c.prototype.service.processor.query.IQueryService;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.processor.finder.parameter.Parameter;
import com.tm.core.processor.thread.ThreadLocalSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class ItemService implements IItemService {

    private final IEntityOperationDao entityOperationDao;
    private final IQueryService queryService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;

    public ItemService(IItemDao itemDao,
                       IQueryService queryService,
                       ITransformationFunctionService transformationFunctionService,
                       ISupplierService supplierService) {
        this.entityOperationDao = new EntityOperationDao(itemDao);
        this.queryService = queryService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
    }

    @Override
    public void saveUpdateItem(ItemDtoUpdate itemDtoUpdate) {
        entityOperationDao.executeConsumer(session -> {

        });
    }

    @Override
    public void deleteItem(OneFieldEntityDto oneFieldEntityDto) {

    }

    @Override
    public Item getItemByItemId(OneFieldEntityDto oneFieldEntityDto) {
        return null;
    }

    @Override
    public List<Item> getItemListByCategory(OneFieldEntityDto oneFieldEntityDto) {
        return List.of();
    }

    @Override
    public List<Item> getItemListByItemType(OneFieldEntityDto oneFieldEntityDto) {
        return List.of();
    }

    @Override
    public List<Item> getItemListByBrand(OneFieldEntityDto oneFieldEntityDto) {
        return List.of();
    }

    @Override
    public List<Item> getItemListByItemStatus(OneFieldEntityDto oneFieldEntityDto) {
        return List.of();
    }

    @Override
    public List<Item> getItemListByDateOfCreate(OneFieldEntityDto oneFieldEntityDto) {
        return List.of();
    }
}
