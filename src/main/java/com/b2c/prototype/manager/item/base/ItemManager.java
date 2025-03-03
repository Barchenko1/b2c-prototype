package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.item.IItemDao;
import com.b2c.prototype.manager.item.IItemManager;
import com.b2c.prototype.modal.dto.payload.ItemDto;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.Item;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.service.common.EntityOperationManager;
import com.b2c.prototype.service.common.IEntityOperationManager;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.query.ISearchService;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.finder.factory.IParameterFactory;
import lombok.extern.slf4j.Slf4j;

import static com.b2c.prototype.util.Constant.ARTICULAR_ID;

@Slf4j
public class ItemManager implements IItemManager {

    private final IEntityOperationManager entityOperationDao;
    private final ISearchService searchService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;
    private final IParameterFactory parameterFactory;

    public ItemManager(IItemDao itemDao,
                       ISearchService searchService,
                       ITransformationFunctionService transformationFunctionService,
                       ISupplierService supplierService,
                       IParameterFactory parameterFactory) {
        this.entityOperationDao = new EntityOperationManager(itemDao);
        this.searchService = searchService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveUpdateItem(String articularId, ItemDto itemDto) {
        entityOperationDao.executeConsumer(session -> {
            ArticularItem articularItem = searchService.getNamedQueryEntity(
                    ArticularItem.class,
                    "",
                    parameterFactory.createStringParameter(ARTICULAR_ID, articularId));
            Item item = transformationFunctionService.getEntity(Item.class, itemDto);
            item.setArticularItem(articularItem);
            session.merge(item);
        });
    }

    @Override
    public void deleteItem(String articularId) {
        entityOperationDao.deleteEntity(
                supplierService.entityFieldSupplier(
                        ItemData.class,
                        "",
                        supplierService.parameterStringSupplier(ARTICULAR_ID, articularId),
                        transformationFunctionService.getTransformationFunction(ItemData.class, Item.class)));
    }

    @Override
    public Item getItemByItemId(String articularId) {
        return searchService.getGraphEntityDto(
                ItemData.class,
                "",
                parameterFactory.createStringParameter(ARTICULAR_ID, articularId),
                transformationFunctionService.getTransformationFunction(ItemData.class, Item.class));
    }

}
