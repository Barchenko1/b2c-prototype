package com.b2c.prototype.dao.address;

import com.b2c.prototype.dao.AbstractConstantEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import com.tm.core.process.dao.query.QueryService;
import com.tm.core.finder.manager.EntityMappingManager;
import com.tm.core.finder.manager.IEntityMappingManager;
import com.tm.core.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;

class BasicAddressDaoTest extends AbstractConstantEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(Address.class, "address"));
        queryService = new QueryService(entityMappingManager);
        dao = new BasicAddressDao(sessionFactory, queryService);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/order/address/emptyAddressDataSet.yml";
    }

    @Override
    protected EntityDataSet<?> getTestDataSet() {
        Country country = Country.builder()
                .id(1L)
                .label("USA")
                .value("USA")
                .build();
        Address address = Address.builder()
                .id(1L)
                .country(country)
                .street("street")
                .buildingNumber("1")
                .apartmentNumber(101)
                .florNumber(9)
                .zipCode("90000")
                .build();
        return new EntityDataSet<>(address, "/datasets/order/address/testAddressDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        Country country = Country.builder()
                .id(1L)
                .label("USA")
                .value("USA")
                .build();
        Address address = Address.builder()
                .country(country)
                .street("street")
                .buildingNumber("1")
                .apartmentNumber(101)
                .florNumber(9)
                .zipCode("90000")
                .build();
        return new EntityDataSet<>(address, "/datasets/order/address/saveAddressDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        Country country = Country.builder()
                .id(1L)
                .label("USA")
                .value("USA")
                .build();
        Address address = Address.builder()
                .id(1L)
                .country(country)
                .street("Update street")
                .buildingNumber("1")
                .apartmentNumber(102)
                .florNumber(9)
                .zipCode("90001")
                .build();
        return new EntityDataSet<>(address, "/datasets/order/address/updateAddressDataSet.yml");
    }

}