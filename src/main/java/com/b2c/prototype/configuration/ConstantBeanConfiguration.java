package com.b2c.prototype.configuration;

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
import com.b2c.prototype.modal.entity.review.Rating;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.tm.core.process.dao.common.ITransactionEntityDao;
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
    public Map<String, DeliveryType> deliveryTypeMap(ITransactionEntityDao deliveryTypeDao) {
        List<DeliveryType> userRoleList = deliveryTypeDao.getNamedQueryEntityListClose("DeliveryType.all");
        return userRoleList.stream()
                .collect(Collectors.toMap(DeliveryType::getValue, Function.identity(), (existing, replacement) -> existing));
    }

    @Bean
    public Map<String, PaymentMethod> paymentMethodMap(ITransactionEntityDao paymentMethodDao) {
        List<PaymentMethod> paymentMethodList = paymentMethodDao.getNamedQueryEntityListClose("PaymentMethod.all");
        return paymentMethodList.stream()
                .collect(Collectors.toMap(PaymentMethod::getValue, Function.identity(), (existing, replacement) -> existing));
    }

    @Bean
    public Map<String, OrderStatus> orderStatusMap(ITransactionEntityDao orderStatusDao) {
        List<OrderStatus> orderStatusList = orderStatusDao.getNamedQueryEntityListClose("OrderStatus.all");
        return orderStatusList.stream()
                .collect(Collectors.toMap(OrderStatus::getValue, Function.identity(), (existing, replacement) -> existing));
    }

    @Bean
    public Map<String, Category> categoryMap(ITransactionEntityDao categoryDao) {
        List<Category> categoryList = categoryDao.getNamedQueryEntityListClose("Category.all");
        return categoryList.stream()
                .collect(Collectors.toMap(Category::getValue, category -> category, (existing, replacement) -> existing));
    }

    @Bean
    public Map<String, ItemType> itemTypeMap(ITransactionEntityDao itemTypeDao) {
        List<ItemType> itemTypeList = itemTypeDao.getNamedQueryEntityListClose("ItemType.all");
        return itemTypeList.stream()
                .collect(Collectors.toMap(ItemType::getValue, itemType -> itemType, (existing, replacement) -> existing));
    }

    @Bean
    public Map<String, Brand> brandMap(ITransactionEntityDao brandDao) {
        List<Brand> brandList = brandDao.getNamedQueryEntityListClose("Brand.all");
        return brandList.stream()
                .collect(Collectors.toMap(Brand::getValue, brand -> brand, (existing, replacement) -> existing));
    }

    @Bean
    public Map<Object, Rating> ratingMap(ITransactionEntityDao ratingDao) {
        List<Rating> ratingList = ratingDao.getNamedQueryEntityListClose("Rating.all");
        return ratingList.stream()
                .collect(Collectors.toMap(Rating::getValue, value -> value, (existing, replacement) -> existing));
    }

    @Bean
    public Map<String, ArticularStatus> articularStatusMap(ITransactionEntityDao itemStatusDao) {
        List<ArticularStatus> articularStatusList = itemStatusDao.getNamedQueryEntityListClose("ArticularStatus.all");
        return articularStatusList.stream()
                .collect(Collectors.toMap(ArticularStatus::getValue, itemStatus -> itemStatus, (existing, replacement) -> existing));
    }

    @Bean
    public Map<String, OptionGroup> optionGroupMap(ITransactionEntityDao optionGroupDao) {
        List<OptionGroup> optionGroupList = optionGroupDao.getNamedQueryEntityListClose("OptionGroup.all");
        return optionGroupList.stream()
                .collect(Collectors.toMap(OptionGroup::getValue, optionGroup -> optionGroup, (existing, replacement) -> existing));
    }

    @Bean
    public Map<String, Country> countryMap(ITransactionEntityDao countryDao) {
        List<Country> countryList = countryDao.getNamedQueryEntityListClose("Country.all");
        return countryList.stream()
                .collect(Collectors.toMap(Country::getValue, country -> country, (existing, replacement) -> existing));
    }

    @Bean
    public Map<String, Currency> currencyMap(ITransactionEntityDao currencyDao) {
        List<Currency> currencyList = currencyDao.getNamedQueryEntityListClose("Currency.all");
        return currencyList.stream()
                .collect(Collectors.toMap(Currency::getValue, currency -> currency, (existing, replacement) -> existing));
    }

    @Bean
    public Map<String, CountryPhoneCode> countryPhoneCodeMap(ITransactionEntityDao countryPhoneCodeDao) {
        List<CountryPhoneCode> optionGroupList = countryPhoneCodeDao.getNamedQueryEntityListClose("CountryPhoneCode.all");
        return optionGroupList.stream()
                .collect(Collectors.toMap(CountryPhoneCode::getValue, countryPhoneCode -> countryPhoneCode, (existing, replacement) -> existing));
    }

    @Bean
    public Map<String, MessageStatus> messageStatusMap(ITransactionEntityDao messageStatusDao) {
        List<MessageStatus> messageStatusList = messageStatusDao.getNamedQueryEntityListClose("MessageStatus.all");
        return messageStatusList.stream()
                .collect(Collectors.toMap(MessageStatus::getValue, messageStatus -> messageStatus, (existing, replacement) -> existing));
    }

    @Bean
    public Map<String, MessageType> messageTypeMap(ITransactionEntityDao messageTypeDao) {
        List<MessageType> messageTypeList = messageTypeDao.getNamedQueryEntityListClose("MessageType.all");
        return messageTypeList.stream()
                .collect(Collectors.toMap(MessageType::getValue, messageType -> messageType, (existing, replacement) -> existing));
    }

    @Bean
    public Map<Class<?>, Map<?, ?>> classEntityMap(
            ITransactionEntityDao deliveryTypeDao,
            ITransactionEntityDao paymentMethodDao,
            ITransactionEntityDao orderStatusDao,
            ITransactionEntityDao categoryDao,
            ITransactionEntityDao itemTypeDao,
            ITransactionEntityDao brandDao,
            ITransactionEntityDao ratingDao,
            ITransactionEntityDao itemStatusDao,
            ITransactionEntityDao optionGroupDao,
            ITransactionEntityDao countryDao,
            ITransactionEntityDao currencyDao,
            ITransactionEntityDao countryPhoneCodeDao,
            ITransactionEntityDao messageStatusDao,
            ITransactionEntityDao messageTypeDao) {
        return new HashMap<>(){{
            put(DeliveryType.class, deliveryTypeMap(deliveryTypeDao));
            put(PaymentMethod.class, paymentMethodMap(paymentMethodDao));
            put(OrderStatus.class, orderStatusMap(orderStatusDao));
            put(Category.class, categoryMap(categoryDao));
            put(ItemType.class, itemTypeMap(itemTypeDao));
            put(Brand.class, brandMap(brandDao));
            put(Rating.class, ratingMap(ratingDao));
            put(ArticularStatus.class, articularStatusMap(itemStatusDao));
            put(OptionGroup.class, optionGroupMap(optionGroupDao));
            put(Country.class, countryMap(countryDao));
            put(Currency.class, currencyMap(currencyDao));
            put(CountryPhoneCode.class, countryPhoneCodeMap(countryPhoneCodeDao));
            put(MessageStatus.class, messageStatusMap(messageStatusDao));
            put(MessageType.class, messageTypeMap(messageTypeDao));
        }};
    }

}
