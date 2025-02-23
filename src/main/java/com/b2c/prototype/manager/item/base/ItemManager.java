package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.item.IItemDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.searchfield.ItemSearchFieldEntityDto;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.Item;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.service.common.EntityOperationManager;
import com.b2c.prototype.service.common.IEntityOperationManager;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.item.IItemManager;
import com.b2c.prototype.service.query.ISearchService;
import com.b2c.prototype.service.supplier.ISupplierService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
import static com.b2c.prototype.util.Constant.ITEM_ID;

@Slf4j
public class ItemManager implements IItemManager {

    private final IEntityOperationManager entityOperationDao;
    private final ISearchService searchService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;

    public ItemManager(IItemDao itemDao,
                       ISearchService searchService,
                       ITransformationFunctionService transformationFunctionService,
                       ISupplierService supplierService) {
        this.entityOperationDao = new EntityOperationManager(itemDao);
        this.searchService = searchService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
    }

    @Override
    public void saveUpdateItem(ItemSearchFieldEntityDto itemSearchFieldEntityDto) {
        entityOperationDao.executeConsumer(session -> {
            ArticularItem articularItem = searchService.getEntity(
                    ArticularItem.class,
                    supplierService.parameterStringSupplier(ARTICULAR_ID, itemSearchFieldEntityDto.getSearchField()));
            Item item = transformationFunctionService.getEntity(Item.class, itemSearchFieldEntityDto.getNewEntity());
            item.setArticularItem(articularItem);
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
        return searchService.getEntityDto(
                ItemData.class,
                supplierService.parameterStringSupplier(ITEM_ID, oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(ItemData.class, Item.class)
        );
    }

    @Override
    public List<Item> getItemListByCategory(OneFieldEntityDto oneFieldEntityDto) {
        return searchService.getSubEntityDtoList(
                ItemData.class,
                supplierService.parameterStringSupplier(ITEM_ID, oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(ItemData.class, Item.class));
    }

    @Override
    public List<Item> getItemListByItemType(OneFieldEntityDto oneFieldEntityDto) {
        return searchService.getSubEntityDtoList(
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
