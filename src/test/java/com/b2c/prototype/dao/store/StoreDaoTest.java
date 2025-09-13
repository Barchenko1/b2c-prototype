package com.b2c.prototype.dao.store;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.item.ArticularItemQuantity;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.MetaData;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.ArticularStatus;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.modal.entity.store.Store;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nimbusds.jose.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Converter.getLocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StoreDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/store/store/emptyStoreDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/store/store/saveStoreDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        Store entity = getStore();
        entity.setId(0L);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/store/store/testStoreDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/store/store/updateStoreDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        Store entity = getStore();

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/store/store/testStoreDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/store/store/emptyStoreDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        Store entity = getStore();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/store/store/testStoreDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        Store expected = getStore();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Store entity = generalEntityDao.findEntity("Store.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/store/store/testStoreDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        Store expected = getStore();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<Store> optionEntity = generalEntityDao.findOptionEntity("Store.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        Store entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/store/store/testStoreDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        Store entity = getStore();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<Store> entityList = generalEntityDao.findEntityList("Store.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }
    
    private Store getStore() {
        ArticularItemQuantity articularItemQuantity = ArticularItemQuantity.builder()
                .id(1L)
                .articularItem(getArticularItem())
                .quantity(1)
                .build();
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
                .city("city")
                .build();
        return Store.builder()
                .id(1L)
//                .articularStocks(List.of(articularItemQuantity))
                .address(address)
                .storeId("123")
                .storeName("Store")
                .isActive(true)
                .build();
    }

    private Category prepareCategories() {
        Category parent = Category.builder()
                .id(1L)
                .label("parent")
                .value("parent")
                .build();
        Category root = Category.builder()
                .id(2L)
                .label("root")
                .value("root")
                .parent(parent)
                .build();
        Category child = Category.builder()
                .id(3L)
                .label("child")
                .value("child")
                .build();

        parent.setChildList(List.of(root));
        root.setParent(parent);
        root.setChildList(List.of(child));
        child.setParent(root);

        return child;
    }

    private ArticularItem getArticularItem() {
        Brand brand = Brand.builder()
                .id(1L)
                .value("Hermes")
                .label("Hermes")
                .build();
        Category category = prepareCategories();
        Currency currency = Currency.builder()
                .id(1L)
                .label("USD")
                .value("USD")
                .build();
        ArticularStatus articularStatus = ArticularStatus.builder()
                .id(1L)
                .value("NEW")
                .label("NEW")
                .build();
        ItemType itemType = ItemType.builder()
                .id(1L)
                .value("Clothes")
                .label("Clothes")
                .build();
        OptionItem optionItem = OptionItem.builder()
                .id(1L)
                .value("L")
                .label("L")
                .build();
        OptionGroup optionGroup = OptionGroup.builder()
                .id(1L)
                .value("Size")
                .label("Size")
                .build();
        optionGroup.addOptionItem(optionItem);
        Price price = Price.builder()
                .id(1L)
                .amount(100)
                .currency(currency)
                .build();

        MetaData metaData = MetaData.builder()
                .id(1L)
                .category(category)
                .brand(brand)
                .itemType(itemType)
                .build();
        ArticularItem articularItem = ArticularItem.builder()
                .id(1L)
                .articularId("1")
                .dateOfCreate(getLocalDateTime("2024-03-03 12:00:00"))
                .articularId("1")
                .build();
        articularItem.addOptionItem(optionItem);

        return articularItem;
    }
}