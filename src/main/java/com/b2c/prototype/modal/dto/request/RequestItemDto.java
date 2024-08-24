package com.b2c.prototype.modal.dto.request;

import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import lombok.Data;

import java.util.List;

@Data
public class RequestItemDto {
    private String name;
    private String articularId;
    private int count;
    private String category;
    private String brand;
    private String itemType;
    private List<RequestOneFieldEntityDto> optionGroupList;

}
