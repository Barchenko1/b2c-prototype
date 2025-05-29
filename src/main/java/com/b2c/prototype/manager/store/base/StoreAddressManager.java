package com.b2c.prototype.manager.store.base;

import com.b2c.prototype.dao.address.IAddressDao;
import com.b2c.prototype.manager.store.IStoreAddressManager;
import com.b2c.prototype.modal.dto.payload.order.AddressDto;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.store.Store;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.identifier.IQueryService;
import com.tm.core.process.manager.common.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;

import java.util.Optional;

import static com.b2c.prototype.util.Constant.STORE_ID;

public class StoreAddressManager implements IStoreAddressManager {

    private final IEntityOperationManager entityOperationManager;
    private final IQueryService queryService;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;

    public StoreAddressManager(IAddressDao addressDao,
                               IQueryService queryService,
                               ITransformationFunctionService transformationFunctionService,
                               IParameterFactory parameterFactory) {
        this.entityOperationManager = new EntityOperationManager(addressDao);
        this.queryService = queryService;
        this.transformationFunctionService = transformationFunctionService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveUpdateStoreAddress(String storeId, AddressDto addressDto) {
        entityOperationManager.executeConsumer(session -> {
            Store existingStore = queryService.getNamedQueryEntity(
                    session,
                    Store.class,
                    "Store.findStoreWithAddressByStoreId",
                    parameterFactory.createStringParameter(STORE_ID, storeId));

            Address address = transformationFunctionService.getEntity(session, Address.class, addressDto);
            Address existingAddress = existingStore.getAddress();
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
    public AddressDto getResponseStoreAddressByStoreId(String storeId) {
        Store store = entityOperationManager.getNamedQueryEntity(
                "Store.findStoreWithAddressByStoreId",
                parameterFactory.createStringParameter(STORE_ID, storeId));

        return Optional.ofNullable(store)
                .map(s -> transformationFunctionService.getTransformationFunction(Address.class, AddressDto.class).apply(s.getAddress()))
                .orElse(null);
    }
}
