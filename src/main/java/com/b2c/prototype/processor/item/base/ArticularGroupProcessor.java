package com.b2c.prototype.processor.item.base;

import com.b2c.prototype.modal.dto.payload.item.ArticularGroupDto;
import com.b2c.prototype.manager.item.IArticularGroupManager;
import com.b2c.prototype.processor.item.IArticularGroupProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ArticularGroupProcessor implements IArticularGroupProcessor {

    private final IArticularGroupManager itemDataManager;

    public ArticularGroupProcessor(IArticularGroupManager itemDataManager) {
        this.itemDataManager = itemDataManager;
    }

    @Override
    public void saveArticularGroup(Map<String, String> requestParams, ArticularGroupDto articularGroupDto) {
        itemDataManager.saveArticularGroup(articularGroupDto);
    }

    @Override
    public void updateArticularGroup(Map<String, String> requestParams, ArticularGroupDto articularGroupDto) {
        String itemId = requestParams.get("itemId");
        if (itemId != null) {
            itemDataManager.updateArticularGroup(itemId, articularGroupDto);
        }
    }

    @Override
    public void deleteArticularGroup(Map<String, String> requestParams) {
        String itemId = requestParams.get("itemId");
        itemDataManager.deleteArticularGroup(itemId);
    }

    @Override
    public ArticularGroupDto getArticularGroup(Map<String, String> requestParams) {
        String itemId = requestParams.get("itemId");
        return itemDataManager.getArticularGroup(itemId);
    }

    @Override
    public List<ArticularGroupDto> getArticularGroupList(Map<String, String> requestParams) {
        return itemDataManager.getArticularGroupList();
    }
}
