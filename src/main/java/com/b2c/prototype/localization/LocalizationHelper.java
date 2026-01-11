package com.b2c.prototype.localization;

import com.b2c.prototype.configuration.LocalizationInterpreter;

import org.springframework.stereotype.Service;

@Service
public class LocalizationHelper {

    private final LocalizationInterpreter localizationInterpreter;
//    private final Map<String, DeliveryType> deliveryTypeMap;
//    private final Map<String, PaymentMethod> paymenMethodMap;
//    private final Map<String, OrderStatus> orderStatusMap;
//    private final Map<String, ArticularStatus> itemStatusMap;
//    private final Map<String, Category> categoryMap;
//    private final Map<Integer, Rating> ratingMap;
//    private final Map<String, OptionGroup> optionGroupMap;

//    private final IConstantsScope singleValueMap;

    public LocalizationHelper(LocalizationInterpreter localizationInterpreter) {
        this.localizationInterpreter = localizationInterpreter;
//        this.singleValueMap = singleValueMap;
    }

//    public LocalizationHelper(LocalizationInterpreter localizationInterpreter,
//                              Map<String, DeliveryType> deliveryTypeMap,
//                              Map<String, PaymentMethod> paymenMethodMap,
//                              Map<String, OrderStatus> orderStatusMap,
//                              Map<String, ArticularStatus> itemStatusMap,
//                              Map<String, Category> categoryMap,
//                              Map<Integer, Rating> ratingMap,
//                              Map<String, OptionGroup> optionGroupMap,
//                              IsingleValueMap singleValueMap) {
//        this.localizationInterpreter = localizationInterpreter;
//        this.deliveryTypeMap = deliveryTypeMap;
//        this.paymenMethodMap = paymenMethodMap;
//        this.orderStatusMap = orderStatusMap;
//        this.itemStatusMap = itemStatusMap;
//        this.categoryMap = categoryMap;
//        this.ratingMap = ratingMap;
//        this.optionGroupMap = optionGroupMap;
//
//        this.singleValueMap = singleValueMap;
//    }


//    public String getLocalizeDeliveryTypeName(String deliveryTypeConfigName, Locale locale) {
//        Map<Object, Object> deliveryTypeMap = singleValueMap.getEntityMap(DeliveryType.class);
//        DeliveryType deliveryType = (DeliveryType) deliveryTypeMap.get(deliveryTypeConfigName);
//        return localizationInterpreter.interpret("delivery.type", deliveryType.getValue(), locale);
//    }
//
//    public String getLocalizePaymentMethodName(String paymentMethodConfigName, Locale locale) {
//        PaymentMethod paymentMethod = paymenMethodMap.get(paymentMethodConfigName);
//        return localizationInterpreter.interpret("payment.method", paymentMethod.getMethod(), locale);
//    }
//
//    public List<String> getLocalizePaymentMethodNameList(Locale locale) {
//        return paymenMethodMap.values().stream()
//                .map(paymentMethod -> localizationInterpreter.interpret("payment.method", paymentMethod.getMethod(), locale))
//                .collect(Collectors.toList());
//    }
//
//    public String getLocalizeOrderStatusName(String orderStatusConfigName, Locale locale) {
//        OrderStatus orderStatus = orderStatusMap.get(orderStatusConfigName);
//        return localizationInterpreter.interpret("order.status", orderStatus.getValue(), locale);
//    }
//
//    public String getLocalizeItemStatusName(String itemStatusConfigName, Locale locale) {
//        ArticularStatus itemStatus = itemStatusMap.get(itemStatusConfigName);
//        return localizationInterpreter.interpret("item.status", itemStatus.getValue(), locale);
//    }
//
//    public List<String> getLocalizeItemStatusNameList(Locale locale) {
//        return itemStatusMap.values().stream()
//                .map(itemStatus -> localizationInterpreter.interpret("item.status", itemStatus.getValue(), locale))
//                .collect(Collectors.toList());
//    }
//

}
