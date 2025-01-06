package com.b2c.prototype.service.processor.item.base;

import com.b2c.prototype.dao.item.IItemDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.searchfield.ItemSearchFieldEntityDto;
import com.b2c.prototype.modal.entity.item.Item;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.item.IItemService;
import com.b2c.prototype.service.processor.query.IQueryService;
import com.b2c.prototype.service.supplier.ISupplierService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.b2c.prototype.util.Constant.ITEM_ID;

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
    public void saveUpdateItem(ItemSearchFieldEntityDto itemSearchFieldEntityDto) {
        entityOperationDao.executeConsumer(session -> {
            ItemData itemData = queryService.getEntity(
                    ItemData.class,
                    supplierService.parameterStringSupplier(ITEM_ID, itemSearchFieldEntityDto.getSearchField()));
            Item item = transformationFunctionService.getEntity(Item.class, itemSearchFieldEntityDto.getNewEntity());
            item.setItemData(itemData);
            session.merge(item);
        });
    }

    @Override
    public void deleteItem(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntity(
                supplierService.entityFieldSupplier(
                        ItemData.class,
                        supplierService.parameterStringSupplier(ITEM_ID, oneFieldEntityDto.getValue()),
                        transformationFunctionService.getTransformationFunction(ItemData.class, Item.class)));
    }

    @Override
    public Item getItemByItemId(OneFieldEntityDto oneFieldEntityDto) {
        return queryService.getEntityDto(
                ItemData.class,
                supplierService.parameterStringSupplier(ITEM_ID, oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(ItemData.class, Item.class)
        );
    }

    @Override
    public List<Item> getItemListByCategory(OneFieldEntityDto oneFieldEntityDto) {
        return queryService.getSubEntityDtoList(
                ItemData.class,
                supplierService.parameterStringSupplier(ITEM_ID, oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(ItemData.class, Item.class));
    }

    @Override
    public List<Item> getItemListByItemType(OneFieldEntityDto oneFieldEntityDto) {
        return queryService.getSubEntityDtoList(
                ItemData.class,
                supplierService.parameterStringSupplier(ITEM_ID, oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(ItemData.class, Item.class));
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
