package com.b2c.prototype.manager.store.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.address.Address;

import com.b2c.prototype.modal.dto.payload.store.StoreDto;
import com.b2c.prototype.modal.dto.payload.store.ResponseStoreDto;
import com.b2c.prototype.modal.entity.store.Store;
import com.b2c.prototype.manager.store.IStoreManager;
import com.b2c.prototype.transform.item.IItemTransformService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
import static com.b2c.prototype.util.Constant.STORE_ID;
import static com.b2c.prototype.util.Constant.VALUE;

@Service
public class StoreManager implements IStoreManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IItemTransformService itemTransformService;

    public StoreManager(IGeneralEntityDao generalEntityDao,
                        IItemTransformService itemTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.itemTransformService = itemTransformService;
    }

    @Override
    public void saveStore(StoreDto storeDto) {
        Store store = itemTransformService.mapStoreDtoToStore(storeDto);
        generalEntityDao.mergeEntity(store);
    }

    @Override
    public void updateStore(String storeId, StoreDto storeDto) {
        Store existingStore = generalEntityDao.findEntity(
                "Store.findStoreWithAddressByStoreId",
                Pair.of(STORE_ID, storeId));

        Store store = itemTransformService.mapStoreDtoToStore(storeDto);
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
        generalEntityDao.mergeEntity(existingStore);
    }

    @Override
    public void deleteStore(String storeId) {
        Store existingStore = generalEntityDao.findEntity(
                "Store.findStoreByStoreId",
                Pair.of(STORE_ID, storeId));

        generalEntityDao.removeEntity(existingStore);
    }

    @Override
    public ResponseStoreDto getResponseStoreByStoreId(String storeId) {
        Store store = generalEntityDao.findEntity(
                "Store.findStoreWithAddressArticularItemQuantityByStoreId",
                Pair.of(STORE_ID, storeId));
        return itemTransformService.mapStoreToResponseStoreDto(store);
    }

    @Override
    public List<ResponseStoreDto> getAllResponseStoresByArticularId(String articularId) {
        List<Store> stores = generalEntityDao.findEntityList(
                "Store.findStoreWithAddressArticularItemQuantityByArticularId",
                Pair.of(ARTICULAR_ID, articularId));
        return stores.stream()
                .map(itemTransformService::mapStoreToResponseStoreDto)
                .toList();
    }

    @Override
    public List<ResponseStoreDto> getAllResponseStoreByCountry(String countryName) {
        List<Store> stores = generalEntityDao.findEntityList(
                "Store.findStoreWithAddressArticularItemQuantityByCountry",
                Pair.of(VALUE, countryName));

        return stores.stream()
                .map(itemTransformService::mapStoreToResponseStoreDto)
                .toList();
    }

    // fix next
    @Override
    public List<ResponseStoreDto> getAllResponseStoreByCountryAndCity(String countryName, String cityName) {
        List<Store> stores = generalEntityDao.findEntityList(
                "Store.findStoreWithAddressArticularItemQuantityByCountryCity",
                Pair.of(VALUE, countryName));

        return stores.stream()
                .map(itemTransformService::mapStoreToResponseStoreDto)
                .toList();
    }
}
