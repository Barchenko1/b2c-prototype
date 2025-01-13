package com.b2c.prototype.dao.payment.base;

import com.b2c.prototype.dao.AbstractSimpleEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.util.CardUtil;
import com.tm.core.dao.identifier.EntityIdentifierDao;
import com.tm.core.processor.finder.manager.EntityMappingManager;
import com.tm.core.processor.finder.manager.IEntityMappingManager;
import com.tm.core.processor.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;

class BasicCreditCardDaoTest extends AbstractSimpleEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(CreditCard.class, "credit_card"));
        entityIdentifierDao = new EntityIdentifierDao(sessionManager, entityMappingManager);
        dao = new BasicCreditCardDao(sessionFactory, entityIdentifierDao);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/payment/credit_card/emptyCreditCardDataSet.yml";
    }

    @Override
    protected EntityDataSet<?> getTestDataSet() {
        CreditCard creditCard = CreditCard.builder()
                .id(1L)
                .cardNumber("4444-1111-2222-3333")
                .dateOfExpire("06/28")
                .ownerName("name")
                .ownerSecondName("secondName")
                .isActive(CardUtil.isCardActive("06/28"))
                .cvv("818")
                .build();
        return new EntityDataSet<>(creditCard, "/datasets/payment/credit_card/testCreditCardDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        CreditCard creditCard = CreditCard.builder()
                .cardNumber("4444-1111-2222-3333")
                .dateOfExpire("06/28")
                .ownerName("name")
                .ownerSecondName("secondName")
                .isActive(CardUtil.isCardActive("06/28"))
                .cvv("818")
                .build();
        return new EntityDataSet<>(creditCard, "/datasets/payment/credit_card/saveCreditCardDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        CreditCard creditCard = CreditCard.builder()
                .id(1L)
                .cardNumber("4444-1111-2222-3333")
                .dateOfExpire("06/28")
                .ownerName("Update Name")
                .ownerSecondName("secondName")
                .isActive(CardUtil.isCardActive("06/28"))
                .cvv("818")
                .build();
        return new EntityDataSet<>(creditCard, "/datasets/payment/credit_card/updateCreditCardDataSet.yml");
    }

}