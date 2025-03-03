package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.item.IItemDataOptionQuantityDao;
import com.b2c.prototype.modal.dto.payload.ItemDataOptionOneQuantityDto;
import com.b2c.prototype.modal.dto.payload.ArticularItemQuantityDto;
import com.b2c.prototype.modal.entity.item.ArticularItemQuantity;
import com.b2c.prototype.modal.entity.order.OrderArticularItem;
import com.b2c.prototype.modal.entity.store.Store;
import com.b2c.prototype.service.common.EntityOperationManager;
import com.b2c.prototype.service.common.IEntityOperationManager;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.item.IArticularItemQuantityManager;
import com.b2c.prototype.service.query.ISearchService;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.finder.factory.IParameterFactory;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import static com.b2c.prototype.modal.constant.CountTypeEnum.LIMITED;
import static com.b2c.prototype.util.Constant.ORDER_ID;
import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
import static com.b2c.prototype.util.Query.SELECT_STORE_BY_ARTICULAR_ID;

public class ArticularItemQuantityManager implements IArticularItemQuantityManager {

    private final IEntityOperationManager entityOperationDao;
    private final ISearchService searchService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;
    private final IParameterFactory parameterFactory;

    public ArticularItemQuantityManager(IItemDataOptionQuantityDao itemDataOptionQuantityDao,
                                        ISearchService searchService,
                                        ITransformationFunctionService transformationFunctionService,
                                        ISupplierService supplierService,
                                        IParameterFactory parameterFactory) {
        this.entityOperationDao = new EntityOperationManager(itemDataOptionQuantityDao);
        this.searchService = searchService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
        this.parameterFactory = parameterFactory;
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
            Store store = searchService.getQueryEntity(
                    query,
                    supplierService.parameterStringSupplier(ARTICULAR_ID, itemDataOptionOneQuantityDto.getArticularId()));
            int storeCount = store.getCount();
            store.setCount(storeCount + 1);
            session.merge(articularItemQuantity);
            session.merge(store);
        });
    }

    @Override
    public void increaseItemDataOptionQuantityCount(ArticularItemQuantityDto articularItemQuantityDto) {
        entityOperationDao.executeConsumer(session -> {
            ArticularItemQuantity articularItemQuantity = updateCounter(articularItemQuantityDto, true);
            session.merge(articularItemQuantity);
        });
    }

    @Override
    public void decreaseItemDataOptionQuantityCount(ArticularItemQuantityDto articularItemQuantityDto) {
        entityOperationDao.executeConsumer(session -> {
            ArticularItemQuantity articularItemQuantity = updateCounter(articularItemQuantityDto, false);
            session.merge(articularItemQuantity);
        });
    }

    @Override
    public void increaseItemDataOptionQuantityCountAndStore(ArticularItemQuantityDto articularItemQuantityDto) {
        entityOperationDao.executeConsumer(session -> {
            ArticularItemQuantity articularItemQuantity = updateCounter(articularItemQuantityDto, true);
            Store store = getStore(session, articularItemQuantityDto.getArticularId(), articularItemQuantityDto.getQuantity());
            int storeCount = store.getCount();
            if (LIMITED.getValue().equalsIgnoreCase(store.getCountType().getValue())) {
                store.setCount(storeCount - articularItemQuantity.getQuantity());
                session.merge(store);
            }
            session.merge(articularItemQuantity);
        });
    }

    @Override
    public void decreaseItemDataOptionQuantityCountAndStore(ArticularItemQuantityDto articularItemQuantityDto) {
        entityOperationDao.executeConsumer(session -> {
            ArticularItemQuantity articularItemQuantity = updateCounter(articularItemQuantityDto, false);
            NativeQuery<Store> query = session.createNativeQuery(SELECT_STORE_BY_ARTICULAR_ID, Store.class);
            Store store = searchService.getQueryEntity(
                    query,
                    supplierService.parameterStringSupplier(ARTICULAR_ID, articularItemQuantityDto.getArticularId()));
            int storeCount = store.getCount();
            store.setCount(storeCount + articularItemQuantity.getQuantity());
            session.merge(articularItemQuantity);
            session.merge(store);
        });
    }

    private Store getStore(Session session, String articularId, int quantity) {
        NativeQuery<Store> query = session.createNativeQuery(SELECT_STORE_BY_ARTICULAR_ID, Store.class);
        Store store = searchService.getQueryEntity(
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
        OrderArticularItem orderItemDataOption = searchService.getNamedQueryEntity(
                OrderArticularItem.class,
                "",
                parameterFactory.createStringParameter(ORDER_ID, itemDataOptionOneQuantityDto.getArticularId()));
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

    private ArticularItemQuantity updateCounter(ArticularItemQuantityDto articularItemQuantityDto, boolean increase) {
        OrderArticularItem orderItemDataOption = searchService.getNamedQueryEntity(
                OrderArticularItem.class,
                "",
                parameterFactory.createStringParameter(ORDER_ID, null));
        ArticularItemQuantity existingArticularItemQuantity = orderItemDataOption.getArticularItemQuantityList().stream()
                .filter(idq ->
                        idq.getArticularItem().getArticularId().equals(articularItemQuantityDto.getArticularId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException());
        int existingItemDataQuantityQuantityCount = existingArticularItemQuantity.getQuantity();
        int counter = increase
                ? existingItemDataQuantityQuantityCount + articularItemQuantityDto.getQuantity()
                : existingItemDataQuantityQuantityCount - articularItemQuantityDto.getQuantity();
        existingArticularItemQuantity.setQuantity(counter);
        return existingArticularItemQuantity;
    }
}
