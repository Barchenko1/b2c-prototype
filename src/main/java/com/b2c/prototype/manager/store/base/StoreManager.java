package com.b2c.prototype.manager.store.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.address.Address;

import com.b2c.prototype.modal.dto.payload.store.StoreDto;
import com.b2c.prototype.modal.dto.payload.store.ResponseStoreDto;
import com.b2c.prototype.modal.entity.store.Store;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.b2c.prototype.manager.store.IStoreManager;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.query.IQueryService;
import com.tm.core.process.manager.common.ITransactionEntityOperationManager;
import com.tm.core.process.manager.common.operator.TransactionEntityOperationManager;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
import static com.b2c.prototype.util.Constant.STORE_ID;
import static com.b2c.prototype.util.Constant.VALUE;

@Service
public class StoreManager implements IStoreManager {

    private final ITransactionEntityOperationManager entityOperationManager;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;

    public StoreManager(IGeneralEntityDao storeDao,
                        ITransformationFunctionService transformationFunctionService,
                        IParameterFactory parameterFactory) {
        this.entityOperationManager = new TransactionEntityOperationManager(null);
        this.transformationFunctionService = transformationFunctionService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveStore(StoreDto storeDto) {
        entityOperationManager.executeConsumer(session -> {
            Store store = transformationFunctionService.getEntity((Session) session, Store.class, storeDto);
            session.merge(store);
        });
    }

    @Override
    public void updateStore(String storeId, StoreDto storeDto) {
        entityOperationManager.executeConsumer(session -> {
            Store existingStore = entityOperationManager.getNamedQueryEntityClose(
                    "Store.findStoreWithAddressByStoreId",
                    parameterFactory.createStringParameter(STORE_ID, storeId));

            Store store = transformationFunctionService.getEntity((Session) session, Store.class, storeDto);
            existingStore.setStoreName(store.getStoreName());
            existingStore.setActive(store.isActive());
            Address existingAddress = existingStore.getAddress();
            Address address = store.getAddress();
            existingAddress.setCountry(address.getCountry());
            existingAddress.setCity(address.getCity());
            existingAddress.setStreet(address.getStreet());
            existingAddress.setBuildingNumber(address.getBuildingNumber());
            existingAddress.setFlorNumber(address.getFlorNumber());
            existingAddress.setApartmentNumber(address.getApartmentNumber());
            existingAddress.setZipCode(address.getZipCode());
            session.merge(existingStore);
        });
    }

    @Override
    public void deleteStore(String storeId) {
        entityOperationManager.executeConsumer(session -> {
            Store existingStore = entityOperationManager.getNamedQueryEntityClose(
                    "Store.findStoreByStoreId",
                    parameterFactory.createStringParameter(STORE_ID, storeId));

            session.remove(existingStore);
        });
    }

    @Override
    public ResponseStoreDto getResponseStoreByStoreId(String storeId) {
        Store store = entityOperationManager.getNamedQueryEntityClose(
                "Store.findStoreWithAddressArticularItemQuantityByStoreId",
                parameterFactory.createStringParameter(STORE_ID, storeId));
        return transformationFunctionService.getTransformationFunction(Store.class, ResponseStoreDto.class)
                .apply(store);
    }

    @Override
    public List<ResponseStoreDto> getAllResponseStoresByArticularId(String articularId) {
        List<Store> stores = entityOperationManager.getNamedQueryEntityListClose(
                "Store.findStoreWithAddressArticularItemQuantityByArticularId",
                parameterFactory.createStringParameter(ARTICULAR_ID, articularId));
        return stores.stream()
                .map(transformationFunctionService.getTransformationFunction(Store.class, ResponseStoreDto.class))
                .toList();
    }

    @Override
    public List<ResponseStoreDto> getAllResponseStoreByCountry(String countryName) {
        List<Store> stores = entityOperationManager.getNamedQueryEntityListClose(
                "Store.findStoreWithAddressArticularItemQuantityByCountry",
                parameterFactory.createStringParameter(VALUE, countryName));

        return stores.stream()
                .map(transformationFunctionService.getTransformationFunction(Store.class, ResponseStoreDto.class))
                .toList();
    }

    // fix next
    @Override
    public List<ResponseStoreDto> getAllResponseStoreByCountryAndCity(String countryName, String cityName) {
        List<Store> stores = entityOperationManager.getNamedQueryEntityClose(
                "Store.findStoreWithAddressArticularItemQuantityByCountryCity",
                parameterFactory.createStringParameter(VALUE, countryName));

        return stores.stream()
                .map(transformationFunctionService.getTransformationFunction(Store.class, ResponseStoreDto.class))
                .toList();
    }
}
