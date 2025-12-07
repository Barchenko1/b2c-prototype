package com.b2c.prototype.manager.store.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.address.Address;

import com.b2c.prototype.modal.dto.payload.store.StoreDto;
import com.b2c.prototype.modal.entity.store.Store;
import com.b2c.prototype.manager.store.IStoreManager;
import com.b2c.prototype.transform.item.IItemTransformService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
import static com.b2c.prototype.util.Constant.CODE;
import static com.b2c.prototype.util.Constant.STORE_ID;
import static com.b2c.prototype.util.Constant.KEY;

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
    @Transactional
    public void saveStore(StoreDto storeDto) {
        Store store = itemTransformService.mapStoreDtoToStore(storeDto);
        generalEntityDao.mergeEntity(store);
    }

    @Override
    @Transactional
    public void updateStore(String tenantId, String storeId, StoreDto storeDto) {
        Store existingStore = generalEntityDao.findEntity(
                "Store.findStoreByRegionStoreIdDetails",
                List.of(Pair.of(CODE, tenantId), Pair.of(STORE_ID, storeId)));

        Store store = itemTransformService.mapStoreDtoToStore(storeDto);
        existingStore.setStoreName(store.getStoreName());
        existingStore.setActive(store.isActive());

        existingStore.setTenant(store.getTenant());

        Address existingAddress = existingStore.getAddress();
        Address address = store.getAddress();
        updateAddress(existingAddress, address);

        generalEntityDao.mergeEntity(existingStore);
    }

    @Override
    @Transactional
    public void deleteStore(String tenantId, String storeId) {
        generalEntityDao.findAndRemoveEntity("Store.findStoreByRegionStoreId",
                List.of(Pair.of(STORE_ID, storeId), Pair.of(CODE, tenantId)));
    }

    @Override
    @Transactional(readOnly = true)
    public StoreDto getStoreByStoreId(String tenantId, String storeId) {
        Store store = generalEntityDao.findEntity(
                "Store.findStoreByRegionStoreIdDetails",
                List.of(Pair.of(STORE_ID, storeId), Pair.of(CODE, tenantId)));
        return itemTransformService.mapStoreToStoreDto(store);
    }

    @Override
    public List<StoreDto> getAllStoresByRegion(String region) {
        List<Store> stores = generalEntityDao.findEntityList(
                "Store.findAllStoreByRegion",
                Pair.of(CODE, region));
        return stores.stream()
                .map(itemTransformService::mapStoreToStoreDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StoreDto> getAllStoresByArticularId(String tenantId, String articularId) {
        List<Store> stores = generalEntityDao.findEntityList(
                "Store.findStoreByRegionStoreIdDetails",
                Pair.of(ARTICULAR_ID, articularId));
        return stores.stream()
                .map(itemTransformService::mapStoreToStoreDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StoreDto> getAllStoreByRegionAndCountry(String tenantId, String countryKey) {
        List<Store> stores = generalEntityDao.findEntityList(
                "Store.findAllStoreByRegionAndCountry",
                List.of(Pair.of(CODE, tenantId), Pair.of(KEY, countryKey)));

        return stores.stream()
                .map(itemTransformService::mapStoreToStoreDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StoreDto> getAllStoreByRegionAndCountryAndCity(String tenantId, String countryKey, String city) {
        List<Store> stores = generalEntityDao.findEntityList(
                "Store.findAllStoreByRegionAndCountryAndCity",
                List.of(Pair.of(CODE, tenantId), Pair.of(KEY, countryKey), Pair.of("city", city)));

        return stores.stream()
                .map(itemTransformService::mapStoreToStoreDto)
                .toList();
    }

    private void updateAddress(Address target, Address source) {
        target.setCountry(source.getCountry());
        target.setCity(source.getCity());
        target.setStreet(source.getStreet());
        target.setBuildingNumber(source.getBuildingNumber());
        target.setFlorNumber(source.getFlorNumber());
        target.setApartmentNumber(source.getApartmentNumber());
        target.setZipCode(source.getZipCode());
    }
}
