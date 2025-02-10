package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.item.IItemDataOptionQuantityDao;
import com.b2c.prototype.modal.dto.payload.ItemDataOptionOneQuantityDto;
import com.b2c.prototype.modal.dto.payload.ItemDataOptionQuantityDto;
import com.b2c.prototype.modal.entity.item.ArticularItemQuantity;
import com.b2c.prototype.modal.entity.order.OrderArticularItem;
import com.b2c.prototype.modal.entity.store.Store;
import com.b2c.prototype.service.common.EntityOperationManager;
import com.b2c.prototype.service.common.IEntityOperationManager;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.item.IArticularItemQuantityManager;
import com.b2c.prototype.service.query.IQueryService;
import com.b2c.prototype.service.supplier.ISupplierService;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import static com.b2c.prototype.modal.constant.CountTypeEnum.LIMITED;
import static com.b2c.prototype.util.Constant.ORDER_ID;
import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
import static com.b2c.prototype.util.Query.SELECT_STORE_BY_ARTICULAR_ID;

public class ArticularItemQuantityManager implements IArticularItemQuantityManager {

    private final IEntityOperationManager entityOperationDao;
    private final IQueryService queryService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;

    public ArticularItemQuantityManager(IItemDataOptionQuantityDao itemDataOptionQuantityDao,
                                        IQueryService queryService,
                                        ITransformationFunctionService transformationFunctionService,
                                        ISupplierService supplierService) {
        this.entityOperationDao = new EntityOperationManager(itemDataOptionQuantityDao);
        this.queryService = queryService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
    }

    @Override
    public void increaseOneItemDataOptionQuantityCount(ItemDataOptionOneQuantityDto itemDataOptionOneQuantityDto) {
        entityOperationDao.executeConsumer(session -> {
            ArticularItemQuantity articularItemQuantity = updateOneIncrementCounter(itemDataOptionOneQuantityDto, true);
            session.merge(articularItemQuantity);
        });
    }

    @Override
    public void decreaseOneItemDataOptionQuantityCount(ItemDataOptionOneQuantityDto itemDataOptionOneQuantityDto) {
        entityOperationDao.executeConsumer(session -> {
            ArticularItemQuantity articularItemQuantity = updateOneIncrementCounter(itemDataOptionOneQuantityDto, false);
            session.merge(articularItemQuantity);
        });
    }

    @Override
    public void increaseOneItemDataOptionQuantityCountAndStore(ItemDataOptionOneQuantityDto itemDataOptionOneQuantityDto) {
        entityOperationDao.executeConsumer(session -> {
            ArticularItemQuantity articularItemQuantity = updateOneIncrementCounter(itemDataOptionOneQuantityDto, true);
            Store store = getStore(session, itemDataOptionOneQuantityDto.getArticularId(), 1);
            int storeCount = store.getCount();
            if (LIMITED.getValue().equalsIgnoreCase(store.getCountType().getValue())) {
                store.setCount(storeCount - 1);
                session.merge(store);
            }
            session.merge(articularItemQuantity);
        });
    }

    @Override
    public void decreaseOneItemDataOptionQuantityCountAndStore(ItemDataOptionOneQuantityDto itemDataOptionOneQuantityDto) {
        entityOperationDao.executeConsumer(session -> {
            ArticularItemQuantity articularItemQuantity = updateOneIncrementCounter(itemDataOptionOneQuantityDto, false);
            NativeQuery<Store> query = session.createNativeQuery(SELECT_STORE_BY_ARTICULAR_ID, Store.class);
            Store store = queryService.getQueryEntity(
                    query,
                    supplierService.parameterStringSupplier(ARTICULAR_ID, itemDataOptionOneQuantityDto.getArticularId()));
            int storeCount = store.getCount();
            store.setCount(storeCount + 1);
            session.merge(articularItemQuantity);
            session.merge(store);
        });
    }

    @Override
    public void increaseItemDataOptionQuantityCount(ItemDataOptionQuantityDto itemDataOptionQuantityDto) {
        entityOperationDao.executeConsumer(session -> {
            ArticularItemQuantity articularItemQuantity = updateCounter(itemDataOptionQuantityDto, true);
            session.merge(articularItemQuantity);
        });
    }

