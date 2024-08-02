package com.b2c.prototype.modal.client.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class RequestCategoryDto {
    private String name;
    private RequestCategoryDto parent;
    private List<RequestCategoryDto> childNodeList;
}
