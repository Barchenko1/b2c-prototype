package com.b2c.prototype.dao.store;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.constant.CountType;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.item.ArticularItemQuantity;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.ArticularStatus;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.modal.entity.store.ArticularStock;
import com.b2c.prototype.modal.entity.store.AvailabilityStatus;
import com.b2c.prototype.modal.entity.store.Store;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nimbusds.jose.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.b2c.prototype.util.Converter.getLocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StoreDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/dao/store/store/emptyStoreDataSet.yml", cleanBefore = true,
            executeStatementsBefore = {
                    "TRUNCATE TABLE address RESTART IDENTITY CASCADE",
            })
    @ExpectedDataSet(value = "datasets/dao/store/store/saveStoreDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        Store entity = getStore();
        entity.setId(0L);
        entity.getAddress().setId(0);
        entity.getArticularStocks().forEach(stock -> {
            stock.setId(0);
            stock.getArticularItemQuantities().forEach(itemQuantities -> {
                itemQuantities.setId(0);
            });
        });

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/store/store/testStoreDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/store/store/updateStoreDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        Store entity = getStore();
        entity.setStoreName("Update Store Name");

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/store/store/testStoreDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/store/store/emptyStoreDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        Store entity = getStore();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/store/store/testStoreDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        Store expected = getStore();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Store entity = generalEntityDao.findEntity("Store.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/store/store/testStoreDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        Store expected = getStore();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<Store> optionEntity = generalEntityDao.findOptionEntity("Store.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        Store entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/store/store/testStoreDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        Store entity = getStore();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<Store> entityList = generalEntityDao.findEntityList("Store.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }


    private ArticularItem getArticularItem() {
        Currency currency = Currency.builder()
                .id(1L)
                .key("USD")
                .value("USD")
                .build();
        OptionGroup optionGroup = OptionGroup.builder()
                .id(1L)
                .value("Size")
                .key("Size")
                .build();
        OptionItem optionItemL = OptionItem.builder()
                .id(1L)
                .value("L")
                .key("L")
                .build();
        OptionItem optionItemM = OptionItem.builder()
                .id(2L)
                .value("M")
                .key("M")
                .build();

        optionGroup.addOptionItem(optionItemL);
        optionGroup.addOptionItem(optionItemM);
        Price price1 = Price.builder()
                .id(1L)
                .amount(100)
                .currency(currency)
                .build();
        Price price2 = Price.builder()
                .id(2L)
                .amount(20)
                .currency(currency)
                .build();
        Discount discount = Discount.builder()
                .id(1L)
                .amount(5)
                .charSequenceCode("abc")
                .isActive(true)
                .isPercent(false)
                .currency(currency)
                .build();
        ArticularStatus articularStatus = ArticularStatus.builder()
                .id(1L)
                .key("NEW")
                .value("NEW")
                .build();

        return ArticularItem.builder()
                .id(1L)
                .articularUniqId("123")
                .productName("Mob 1")
                .dateOfCreate(getLocalDateTime("2024-03-03 12:00:00"))
                .optionItems(Set.of(optionItemL, optionItemM))
                .status(articularStatus)
                .fullPrice(price1)
                .discount(discount)
                .totalPrice(price2)
                .build();
    }

    private ArticularItemQuantity getArticularItemQuantity() {
        return ArticularItemQuantity.builder()
                .id(1L)
                .articularItem(getArticularItem())
                .quantity(1)
                .build();
    }

    private ArticularStock getArticularStock() {
        AvailabilityStatus availabilityStatus = AvailabilityStatus.builder()
                .id(1L)
                .value("Available")
                .key("Available")
                .build();
        return ArticularStock.builder()
                .id(1L)
                .articularItemQuantities(List.of(getArticularItemQuantity()))
                .availabilityState(availabilityStatus)
                .countType(CountType.LIMITED)
                .build();
    }

    private Store getStore() {
        Country country = Country.builder()
                .id(1L)
                .key("USA")
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
                .city("city")
                .build();
        return Store.builder()
                .id(1L)
                .articularStocks(List.of(getArticularStock()))
                .address(address)
                .storeUniqId("123")
                .storeName("Store")
                .isActive(true)
                .build();
    }

}
