package com.b2c.prototype.configuration;

import com.b2c.prototype.dao.address.ICountryDao;
import com.b2c.prototype.dao.delivery.IDeliveryTypeDao;
import com.b2c.prototype.dao.message.IMessageStatusDao;
import com.b2c.prototype.dao.message.IMessageTypeDao;
import com.b2c.prototype.dao.payment.IPaymentMethodDao;
import com.b2c.prototype.dao.price.ICurrencyDao;
import com.b2c.prototype.dao.user.ICountryPhoneCodeDao;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.modal.entity.item.ArticularStatus;
import com.b2c.prototype.modal.entity.message.MessageStatus;
import com.b2c.prototype.modal.entity.message.MessageType;
import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.modal.entity.item.Rating;
import com.b2c.prototype.dao.option.IOptionGroupDao;
import com.b2c.prototype.dao.order.IOrderStatusDao;
import com.b2c.prototype.dao.item.IBrandDao;
import com.b2c.prototype.dao.item.ICategoryDao;
import com.b2c.prototype.dao.item.IItemStatusDao;
import com.b2c.prototype.dao.item.IItemTypeDao;
import com.b2c.prototype.dao.rating.IRatingDao;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
@DependsOn({"initializeConstantDbConfiguration"})
public class ConstantBeanConfiguration {

    @Bean
    public Map<String, DeliveryType> deliveryTypeMap(IDeliveryTypeDao deliveryTypeDao) {
        List<DeliveryType> userRoleList = deliveryTypeDao.getEntityList();
        return userRoleList.stream()
                .collect(Collectors.toMap(DeliveryType::getValue, Function.identity(), (existing, replacement) -> existing));
    }

    @Bean
    public Map<String, PaymentMethod> paymentMethodMap(IPaymentMethodDao paymentMethodDao) {
        List<PaymentMethod> paymentMethodList = paymentMethodDao.getEntityList();
        return paymentMethodList.stream()
                .collect(Collectors.toMap(PaymentMethod::getValue, Function.identity(), (existing, replacement) -> existing));
    }

    @Bean
    public Map<String, OrderStatus> orderStatusMap(IOrderStatusDao orderStatusDao) {
        List<OrderStatus> orderStatusList = orderStatusDao.getEntityList();
        return orderStatusList.stream()
                .collect(Collectors.toMap(OrderStatus::getValue, Function.identity(), (existing, replacement) -> existing));
    }

    @Bean
    public Map<String, Category> categoryMap(ICategoryDao categoryDao) {
        List<Category> categoryList = categoryDao.getTransitiveSelfEntityList();
        return categoryList.stream()
                .collect(Collectors.toMap(Category::getName, category -> category, (existing, replacement) -> existing));
    }

    @Bean
    public Map<String, ItemType> itemTypeMap(IItemTypeDao itemTypeDao) {
        List<ItemType> itemTypeList = itemTypeDao.getEntityList();
        return itemTypeList.stream()
                .collect(Collectors.toMap(ItemType::getValue, itemType -> itemType, (existing, replacement) -> existing));
    }

    @Bean
    public Map<String, Brand> brandMap(IBrandDao brandDao) {
        List<Brand> brandList = brandDao.getEntityList();
        return brandList.stream()
                .collect(Collectors.toMap(Brand::getValue, brand -> brand, (existing, replacement) -> existing));
    }

    @Bean
    public Map<Object, Rating> ratingMap(IRatingDao ratingDao) {
        List<Rating> ratingList = ratingDao.getEntityList();
        return ratingList.stream()
                .collect(Collectors.toMap(Rating::getValue, value -> value, (existing, replacement) -> existing));
    }

    @Bean
    public Map<String, ArticularStatus> itemStatusMap(IItemStatusDao itemStatusDao) {
        List<ArticularStatus> articularStatusList = itemStatusDao.getEntityList();
        return articularStatusList.stream()
                .collect(Collectors.toMap(ArticularStatus::getValue, itemStatus -> itemStatus, (existing, replacement) -> existing));
    }

