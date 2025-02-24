package com.b2c.prototype.processor.constant;

import com.b2c.prototype.modal.dto.common.NumberConstantPayloadDto;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.dto.payload.constant.CountryDto;
import com.b2c.prototype.manager.IConstantEntityManager;
import com.b2c.prototype.manager.address.ICountryManager;
import com.b2c.prototype.manager.delivery.IDeliveryTypeManager;
import com.b2c.prototype.manager.item.IBrandManager;
import com.b2c.prototype.manager.item.IItemStatusManager;
import com.b2c.prototype.manager.item.IItemTypeManager;
import com.b2c.prototype.manager.message.IMessageStatusManager;
import com.b2c.prototype.manager.message.IMessageTypeManager;
import com.b2c.prototype.manager.option.IOptionGroupManager;
import com.b2c.prototype.manager.order.IOrderStatusManager;
import com.b2c.prototype.manager.payment.IPaymentMethodManager;
import com.b2c.prototype.manager.price.ICurrencyManager;
import com.b2c.prototype.manager.rating.IRatingManager;
import com.b2c.prototype.manager.store.ICountTypeManager;
import com.b2c.prototype.manager.userprofile.ICountryPhoneCodeManager;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.b2c.prototype.util.Constant.BRAND_SERVICE_ID;
import static com.b2c.prototype.util.Constant.COUNTRY_PHONE_CODE_SERVICE_ID;
import static com.b2c.prototype.util.Constant.COUNTRY_SERVICE_ID;
import static com.b2c.prototype.util.Constant.COUNT_TYPE_SERVICE_ID;
import static com.b2c.prototype.util.Constant.CURRENCY_SERVICE_ID;
import static com.b2c.prototype.util.Constant.DELIVERY_TYPE_SERVICE_ID;
import static com.b2c.prototype.util.Constant.ITEM_STATUS_SERVICE_ID;
import static com.b2c.prototype.util.Constant.ITEM_TYPE_SERVICE_ID;
import static com.b2c.prototype.util.Constant.MESSAGE_STATUS_SERVICE_ID;
import static com.b2c.prototype.util.Constant.MESSAGE_TYPE_SERVICE_ID;
import static com.b2c.prototype.util.Constant.OPTION_GROUP_SERVICE_ID;
import static com.b2c.prototype.util.Constant.ORDER_STATUS_SERVICE_ID;
import static com.b2c.prototype.util.Constant.PAYMENT_METHOD_SERVICE_ID;
import static com.b2c.prototype.util.Constant.RATING_SERVICE_ID;

public class ConstantProcessorService implements IConstantProcessorService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, IConstantEntityManager<ConstantPayloadDto>> constantEntityManagerMap;
    private final ICountryManager countryManager;
    private final IRatingManager ratingManager;

    public ConstantProcessorService(
            IBrandManager brandManager,
            ICountTypeManager countTypeManager,
            ICountryPhoneCodeManager countryPhoneCodeManager,
            ICountryManager countryManager,
            ICurrencyManager currencyManager,
            IDeliveryTypeManager deliveryTypeManager,
            IItemStatusManager itemStatusManager,
            IItemTypeManager itemTypeManager,
            IMessageStatusManager messageStatusManager,
            IMessageTypeManager messageTypeManager,
            IOptionGroupManager optionGroupManager,
            IOrderStatusManager orderStatusManager,
            IPaymentMethodManager paymentMethodManager,
            IRatingManager ratingManager
    ) {
        this.countryManager = countryManager;
        this.ratingManager = ratingManager;
        constantEntityManagerMap = new HashMap<>(){{
            put(BRAND_SERVICE_ID, brandManager);
            put(COUNT_TYPE_SERVICE_ID, countTypeManager);
            put(COUNTRY_PHONE_CODE_SERVICE_ID, countryPhoneCodeManager);
            put(CURRENCY_SERVICE_ID, currencyManager);
            put(DELIVERY_TYPE_SERVICE_ID, deliveryTypeManager);
            put(ITEM_STATUS_SERVICE_ID, itemStatusManager);
            put(ITEM_TYPE_SERVICE_ID, itemTypeManager);
            put(MESSAGE_STATUS_SERVICE_ID, messageStatusManager);
            put(MESSAGE_TYPE_SERVICE_ID, messageTypeManager);
            put(OPTION_GROUP_SERVICE_ID, optionGroupManager);
            put(ORDER_STATUS_SERVICE_ID, orderStatusManager);
            put(PAYMENT_METHOD_SERVICE_ID, paymentMethodManager);
        }};
    }

    public void saveConstantEntity(final Map<String, Object> payload,
                                   final String serviceId) {
        if (serviceId.equals(COUNTRY_SERVICE_ID)) {
            CountryDto countryDto = objectMapper.convertValue(payload, CountryDto.class);
            countryManager.saveEntity(countryDto);
        }
        if (serviceId.equals(RATING_SERVICE_ID)) {
            ratingManager.saveEntity((NumberConstantPayloadDto) payload);
        }
        if (constantEntityManagerMap.containsKey(serviceId)) {
            ConstantPayloadDto constantPayloadDto = objectMapper.convertValue(payload, ConstantPayloadDto.class);
            constantEntityManagerMap.get(serviceId).saveEntity(constantPayloadDto);
        }
    }

    public void putConstantEntity(final Map<String, Object> payload,
                                  final String serviceId,
                                  final String value) {
        if (serviceId.equals(COUNTRY_SERVICE_ID)) {
            CountryDto countryDto = objectMapper.convertValue(payload, CountryDto.class);
            countryManager.updateEntity(value, countryDto);
        }
        if (constantEntityManagerMap.containsKey(serviceId)) {
            ConstantPayloadDto constantPayloadDto = objectMapper.convertValue(payload, ConstantPayloadDto.class);
            constantEntityManagerMap.get(serviceId).updateEntity(value, constantPayloadDto);
        }
    }

    public void patchConstantEntity(Map<String, Object> payload,
                                                      final String serviceId,
                                                      final String value) {
        if (serviceId.equals(COUNTRY_SERVICE_ID)) {
            CountryDto countryDto = objectMapper.convertValue(payload, CountryDto.class);
            countryManager.updateEntity(value, countryDto);
        }
        if (constantEntityManagerMap.containsKey(serviceId)) {
            ConstantPayloadDto constantPayloadDto = objectMapper.convertValue(payload, ConstantPayloadDto.class);
            constantEntityManagerMap.get(serviceId).updateEntity(value, constantPayloadDto);
        }
    }

    public void deleteConstantEntity(final String serviceId,
                                     final String value) {
        if (serviceId.equals(COUNTRY_SERVICE_ID)) {
            countryManager.deleteEntity(value);
        }
        if (constantEntityManagerMap.containsKey(serviceId)) {
            constantEntityManagerMap.get(serviceId).deleteEntity(value);
        }
    }

    public List<?> getConstantEntities(final String location,
                                       final String serviceId) {
        if (serviceId.equals(COUNTRY_SERVICE_ID)) {
            return countryManager.getEntities();
        }
        if (constantEntityManagerMap.containsKey(serviceId)) {
            return constantEntityManagerMap.get(serviceId).getEntities();
        }

        return Collections.emptyList();
    }

    public Object getConstantEntity(final String location,
                                    final String serviceId,
                                    final String value) {
        if (serviceId.equals(COUNTRY_SERVICE_ID)) {
            return countryManager.getEntity(value);
        }
        if (constantEntityManagerMap.containsKey(serviceId)) {
            return constantEntityManagerMap.get(serviceId).getEntity(value);
        }

        return null;
    }
}
