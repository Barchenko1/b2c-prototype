package com.b2c.prototype.manager.payment.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import com.b2c.prototype.manager.payment.IPaymentMethodManager;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.VALUE;

@Service
public class PaymentMethodManager implements IPaymentMethodManager {

    private final IGeneralEntityDao generalEntityDao;

    public PaymentMethodManager(IGeneralEntityDao generalEntityDao) {
        this.generalEntityDao = generalEntityDao;
    }

    public void persistEntity(PaymentMethod entity) {
        generalEntityDao.persistEntity(entity);
    }

    @Transactional
    public void mergeEntity(String searchValue, PaymentMethod payload) {
        PaymentMethod fetchedEntity =
                generalEntityDao.findEntity("PaymentMethod.findByValue", Pair.of(VALUE, searchValue));
        payload.setId(fetchedEntity.getId());
        generalEntityDao.mergeEntity(payload);
    }

    @Transactional
    public void removeEntity(String value) {
        PaymentMethod fetchedEntity = generalEntityDao.findEntity("PaymentMethod.findByValue", Pair.of(VALUE, value));
        generalEntityDao.removeEntity(fetchedEntity);
    }

    public PaymentMethod getEntity(String value) {
        return generalEntityDao.findEntity("PaymentMethod.findByValue", Pair.of(VALUE, value));
    }

    public Optional<PaymentMethod> getEntityOptional(String value) {
        PaymentMethod entity = generalEntityDao.findEntity("PaymentMethod.findByValue", Pair.of(VALUE, value));
        return Optional.of(entity);
    }


    public List<PaymentMethod> getEntities() {
        return generalEntityDao.findEntityList("PaymentMethod.all", (Pair<String, ?>) null);
    }
}
