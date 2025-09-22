package com.b2c.prototype.processor.order.base;

import com.b2c.prototype.manager.payment.IPaymentMethodManager;
import com.b2c.prototype.modal.dto.payload.order.PaymentMethodDto;
import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import com.b2c.prototype.processor.order.IPaymentMethodProcess;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PaymentMethodProcess implements IPaymentMethodProcess {
    private final ObjectMapper objectMapper;
    private final IPaymentMethodManager paymentMethodManager;

    public PaymentMethodProcess(ObjectMapper objectMapper,
                                IPaymentMethodManager paymentMethodManager) {
        this.objectMapper = objectMapper;
        this.paymentMethodManager = paymentMethodManager;
    }

    @Override
    public void persistEntity(Map<String, Object> payload) {
        PaymentMethod entity = objectMapper.convertValue(payload, PaymentMethod.class);
        paymentMethodManager.persistEntity(entity);
    }

    @Override
    public void mergeEntity(Map<String, Object> payload, String value) {
        PaymentMethod entity = objectMapper.convertValue(payload, PaymentMethod.class);
        paymentMethodManager.mergeEntity(value, entity);
    }

    @Override
    public void removeEntity(String value) {
        paymentMethodManager.removeEntity(value);
    }

    @Override
    public List<PaymentMethodDto> getEntityList(String location) {
        return  paymentMethodManager.getEntities().stream()
                .map(entity -> objectMapper.convertValue(entity, PaymentMethodDto.class))
                .toList();
    }

    @Override
    public PaymentMethodDto getEntity(String location, String value) {
        return Optional.of(paymentMethodManager.getEntity(value))
                .map(entity -> objectMapper.convertValue(entity, PaymentMethodDto.class))
                .orElseThrow(() -> new RuntimeException(""));
    }
}
