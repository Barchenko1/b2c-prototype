package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.modal.dto.common.SearchFieldUpdateCollectionEntityDto;
import com.b2c.prototype.modal.dto.payload.item.ArticularItemDto;
import com.b2c.prototype.modal.dto.payload.item.ResponseArticularItemDto;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.b2c.prototype.manager.item.IArticularItemManager;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.IFetchHandler;
import com.tm.core.process.dao.common.ITransactionEntityDao;
import com.tm.core.process.manager.common.ITransactionEntityOperationManager;
import com.tm.core.process.manager.common.operator.TransactionEntityOperationManager;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.ARTICULAR_ID;

public class ArticularItemManager implements IArticularItemManager {

    private final ITransactionEntityOperationManager entityOperationManager;
    private final IFetchHandler fetchHandler;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;

    public ArticularItemManager(ITransactionEntityDao itemDataOptionDao,
                                IFetchHandler fetchHandler,
                                ITransformationFunctionService transformationFunctionService,
                                IParameterFactory parameterFactory) {
        this.entityOperationManager = new TransactionEntityOperationManager(itemDataOptionDao);
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
                    (Session) session,
                    ItemData.class,
                    searchFieldUpdateCollectionEntityDto);

            session.merge(itemData);
        });
    }

    @Override
    public void deleteArticularItem(String articularId) {
        entityOperationManager.executeConsumer(session -> {
            ItemData itemData = fetchHandler.getNamedQueryEntityClose(
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
        ArticularItem articularItem = entityOperationManager.getNamedQueryEntityClose(
                "",
                parameterFactory.createStringParameter(ARTICULAR_ID, articularId));

        return Optional.of(articularItem)
                .map(transformationFunctionService.getTransformationFunction(ArticularItem.class, ResponseArticularItemDto.class))
                .orElseThrow(() -> new RuntimeException(""));
    }

    @Override
    public List<ResponseArticularItemDto> getResponseArticularItemDtoList() {
        List<ArticularItem> articularItemList = entityOperationManager.getNamedQueryEntityListClose(
                "ArticularItem.full");

        return articularItemList.stream()
                .map(transformationFunctionService.getTransformationFunction(ArticularItem.class, ResponseArticularItemDto.class))
                .toList();
    }

    @Override
    public List<ResponseArticularItemDto> getResponseArticularItemDtoFiltered() {
        List<ArticularItem> articularItemList= entityOperationManager.getNamedQueryEntityListClose(
                "ArticularItem.full",
                parameterFactory.createStringParameter("", ""));

        return articularItemList.stream()
                .map(transformationFunctionService.getTransformationFunction(ArticularItem.class, ResponseArticularItemDto.class))
                .toList();
    }

    @Override
    public List<ResponseArticularItemDto> getResponseArticularItemDtoSorted(String sortType) {
        List<ArticularItem> articularItemList = entityOperationManager.getNamedQueryEntityListClose(
                "ArticularItem.full");

        return articularItemList.stream()
                .map(transformationFunctionService.getTransformationFunction(ArticularItem.class, ResponseArticularItemDto.class))
                .toList();
    }

}
