package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.item.IItemDataOptionDao;
import com.b2c.prototype.modal.dto.common.SearchFieldUpdateCollectionEntityDto;
import com.b2c.prototype.modal.dto.payload.item.ArticularItemDto;
import com.b2c.prototype.modal.dto.payload.item.ResponseArticularItemDto;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.b2c.prototype.manager.item.IArticularItemManager;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.query.IFetchHandler;
import com.tm.core.process.manager.common.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;

import java.util.List;

import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
import static com.b2c.prototype.util.Constant.ARTICULAR_ITEM_FULL;

public class ArticularItemManager implements IArticularItemManager {

    private final IEntityOperationManager entityOperationManager;
    private final IFetchHandler fetchHandler;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;

    public ArticularItemManager(IItemDataOptionDao itemDataOptionDao,
                                IFetchHandler fetchHandler,
                                ITransformationFunctionService transformationFunctionService,
                                IParameterFactory parameterFactory) {
        this.entityOperationManager = new EntityOperationManager(itemDataOptionDao);
        this.fetchHandler = fetchHandler;
        this.transformationFunctionService = transformationFunctionService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveUpdateArticularItem(String itemId, List<ArticularItemDto> articularItemDtoList) {
        entityOperationManager.executeConsumer(session -> {
            SearchFieldUpdateCollectionEntityDto<ArticularItemDto> searchFieldUpdateCollectionEntityDto =
                    SearchFieldUpdateCollectionEntityDto.<ArticularItemDto>builder()
                            .searchField(itemId)
                            .updateDtoSet(articularItemDtoList)
                            .build();
            ItemData itemData = transformationFunctionService.getEntity(
                    session,
                    ItemData.class,
                    searchFieldUpdateCollectionEntityDto);

            session.merge(itemData);
        });
    }

    @Override
    public void deleteArticularItem(String articularId) {
        entityOperationManager.executeConsumer(session -> {
            ItemData itemData = fetchHandler.getNamedQueryEntity(
                    ItemData.class,
                    "ArticularItem.findItemDataByArticularId",
                    parameterFactory.createStringParameter(ARTICULAR_ID, articularId));
            itemData.getArticularItemSet().stream()
                    .filter(ai -> ai.getArticularId().equals(articularId))
                    .findFirst()
                    .ifPresent(articularItem -> {
                        Discount discount = articularItem.getDiscount();
                        articularItem.setDiscount(null);
                        itemData.getArticularItemSet().remove(articularItem);
                        session.remove(articularItem);
                        if (discount != null &&
                            discount.getArticularItemList().size() == 1 &&
                            discount.getArticularItemList().get(0).getArticularId().equals(articularId)) {
                            session.remove(discount);
                        }
                    });
        });
    }

    @Override
    public ResponseArticularItemDto getResponseArticularItemDto(String articularId) {
        return entityOperationManager.getGraphEntityDto(
                ARTICULAR_ITEM_FULL,
                parameterFactory.createStringParameter(ARTICULAR_ID, articularId),
                transformationFunctionService.getTransformationFunction(ArticularItem.class, ResponseArticularItemDto.class));
    }

    @Override
    public List<ResponseArticularItemDto> getResponseArticularItemDtoList() {
        return entityOperationManager.getGraphEntityDtoList(
                ARTICULAR_ITEM_FULL,
                transformationFunctionService.getTransformationFunction(ArticularItem.class, ResponseArticularItemDto.class));

    }

    @Override
    public List<ResponseArticularItemDto> getResponseArticularItemDtoFiltered() {
        return entityOperationManager.getGraphEntityDtoList(
                ARTICULAR_ITEM_FULL,
                transformationFunctionService.getTransformationFunction(ArticularItem.class, ResponseArticularItemDto.class));
    }

    @Override
    public List<ResponseArticularItemDto> getResponseArticularItemDtoSorted(String sortType) {
        return entityOperationManager.getGraphEntityDtoList(
                ARTICULAR_ITEM_FULL,
                transformationFunctionService.getTransformationFunction(ArticularItem.class, ResponseArticularItemDto.class));
    }

}
