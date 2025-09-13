package com.b2c.prototype.dao.user;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.modal.entity.user.UserCreditCard;
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

class UserCreditCardDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/user/user_credit_card/emptyUserCreditCardDataSet.yml", cleanBefore = true,
            executeStatementsBefore = {
                    "TRUNCATE TABLE credit_card RESTART IDENTITY CASCADE"
    })
    @ExpectedDataSet(value = "datasets/user/user_credit_card/saveUserCreditCardDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        UserCreditCard entity = getUserCreditCard();
        entity.setId(0L);
        entity.getCreditCard().setId(0L);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/user/user_credit_card/testUserCreditCardDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/user/user_credit_card/updateUserCreditCardDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        UserCreditCard entity = getUserCreditCard();
        entity.getCreditCard().setOwnerName("Update name");

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/user/user_credit_card/testUserCreditCardDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/user/user_credit_card/emptyUserCreditCardDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        UserCreditCard entity = getUserCreditCard();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/user/user_credit_card/testUserCreditCardDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        UserCreditCard expected = getUserCreditCard();

        Pair<String, Long> pair = Pair.of("id", 1L);
        UserCreditCard entity = generalEntityDao.findEntity("UserCreditCard.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/user/user_credit_card/testUserCreditCardDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        UserCreditCard expected = getUserCreditCard();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<UserCreditCard> optionEntity = generalEntityDao.findOptionEntity("UserCreditCard.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        UserCreditCard entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/user/user_credit_card/testUserCreditCardDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        UserCreditCard entity = getUserCreditCard();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<UserCreditCard> entityList = generalEntityDao.findEntityList("UserCreditCard.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }

    private UserCreditCard getUserCreditCard() {
        CreditCard creditCard = CreditCard.builder()
                .id(1L)
                .cardNumber("4444-1111-2222-3333")
                .monthOfExpire(6)
                .yearOfExpire(28)
                .ownerName("name")
                .ownerSecondName("secondName")
                .isActive(CardUtil.isCardActive(6, 28))
                .cvv("818")
                .build();
        return UserCreditCard.builder()
                .id(1L)
                .creditCard(creditCard)
                .isDefault(true)
                .build();
    }

}