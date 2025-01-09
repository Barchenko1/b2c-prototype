package com.b2c.prototype.service.processor.item.base;

import com.b2c.prototype.dao.item.IItemDataOptionQuantityDao;
import com.b2c.prototype.modal.dto.payload.ItemDataOptionOneQuantityDto;
import com.b2c.prototype.modal.dto.payload.ItemDataOptionQuantityDto;
import com.b2c.prototype.modal.entity.item.ItemDataOptionQuantity;
import com.b2c.prototype.modal.entity.order.OrderItemData;
import com.b2c.prototype.modal.entity.store.Store;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.item.IItemDataOptionQuantityService;
import com.b2c.prototype.service.processor.query.IQueryService;
import com.b2c.prototype.service.supplier.ISupplierService;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import static com.b2c.prototype.modal.constant.CountTypeEnum.LIMITED;
import static com.b2c.prototype.util.Constant.ORDER_ID;
import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
import static com.b2c.prototype.util.Query.SELECT_STORE_BY_ARTICULAR_ID;

public class ItemDataOptionQuantityService implements IItemDataOptionQuantityService {

    private final IEntityOperationDao entityOperationDao;
    private final IQueryService queryService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;

    public ItemDataOptionQuantityService(IItemDataOptionQuantityDao itemDataOptionQuantityDao,
                                         IQueryService queryService,
                                         ITransformationFunctionService transformationFunctionService,
                                         ISupplierService supplierService) {
        this.entityOperationDao = new EntityOperationDao(itemDataOptionQuantityDao);
        this.queryService = queryService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
    }

    @Override
    public void increaseOneItemDataOptionQuantityCount(ItemDataOptionOneQuantityDto itemDataOptionOneQuantityDto) {
        entityOperationDao.executeConsumer(session -> {
            ItemDataOptionQuantity itemDataOptionQuantity = updateOneIncrementCounter(itemDataOptionOneQuantityDto, true);
            session.merge(itemDataOptionQuantity);
        });
    }

    @Override
    public void decreaseOneItemDataOptionQuantityCount(ItemDataOptionOneQuantityDto itemDataOptionOneQuantityDto) {
        entityOperationDao.executeConsumer(session -> {
            ItemDataOptionQuantity itemDataOptionQuantity = updateOneIncrementCounter(itemDataOptionOneQuantityDto, false);
            session.merge(itemDataOptionQuantity);
        });
    }

    @Override
    public void increaseOneItemDataOptionQuantityCountAndStore(ItemDataOptionOneQuantityDto itemDataOptionOneQuantityDto) {
        entityOperationDao.executeConsumer(session -> {
            ItemDataOptionQuantity itemDataOptionQuantity = updateOneIncrementCounter(itemDataOptionOneQuantityDto, true);
            Store store = getStore(session, itemDataOptionOneQuantityDto.getArticularId(), 1);
            int storeCount = store.getCount();
            if (LIMITED.getValue().equalsIgnoreCase(store.getCountType().getValue())) {
                store.setCount(storeCount - 1);
                session.merge(store);
            }
            session.merge(itemDataOptionQuantity);
        });
    }

    @Override
    public void decreaseOneItemDataOptionQuantityCountAndStore(ItemDataOptionOneQuantityDto itemDataOptionOneQuantityDto) {
        entityOperationDao.executeConsumer(session -> {
            ItemDataOptionQuantity itemDataOptionQuantity = updateOneIncrementCounter(itemDataOptionOneQuantityDto, false);
            NativeQuery<Store> query = session.createNativeQuery(SELECT_STORE_BY_ARTICULAR_ID, Store.class);
            Store store = queryService.getQueryEntity(
                    query,
                    supplierService.parameterStringSupplier(ARTICULAR_ID, itemDataOptionOneQuantityDto.getArticularId()));
            int storeCount = store.getCount();
            store.setCount(storeCount + 1);
            session.merge(itemDataOptionQuantity);
            session.merge(store);
        });
    }

    @Override
    public void increaseItemDataOptionQuantityCount(ItemDataOptionQuantityDto itemDataOptionQuantityDto) {
        entityOperationDao.executeConsumer(session -> {
            ItemDataOptionQuantity itemDataOptionQuantity = updateCounter(itemDataOptionQuantityDto, true);
            session.merge(itemDataOptionQuantity);
        });
    }

