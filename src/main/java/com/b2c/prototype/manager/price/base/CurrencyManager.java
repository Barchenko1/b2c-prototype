package com.b2c.prototype.manager.price.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.transform.constant.IConstantTransformService;
import com.b2c.prototype.manager.price.ICurrencyManager;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.VALUE;

@Service
public class CurrencyManager implements ICurrencyManager {
    
    private final IGeneralEntityDao generalEntityDao;
    private final IConstantTransformService constantTransformService;

    public CurrencyManager(IGeneralEntityDao generalEntityDao, 
                           IConstantTransformService constantTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.constantTransformService = constantTransformService;
    }

    public void saveEntity(ConstantPayloadDto payload) {
        Currency entity = constantTransformService.mapConstantPayloadDtoToCurrency(payload);
        generalEntityDao.persistEntity(entity);
    }

    public void updateEntity(String searchValue, ConstantPayloadDto payload) {
        Currency fetchedEntity =
                generalEntityDao.findEntity("Currency.findByValue", Pair.of(VALUE, searchValue));
        fetchedEntity.setValue(payload.getValue());
        fetchedEntity.setLabel(payload.getLabel());
        generalEntityDao.mergeEntity(fetchedEntity);
    }

    public void deleteEntity(String value) {
        Currency fetchedEntity = generalEntityDao.findEntity("Currency.findByValue", Pair.of(VALUE, value));
        generalEntityDao.removeEntity(fetchedEntity);
    }

    public ConstantPayloadDto getEntity(String value) {
        Currency entity = generalEntityDao.findEntity("Currency.findByValue", Pair.of(VALUE, value));
        return constantTransformService.mapCurrencyToConstantPayloadDto(entity);
    }

    public Optional<ConstantPayloadDto> getEntityOptional(String value) {
        Currency entity = generalEntityDao.findEntity("Currency.findByValue", Pair.of(VALUE, value));
        return Optional.of(constantTransformService.mapCurrencyToConstantPayloadDto(entity));
    }


    public List<ConstantPayloadDto> getEntities() {
        return generalEntityDao.findEntityList("Currency.all", (Pair<String, ?>) null).stream()
                .map(e -> constantTransformService.mapCurrencyToConstantPayloadDto((Currency) e))
                .toList();
    }
}