    @Override
    public void decreaseItemDataOptionQuantityCount(ItemDataOptionQuantityDto itemDataOptionQuantityDto) {
        entityOperationDao.executeConsumer(session -> {
            ArticularItemQuantity articularItemQuantity = updateCounter(itemDataOptionQuantityDto, false);
            session.merge(articularItemQuantity);
        });
    }

    @Override
    public void increaseItemDataOptionQuantityCountAndStore(ItemDataOptionQuantityDto itemDataOptionQuantityDto) {
        entityOperationDao.executeConsumer(session -> {
            ArticularItemQuantity articularItemQuantity = updateCounter(itemDataOptionQuantityDto, true);
            Store store = getStore(session, itemDataOptionQuantityDto.getArticularId(), itemDataOptionQuantityDto.getQuantity());
            int storeCount = store.getCount();
            if (LIMITED.getValue().equalsIgnoreCase(store.getCountType().getValue())) {
                store.setCount(storeCount - articularItemQuantity.getQuantity());
                session.merge(store);
            }
            session.merge(articularItemQuantity);
        });
    }

    @Override
    public void decreaseItemDataOptionQuantityCountAndStore(ItemDataOptionQuantityDto itemDataOptionQuantityDto) {
        entityOperationDao.executeConsumer(session -> {
            ArticularItemQuantity articularItemQuantity = updateCounter(itemDataOptionQuantityDto, false);
            NativeQuery<Store> query = session.createNativeQuery(SELECT_STORE_BY_ARTICULAR_ID, Store.class);
            Store store = queryService.getQueryEntity(
                    query,
                    supplierService.parameterStringSupplier(ARTICULAR_ID, itemDataOptionQuantityDto.getArticularId()));
            int storeCount = store.getCount();
            store.setCount(storeCount + articularItemQuantity.getQuantity());
            session.merge(articularItemQuantity);
            session.merge(store);
        });
    }

    private Store getStore(Session session, String articularId, int quantity) {
        NativeQuery<Store> query = session.createNativeQuery(SELECT_STORE_BY_ARTICULAR_ID, Store.class);
        Store store = queryService.getQueryEntity(
                query,
                supplierService.parameterStringSupplier(ARTICULAR_ID, articularId));
        int storeCount = store.getCount();
        if (LIMITED.getValue().equalsIgnoreCase(store.getCountType().getValue())
                && storeCount < quantity) {
            throw new RuntimeException("Limited item quantity limit reached");
        }

        return store;
    }

    private ArticularItemQuantity updateOneIncrementCounter(ItemDataOptionOneQuantityDto itemDataOptionOneQuantityDto, boolean increase) {
        OrderArticularItem orderItemDataOption = queryService.getEntity(
                OrderArticularItem.class,
                supplierService.parameterStringSupplier(ORDER_ID, itemDataOptionOneQuantityDto.getOrderId()));
        ArticularItemQuantity existingArticularItemQuantity = orderItemDataOption.getArticularItemQuantityList().stream()
                .filter(idq ->
                        idq.getArticularItem().getArticularId().equals(itemDataOptionOneQuantityDto.getArticularId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException());
        int existingItemDataQuantityQuantityCount = existingArticularItemQuantity.getQuantity();
        int count = increase
                ? existingItemDataQuantityQuantityCount + 1
                : existingItemDataQuantityQuantityCount - 1;
        existingArticularItemQuantity.setQuantity(count);
        return existingArticularItemQuantity;
    }

    private ArticularItemQuantity updateCounter(ItemDataOptionQuantityDto itemDataOptionQuantityDto, boolean increase) {
        OrderArticularItem orderItemDataOption = queryService.getEntity(
                OrderArticularItem.class,
                supplierService.parameterStringSupplier(ORDER_ID, itemDataOptionQuantityDto.getOrderId()));
        ArticularItemQuantity existingArticularItemQuantity = orderItemDataOption.getArticularItemQuantityList().stream()
                .filter(idq ->
                        idq.getArticularItem().getArticularId().equals(itemDataOptionQuantityDto.getArticularId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException());
        int existingItemDataQuantityQuantityCount = existingArticularItemQuantity.getQuantity();
        int counter = increase
                ? existingItemDataQuantityQuantityCount + itemDataOptionQuantityDto.getQuantity()
                : existingItemDataQuantityQuantityCount - itemDataOptionQuantityDto.getQuantity();
        existingArticularItemQuantity.setQuantity(counter);
        return existingArticularItemQuantity;
    }
}
