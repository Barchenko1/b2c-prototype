package com.b2c.prototype.dao.payment.base;

import com.b2c.prototype.dao.AbstractSingleEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.payment.Card;
import com.b2c.prototype.util.CardUtil;
import com.tm.core.dao.identifier.EntityIdentifierDao;
import com.tm.core.processor.finder.manager.EntityMappingManager;
import com.tm.core.processor.finder.manager.IEntityMappingManager;
import com.tm.core.processor.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;

class BasicCardDaoTest extends AbstractSingleEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(Card.class, "card"));
        entityIdentifierDao = new EntityIdentifierDao(sessionManager, entityMappingManager);
        dao = new BasicCardDao(sessionFactory, entityIdentifierDao);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/payment/card/emptyCardDataSet.yml";
    }

    @Override
    protected EntityDataSet<?> getTestDataSet() {
        Card card = Card.builder()
                .id(1L)
                .cardNumber("4444-1111-2222-3333")
                .dateOfExpire("06/28")
                .ownerName("name")
                .ownerSecondName("secondName")
                .isActive(CardUtil.isCardActive("06/28"))
                .cvv(818)
                .build();
        return new EntityDataSet<>(card, "/datasets/payment/card/testCardDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        Card card = Card.builder()
                .cardNumber("4444-1111-2222-3333")
                .dateOfExpire("06/28")
                .ownerName("name")
                .ownerSecondName("secondName")
                .isActive(CardUtil.isCardActive("06/28"))
                .cvv(818)
                .build();
        return new EntityDataSet<>(card, "/datasets/payment/card/saveCardDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        Card card = Card.builder()
                .id(1L)
                .cardNumber("4444-1111-2222-3333")
                .dateOfExpire("06/28")
                .ownerName("Update Name")
                .ownerSecondName("secondName")
                .isActive(CardUtil.isCardActive("06/28"))
                .cvv(818)
                .build();
        return new EntityDataSet<>(card, "/datasets/payment/card/updateCardDataSet.yml");
    }

}