package com.b2c.prototype.service.manager.store.base;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.dao.store.IStoreDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.payload.StoreDto;
import com.b2c.prototype.modal.dto.response.ResponseStoreDto;
import com.b2c.prototype.modal.entity.item.ItemDataOption;
import com.b2c.prototype.modal.entity.store.CountType;
import com.b2c.prototype.modal.entity.store.Store;
import com.b2c.prototype.service.common.EntityOperationDao;
import com.b2c.prototype.service.common.IEntityOperationDao;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.query.IQueryService;
import com.b2c.prototype.service.manager.store.IStoreManager;
import com.b2c.prototype.service.supplier.ISupplierService;
import org.hibernate.query.NativeQuery;

import java.util.List;

import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
import static com.b2c.prototype.util.Query.SELECT_STORE_BY_ARTICULAR_ID;

public class StoreManager implements IStoreManager {

    private final IEntityOperationDao entityOperationDao;
    private final IQueryService queryService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;
    private final ISingleValueMap singleValueMap;

    public StoreManager(IStoreDao storeDao,
                        IQueryService queryService,
                        ITransformationFunctionService transformationFunctionService,
                        ISupplierService supplierService,
                        ISingleValueMap singleValueMap) {
        this.entityOperationDao = new EntityOperationDao(storeDao);
        this.queryService = queryService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
        this.singleValueMap = singleValueMap;
    }

    @Override
    public void saveStore(StoreDto storeDto) {
        entityOperationDao.executeConsumer(session -> {
            ItemDataOption itemDataOption = queryService.getEntity(
                    ItemDataOption.class,
                    supplierService.parameterStringSupplier(ARTICULAR_ID, storeDto.getArticularId()));
            CountType countType = singleValueMap.getEntity(CountType.class, "value", storeDto.getCountType());
            Store store = Store.builder()
                    .itemDataOption(itemDataOption)
                    .count(storeDto.getCount())
                    .countType(countType)
                    .build();
            session.merge(store);
        });
    }

    @Override
    public void updateStore(StoreDto storeDto) {
        entityOperationDao.executeConsumer(session -> {
            NativeQuery<Store> query = session.createNativeQuery(SELECT_STORE_BY_ARTICULAR_ID, Store.class);
            Store store = queryService.getQueryEntity(
                    query,
                    supplierService.parameterStringSupplier(ARTICULAR_ID, storeDto.getArticularId()));
            CountType countType = singleValueMap.getEntity(CountType.class, "value", storeDto.getCountType());
            store.setCount(storeDto.getCount());
            store.setCountType(countType);
            session.merge(store);
        });
    }

    @Override
    public void deleteStore(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntity(
                supplierService.entityFieldSupplier(
                        ItemDataOption.class,
                        supplierService.parameterStringSupplier(ARTICULAR_ID, oneFieldEntityDto.getValue()),
                        transformationFunctionService.getTransformationFunction(ItemDataOption.class, Store.class)));
    }

    @Override
    public ResponseStoreDto getStoreResponse(OneFieldEntityDto oneFieldEntityDto) {
        return entityOperationDao.getEntityDto("",
                supplierService.parameterStringSupplier(ARTICULAR_ID, oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(Store.class, ResponseStoreDto.class));
    }

    @Override
    public List<ResponseStoreDto> getAllStoreResponse() {
        return entityOperationDao.getEntityDtoList("",
                transformationFunctionService.getTransformationFunction(Store.class, ResponseStoreDto.class));
    }
}
