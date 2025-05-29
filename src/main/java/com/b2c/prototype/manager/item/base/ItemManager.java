//package com.b2c.prototype.manager.item.base;
//
//import com.b2c.prototype.dao.item.IItemDao;
//import com.b2c.prototype.manager.item.IItemManager;
//import com.b2c.prototype.modal.dto.payload.ItemDto;
//import com.b2c.prototype.modal.entity.item.ArticularItem;
//import com.b2c.prototype.modal.entity.item.Item;
//import com.b2c.prototype.modal.entity.item.ItemData;
//import com.b2c.prototype.function.transform.ITransformationFunctionService;
//import com.tm.core.finder.factory.IParameterFactory;
//import com.tm.core.process.manager.common.EntityOperationManager;
//import com.tm.core.process.manager.common.IEntityOperationManager;
//import lombok.extern.slf4j.Slf4j;
//
//import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
//
//public class ItemManager implements IItemManager {
//
//    private final IEntityOperationManager entityOperationManager;
//    private final ISearchService searchService;
//    private final ITransformationFunctionService transformationFunctionService;
//    private final ISupplierService supplierService;
//    private final IParameterFactory parameterFactory;
//
//    public ItemManager(IItemDao itemDao,
//                       ISearchService searchService,
//                       ITransformationFunctionService transformationFunctionService,
//                       ISupplierService supplierService,
//                       IParameterFactory parameterFactory) {
//        this.entityOperationManager = new EntityOperationManager(itemDao);
//        this.searchService = searchService;
//        this.transformationFunctionService = transformationFunctionService;
//        this.supplierService = supplierService;
//        this.parameterFactory = parameterFactory;
//    }
//
//    @Override
//    public void saveUpdateItem(String articularId, ItemDto itemDto) {
//        entityOperationManager.executeConsumer(session -> {
//            ArticularItem articularItem = searchService.getNamedQueryEntity(
//                    ArticularItem.class,
//                    "",
//                    parameterFactory.createStringParameter(ARTICULAR_ID, articularId));
//            Item item = transformationFunctionService.getEntity(Item.class, itemDto);
//            item.setArticularItem(articularItem);
//            session.merge(item);
//        });
//    }
//
//    @Override
//    public void deleteItem(String articularId) {
//        entityOperationManager.deleteEntity(
//                supplierService.entityFieldSupplier(
//                        ItemData.class,
//                        "",
//                        supplierService.parameterStringSupplier(ARTICULAR_ID, articularId),
//                        transformationFunctionService.getTransformationFunction(ItemData.class, Item.class)));
//    }
//
//    @Override
//    public Item getItemByItemId(String articularId) {
//        return searchService.getGraphEntityDto(
//                ItemData.class,
//                "",
//                parameterFactory.createStringParameter(ARTICULAR_ID, articularId),
//                transformationFunctionService.getTransformationFunction(ItemData.class, Item.class));
//    }
//
//}
