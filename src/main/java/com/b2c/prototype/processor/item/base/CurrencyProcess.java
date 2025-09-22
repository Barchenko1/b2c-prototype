package com.b2c.prototype.processor.item.base;

import com.b2c.prototype.manager.price.ICurrencyManager;
import com.b2c.prototype.modal.dto.payload.constant.CurrencyDto;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.processor.item.ICurrencyProcess;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CurrencyProcess implements ICurrencyProcess {

    private final ObjectMapper objectMapper;
    private final ICurrencyManager currencyManager;

    public CurrencyProcess(ObjectMapper objectMapper,
                           ICurrencyManager currencyManager) {
        this.objectMapper = objectMapper;
        this.currencyManager = currencyManager;
    }

    @Override
    public void persistEntity(Map<String, Object> payload) {
        Currency entity = objectMapper.convertValue(payload, Currency.class);
        currencyManager.persistEntity(entity);
    }

    @Override
    public void mergeEntity(Map<String, Object> payload, String value) {
        Currency entity = objectMapper.convertValue(payload, Currency.class);
        currencyManager.mergeEntity(value, entity);
    }

    @Override
    public void removeEntity(String value) {
        currencyManager.removeEntity(value);
    }

    @Override
    public List<CurrencyDto> getEntityList(String location) {
        return currencyManager.getEntities().stream()
                .map(entity -> objectMapper.convertValue(entity, CurrencyDto.class))
                .toList();
    }

    @Override
    public CurrencyDto getEntity(String location, String value) {
        return Optional.of(currencyManager.getEntity(value))
                .map(entity -> objectMapper.convertValue(entity, CurrencyDto.class))
                .orElseThrow(() -> new RuntimeException(""));
    }
}
