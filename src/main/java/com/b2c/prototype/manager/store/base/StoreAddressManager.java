package com.b2c.prototype.manager.store.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.manager.store.IStoreAddressManager;
import com.b2c.prototype.modal.dto.payload.order.AddressDto;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.store.Store;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.b2c.prototype.util.Constant.STORE_ID;

@Service
public class StoreAddressManager implements IStoreAddressManager {

    private final IGeneralEntityDao generalEntityDao;
    private final ITransformationFunctionService transformationFunctionService;

    public StoreAddressManager(IGeneralEntityDao generalEntityDao,
                               ITransformationFunctionService transformationFunctionService) {
        this.generalEntityDao = generalEntityDao;
        this.transformationFunctionService = transformationFunctionService;
    }

    @Override
    public void saveUpdateStoreAddress(String storeId, AddressDto addressDto) {
        Store existingStore = generalEntityDao.findEntity(
                "Store.findStoreWithAddressByStoreId",
                Pair.of(STORE_ID, storeId));

        Address address = transformationFunctionService.getEntity(Address.class, addressDto);
        Address existingAddress = existingStore.getAddress();
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
    public AddressDto getResponseStoreAddressByStoreId(String storeId) {
        Store store = generalEntityDao.findEntity(
                "Store.findStoreWithAddressByStoreId",
                Pair.of(STORE_ID, storeId));

        return Optional.ofNullable(store)
                .map(s -> transformationFunctionService.getTransformationFunction(Address.class, AddressDto.class).apply(s.getAddress()))
                .orElse(null);
    }
}
