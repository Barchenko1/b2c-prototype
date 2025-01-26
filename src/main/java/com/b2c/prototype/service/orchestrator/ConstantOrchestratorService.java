package com.b2c.prototype.service.orchestrator;

import com.b2c.prototype.modal.dto.common.NumberConstantPayloadDto;
import com.b2c.prototype.modal.dto.payload.ConstantPayloadDto;
import com.b2c.prototype.modal.dto.payload.CountryDto;
import com.b2c.prototype.service.processor.IConstantEntityService;
import com.b2c.prototype.service.processor.address.ICountryService;
import com.b2c.prototype.service.processor.delivery.IDeliveryTypeService;
import com.b2c.prototype.service.processor.item.IBrandService;
import com.b2c.prototype.service.processor.item.IItemStatusService;
import com.b2c.prototype.service.processor.item.IItemTypeService;
import com.b2c.prototype.service.processor.message.IMessageStatusService;
import com.b2c.prototype.service.processor.message.IMessageTypeService;
import com.b2c.prototype.service.processor.option.IOptionGroupService;
import com.b2c.prototype.service.processor.order.IOrderStatusService;
import com.b2c.prototype.service.processor.payment.IPaymentMethodService;
import com.b2c.prototype.service.processor.price.ICurrencyService;
import com.b2c.prototype.service.processor.rating.IRatingService;
import com.b2c.prototype.service.processor.store.ICountTypeService;
import com.b2c.prototype.service.processor.userprofile.ICountryPhoneCodeService;
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

public class ConstantOrchestratorService implements IConstantOrchestratorService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, IConstantEntityService<ConstantPayloadDto>> constantEntityServiceMap;
    private final ICountryService countryService;
    private final IRatingService ratingService;

    public ConstantOrchestratorService(
            IBrandService brandService,
                                       ICountTypeService countTypeService,
                                       ICountryPhoneCodeService countryPhoneCodeService,
                                       ICountryService countryService,
                                       ICurrencyService currencyService,
                                       IDeliveryTypeService deliveryTypeService,
                                       IItemStatusService itemStatusService,
                                       IItemTypeService itemTypeService,
                                       IMessageStatusService messageStatusService,
                                       IMessageTypeService messageTypeService,
                                       IOptionGroupService optionGroupService,
                                       IOrderStatusService orderStatusService,
                                       IPaymentMethodService paymentMethodService,
                                       IRatingService ratingService
    ) {
        this.countryService = countryService;
        this.ratingService = ratingService;
        constantEntityServiceMap = new HashMap<>(){{
            put(BRAND_SERVICE_ID, brandService);
            put(COUNT_TYPE_SERVICE_ID, countTypeService);
            put(COUNTRY_PHONE_CODE_SERVICE_ID, countryPhoneCodeService);
            put(CURRENCY_SERVICE_ID, currencyService);
            put(DELIVERY_TYPE_SERVICE_ID, deliveryTypeService);
            put(ITEM_STATUS_SERVICE_ID, itemStatusService);
            put(ITEM_TYPE_SERVICE_ID, itemTypeService);
            put(MESSAGE_STATUS_SERVICE_ID, messageStatusService);
            put(MESSAGE_TYPE_SERVICE_ID, messageTypeService);
            put(OPTION_GROUP_SERVICE_ID, optionGroupService);
            put(ORDER_STATUS_SERVICE_ID, orderStatusService);
            put(PAYMENT_METHOD_SERVICE_ID, paymentMethodService);
        }};
    }

    public void saveConstantEntity(final Map<String, Object> payload,
                                   final String serviceId) {
        if (serviceId.equals(COUNTRY_SERVICE_ID)) {
            CountryDto countryDto = objectMapper.convertValue(payload, CountryDto.class);
            countryService.saveEntity(countryDto);
        }
        if (serviceId.equals(RATING_SERVICE_ID)) {
            ratingService.saveEntity((NumberConstantPayloadDto) payload);
        }
        if (constantEntityServiceMap.containsKey(serviceId)) {
            ConstantPayloadDto constantPayloadDto = objectMapper.convertValue(payload, ConstantPayloadDto.class);
            constantEntityServiceMap.get(serviceId).saveEntity(constantPayloadDto);
        }
    }

    public void putConstantEntity(final Map<String, Object> payload,
                                  final String serviceId,
                                  final String value) {
        if (serviceId.equals(COUNTRY_SERVICE_ID)) {
            CountryDto countryDto = objectMapper.convertValue(payload, CountryDto.class);
            countryService.updateEntity(value, countryDto);
        }
        if (constantEntityServiceMap.containsKey(serviceId)) {
            ConstantPayloadDto constantPayloadDto = objectMapper.convertValue(payload, ConstantPayloadDto.class);
            constantEntityServiceMap.get(serviceId).updateEntity(value, constantPayloadDto);
        }
    }

    public void patchConstantEntity(Map<String, Object> payload,
                                                      final String serviceId,
                                                      final String value) {
        if (serviceId.equals(COUNTRY_SERVICE_ID)) {
            CountryDto countryDto = objectMapper.convertValue(payload, CountryDto.class);
            countryService.updateEntity(value, countryDto);
        }
        if (constantEntityServiceMap.containsKey(serviceId)) {
            ConstantPayloadDto constantPayloadDto = objectMapper.convertValue(payload, ConstantPayloadDto.class);
            constantEntityServiceMap.get(serviceId).updateEntity(value, constantPayloadDto);
        }
    }

    public void deleteConstantEntity(final String serviceId,
                                     final String value) {
        if (serviceId.equals(COUNTRY_SERVICE_ID)) {
            countryService.deleteEntity(value);
        }
        if (constantEntityServiceMap.containsKey(serviceId)) {
            constantEntityServiceMap.get(serviceId).deleteEntity(value);
        }
    }

    public List<?> getConstantEntities(final String location,
                                       final String serviceId) {
        if (serviceId.equals(COUNTRY_SERVICE_ID)) {
            return countryService.getEntities();
        }
        if (constantEntityServiceMap.containsKey(serviceId)) {
            return constantEntityServiceMap.get(serviceId).getEntities();
        }

        return Collections.emptyList();
    }

    public Object getConstantEntity(final String location,
                                    final String serviceId,
                                    final String value) {
        if (serviceId.equals(COUNTRY_SERVICE_ID)) {
            return countryService.getEntity(value);
        }
        if (constantEntityServiceMap.containsKey(serviceId)) {
            return constantEntityServiceMap.get(serviceId).getEntity(value);
        }

        return null;
    }
}
