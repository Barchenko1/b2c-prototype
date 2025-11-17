package com.b2c.prototype.modal.dto.payload.item;

import com.b2c.prototype.modal.dto.payload.constant.ArticularStatusDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticularItemAssignmentDto {
    private String articularId;
    private String productName;
    private LocalDateTime dateOfCreate;
    private PriceDto fullPrice;
    private PriceDto totalPrice;
    private ArticularStatusDto status;
    private List<GroupOptionKeys> optionKeys;
    private List<GroupOptionKeys> optionCostKeys;
    private GroupOptionKeys discountKey;
}
