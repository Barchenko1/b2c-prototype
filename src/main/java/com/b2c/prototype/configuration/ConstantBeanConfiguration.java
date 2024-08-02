package com.b2c.prototype.configuration;

import com.b2c.prototype.dao.delivery.IDeliveryTypeDao;
import com.b2c.prototype.dao.payment.IPaymentMethodDao;
import com.b2c.prototype.modal.client.entity.delivery.DeliveryType;
import com.b2c.prototype.modal.client.entity.payment.PaymentMethod;
import com.b2c.prototype.modal.client.entity.option.OptionGroup;
import com.b2c.prototype.modal.client.entity.order.OrderStatus;
import com.b2c.prototype.modal.client.entity.item.Brand;
import com.b2c.prototype.modal.client.entity.item.Category;
import com.b2c.prototype.modal.client.entity.item.ItemStatus;
import com.b2c.prototype.modal.client.entity.item.ItemType;
import com.b2c.prototype.modal.client.entity.item.Rating;
import com.b2c.prototype.dao.option.IOptionGroupDao;
import com.b2c.prototype.dao.order.IOrderStatusDao;
import com.b2c.prototype.dao.item.IBrandDao;
import com.b2c.prototype.dao.item.ICategoryDao;
import com.b2c.prototype.dao.item.IItemStatusDao;
import com.b2c.prototype.dao.item.IItemTypeDao;
import com.b2c.prototype.dao.rating.IRatingDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.b2c.prototype.util.Query.SELECT_ALL_BRANDS;
import static com.b2c.prototype.util.Query.SELECT_ALL_CATEGORIES;
import static com.b2c.prototype.util.Query.SELECT_ALL_DELIVERY_TYPE;
import static com.b2c.prototype.util.Query.SELECT_ALL_ITEM_STATUS;
import static com.b2c.prototype.util.Query.SELECT_ALL_ITEM_TYPE;
import static com.b2c.prototype.util.Query.SELECT_ALL_OPTION_GROUP;
import static com.b2c.prototype.util.Query.SELECT_ALL_ORDER_STATUS;
import static com.b2c.prototype.util.Query.SELECT_ALL_PAYMENT_METHOD;

@Configuration
@DependsOn({"initializeConstantDbConfiguration"})
public class ConstantBeanConfiguration {

    @Bean
    public Map<String, DeliveryType> deliveryTypeMap(IDeliveryTypeDao deliveryTypeDao) {
        List<DeliveryType> userRoleList =
                deliveryTypeDao.getEntityListBySQLQuery(SELECT_ALL_DELIVERY_TYPE);
        return userRoleList.stream()
                .collect(Collectors.toMap(DeliveryType::getName, Function.identity(), (existing, replacement) -> existing));
    }

    @Bean
    public Map<String, PaymentMethod> paymentMethodMap(IPaymentMethodDao paymentMethodDao) {
        List<PaymentMethod> paymentMethodList =
                paymentMethodDao.getEntityListBySQLQuery(SELECT_ALL_PAYMENT_METHOD);
        return paymentMethodList.stream()
                .collect(Collectors.toMap(PaymentMethod::getMethod, Function.identity(), (existing, replacement) -> existing));
    }

    @Bean
    public Map<String, OrderStatus> orderStatusMap(IOrderStatusDao orderStatusDao) {
        List<OrderStatus> orderStatusList =
                orderStatusDao.getEntityListBySQLQuery(SELECT_ALL_ORDER_STATUS);
        return orderStatusList.stream()
                .collect(Collectors.toMap(OrderStatus::getName, Function.identity(), (existing, replacement) -> existing));
    }

    @Bean
    public Map<String, Category> categoryMap(ICategoryDao categoryDao) {
        List<Category> categoryList =
                categoryDao.getEntityListBySQLQuery(SELECT_ALL_CATEGORIES);
        return categoryList.stream()
                .collect(Collectors.toMap(Category::getName, category -> category, (existing, replacement) -> existing));
    }

    @Bean
    public Map<String, ItemType> itemTypeMap(IItemTypeDao itemTypeDao) {
        List<ItemType> itemTypeList =
                itemTypeDao.getEntityListBySQLQuery(SELECT_ALL_ITEM_TYPE);
        return itemTypeList.stream()
                .collect(Collectors.toMap(ItemType::getName, itemType -> itemType, (existing, replacement) -> existing));
    }

    @Bean
    public Map<String, Brand> brandMap(IBrandDao brandDao) {
        List<Brand> brandList =
                brandDao.getEntityListBySQLQuery(SELECT_ALL_BRANDS);
        return brandList.stream()
                .collect(Collectors.toMap(Brand::getName, brand -> brand, (existing, replacement) -> existing));
    }

    @Bean
    public Map<Integer, Rating> ratingMap(IRatingDao ratingDao) {
        List<Rating> ratingList =
                ratingDao.getEntityListBySQLQuery("SELECT * FROM rating r");
        return ratingList.stream()
                .collect(Collectors.toMap(Rating::getValue, value -> value, (existing, replacement) -> existing));
    }

    @Bean
    public Map<String, ItemStatus> itemStatusMap(IItemStatusDao itemStatusDao) {
        List<ItemStatus> itemStatusList =
                itemStatusDao.getEntityListBySQLQuery(SELECT_ALL_ITEM_STATUS);
        return itemStatusList.stream()
                .collect(Collectors.toMap(ItemStatus::getName, itemStatus -> itemStatus, (existing, replacement) -> existing));
    }

    @Bean
    public Map<String, OptionGroup> optionGroupMap(IOptionGroupDao optionGroupDao) {
        List<OptionGroup> optionGroupList =
                optionGroupDao.getEntityListBySQLQuery(SELECT_ALL_OPTION_GROUP);
        return optionGroupList.stream()
                .collect(Collectors.toMap(OptionGroup::getName, optionGroup -> optionGroup, (existing, replacement) -> existing));
    }

    ////

//    @Bean
//    public Set<String> itemTypeSet(IItemTypeDao itemTypeDao) {
//        List<ItemType> itemTypeList = itemTypeDao.getEntityListBySQLQuery(SELECT_ALL_ITEM_TYPE);
//        return itemTypeList.stream()
//                .map(ItemType::getName)
//                .collect(Collectors.toSet());
//    }
//
//    @Bean
//    public Set<String> itemStatusSet(IItemStatusDao itemStatusDao) {
//        List<ItemStatus> itemStatusList =
//                itemStatusDao.getEntityListBySQLQuery(SELECT_ALL_ITEM_STATUS);
//        return itemStatusList.stream()
//                .map(ItemStatus::getName)
//                .collect(Collectors.toSet());
//    }
//
//    @Bean
//    public Set<String> optionGroupSet(IOptionGroupDao optionGroupDao) {
//        List<OptionGroup> optionGroupList =
//                optionGroupDao.getEntityListBySQLQuery(SELECT_ALL_OPTION_GROUP);
//        return optionGroupList.stream()
//                .map(OptionGroup::getName)
//                .collect(Collectors.toSet());
//    }

}
