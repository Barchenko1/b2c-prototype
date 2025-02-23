package com.b2c.prototype.manager.store.base;

import com.b2c.prototype.modal.entity.item.ArticularItem;

import com.b2c.prototype.dao.store.IStoreDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.payload.StoreDto;
import com.b2c.prototype.modal.dto.response.ResponseStoreDto;
import com.b2c.prototype.modal.entity.store.CountType;
import com.b2c.prototype.modal.entity.store.Store;
import com.b2c.prototype.service.common.EntityOperationManager;
import com.b2c.prototype.service.common.IEntityOperationManager;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.query.ISearchService;
import com.b2c.prototype.manager.store.IStoreManager;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.finder.factory.IParameterFactory;
import org.hibernate.query.NativeQuery;

import java.util.List;

import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
import static com.b2c.prototype.util.Query.SELECT_STORE_BY_ARTICULAR_ID;

public class StoreManager implements IStoreManager {

    private final IEntityOperationManager entityOperationDao;
    private final ISearchService searchService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;
    private final IParameterFactory parameterFactory;

    public StoreManager(IStoreDao storeDao,
                        ISearchService searchService,
                        ITransformationFunctionService transformationFunctionService,
                        ISupplierService supplierService,
                        IParameterFactory parameterFactory) {
        this.entityOperationDao = new EntityOperationManager(storeDao);
        this.searchService = searchService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveStore(StoreDto storeDto) {
        entityOperationDao.executeConsumer(session -> {
            ArticularItem articularItem = searchService.getEntity(
                    ArticularItem.class,
                    supplierService.parameterStringSupplier(ARTICULAR_ID, storeDto.getArticularId()));
//            CountType countType = singleValueMap.getEntity(CountType.class, "value", storeDto.getCountType());
            Store store = Store.builder()
                    .articularItem(articularItem)
                    .count(storeDto.getCount())
//                    .countType(countType)
                    .build();
            session.merge(store);
        });
    }

    @Override
    public void updateStore(StoreDto storeDto) {
        entityOperationDao.executeConsumer(session -> {
            NativeQuery<Store> query = session.createNativeQuery(SELECT_STORE_BY_ARTICULAR_ID, Store.class);
            Store store = searchService.getQueryEntity(
                    query,
                    supplierService.parameterStringSupplier(ARTICULAR_ID, storeDto.getArticularId()));
//            CountType countType = singleValueMap.getEntity(CountType.class, "value", storeDto.getCountType());
            store.setCount(storeDto.getCount());
//            store.setCountType(countType);
            session.merge(store);
        });
    }

    @Override
    public void deleteStore(OneFieldEntityDto oneFieldEntityDto) {
        entityOperationDao.deleteEntity(
                supplierService.entityFieldSupplier(
                        ArticularItem.class,
                        supplierService.parameterStringSupplier(ARTICULAR_ID, oneFieldEntityDto.getValue()),
                        transformationFunctionService.getTransformationFunction(ArticularItem.class, Store.class)));
    }

    @Override
    public ResponseStoreDto getStoreResponse(OneFieldEntityDto oneFieldEntityDto) {
        return entityOperationDao.getEntityGraphDto(
                "",
                parameterFactory.createStringParameter(ARTICULAR_ID, oneFieldEntityDto.getValue()),
                transformationFunctionService.getTransformationFunction(Store.class, ResponseStoreDto.class));
    }

    @Override
    public List<ResponseStoreDto> getAllStoreResponse() {
        return entityOperationDao.getEntityGraphDtoList("",
                transformationFunctionService.getTransformationFunction(Store.class, ResponseStoreDto.class));
    }
}
