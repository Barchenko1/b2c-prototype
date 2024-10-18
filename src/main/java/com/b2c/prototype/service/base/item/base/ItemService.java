package com.b2c.prototype.service.base.item.base;

import com.b2c.prototype.dao.item.ICurrencyDiscountDao;
import com.b2c.prototype.dao.item.IItemDao;
import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.RequestItemDto;
import com.b2c.prototype.modal.dto.update.RequestItemDtoUpdate;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.Item;
import com.b2c.prototype.modal.entity.item.ItemStatus;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.modal.constant.ItemStatusEnum;
import com.b2c.prototype.dao.cashed.IEntityStringMapWrapper;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.processor.IAsyncProcessor;
import com.b2c.prototype.processor.Task;
import com.b2c.prototype.service.base.item.IItemService;
import com.b2c.prototype.service.general.AbstractGeneralEntityService;
import com.tm.core.dao.general.IGeneralEntityDao;
import com.tm.core.processor.finder.parameter.Parameter;
import com.tm.core.processor.thread.ThreadLocalSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.b2c.prototype.util.UniqueIdUtil.getUUID;

@Slf4j
public class ItemService extends AbstractGeneralEntityService implements IItemService {

    private ThreadLocalSessionManager sessionManager;
    private final IAsyncProcessor asyncProcessor;
    private final IEntityStringMapWrapper<Brand> brandMapWrapper;
    private final IEntityStringMapWrapper<ItemStatus> itemStatusMapWrapper;
    private final IEntityStringMapWrapper<ItemType> itemTypeMapWrapper;
    private final IEntityStringMapWrapper<Category> categoryMapWrapper;
    private final IEntityStringMapWrapper<OptionGroup> optionGroupMapWrapper;
    private final IItemDao iItemDao;
    private final ICurrencyDiscountDao discountDao;

    public ItemService(IAsyncProcessor asyncProcessor,
                       IEntityStringMapWrapper<Brand> brandMapWrapper,
                       IEntityStringMapWrapper<ItemStatus> itemStatusMapWrapper,
                       IEntityStringMapWrapper<ItemType> itemTypeMapWrapper,
                       IEntityStringMapWrapper<Category> categoryMapWrapper,
                       IEntityStringMapWrapper<OptionGroup> optionGroupMapWrapper,
                       IItemDao iItemDao,
                       ICurrencyDiscountDao discountDao) {
        this.asyncProcessor = asyncProcessor;
        this.brandMapWrapper = brandMapWrapper;
        this.itemStatusMapWrapper = itemStatusMapWrapper;
        this.itemTypeMapWrapper = itemTypeMapWrapper;
        this.categoryMapWrapper = categoryMapWrapper;
        this.optionGroupMapWrapper = optionGroupMapWrapper;
        this.iItemDao = iItemDao;
        this.discountDao = discountDao;
    }

    @Override
    protected IGeneralEntityDao getEntityDao() {
        return this.iItemDao;
    }

