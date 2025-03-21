package com.b2c.prototype.manager.store.base;

import com.b2c.prototype.modal.entity.address.Address;

import com.b2c.prototype.dao.store.IStoreDao;
import com.b2c.prototype.modal.dto.payload.store.StoreDto;
import com.b2c.prototype.modal.dto.response.ResponseStoreDto;
import com.b2c.prototype.modal.entity.store.Store;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.query.ISearchService;
import com.b2c.prototype.manager.store.IStoreManager;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.manager.common.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;

import java.util.List;

import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
import static com.b2c.prototype.util.Constant.STORE_ID;
import static com.b2c.prototype.util.Constant.VALUE;

public class StoreManager implements IStoreManager {

    private final IEntityOperationManager entityOperationManager;
    private final ISearchService searchService;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;

    public StoreManager(IStoreDao storeDao,
                        ISearchService searchService,
                        ITransformationFunctionService transformationFunctionService,
                        IParameterFactory parameterFactory) {
        this.entityOperationManager = new EntityOperationManager(storeDao);
        this.searchService = searchService;
        this.transformationFunctionService = transformationFunctionService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveStore(StoreDto storeDto) {
        entityOperationManager.executeConsumer(session -> {
            Store store = transformationFunctionService.getEntity(session, Store.class, storeDto);
            session.merge(store);
        });
    }

    @Override
    public void updateStore(String storeId, StoreDto storeDto) {
        entityOperationManager.executeConsumer(session -> {
            Store existingStore = entityOperationManager.getNamedQueryEntity(
                    "Store.findStoreWithAddressByStoreId",
                    parameterFactory.createStringParameter(STORE_ID, storeId));

            Store store = transformationFunctionService.getEntity(session, Store.class, storeDto);
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
            Store existingStore = entityOperationManager.getNamedQueryEntity(
                    "Store.findStoreByStoreId",
                    parameterFactory.createStringParameter(STORE_ID, storeId));

            session.remove(existingStore);
        });
    }

    @Override
    public ResponseStoreDto getResponseStoreByStoreId(String storeId) {
        return entityOperationManager.getNamedQueryEntityDto(
                "Store.findStoreWithAddressArticularItemQuantityByStoreId",
                parameterFactory.createStringParameter(STORE_ID, storeId),
                transformationFunctionService.getTransformationFunction(Store.class, ResponseStoreDto.class)
        );
    }

    @Override
    public List<ResponseStoreDto> getAllResponseStoresByArticularId(String articularId) {
        return entityOperationManager.getSubNamedQueryEntityDtoList(
                "Store.findStoreWithAddressArticularItemQuantityByArticularId",
                parameterFactory.createStringParameter(ARTICULAR_ID, articularId),
                transformationFunctionService.getTransformationFunction(Store.class, ResponseStoreDto.class));
    }

    @Override
    public List<ResponseStoreDto> getAllResponseStoreByCountry(String countryName) {
        return entityOperationManager.getSubNamedQueryEntityDtoList(
                "Store.findStoreWithAddressArticularItemQuantityByCountry",
                parameterFactory.createStringParameter(VALUE, countryName),
                transformationFunctionService.getTransformationFunction(Store.class, ResponseStoreDto.class));
    }

    // fix next
    @Override
    public List<ResponseStoreDto> getAllResponseStoreByCountryAndCity(String countryName, String cityName) {
        return entityOperationManager.getSubNamedQueryEntityDtoList(
                "Store.findStoreWithAddressArticularItemQuantityByCountryCity",
                parameterFactory.createStringParameter(VALUE, countryName),
                transformationFunctionService.getTransformationFunction(Store.class, ResponseStoreDto.class));
    }
}
