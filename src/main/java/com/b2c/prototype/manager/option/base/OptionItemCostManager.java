package com.b2c.prototype.manager.option.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.manager.option.IOptionItemCostManager;
import org.springframework.stereotype.Service;

@Service
public class OptionItemCostManager implements IOptionItemCostManager {

    private final IGeneralEntityDao generalEntityDao;

    public OptionItemCostManager(IGeneralEntityDao generalEntityDao) {
        this.generalEntityDao = generalEntityDao;
    }


}
