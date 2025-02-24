package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.item.IItemDataOptionDao;
import com.b2c.prototype.modal.dto.common.SearchFieldUpdateCollectionEntityDto;
import com.b2c.prototype.modal.dto.payload.ArticularItemDto;
import com.b2c.prototype.modal.dto.response.ResponseArticularItemDto;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.service.common.EntityOperationManager;
import com.b2c.prototype.service.common.IEntityOperationManager;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.item.IArticularItemManager;
import com.b2c.prototype.service.query.ISearchService;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.finder.factory.IParameterFactory;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.ARTICULAR_ID;
import static com.b2c.prototype.util.Constant.ARTICULAR_ITEM_FULL;

public class ArticularItemManager implements IArticularItemManager {

    private final IEntityOperationManager entityOperationDao;
    private final ISearchService searchService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;
    private final IParameterFactory parameterFactory;

    public ArticularItemManager(IItemDataOptionDao itemDataOptionDao,
                                ISearchService searchService,
                                ITransformationFunctionService transformationFunctionService,
                                ISupplierService supplierService,
                                IParameterFactory parameterFactory) {
        this.entityOperationDao = new EntityOperationManager(itemDataOptionDao);
        this.searchService = searchService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveUpdateArticularItem(String itemId, List<ArticularItemDto> articularItemDtoList) {
        entityOperationDao.executeConsumer(session -> {
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
        entityOperationDao.executeConsumer(session -> {
            ItemData itemData = searchService.getNamedQueryEntity(
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
        return entityOperationDao.getEntityGraphDto(
                ARTICULAR_ITEM_FULL,
                parameterFactory.createStringParameter(ARTICULAR_ID, articularId),
                transformationFunctionService.getTransformationFunction(ArticularItem.class, ResponseArticularItemDto.class));
    }

    @Override
    public List<ResponseArticularItemDto> getResponseArticularItemDtoList() {
        return entityOperationDao.getEntityGraphDtoList(
                ARTICULAR_ITEM_FULL,
                transformationFunctionService.getTransformationFunction(ArticularItem.class, ResponseArticularItemDto.class));

    }

    @Override
    public List<ResponseArticularItemDto> getResponseArticularItemDtoFiltered() {
        return entityOperationDao.getEntityGraphDtoList(
                ARTICULAR_ITEM_FULL,
                transformationFunctionService.getTransformationFunction(ArticularItem.class, ResponseArticularItemDto.class));
    }

    @Override
    public List<ResponseArticularItemDto> getResponseArticularItemDtoSorted(String sortType) {
        return entityOperationDao.getEntityGraphDtoList(
                ARTICULAR_ITEM_FULL,
                transformationFunctionService.getTransformationFunction(ArticularItem.class, ResponseArticularItemDto.class));
    }

}
