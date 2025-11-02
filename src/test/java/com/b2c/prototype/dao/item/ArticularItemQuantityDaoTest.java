package com.b2c.prototype.dao.item;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.ArticularItemQuantity;
import com.b2c.prototype.modal.entity.item.ArticularStatus;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
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

class ArticularItemQuantityDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/dao/item/articular_item_quantity/emptyArticularItemQuantityDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/item/articular_item_quantity/saveArticularItemQuantityDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        ArticularItemQuantity entity = getArticularItemQuantityPrice();
        entity.setId(0);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/item/articular_item_quantity/testArticularItemQuantityDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/item/articular_item_quantity/updateArticularItemQuantityDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        ArticularItemQuantity entity = getArticularItemQuantityPrice();
//        entity.setValue("Update InMail");

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/item/articular_item_quantity/testArticularItemQuantityDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/item/articular_item_quantity/emptyArticularItemQuantityDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        ArticularItemQuantity entity = getArticularItemQuantityPrice();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/item/articular_item_quantity/testArticularItemQuantityDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        ArticularItemQuantity expected = getArticularItemQuantityPrice();

        Pair<String, Long> pair = Pair.of("id", 1L);
        ArticularItemQuantity entity = generalEntityDao.findEntity("ArticularItemQuantity.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/item/articular_item_quantity/testArticularItemQuantityDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        ArticularItemQuantity expected = getArticularItemQuantityPrice();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<ArticularItemQuantity> optionEntity = generalEntityDao.findOptionEntity("ArticularItemQuantity.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        ArticularItemQuantity entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/item/articular_item_quantity/testArticularItemQuantityDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        ArticularItemQuantity entity = getArticularItemQuantityPrice();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<ArticularItemQuantity> entityList = generalEntityDao.findEntityList("ArticularItemQuantity.findById", List.of(pair));

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
    
    private ArticularItemQuantity getArticularItemQuantityPrice() {
        return ArticularItemQuantity.builder()
                .id(1L)
                .articularItem(getArticularItem())
                .quantity(1)
                .build();
    }
}