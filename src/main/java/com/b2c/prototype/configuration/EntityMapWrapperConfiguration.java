package com.b2c.prototype.configuration;

import com.b2c.prototype.dao.delivery.IDeliveryTypeDao;
import com.b2c.prototype.dao.item.IBrandDao;
import com.b2c.prototype.dao.item.ICategoryDao;
import com.b2c.prototype.dao.item.IItemStatusDao;
import com.b2c.prototype.dao.item.IItemTypeDao;
import com.b2c.prototype.dao.option.IOptionGroupDao;
import com.b2c.prototype.dao.order.IOrderStatusDao;
import com.b2c.prototype.dao.payment.IPaymentMethodDao;
import com.b2c.prototype.dao.rating.IRatingDao;
import com.b2c.prototype.dao.cashed.EntityCachedMap;
import com.b2c.prototype.dao.cashed.IEntityCachedMap;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.ItemStatus;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.modal.entity.item.Rating;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import com.b2c.prototype.dao.cashed.IEntityIntegerMapWrapper;
import com.b2c.prototype.dao.cashed.IEntityStringMapWrapper;
import com.b2c.prototype.dao.cashed.base.BrandMapWrapper;
import com.b2c.prototype.dao.cashed.base.CategoryMapWrapper;
import com.b2c.prototype.dao.cashed.base.DeliveryTypeMapWrapper;
import com.b2c.prototype.dao.cashed.base.ItemMapWrapper;
import com.b2c.prototype.dao.cashed.base.ItemTypeMapWrapper;
import com.b2c.prototype.dao.cashed.base.OptionGroupMapWrapper;
import com.b2c.prototype.dao.cashed.base.OrderStatusMapWrapper;
import com.b2c.prototype.dao.cashed.base.PaymentMethodMapWrapper;
import com.b2c.prototype.dao.cashed.base.RatingEntityWrapper;
import com.tm.core.dao.identifier.IEntityIdentifierDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.Map;

@Configuration
@DependsOn({"constantBeanConfiguration"})
public class EntityMapWrapperConfiguration {

    private final Map<String, DeliveryType> deliveryTypeMap;
    private final IDeliveryTypeDao deliveryTypeDao;
    private final Map<String, PaymentMethod> paymentMethodMap;
    private final IPaymentMethodDao paymentMethodDao;
    private final Map<String, OrderStatus> orderStatusMap;
    private final IOrderStatusDao orderStatusDao;
    private final Map<String, Category> categoryMap;
    private final ICategoryDao categoryDao;
    private final Map<String, ItemType> itemTypeMap;
    private final IItemTypeDao itemTypeDao;
    private final Map<String, Brand> brandMap;
    private final IBrandDao brandDao;
    private final Map<Integer, Rating> ratingMap;
    private final IRatingDao ratingDao;
    private final Map<String, ItemStatus> itemStatusMap;
    private final IItemStatusDao itemStatusDao;
    private final Map<String, OptionGroup> optionGroupMap;
    private final IOptionGroupDao optionGroupDao;

    public EntityMapWrapperConfiguration(Map<String, DeliveryType> deliveryTypeMap, IDeliveryTypeDao deliveryTypeDao, Map<String, PaymentMethod> paymentMethodMap, IPaymentMethodDao paymentMethodDao, Map<String, OrderStatus> orderStatusMap, IOrderStatusDao orderStatusDao, Map<String, Category> categoryMap, ICategoryDao categoryDao, Map<String, ItemType> itemTypeMap, IItemTypeDao itemTypeDao, Map<String, Brand> brandMap, IBrandDao brandDao, Map<Integer, Rating> ratingMap, IRatingDao ratingDao, Map<String, ItemStatus> itemStatusMap, IItemStatusDao itemStatusDao, Map<String, OptionGroup> optionGroupMap, IOptionGroupDao optionGroupDao) {
        this.deliveryTypeMap = deliveryTypeMap;
        this.deliveryTypeDao = deliveryTypeDao;
        this.paymentMethodMap = paymentMethodMap;
        this.paymentMethodDao = paymentMethodDao;
        this.orderStatusMap = orderStatusMap;
        this.orderStatusDao = orderStatusDao;
        this.categoryMap = categoryMap;
        this.categoryDao = categoryDao;
        this.itemTypeMap = itemTypeMap;
        this.itemTypeDao = itemTypeDao;
        this.brandMap = brandMap;
        this.brandDao = brandDao;
        this.ratingMap = ratingMap;
        this.ratingDao = ratingDao;
        this.itemStatusMap = itemStatusMap;
        this.itemStatusDao = itemStatusDao;
        this.optionGroupMap = optionGroupMap;
        this.optionGroupDao = optionGroupDao;
    }

    @Bean
    public IEntityStringMapWrapper<DeliveryType> deliveryTypeMapWrapper() {
        return new DeliveryTypeMapWrapper(
                deliveryTypeDao,
                deliveryTypeMap
        );
    }

    @Bean
    public IEntityStringMapWrapper<PaymentMethod> paymentMethodMapWrapper() {
        return new PaymentMethodMapWrapper(
                paymentMethodDao,
                paymentMethodMap
        );
    }

    @Bean
    public IEntityStringMapWrapper<OrderStatus> orderStatusMapWrapper() {
        return new OrderStatusMapWrapper(
                orderStatusDao,
                orderStatusMap
        );
    }

    @Bean
    public IEntityStringMapWrapper<Category> categoryMapWrapper() {
        return new CategoryMapWrapper(
                categoryDao,
                categoryMap
        );
    }

    @Bean
    public IEntityStringMapWrapper<ItemType> itemTypeMapWrapper() {
        return new ItemTypeMapWrapper(
                itemTypeDao,
                itemTypeMap
        );
    }

    @Bean
    public IEntityStringMapWrapper<Brand> brandMapWrapper() {
        return new BrandMapWrapper(
                brandDao,
                brandMap
        );
    }

    @Bean
    public IEntityStringMapWrapper<ItemStatus> itemStatusMapWrapper() {
        return new ItemMapWrapper(
                itemStatusDao,
                itemStatusMap
        );
    }

    @Bean
    public IEntityStringMapWrapper<OptionGroup> optionGroupMapWrapper() {
        return new OptionGroupMapWrapper(
                optionGroupDao,
                optionGroupMap
        );
    }

    @Bean
    public IEntityIntegerMapWrapper<Rating> ratingMapWrapper() {
        return new RatingEntityWrapper(
                ratingDao,
                ratingMap
        );
    }

    @Bean
    public IEntityCachedMap entityCachedMap(Map<Class<?>, Map<?, ?>> classEntityMap,
                                            IEntityIdentifierDao entityIdentifierDao) {
        return new EntityCachedMap(classEntityMap ,entityIdentifierDao);
    }
}
