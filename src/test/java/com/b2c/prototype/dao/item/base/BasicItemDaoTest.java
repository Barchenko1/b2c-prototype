package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.AbstractGeneralEntityDaoTest;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.CurrencyDiscount;
import com.b2c.prototype.modal.entity.item.Item;
import com.b2c.prototype.modal.entity.item.ItemStatus;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.modal.entity.item.Rating;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.tm.core.dao.identifier.EntityIdentifierDao;
import com.tm.core.processor.finder.manager.EntityMappingManager;
import com.tm.core.processor.finder.manager.IEntityMappingManager;
import com.tm.core.processor.finder.table.EntityTable;
import com.tm.core.processor.thread.IThreadLocalSessionManager;
import com.tm.core.processor.thread.ThreadLocalSessionManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

class BasicItemDaoTest extends AbstractGeneralEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IThreadLocalSessionManager sessionManager = new ThreadLocalSessionManager(sessionFactory);
        entityIdentifierDao = new EntityIdentifierDao(sessionManager, getEntityMappingManager());
        dao = new BasicItemDao(sessionFactory, entityIdentifierDao);
    }

    private static IEntityMappingManager getEntityMappingManager() {
        EntityTable brandEntityTable = new EntityTable(Brand.class, "brand");
        EntityTable categoryEntityTable = new EntityTable(Category.class, "category");
        EntityTable discountEntityTable = new EntityTable(CurrencyDiscount.class, "currency_discount");
        EntityTable itemStatusEntityTable = new EntityTable(ItemStatus.class, "item_status");
        EntityTable itemTypeEntityTable = new EntityTable(ItemType.class, "item_type");
        EntityTable optionGroupEntityTable = new EntityTable(OptionGroup.class, "option_group");
        EntityTable optionItemEntityTable = new EntityTable(CurrencyDiscount.class, "option_item");
        EntityTable currencyEntityTable = new EntityTable(Currency.class, "currency");
        EntityTable priceEntityTable = new EntityTable(Price.class, "price");
        EntityTable ratingEntityTable = new EntityTable(Rating.class, "rating");
        EntityTable reviewEntityTable = new EntityTable(ItemStatus.class, "review");
        EntityTable postEntityTable = new EntityTable(ItemType.class, "post");

        EntityTable itemEntityTable = new EntityTable(Item.class, "item");

        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(brandEntityTable);
        entityMappingManager.addEntityTable(categoryEntityTable);
        entityMappingManager.addEntityTable(discountEntityTable);
        entityMappingManager.addEntityTable(itemStatusEntityTable);
        entityMappingManager.addEntityTable(itemTypeEntityTable);
        entityMappingManager.addEntityTable(optionGroupEntityTable);
        entityMappingManager.addEntityTable(optionItemEntityTable);
        entityMappingManager.addEntityTable(currencyEntityTable);
        entityMappingManager.addEntityTable(priceEntityTable);
        entityMappingManager.addEntityTable(ratingEntityTable);
        entityMappingManager.addEntityTable(reviewEntityTable);
        entityMappingManager.addEntityTable(postEntityTable);

        entityMappingManager.addEntityTable(itemEntityTable);
        return entityMappingManager;
    }

    private Item prepareToSaveItem() {
        Brand brand = Brand.builder()
                .id(1L)
                .name("Hermes")
                .build();
        Category category = Category.builder()
                .id(3L)
                .build();
        Currency currency = Currency.builder()
                .id(1L)
                .name("USD")
                .build();
        CurrencyDiscount currencyDiscount = CurrencyDiscount.builder()
                .id(1L)
                .amount(10)
                .charSequenceCode("abc")
                .currency(currency)
                .build();
        ItemStatus itemStatus = ItemStatus.builder()
                .id(1L)
                .name("NEW")
                .build();
        ItemType itemType = ItemType.builder()
                .id(1L)
                .name("Clothes")
                .build();
        OptionGroup optionGroup = OptionGroup.builder()
                .id(1L)
                .name("Size")
                .build();
        OptionItem optionItem1 = OptionItem.builder()
                .id(1L)
                .optionName("L")
                .optionGroup(optionGroup)
                .build();
        OptionItem optionItem2 = OptionItem.builder()
                .id(2L)
                .optionName("M")
                .optionGroup(optionGroup)
                .build();
        Price price = Price.builder()
                .id(1L)
                .amount(100)
                .currency(currency)
                .build();

        return Item.builder()
                .name("name")
                .articularId("100")
                .dateOfCreate(100L)
                .itemType(itemType)
                .category(category)
                .brand(brand)
                .currencyDiscount(currencyDiscount)
                .status(itemStatus)
                .itemType(itemType)
                .price(price)
                .optionItems(List.of(optionItem1, optionItem2))
                .reviews(null)
                .posts(null)
                .build();
    }

    @Test
    public void test() {
        Item item = prepareToSaveItem();
        String test = "";
    }

}