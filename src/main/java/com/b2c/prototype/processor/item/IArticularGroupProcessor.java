package com.b2c.prototype.processor.item;

import com.b2c.prototype.modal.dto.payload.item.ArticularGroupDto;

import java.util.List;
import java.util.Map;

public interface IArticularGroupProcessor {
    void saveArticularGroup(Map<String, String> requestParams, ArticularGroupDto articularGroupDto);
    void updateArticularGroup(Map<String, String> requestParams, ArticularGroupDto articularGroupDto);
    void deleteArticularGroup(Map<String, String> requestParams);

    ArticularGroupDto getArticularGroup(Map<String, String> requestParams);
    List<ArticularGroupDto> getArticularGroupList(Map<String, String> requestParams);
}
