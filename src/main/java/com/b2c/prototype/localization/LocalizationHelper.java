package com.b2c.prototype.localization;

import com.b2c.prototype.configuration.LocalizationInterpreter;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.ItemStatus;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.modal.entity.item.Rating;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.order.OrderStatus;
import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LocalizationHelper {

    private final LocalizationInterpreter localizationInterpreter;
    private final Map<String, DeliveryType> deliveryTypeMap;
    private final Map<String, PaymentMethod> paymenMethodMap;
    private final Map<String, OrderStatus> orderStatusMap;
    private final Map<String, Brand> brandMap;
    private final Map<String, ItemStatus> itemStatusMap;
    private final Map<String, ItemType> itemTypeMap;
    private final Map<String, Category> categoryMap;
    private final Map<Integer, Rating> ratingMap;
    private final Map<String, OptionGroup> optionGroupMap;

    public LocalizationHelper(LocalizationInterpreter localizationInterpreter,
                              Map<String, DeliveryType> deliveryTypeMap,
                              Map<String, PaymentMethod> paymenMethodMap,
                              Map<String, OrderStatus> orderStatusMap,
                              Map<String, Brand> brandMap,
                              Map<String, ItemStatus> itemStatusMap,
                              Map<String, ItemType> itemTypeMap,
                              Map<String, Category> categoryMap,
                              Map<Integer, Rating> ratingMap, Map<String, OptionGroup> optionGroupMap) {
        this.localizationInterpreter = localizationInterpreter;
        this.deliveryTypeMap = deliveryTypeMap;
        this.paymenMethodMap = paymenMethodMap;
        this.orderStatusMap = orderStatusMap;
        this.brandMap = brandMap;
        this.itemStatusMap = itemStatusMap;
        this.itemTypeMap = itemTypeMap;
        this.categoryMap = categoryMap;
        this.ratingMap = ratingMap;
        this.optionGroupMap = optionGroupMap;
    }

    public String getLocalizeDeliveryTypeName(String deliveryTypeConfigName, Locale locale) {
        DeliveryType deliveryType = deliveryTypeMap.get(deliveryTypeConfigName);
        return localizationInterpreter.interpret("delivery.type", deliveryType.getName(), locale);
    }

    public List<String> getLocalizeDeliveryTypeNameList(Locale locale) {
        return deliveryTypeMap.values().stream()
                .map(brand -> localizationInterpreter.interpret("delivery.type", brand.getName(), locale))
                .collect(Collectors.toList());
    }

    public String getLocalizePaymentMethodName(String paymentMethodConfigName, Locale locale) {
        PaymentMethod paymentMethod = paymenMethodMap.get(paymentMethodConfigName);
        return localizationInterpreter.interpret("payment.method", paymentMethod.getMethod(), locale);
    }

    public List<String> getLocalizePaymentMethodNameList(Locale locale) {
        return paymenMethodMap.values().stream()
                .map(paymentMethod -> localizationInterpreter.interpret("payment.method", paymentMethod.getMethod(), locale))
                .collect(Collectors.toList());
    }

    public String getLocalizeOrderStatusName(String orderStatusConfigName, Locale locale) {
        OrderStatus orderStatus = orderStatusMap.get(orderStatusConfigName);
        return localizationInterpreter.interpret("order.status", orderStatus.getName(), locale);
    }

    public List<String> getLocalizeOrderStatusNameList(Locale locale) {
        return orderStatusMap.values().stream()
                .map(brand -> localizationInterpreter.interpret("order.status", brand.getName(), locale))
                .collect(Collectors.toList());
    }

    public String getLocalizeBrandName(String brandConfigName, Locale locale) {
        Brand brand = brandMap.get(brandConfigName);
        return localizationInterpreter.interpret("brand", brand.getName(), locale);
    }

    public List<String> getLocalizeBrandNameList(Locale locale) {
        return brandMap.values().stream()
                .map(brand -> localizationInterpreter.interpret("brand", brand.getName(), locale))
                .collect(Collectors.toList());
    }

    public String getLocalizeItemStatusName(String itemStatusConfigName, Locale locale) {
        ItemStatus itemStatus = itemStatusMap.get(itemStatusConfigName);
        return localizationInterpreter.interpret("item.status", itemStatus.getName(), locale);
    }

    public List<String> getLocalizeItemStatusNameList(Locale locale) {
        return itemStatusMap.values().stream()
                .map(itemStatus -> localizationInterpreter.interpret("item.status", itemStatus.getName(), locale))
                .collect(Collectors.toList());
    }

    public String getLocalizeItemTypeName(String itemTypeConfigName, Locale locale) {
        ItemType itemType = itemTypeMap.get(itemTypeConfigName);
        return localizationInterpreter.interpret("item.type", itemType.getName(), locale);
    }

    public List<String> getLocalizeItemTypeNameList(Locale locale) {
        return itemTypeMap.values().stream()
                .map(itemType -> localizationInterpreter.interpret("item.type", itemType.getName(), locale))
                .collect(Collectors.toList());
    }

}
