package com.b2c.prototype.controller.singlevalue;

import com.b2c.prototype.modal.dto.payload.ConstantEntityPayloadDto;
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
import com.b2c.prototype.service.processor.userprofile.ICountryPhoneCodeService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/singlevalue")
public class ConstantController {

    private final Map<String, IConstantEntityService> keyServiceMap;

    public ConstantController(IBrandService brandService,
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
                              IPaymentMethodService paymentMethodService) {
        keyServiceMap = new HashMap<>(){{
            put("brand", brandService);
            put("countryPhoneCode", countryPhoneCodeService);
            put("country", countryService);
            put("currency", currencyService);
            put("deliveryType", deliveryTypeService);
            put("itemStatus", itemStatusService);
            put("itemType", itemTypeService);
            put("messageStatus", messageStatusService);
            put("messageType", messageTypeService);
            put("optionGroup", optionGroupService);
            put("orderStatus", orderStatusService);
            put("paymentMethod", paymentMethodService);
        }};
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveConstantEntity(@RequestBody ConstantEntityPayloadDto constantEntityPayloadDto,
                                                @RequestHeader(value = "serviceId") String serviceId) {
        keyServiceMap.get(serviceId).saveEntity(constantEntityPayloadDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateConstantEntity(@RequestBody ConstantEntityPayloadDto constantEntityPayloadDto,
                                                     @RequestHeader(value = "serviceId") String serviceId,
                                                     @RequestParam(value = "value") final String value) {
        keyServiceMap.get(serviceId).updateEntity(value, constantEntityPayloadDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Void> deleteConstantEntity(@RequestHeader(value = "serviceId") String serviceId,
                                                     @RequestParam(value = "value") final String value) {
        keyServiceMap.get(serviceId).deleteEntity(value);
        return ResponseEntity.ok().build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping("/all")
    public ResponseEntity<?> getConstantEntities(@RequestHeader(name = "Accept-Language", defaultValue = "en") String location,
                                                 @RequestHeader(value = "serviceId") String serviceId) {
        if (keyServiceMap.containsKey(serviceId)) {
            return ResponseEntity.ok(keyServiceMap.get(serviceId).getEntities());
        }
        return ResponseEntity.badRequest().body("Invalid serviceId.");
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getConstantEntity(@RequestHeader(name = "Accept-Language", defaultValue = "en") String location,
                                               @RequestHeader(name = "serviceId") final String serviceId,
                                               @RequestParam(value = "value") final String value) {
        if (keyServiceMap.containsKey(serviceId)) {
            return ResponseEntity.ok(keyServiceMap.get(serviceId).getEntity(value));
        }
        return ResponseEntity.badRequest().body("Invalid serviceId.");
    }
}
