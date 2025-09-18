//package com.b2c.prototype.manager.item.base;
//
//import com.b2c.prototype.modal.entity.item.ArticularItem;
//import com.b2c.prototype.modal.entity.item.Item;
//import com.b2c.prototype.modal.entity.item.MetaData;
//import com.tm.core.finder.factory.IParameterFactory;
//import com.tm.core.process.manager.common.operator.EntityOperationManager;
//import com.tm.core.process.manager.common.IEntityOperationManager;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
//
//@Service
//public class ItemManager implements IItemManager {
//
//    private final IEntityOperationManager entityOperationManager;
//    private final ISearchService searchService;
//    private final ITransformationFunctionService transformationFunctionService;
//    private final ISupplierService supplierService;
//    private final IParameterFactory parameterFactory;
//
//    public ItemManager(ITransaction itemDao,
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
//                        MetaData.class,
//                        "",
//                        supplierService.parameterStringSupplier(ARTICULAR_ID, articularId),
//                        transformationFunctionService.getTransformationFunction(MetaData.class, Item.class)));
//    }
//
//    @Override
//    public Item getItemByItemId(String articularId) {
//        return searchService.getGraphEntityDto(
//                MetaData.class,
//                "",
//                parameterFactory.createStringParameter(ARTICULAR_ID, articularId),
//                transformationFunctionService.getTransformationFunction(MetaData.class, Item.class));
//    }
//
//}
