package com.b2c.prototype.dao.payment;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.util.CardUtil;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nimbusds.jose.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CreditCardDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/dao/payment/credit_card/emptyCreditCardDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/payment/credit_card/saveCreditCardDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        CreditCard entity = getCreditCard();
        entity.setId(0);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/payment/credit_card/testCreditCardDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/payment/credit_card/updateCreditCardDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        CreditCard entity = getCreditCard();
        entity.setOwnerName("Update name");

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/payment/credit_card/testCreditCardDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/payment/credit_card/emptyCreditCardDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        CreditCard entity = getCreditCard();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/payment/credit_card/testCreditCardDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        CreditCard expected = getCreditCard();

        Pair<String, Long> pair = Pair.of("id", 1L);
        CreditCard entity = generalEntityDao.findEntity("CreditCard.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/payment/credit_card/testCreditCardDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        CreditCard expected = getCreditCard();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<CreditCard> optionEntity = generalEntityDao.findOptionEntity("CreditCard.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        CreditCard entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/payment/credit_card/testCreditCardDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        CreditCard entity = getCreditCard();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<CreditCard> entityList = generalEntityDao.findEntityList("CreditCard.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }
    
    private CreditCard getCreditCard() {
        return CreditCard.builder()
                .id(1L)
                .cardNumber("4444-1111-2222-3333")
                .monthOfExpire(6)
                .yearOfExpire(28)
                .ownerName("name")
                .ownerSecondName("secondName")
                .isActive(CardUtil.isCardActive(6, 28))
                .cvv("818")
                .build();
    }

}