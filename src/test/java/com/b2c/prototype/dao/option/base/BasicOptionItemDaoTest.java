package com.b2c.prototype.dao.option.base;

import com.b2c.prototype.dao.AbstractGeneralEntityDaoTest;
import com.b2c.prototype.dao.payment.base.BasicPaymentDao;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.payment.Card;
import com.b2c.prototype.modal.entity.payment.Payment;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.tm.core.dao.identifier.EntityIdentifierDao;
import com.tm.core.processor.finder.manager.EntityMappingManager;
import com.tm.core.processor.finder.manager.IEntityMappingManager;
import com.tm.core.processor.finder.table.EntityTable;
import com.tm.core.processor.thread.IThreadLocalSessionManager;
import com.tm.core.processor.thread.ThreadLocalSessionManager;
import org.junit.jupiter.api.BeforeAll;

class BasicOptionItemDaoTest extends AbstractGeneralEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IThreadLocalSessionManager sessionManager = new ThreadLocalSessionManager(sessionFactory);
        entityIdentifierDao = new EntityIdentifierDao(sessionManager, getEntityMappingManager());
        dao = new BasicPaymentDao(sessionFactory, entityIdentifierDao);
    }

    private static IEntityMappingManager getEntityMappingManager() {
        EntityTable optionGroupEntityTable = new EntityTable(OptionGroup.class, "option_group");
        EntityTable optionItemEntityTable = new EntityTable(OptionItem.class, "option_item");

        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(optionGroupEntityTable);
        entityMappingManager.addEntityTable(optionItemEntityTable);
        return entityMappingManager;
    }


}