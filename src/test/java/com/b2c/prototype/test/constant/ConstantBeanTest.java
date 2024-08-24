//package com.b2c.prototype.test.constant;
//
//import com.b2c.prototype.configuration.AbstractBeanTest;
//import com.b2c.prototype.configuration.BeanConfiguration;
//import com.b2c.prototype.configuration.ConstantBeanConfiguration;
//import com.b2c.prototype.mapper.TestUtil;
//import com.b2c.prototype.modal.constant.OptionGroupEnum;
//import com.b2c.prototype.modal.constant.OrderStatusEnum;
//import com.b2c.prototype.modal.constant.ItemStatusEnum;
//import com.b2c.prototype.modal.constant.RateEnum;
//import com.b2c.prototype.modal.option.entity.OptionGroup;
//import com.b2c.prototype.modal.order.entity.OrderStatus;
//import com.b2c.prototype.modal.item.entity.Brand;
//import com.b2c.prototype.modal.item.entity.Category;
//import com.b2c.prototype.modal.item.entity.ItemStatus;
//import com.b2c.prototype.modal.item.entity.ItemType;
//import com.b2c.prototype.modal.item.entity.Rating;
//import com.github.database.rider.core.api.connection.ConnectionHolder;
//import com.github.database.rider.core.api.dataset.DataSet;
//import com.github.database.rider.junit5.DBUnitExtension;
//import org.dbunit.operation.DatabaseOperation;
//import org.hibernate.SessionFactory;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//
//import java.util.Map;
//
//@ExtendWith({DBUnitExtension.class})
//public class ConstantBeanTest extends AbstractBeanTest {
//    private static ConnectionHolder connectionHolder;
//
//    @BeforeAll
//    public static void getSessionFactory() {
//        connectionHolder = dataSource::getConnection;
//
////        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT, "/data/constant/initUserRoleDataSet.xml");
//        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT, "/data/constant/initCategoryDataSet.xml");
//        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT, "/data/constant/initOrderStatusDataSet.xml");
//        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT, "/data/constant/initInitTypeDataSet.xml");
//        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT, "/data/constant/initBrandDataSet.xml");
//        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT, "/data/constant/initRatingDataSet.xml");
//        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT, "/data/constant/initItemStatusDataSet.xml");
//        TestUtil.prepareTestEntityDb(dataSource, DatabaseOperation.CLEAN_INSERT, "/data/constant/initOptionGroupDataSet.xml");
//
//        context = new AnnotationConfigApplicationContext(BeanConfiguration.class, ConstantBeanConfiguration.class);
//        sessionFactory = context.getBean("sessionFactory", SessionFactory.class);
//    }
//
//    @Test
//    @DataSet(cleanBefore = true, cleanAfter = true)
//    void brandConstantDaoBeanTest() {
//        Map<String, Brand> brandMap = context.getBean("brandMap", Map.class);
//        Assertions.assertFalse(brandMap.isEmpty());
//    }
//
//    @Test
//    void categoryConstantDaoBeanTest() {
//        Map<String, Category> categoryMap = context.getBean("categoryMap", Map.class);
//        Assertions.assertFalse(categoryMap.isEmpty());
//    }
//
//    @Test
//    void orderStatusConstantDaoBeanTest() {
//        Map<OrderStatusEnum, OrderStatus> orderStatusMap = context.getBean("orderStatusMap", Map.class);
//        Assertions.assertFalse(orderStatusMap.isEmpty());
//    }
//
//    @Test
//    void itemStatusConstantDaoBeanTest() {
//        Map<ItemStatusEnum, ItemStatus> itemStatusMap = context.getBean("itemStatusMap", Map.class);
//        Assertions.assertFalse(itemStatusMap.isEmpty());
//    }
//
//    @Test
//    void itemTypeConstantDaoBeanTest() {
//        Map<String, ItemType> itemTypeMap = context.getBean("itemTypeMap", Map.class);
//        Assertions.assertFalse(itemTypeMap.isEmpty());
//    }
//
//    @Test
//    void ratingConstantDaoBeanTest() {
//        Map<RateEnum, Rating> ratingMap = context.getBean("ratingMap", Map.class);
//        Assertions.assertFalse(ratingMap.isEmpty());
//    }
//
//    @Test
//    void optionGroupConstantDaoBeanTest() {
//        Map<OptionGroupEnum, OptionGroup> optionGroupMap = context.getBean("optionGroupMap", Map.class);
//        Assertions.assertFalse(optionGroupMap.isEmpty());
//    }
//
//}
