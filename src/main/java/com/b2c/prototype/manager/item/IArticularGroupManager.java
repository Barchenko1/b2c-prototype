package com.b2c.prototype.manager.item;

import com.b2c.prototype.modal.dto.payload.item.ArticularGroupDto;

import java.util.List;

public interface IArticularGroupManager {
    void saveArticularGroup(ArticularGroupDto articularGroupDto);
    void updateArticularGroup(String itemId, ArticularGroupDto articularGroupDto);
    void deleteArticularGroup(String itemId);

    ArticularGroupDto getArticularGroup(String itemId);
    List<ArticularGroupDto> getArticularGroupList();
}
