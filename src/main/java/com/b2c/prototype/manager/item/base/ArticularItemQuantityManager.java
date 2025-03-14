//package com.b2c.prototype.manager.item.base;
//
//import com.b2c.prototype.dao.item.IItemDataOptionQuantityDao;
//import com.b2c.prototype.modal.dto.payload.ItemDataOptionOneQuantityDto;
//import com.b2c.prototype.modal.dto.payload.ArticularItemQuantityDto;
//import com.b2c.prototype.modal.entity.item.ArticularItemQuantityPrice;
//import com.b2c.prototype.modal.entity.order.DeliveryArticularItemQuantity;
//import com.b2c.prototype.modal.entity.store.Store;
//import com.b2c.prototype.service.function.ITransformationFunctionService;
//import com.b2c.prototype.manager.item.IArticularItemQuantityManager;
//import com.b2c.prototype.service.query.ISearchService;
//import com.b2c.prototype.service.supplier.ISupplierService;
//import com.tm.core.finder.factory.IParameterFactory;
//import com.tm.core.process.manager.common.EntityOperationManager;
//import com.tm.core.process.manager.common.IEntityOperationManager;
//import org.hibernate.Session;
//import org.hibernate.query.NativeQuery;
//
//import static com.b2c.prototype.util.Constant.ORDER_ID;
//import static com.b2c.prototype.util.Query.SELECT_STORE_BY_ARTICULAR_ID;
//
//public class ArticularItemQuantityManager implements IArticularItemQuantityManager {
//
//    private final IEntityOperationManager entityOperationManager;
//    private final ISearchService searchService;
//    private final ITransformationFunctionService transformationFunctionService;
//    private final IParameterFactory parameterFactory;
//
//    public ArticularItemQuantityManager(IItemDataOptionQuantityDao itemDataOptionQuantityDao,
//                                        ISearchService searchService,
//                                        ITransformationFunctionService transformationFunctionService,
//                                        IParameterFactory parameterFactory) {
//        this.entityOperationManager = new EntityOperationManager(itemDataOptionQuantityDao);
//        this.searchService = searchService;
//        this.transformationFunctionService = transformationFunctionService;
//        this.parameterFactory = parameterFactory;
//    }
//
//    @Override
//    public void increaseOneItemDataOptionQuantityCount(ItemDataOptionOneQuantityDto itemDataOptionOneQuantityDto) {
//        entityOperationManager.executeConsumer(session -> {
//            ArticularItemQuantityPrice articularItemQuantityPrice = updateOneIncrementCounter(itemDataOptionOneQuantityDto, true);
//            session.merge(articularItemQuantityPrice);
//        });
//    }
//
//    @Override
//    public void decreaseOneItemDataOptionQuantityCount(ItemDataOptionOneQuantityDto itemDataOptionOneQuantityDto) {
//        entityOperationManager.executeConsumer(session -> {
//            ArticularItemQuantityPrice articularItemQuantityPrice = updateOneIncrementCounter(itemDataOptionOneQuantityDto, false);
//            session.merge(articularItemQuantityPrice);
//        });
//    }
//
//    @Override
//    public void increaseOneItemDataOptionQuantityCountAndStore(ItemDataOptionOneQuantityDto itemDataOptionOneQuantityDto) {
//        entityOperationManager.executeConsumer(session -> {
//            ArticularItemQuantityPrice articularItemQuantityPrice = updateOneIncrementCounter(itemDataOptionOneQuantityDto, true);
//            Store store = getStore(session, itemDataOptionOneQuantityDto.getArticularId(), 1);
////            int storeCount = store.getCount();
////            if (LIMITED.getValue().equalsIgnoreCase(store.getCountType().getValue())) {
////                store.setCount(storeCount - 1);
////                session.merge(store);
////            }
//            session.merge(articularItemQuantityPrice);
//        });
//    }
//
//    @Override
//    public void decreaseOneItemDataOptionQuantityCountAndStore(ItemDataOptionOneQuantityDto itemDataOptionOneQuantityDto) {
//        entityOperationManager.executeConsumer(session -> {
//            ArticularItemQuantityPrice articularItemQuantityPrice = updateOneIncrementCounter(itemDataOptionOneQuantityDto, false);
//            NativeQuery<Store> query = session.createNativeQuery(SELECT_STORE_BY_ARTICULAR_ID, Store.class);
////            Store store = searchService.getQueryEntity(
////                    query,
////                    supplierService.parameterStringSupplier(ARTICULAR_ID, itemDataOptionOneQuantityDto.getArticularId()));
////            int storeCount = store.getCount();
////            store.setCount(storeCount + 1);
////            session.merge(articularItemQuantityPrice);
////            session.merge(store);
//        });
//    }
//
//    @Override
//    public void increaseItemDataOptionQuantityCount(ArticularItemQuantityDto articularItemQuantityDto) {
//        entityOperationManager.executeConsumer(session -> {
//            ArticularItemQuantityPrice articularItemQuantityPrice = updateCounter(articularItemQuantityDto, true);
//            session.merge(articularItemQuantityPrice);
//        });
//    }
//
//    @Override
//    public void decreaseItemDataOptionQuantityCount(ArticularItemQuantityDto articularItemQuantityDto) {
//        entityOperationManager.executeConsumer(session -> {
//            ArticularItemQuantityPrice articularItemQuantityPrice = updateCounter(articularItemQuantityDto, false);
//            session.merge(articularItemQuantityPrice);
//        });
//    }
//
//    @Override
//    public void increaseItemDataOptionQuantityCountAndStore(ArticularItemQuantityDto articularItemQuantityDto) {
//        entityOperationManager.executeConsumer(session -> {
//            ArticularItemQuantityPrice articularItemQuantityPrice = updateCounter(articularItemQuantityDto, true);
//            Store store = getStore(session, articularItemQuantityDto.getArticularId(), articularItemQuantityDto.getQuantity());
////            int storeCount = store.getCount();
////            if (LIMITED.getValue().equalsIgnoreCase(store.getCountType().getValue())) {
////                store.setCount(storeCount - articularItemQuantityPrice.getQuantity());
////                session.merge(store);
////            }
//            session.merge(articularItemQuantityPrice);
//        });
//    }
//
//    @Override
//    public void decreaseItemDataOptionQuantityCountAndStore(ArticularItemQuantityDto articularItemQuantityDto) {
//        entityOperationManager.executeConsumer(session -> {
//            ArticularItemQuantityPrice articularItemQuantityPrice = updateCounter(articularItemQuantityDto, false);
//            NativeQuery<Store> query = session.createNativeQuery(SELECT_STORE_BY_ARTICULAR_ID, Store.class);
////            Store store = searchService.getQueryEntity(
////                    query,
////                    supplierService.parameterStringSupplier(ARTICULAR_ID, articularItemQuantityDto.getArticularId()));
////            int storeCount = store.getCount();
////            store.setCount(storeCount + articularItemQuantityPrice.getQuantity());
////            session.merge(articularItemQuantityPrice);
////            session.merge(store);
//        });
//    }
//
//    private Store getStore(Session session, String articularId, int quantity) {
//        NativeQuery<Store> query = session.createNativeQuery(SELECT_STORE_BY_ARTICULAR_ID, Store.class);
////        Store store = searchService.getQueryEntity(
////                query,
////                supplierService.parameterStringSupplier(ARTICULAR_ID, articularId));
////        int storeCount = store.getCount();
////        if (LIMITED.getValue().equalsIgnoreCase(store.getCountType().getValue())
////                && storeCount < quantity) {
////            throw new RuntimeException("Limited item quantity limit reached");
////        }
//
//        return null;
//    }
//
//    private ArticularItemQuantityPrice updateOneIncrementCounter(ItemDataOptionOneQuantityDto itemDataOptionOneQuantityDto, boolean increase) {
//        DeliveryArticularItemQuantity orderItemDataOption = searchService.getNamedQueryEntity(
//                DeliveryArticularItemQuantity.class,
//                "",
//                parameterFactory.createStringParameter(ORDER_ID, itemDataOptionOneQuantityDto.getArticularId()));
//        ArticularItemQuantityPrice existingArticularItemQuantityPrice = orderItemDataOption.getArticularItemQuantityPrice();
//        int existingItemDataQuantityQuantityCount = existingArticularItemQuantityPrice.getArticularItemQuantity().getQuantity();
//        int count = increase
//                ? existingItemDataQuantityQuantityCount + 1
//                : existingItemDataQuantityQuantityCount - 1;
//        existingArticularItemQuantityPrice.setQuantity(count);
//        return existingArticularItemQuantityPrice;
//    }
//
//    private ArticularItemQuantityPrice updateCounter(ArticularItemQuantityDto articularItemQuantityDto, boolean increase) {
//        DeliveryArticularItemQuantity orderItemDataOption = searchService.getNamedQueryEntity(
//                DeliveryArticularItemQuantity.class,
//                "",
//                parameterFactory.createStringParameter(ORDER_ID, null));
//        ArticularItemQuantityPrice existingArticularItemQuantityPrice = orderItemDataOption.getArticularItemQuantityPrice();
//        int existingItemDataQuantityQuantityCount = existingArticularItemQuantityPrice.getQuantity();
//        int counter = increase
//                ? existingItemDataQuantityQuantityCount + articularItemQuantityDto.getQuantity()
//                : existingItemDataQuantityQuantityCount - articularItemQuantityDto.getQuantity();
//        existingArticularItemQuantityPrice.setQuantity(counter);
//        return existingArticularItemQuantityPrice;
//    }
//}
