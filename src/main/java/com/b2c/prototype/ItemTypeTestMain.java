package com.b2c.prototype;

import com.b2c.prototype.dao.basic.BasicItemTypeDao;
import com.b2c.prototype.dao.item.IItemTypeDao;
import com.b2c.prototype.modal.client.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.client.entity.item.ItemType;
import com.b2c.prototype.processor.IEntityStringMapWrapper;
import com.b2c.prototype.processor.base.ItemTypeEntityStringMapWrapper;
import com.b2c.prototype.service.client.item.IItemTypeService;
import com.b2c.prototype.service.client.item.base.ItemTypeService;
import com.tm.core.configuration.ConfigDbType;
import com.tm.core.configuration.factory.ConfigurationSessionFactory;
import com.tm.core.configuration.factory.IConfigurationSessionFactory;
import org.hibernate.SessionFactory;

import java.util.HashMap;
import java.util.Map;

import static com.b2c.prototype.util.Query.SELECT_ITEM_STATUS_BY_NAME;

public class ItemTypeTestMain {
    public static void main(String[] args) {

        IConfigurationSessionFactory configurationSessionFactory = new ConfigurationSessionFactory(
                ConfigDbType.XML
        );
        SessionFactory sessionFactory = configurationSessionFactory.getSessionFactory();
        Map<String, ItemType> itemTypeMap = new HashMap<>();
        IItemTypeDao itemTypeDao = new BasicItemTypeDao(sessionFactory);
        IEntityStringMapWrapper<ItemType> itemTypeEntityStringMapWrapper = new ItemTypeEntityStringMapWrapper(
                itemTypeDao,
                itemTypeMap,
                SELECT_ITEM_STATUS_BY_NAME
        );
        IItemTypeService itemTypeService = new ItemTypeService(itemTypeDao, itemTypeEntityStringMapWrapper);

        RequestOneFieldEntityDto requestOneFieldEntityDto = new RequestOneFieldEntityDto();
        requestOneFieldEntityDto.setRequestValue("some value");

        itemTypeService.saveItemType(requestOneFieldEntityDto);

    }
}