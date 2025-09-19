package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.common.SearchFieldUpdateCollectionEntityDto;
import com.b2c.prototype.modal.dto.payload.item.ArticularItemDto;
import com.b2c.prototype.modal.dto.payload.item.ResponseArticularItemDto;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.item.MetaData;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.b2c.prototype.manager.item.IArticularItemManager;
import com.nimbusds.jose.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Constant.ARTICULAR_ID;

@Service
public class ArticularItemManager implements IArticularItemManager {

    private final IGeneralEntityDao generalEntityDao;
    private final ITransformationFunctionService transformationFunctionService;

    public ArticularItemManager(IGeneralEntityDao generalEntityDao,
                                ITransformationFunctionService transformationFunctionService) {
        this.generalEntityDao = generalEntityDao;
        this.transformationFunctionService = transformationFunctionService;
    }

    @Override
    public void saveUpdateArticularItem(String itemId, List<ArticularItemDto> articularItemDtoList) {
        SearchFieldUpdateCollectionEntityDto<ArticularItemDto> searchFieldUpdateCollectionEntityDto =
                SearchFieldUpdateCollectionEntityDto.<ArticularItemDto>builder()
                        .searchField(itemId)
                        .updateDtoSet(articularItemDtoList)
                        .build();
        MetaData metaData = transformationFunctionService.getEntity(
                MetaData.class,
                searchFieldUpdateCollectionEntityDto);

        generalEntityDao.mergeEntity(metaData);
    }

    @Override
    public void deleteArticularItem(String articularId) {
        MetaData metaData = generalEntityDao.findEntity(
                "ArticularItem.findItemDataByArticularId",
                Pair.of(ARTICULAR_ID, articularId));
        metaData.getArticularItemSet().stream()
                .filter(ai -> ai.getArticularUniqId().equals(articularId))
                .findFirst()
                .ifPresent(articularItem -> {
                    Discount discount = articularItem.getDiscount();
                    articularItem.setDiscount(null);
                    metaData.getArticularItemSet().remove(articularItem);
                    generalEntityDao.removeEntity(articularItem);
                    if (discount != null &&
                            discount.getArticularItemList().size() == 1 &&
                            discount.getArticularItemList().get(0).getArticularUniqId().equals(articularId)) {
                        generalEntityDao.removeEntity(discount);
                    }
                });
    }

    @Override
    public ResponseArticularItemDto getResponseArticularItemDto(String articularId) {
        ArticularItem articularItem = generalEntityDao.findEntity(
                "",
                Pair.of(ARTICULAR_ID, articularId));

        return Optional.of(articularItem)
                .map(transformationFunctionService.getTransformationFunction(ArticularItem.class, ResponseArticularItemDto.class))
                .orElseThrow(() -> new RuntimeException(""));
    }

    @Override
    public List<ResponseArticularItemDto> getResponseArticularItemDtoList() {
        List<ArticularItem> articularItemList = generalEntityDao.findEntityList(
                "ArticularItem.full", (Pair<String, ?>) null);

        return articularItemList.stream()
                .map(transformationFunctionService.getTransformationFunction(ArticularItem.class, ResponseArticularItemDto.class))
                .toList();
    }

    @Override
    public List<ResponseArticularItemDto> getResponseArticularItemDtoFiltered() {
        List<ArticularItem> articularItemList= generalEntityDao.findEntityList(
                "ArticularItem.full",
                (Pair<String, ?>) null);

        return articularItemList.stream()
                .map(transformationFunctionService.getTransformationFunction(ArticularItem.class, ResponseArticularItemDto.class))
                .toList();
    }

    @Override
    public List<ResponseArticularItemDto> getResponseArticularItemDtoSorted(String sortType) {
        List<ArticularItem> articularItemList = generalEntityDao.findEntityList(
                "ArticularItem.full", (Pair<String, ?>) null);

        return articularItemList.stream()
                .map(transformationFunctionService.getTransformationFunction(ArticularItem.class, ResponseArticularItemDto.class))
                .toList();
    }

}
