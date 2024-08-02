package com.b2c.prototype.service.client.item.base;

import com.b2c.prototype.dao.item.IDiscountDao;
import com.b2c.prototype.dao.item.IItemDao;
import com.b2c.prototype.modal.client.dto.request.RequestItemDto;
import com.b2c.prototype.modal.client.dto.update.RequestItemDtoUpdate;
import com.b2c.prototype.modal.client.entity.item.Brand;
import com.b2c.prototype.modal.client.entity.item.Category;
import com.b2c.prototype.modal.client.entity.item.Item;
import com.b2c.prototype.modal.client.entity.item.ItemStatus;
import com.b2c.prototype.modal.client.entity.item.ItemType;
import com.b2c.prototype.modal.constant.ItemStatusEnum;
import com.b2c.prototype.processor.IEntityStringMapWrapper;
import com.b2c.prototype.service.client.item.IItemService;
import com.tm.core.processor.ThreadLocalSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.b2c.prototype.util.Query.DELETE_ITEM_BY_ARTICLE;

@Slf4j
public class ItemService implements IItemService {

    private final ThreadLocalSessionManager sessionManager;
    private final IEntityStringMapWrapper<Brand> brandIEntityStringMapWrapper;
    private final IEntityStringMapWrapper<ItemStatus> itemStatusIEntityStringMapWrapper;
    private final IEntityStringMapWrapper<ItemType> itemTypeIEntityStringMapWrapper;
    private final IEntityStringMapWrapper<Category> categoryIEntityStringMapWrapper;
    private final IItemDao iItemDao;
    private final IDiscountDao discountDao;

    public ItemService(ThreadLocalSessionManager sessionManager, IEntityStringMapWrapper<Brand> brandIEntityStringMapWrapper, IEntityStringMapWrapper<ItemStatus> itemStatusIEntityStringMapWrapper, IEntityStringMapWrapper<ItemType> itemTypeIEntityStringMapWrapper, IEntityStringMapWrapper<Category> categoryIEntityStringMapWrapper, IItemDao iItemDao, IDiscountDao discountDao) {
        this.sessionManager = sessionManager;
        this.brandIEntityStringMapWrapper = brandIEntityStringMapWrapper;
        this.itemStatusIEntityStringMapWrapper = itemStatusIEntityStringMapWrapper;
        this.itemTypeIEntityStringMapWrapper = itemTypeIEntityStringMapWrapper;
        this.categoryIEntityStringMapWrapper = categoryIEntityStringMapWrapper;
        this.iItemDao = iItemDao;
        this.discountDao = discountDao;
    }

    @Override
    public void saveItem(RequestItemDto requestItemDto) {
        Transaction transaction = null;
        try (Session session = sessionManager.getSession()) {
            transaction = session.beginTransaction();

            Brand brand = brandIEntityStringMapWrapper.getEntity(requestItemDto.getBrand());
            ItemType itemType = itemTypeIEntityStringMapWrapper.getEntity(requestItemDto.getItemType());
            Category category = categoryIEntityStringMapWrapper.getEntity(requestItemDto.getCategory());
            ItemStatus itemStatus = itemStatusIEntityStringMapWrapper.getEntity(ItemStatusEnum.NEW.name());

            Item item = Item.builder()
                    .name(requestItemDto.getName())
                    .articularId(UUID.randomUUID().toString())
                    .dateOfCreate(System.currentTimeMillis())
                    .brand(brand)
                    .itemType(itemType)
                    .category(category)
                    .status(itemStatus)
                    .build();

            session.persist(item);
            transaction.commit();
        } catch (Exception e) {
            log.warn("transaction error {}", e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            sessionManager.closeSession();
        }
    }

    @Override
    public void updateItem(RequestItemDtoUpdate requestItemDtoUpdate) {
        RequestItemDto requestItemDto = requestItemDtoUpdate.getNewEntityDto();
        String searchField = requestItemDtoUpdate.getSearchField();
        Transaction transaction = null;
        try (Session session = sessionManager.getSession()) {
            transaction = session.beginTransaction();



            transaction.commit();
        } catch (Exception e) {
            log.warn("transaction error {}", e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            sessionManager.closeSession();
        }
    }

    @Override
    public void deleteItem(RequestItemDto requestItemDto) {
        iItemDao.mutateEntityBySQLQueryWithParams(DELETE_ITEM_BY_ARTICLE, requestItemDto.getArticularId());
    }

    @Override
    public Optional<Item> getItem(String name) {
        return Optional.empty();
    }

    @Override
    public List<Item> getItemListByCategory(String categoryName) {
        return List.of();
    }

    @Override
    public List<Item> getItemListByItemType(String itemTypeName) {
        return List.of();
    }

    @Override
    public List<Item> getItemListByBrand(String brandName) {
        return List.of();
    }

    @Override
    public List<Item> getItemListByItemStatus(String itemStatusName) {
        return List.of();
    }

    @Override
    public List<Item> getItemListByDateOfCreate(String dateOfCreate) {
        return List.of();
    }
}
