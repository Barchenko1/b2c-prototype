//package com.b2c.prototype.dao.item.base;
//
//import com.b2c.prototype.dao.AbstractSingleEntityDaoTest;
//import com.b2c.prototype.dao.EntityDataSet;
//import com.b2c.prototype.modal.entity.item.ItemQuantity;
//import com.tm.core.dao.identifier.EntityIdentifierDao;
//import com.tm.core.processor.finder.manager.EntityMappingManager;
//import com.tm.core.processor.finder.manager.IEntityMappingManager;
//import com.tm.core.processor.finder.table.EntityTable;
//import org.junit.jupiter.api.BeforeAll;
//
//class BasicItemQuantityDaoTest extends AbstractSingleEntityDaoTest {
//
//    @BeforeAll
//    public static void setup() {
//        IEntityMappingManager entityMappingManager = new EntityMappingManager();
//        entityMappingManager.addEntityTable(new EntityTable(ItemQuantity.class, "item_quantity"));
//        entityIdentifierDao = new EntityIdentifierDao(sessionManager, entityMappingManager);
//        dao = new BasicItemQuantityDao(sessionFactory, entityIdentifierDao);
//    }
//
//    @Override
//    protected String getEmptyDataSetPath() {
//        return "/datasets/item/item_quantity/emptyItemQuantityDataSet.yml";
//    }
//
//    @Override
//    protected EntityDataSet<?> getTestDataSet() {
//        ItemQuantity itemQuantity = ItemQuantity.builder()
//                .id(1L)
////                .itemList()
//                .quantity(1)
//                .build();
//        return new EntityDataSet<>(itemQuantity, "/datasets/item/item_quantity/testItemQuantityDataSet.yml");
//    }
//
//    @Override
//    protected EntityDataSet<?> getSaveDataSet() {
//        ItemQuantity itemQuantity = ItemQuantity.builder()
////                .itemList()
//                .quantity(1)
//                .build();
//        return new EntityDataSet<>(itemQuantity, "/datasets/item/item_quantity/saveItemQuantityDataSet.yml");
//    }
//
//    @Override
//    protected EntityDataSet<?> getUpdateDataSet() {
//        ItemQuantity itemQuantity = ItemQuantity.builder()
//                .id(1L)
////                .itemList()
//                .quantity(1)
//                .build();
//        return new EntityDataSet<>(itemQuantity, "/datasets/item/item_quantity/updateItemQuantityDataSet.yml");
//    }
//}