    @Bean
    public Map<String, OptionGroup> optionGroupMap(IOptionGroupDao optionGroupDao) {
        List<OptionGroup> optionGroupList = optionGroupDao.getEntityList();
        return optionGroupList.stream()
                .collect(Collectors.toMap(OptionGroup::getValue, optionGroup -> optionGroup, (existing, replacement) -> existing));
    }

    @Bean
    public Map<String, Country> countryMap(ICountryDao countryDao) {
        List<Country> countryList = countryDao.getEntityList();
        return countryList.stream()
                .collect(Collectors.toMap(Country::getValue, country -> country, (existing, replacement) -> existing));
    }

    @Bean
    public Map<String, Currency> currencyMap(ICurrencyDao currencyDao) {
        List<Currency> currencyList = currencyDao.getEntityList();
        return currencyList.stream()
                .collect(Collectors.toMap(Currency::getValue, currency -> currency, (existing, replacement) -> existing));
    }

    @Bean
    public Map<String, CountryPhoneCode> countryPhoneCodeMap(ICountryPhoneCodeDao countryPhoneCodeDao) {
        List<CountryPhoneCode> optionGroupList = countryPhoneCodeDao.getEntityList();
        return optionGroupList.stream()
                .collect(Collectors.toMap(CountryPhoneCode::getValue, countryPhoneCode -> countryPhoneCode, (existing, replacement) -> existing));
    }

    @Bean
    public Map<String, MessageStatus> messageStatusMap(IMessageStatusDao messageStatusDao) {
        List<MessageStatus> messageStatusList = messageStatusDao.getEntityList();
        return messageStatusList.stream()
                .collect(Collectors.toMap(MessageStatus::getValue, messageStatus -> messageStatus, (existing, replacement) -> existing));
    }

    @Bean
    public Map<String, MessageType> messageTypeMap(IMessageTypeDao messageTypeDao) {
        List<MessageType> messageTypeList = messageTypeDao.getEntityList();
        return messageTypeList.stream()
                .collect(Collectors.toMap(MessageType::getValue, messageType -> messageType, (existing, replacement) -> existing));
    }

    @Bean
    public Map<Class<?>, Map<?, ?>> classEntityMap(
            IDeliveryTypeDao deliveryTypeDao,
            IPaymentMethodDao paymentMethodDao,
            IOrderStatusDao orderStatusDao,
            ICategoryDao categoryDao,
            IItemTypeDao itemTypeDao,
            IBrandDao brandDao,
            IRatingDao ratingDao,
            IItemStatusDao itemStatusDao,
            IOptionGroupDao optionGroupDao,
            ICountryDao countryDao,
            ICurrencyDao currencyDao,
            ICountryPhoneCodeDao countryPhoneCodeDao,
            IMessageStatusDao messageStatusDao,
            IMessageTypeDao messageTypeDao) {
        return new HashMap<>(){{
            put(DeliveryType.class, deliveryTypeMap(deliveryTypeDao));
            put(PaymentMethod.class, paymentMethodMap(paymentMethodDao));
            put(OrderStatus.class, orderStatusMap(orderStatusDao));
            put(Category.class, categoryMap(categoryDao));
            put(ItemType.class, itemTypeMap(itemTypeDao));
            put(Brand.class, brandMap(brandDao));
            put(Rating.class, ratingMap(ratingDao));
            put(ArticularStatus.class, itemStatusMap(itemStatusDao));
            put(OptionGroup.class, optionGroupMap(optionGroupDao));
            put(Country.class, countryMap(countryDao));
            put(Currency.class, currencyMap(currencyDao));
            put(CountryPhoneCode.class, countryPhoneCodeMap(countryPhoneCodeDao));
            put(MessageStatus.class, messageStatusMap(messageStatusDao));
            put(MessageType.class, messageTypeMap(messageTypeDao));
        }};
    }

}
