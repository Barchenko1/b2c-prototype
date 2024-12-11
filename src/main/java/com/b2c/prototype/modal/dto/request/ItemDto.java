package com.b2c.prototype.modal.dto.request;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import lombok.Data;

import java.util.List;

@Data
public class ItemDto {
    private String name;
    private String articularId;
    private int count;
    private String category;
    private String brand;
    private String itemType;
    private List<OneFieldEntityDto> optionGroupList;

}