    @Override
    public void decreaseItemDataOptionQuantityCount(ItemDataOptionQuantityDto itemDataOptionQuantityDto) {
        entityOperationDao.executeConsumer(session -> {
            ItemDataOptionQuantity itemDataOptionQuantity = updateCounter(itemDataOptionQuantityDto, false);
            session.merge(itemDataOptionQuantity);
        });
    }

    @Override
    public void increaseItemDataOptionQuantityCountAndStore(ItemDataOptionQuantityDto itemDataOptionQuantityDto) {
        entityOperationDao.executeConsumer(session -> {
            ItemDataOptionQuantity itemDataOptionQuantity = updateCounter(itemDataOptionQuantityDto, true);
            Store store = getStore(session, itemDataOptionQuantityDto.getArticularId(), itemDataOptionQuantityDto.getQuantity());
            int storeCount = store.getCount();
            if (LIMITED.getValue().equalsIgnoreCase(store.getCountType().getValue())) {
                store.setCount(storeCount - itemDataOptionQuantity.getQuantity());
                session.merge(store);
            }
            session.merge(itemDataOptionQuantity);
        });
    }

    @Override
    public void decreaseItemDataOptionQuantityCountAndStore(ItemDataOptionQuantityDto itemDataOptionQuantityDto) {
        entityOperationDao.executeConsumer(session -> {
            ItemDataOptionQuantity itemDataOptionQuantity = updateCounter(itemDataOptionQuantityDto, false);
            NativeQuery<Store> query = session.createNativeQuery(SELECT_STORE_BY_ARTICULAR_ID, Store.class);
            Store store = queryService.getQueryEntity(
                    query,
                    supplierService.parameterStringSupplier(ARTICULAR_ID, itemDataOptionQuantityDto.getArticularId()));
            int storeCount = store.getCount();
            store.setCount(storeCount + itemDataOptionQuantity.getQuantity());
            session.merge(itemDataOptionQuantity);
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

    private ItemDataOptionQuantity updateOneIncrementCounter(ItemDataOptionOneQuantityDto itemDataOptionOneQuantityDto, boolean increase) {
        OrderItemData orderItemData = queryService.getEntity(
                OrderItemData.class,
                supplierService.parameterStringSupplier(ORDER_ID, itemDataOptionOneQuantityDto.getOrderId()));
        ItemDataOptionQuantity existingItemDataOptionQuantity = orderItemData.getItemDataOptionQuantities().stream()
                .filter(idq ->
                        idq.getItemDataOption().getArticularId().equals(itemDataOptionOneQuantityDto.getArticularId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException());
        int existingItemDataQuantityQuantityCount = existingItemDataOptionQuantity.getQuantity();
        int count = increase
                ? existingItemDataQuantityQuantityCount + 1
                : existingItemDataQuantityQuantityCount - 1;
        existingItemDataOptionQuantity.setQuantity(count);
        return existingItemDataOptionQuantity;
    }

    private ItemDataOptionQuantity updateCounter(ItemDataOptionQuantityDto itemDataOptionQuantityDto, boolean increase) {
        OrderItemData orderItemData = queryService.getEntity(
                OrderItemData.class,
                supplierService.parameterStringSupplier(ORDER_ID, itemDataOptionQuantityDto.getOrderId()));
        ItemDataOptionQuantity existingItemDataOptionQuantity = orderItemData.getItemDataOptionQuantities().stream()
                .filter(idq ->
                        idq.getItemDataOption().getArticularId().equals(itemDataOptionQuantityDto.getArticularId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException());
        int existingItemDataQuantityQuantityCount = existingItemDataOptionQuantity.getQuantity();
        int counter = increase
                ? existingItemDataQuantityQuantityCount + itemDataOptionQuantityDto.getQuantity()
                : existingItemDataQuantityQuantityCount - itemDataOptionQuantityDto.getQuantity();
        existingItemDataOptionQuantity.setQuantity(counter);
        return existingItemDataOptionQuantity;
    }
}
