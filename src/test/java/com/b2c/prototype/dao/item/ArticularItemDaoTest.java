package com.b2c.prototype.dao.item;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.ArticularStatus;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.modal.entity.item.MetaData;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nimbusds.jose.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.b2c.prototype.util.Converter.getLocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArticularItemDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/item/articular_item/emptyArticularItemDataSet.yml", cleanBefore = true,
            executeStatementsBefore = {
                    "TRUNCATE TABLE brand RESTART IDENTITY CASCADE",
                    "TRUNCATE TABLE item_type RESTART IDENTITY CASCADE",
                    "TRUNCATE TABLE category RESTART IDENTITY CASCADE",
            })
    @ExpectedDataSet(value = "datasets/item/articular_item/saveArticularItemDataSet.yml", orderBy = "id", ignoreCols = {"label", "value"})
    public void persistEntity_success() {
        ArticularItem entity = getArticularItem();
        entity.setId(0);
        entity.getMetaData().setId(0);
        entity.getMetaData().getBrand().setId(0);
        entity.getMetaData().getItemType().setId(0);
        Category category = Category.builder()
                .id(0L)
                .value("parent")
                .label("parent")
                .build();
        entity.getMetaData().setCategory(category);

        entity.getStatus().setId(0);

        entity.getDiscount().setId(0);
        entity.getFullPrice().setId(0);
        entity.getTotalPrice().setId(0);

        entity.getOptionItems().forEach(optionItem -> {
            optionItem.setId(0);
            optionItem.getOptionGroup().setId(0);
        });


        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/item/articular_item/testArticularItemDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/item/articular_item/updateArticularItemDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        ArticularItem entity = getArticularItem();
//        entity.setValue("Update InMail");

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/item/articular_item/testArticularItemDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/item/articular_item/removeArticularItemDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        ArticularItem entity = getArticularItem();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/item/articular_item/testArticularItemDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        ArticularItem expected = getArticularItem();

        Pair<String, Long> pair = Pair.of("id", 1L);
        ArticularItem entity = generalEntityDao.findEntity("ArticularItem.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/item/articular_item/testArticularItemDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        ArticularItem expected = getArticularItem();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<ArticularItem> optionEntity = generalEntityDao.findOptionEntity("ArticularItem.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        ArticularItem entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/item/articular_item/testArticularItemDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        ArticularItem entity = getArticularItem();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<ArticularItem> entityList = generalEntityDao.findEntityList("ArticularItem.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }

    private Category getCategory() {
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

    private MetaData getMetaData() {
        Brand brand = Brand.builder()
                .id(1L)
                .value("Hermes")
                .label("Hermes")
                .build();
        Category category = getCategory();
        ItemType itemType = ItemType.builder()
                .id(1L)
                .value("Clothes")
                .label("Clothes")
                .build();

        Map<String, String> description = new HashMap<>(){{
            put("desc1", "desc1");
            put("desc2", "desc2");
        }};

        return MetaData.builder()
                .id(1L)
                .metadataUniqId("123")
                .description(description)
                .category(category)
                .brand(brand)
                .itemType(itemType)
                .build();
    }

    private ArticularItem getArticularItem() {
        Currency currency = Currency.builder()
                .id(1L)
                .label("USD")
                .value("USD")
                .build();
        OptionGroup optionGroup = OptionGroup.builder()
                .id(1L)
                .value("Size")
                .label("Size")
                .build();
        OptionItem optionItemL = OptionItem.builder()
                .id(1L)
                .value("L")
                .label("L")
                .build();
        OptionItem optionItemM = OptionItem.builder()
                .id(2L)
                .value("M")
                .label("M")
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
                .label("NEW")
                .value("NEW")
                .build();

        MetaData metaData = getMetaData();

        return ArticularItem.builder()
                .id(1L)
                .articularId("123")
                .productName("Mob 1")
                .metaData(metaData)
                .dateOfCreate(getLocalDateTime("2024-03-03 12:00:00"))
                .optionItems(Set.of(optionItemL, optionItemM))
                .status(articularStatus)
                .fullPrice(price1)
                .discount(discount)
                .totalPrice(price2)
                .build();
    }

}