    @Override
    public void saveItem(RequestItemDto requestItemDto) {
        Transaction transaction = null;
        try (Session session = sessionManager.getSession()) {
            transaction = session.beginTransaction();

//            CompletableFuture<Brand> brandCompletableFuture = CompletableFuture.supplyAsync(() ->
//                    brandIEntityStringMapWrapper.getEntity(requestItemDto.getBrand()), executor);
//            CompletableFuture<ItemType> itemTypeCompletableFuture = CompletableFuture.supplyAsync(() ->
//                    itemTypeIEntityStringMapWrapper.getEntity(requestItemDto.getItemType()), executor);
//            CompletableFuture<Category> categoryCompletableFuture = CompletableFuture.supplyAsync(() ->
//                    categoryIEntityStringMapWrapper.getEntity(requestItemDto.getCategory()), executor);
//            CompletableFuture<ItemStatus> itemStatusCompletableFuture = CompletableFuture.supplyAsync(() ->
//                    itemStatusIEntityStringMapWrapper.getEntity(ItemStatusEnum.NEW.name()), executor);
//            CompletableFuture<Void> allFutures = CompletableFuture.allOf(
//                    brandCompletableFuture,
//                    itemTypeCompletableFuture,
//                    categoryCompletableFuture,
//                    itemStatusCompletableFuture);
//            allFutures.join();

            Map<Class<?>, Object> processResultMap = executeAsyncProcess(requestItemDto);

            Item item = Item.builder()
                    .name(requestItemDto.getName())
                    .articularId(getUUID())
                    .dateOfCreate(System.currentTimeMillis())
                    .brand((Brand) processResultMap.get(Brand.class))
                    .itemType((ItemType) processResultMap.get(ItemType.class))
                    .category((Category) processResultMap.get(Category.class))
                    .status((ItemStatus) processResultMap.get(ItemStatus.class))
                    .optionItems((Set<OptionItem>) processResultMap.get(OptionItem.class))
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

            Map<Class<?>, Object> processResultMap = executeAsyncProcessForUpdate(requestItemDtoUpdate);
            Item oldItem = (Item) processResultMap.get(Item.class);

            if (oldItem != null) {
//                Item item = Item.builder()
//                        .id(oldItem.getId())
//                        .name(requestItemDto.getName())
//                        .articularId(searchField)
//                        .dateOfCreate(System.currentTimeMillis())
//                        .brand((Brand) processResultMap.get(Brand.class))
//                        .itemType((ItemType) processResultMap.get(ItemType.class))
//                        .category((Category) processResultMap.get(Category.class))
//                        .status((ItemStatus) processResultMap.get(ItemStatus.class))
//                        .optionItems((List<OptionItem>) processResultMap.get(OptionItem.class))
//                        .build();

//                session.merge(item);
            }

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
    public void deleteItemByArticularId(String articularId) {
        Parameter parameter = parameterFactory.createStringParameter("articularId", articularId);
        super.deleteEntity(parameter);
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

    private Map<Class<?>, Object> executeAsyncProcess(RequestItemDto requestItemDto) {
        Task brandTask = new Task(
                () -> brandMapWrapper.getEntity(requestItemDto.getBrand()),
                Brand.class
        );
        Task itemTypeTask = new Task(
                () -> itemTypeMapWrapper.getEntity(requestItemDto.getItemType()),
                ItemType.class
        );
        Task categoryTask = new Task(
                () -> categoryMapWrapper.getEntity(requestItemDto.getCategory()),
                Category.class
        );
        Task itemStatusTask = new Task(
                () -> itemStatusMapWrapper.getEntity(ItemStatusEnum.NEW.name()),
                ItemStatus.class
        );

        //todo
        Task optionGroupTask = new Task(
                () -> {
                    List<String> optionGroupValues = requestItemDto.getOptionGroupList().stream()
                            .map(RequestOneFieldEntityDto::getRequestValue)
                            .toList();
                    return optionGroupMapWrapper.getEntityList(optionGroupValues);
                },
                OptionGroup.class
        );

        return asyncProcessor.process(
                brandTask,
                itemTypeTask,
                categoryTask,
                itemStatusTask,
                optionGroupTask
        );
    }

    private Map<Class<?>, Object> executeAsyncProcessForUpdate(RequestItemDtoUpdate requestItemDtoUpdate) {
        RequestItemDto requestItemDto = requestItemDtoUpdate.getNewEntityDto();
        String searchField = requestItemDtoUpdate.getSearchField();
        Task brandTask = new Task(
                () -> brandMapWrapper.getEntity(requestItemDto.getBrand()),
                Brand.class
        );
        Task itemTypeTask = new Task(
                () -> itemTypeMapWrapper.getEntity(requestItemDto.getItemType()),
                ItemType.class
        );
        Task categoryTask = new Task(
                () -> categoryMapWrapper.getEntity(requestItemDto.getCategory()),
                Category.class
        );
        Task itemStatusTask = new Task(
                () -> itemStatusMapWrapper.getEntity(ItemStatusEnum.NEW.name()),
                ItemStatus.class
        );
        Task itemTask = new Task(
                () -> {
                    Parameter parameter = new Parameter("article_id", searchField);
                    return iItemDao.getGeneralEntity(parameter);
                },
                Item.class
        );

        return asyncProcessor.process(
                brandTask,
                itemTypeTask,
                categoryTask,
                itemStatusTask,
                itemTask
        );
    }
}
