package com.b2c.prototype.manager.payment.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.manager.payment.ICurrencyCoefficientManager;
import com.b2c.prototype.modal.dto.payload.order.CurrencyConvertDateDto;
import com.b2c.prototype.modal.entity.payment.CurrencyCoefficient;
import com.b2c.prototype.transform.order.IOrderTransformService;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.b2c.prototype.util.Constant.DATE_OF_CREATE;

@Service
public class CurrencyCoefficientManager implements ICurrencyCoefficientManager {

    private final IGeneralEntityDao generalEntityDao;
    private final IOrderTransformService orderTransformService;

    public CurrencyCoefficientManager(IGeneralEntityDao generalEntityDao,
                                      IOrderTransformService orderTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.orderTransformService = orderTransformService;
    }

    @Override
    public void saveCurrencyCoefficient(CurrencyConvertDateDto currencyConvertDateDto) {
        CurrencyCoefficient currencyCoefficient =
                orderTransformService.mapCurrencyConvertDateDtoToCurrencyCoefficient(currencyConvertDateDto);
        generalEntityDao.persistEntity(currencyCoefficient);
    }

    @Override
    @Transactional
    public void updateCurrencyCoefficient(LocalDate dateOfCreate, CurrencyConvertDateDto currencyConvertDateDto) {
        CurrencyCoefficient currencyCoefficient =
                orderTransformService.mapCurrencyConvertDateDtoToCurrencyCoefficient(currencyConvertDateDto);
        CurrencyCoefficient existingCurrencyCoefficient =
                generalEntityDao.findEntity("CurrencyCoefficient.findByKey", Pair.of(DATE_OF_CREATE, dateOfCreate));
        existingCurrencyCoefficient.setCurrencyFrom(currencyCoefficient.getCurrencyFrom());
        existingCurrencyCoefficient.setCurrencyTo(currencyCoefficient.getCurrencyTo());
        existingCurrencyCoefficient.setCoefficient(currencyCoefficient.getCoefficient());
        existingCurrencyCoefficient.setDateOfCreate(dateOfCreate);
        generalEntityDao.mergeEntity(existingCurrencyCoefficient);
    }

    @Override
    public void deleteCurrencyCoefficient(LocalDate dateOfCreate) {
        generalEntityDao.findAndRemoveEntity("CurrencyCoefficient.findByKey", Pair.of(DATE_OF_CREATE, dateOfCreate));
    }

    @Override
    public CurrencyCoefficient getCurrencyCoefficient(LocalDate dateOfCreate) {
        return generalEntityDao.findEntity("CurrencyCoefficient.findByKey", Pair.of(DATE_OF_CREATE, dateOfCreate));
    }

    @Override
    public List<CurrencyCoefficient> getCurrencyCoefficients() {
        return generalEntityDao.findEntityList("CurrencyCoefficient.findAllByKey", (Pair<String, ?>) null);
    }
}
