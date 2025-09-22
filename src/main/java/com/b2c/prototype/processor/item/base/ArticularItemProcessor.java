package com.b2c.prototype.processor.item.base;

import com.b2c.prototype.modal.dto.payload.item.ArticularItemDto;
import com.b2c.prototype.modal.dto.payload.item.ResponseArticularItemDto;
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
    public ResponseArticularItemDto getResponseArticularItemDto(Map<String, String> requestParams) {
        String articularId = requestParams.get("articularId");
        return articularItemManager.getResponseArticularItemDto(articularId);
    }

    @Override
    public List<ResponseArticularItemDto> getResponseArticularItemDtoList(Map<String, String> requestParams) {
        return articularItemManager.getResponseArticularItemDtoList();
    }

    @Override
    public List<ResponseArticularItemDto> getResponseArticularItemDtoFiltered(Map<String, String> requestParams) {
        return articularItemManager.getResponseArticularItemDtoFiltered();
    }

    @Override
    public List<ResponseArticularItemDto> getResponseArticularItemDtoSorted(Map<String, String> requestParams) {
        String sortType = requestParams.get("sortType");
        return articularItemManager.getResponseArticularItemDtoSorted(sortType);
    }
}
