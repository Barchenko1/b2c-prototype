package com.b2c.prototype.processor.item.base;

import com.b2c.prototype.modal.dto.payload.item.ArticularItemDto;
import com.b2c.prototype.manager.item.IArticularItemManager;
import com.b2c.prototype.processor.item.IArticularItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ArticularItemProcessor implements IArticularItemProcessor {

    private final IArticularItemManager articularItemManager;

    public ArticularItemProcessor(IArticularItemManager articularItemManager) {
        this.articularItemManager = articularItemManager;
    }

    @Override
    public void saveUpdateArticularItemList(Map<String, String> requestParams, List<ArticularItemDto> articularItemDtoList) {
        String itemId = requestParams.get("itemId");
        articularItemManager.saveUpdateArticularItem(itemId, articularItemDtoList);
    }

    @Override
    public void deleteArticularItem(Map<String, String> requestParams) {
        String articularId = requestParams.get("articularId");
        articularItemManager.deleteArticularItem(articularId);
    }

    @Override
    public ArticularItemDto getArticularItemDto(Map<String, String> requestParams) {
        String articularId = requestParams.get("articularId");
        return articularItemManager.getArticularItemDto(articularId);
    }

    @Override
    public List<ArticularItemDto> getArticularItemDtoList(Map<String, String> requestParams) {
        return articularItemManager.getArticularItemDtoList();
    }

    @Override
    public List<ArticularItemDto> getArticularItemDtoFiltered(Map<String, String> requestParams) {
        return articularItemManager.getArticularItemDtoFiltered();
    }

    @Override
    public List<ArticularItemDto> getArticularItemDtoSorted(Map<String, String> requestParams) {
        String sortType = requestParams.get("sortType");
        return articularItemManager.getArticularItemDtoSorted(sortType);
    }
}
