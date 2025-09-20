package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.AbstractConstantControllerE2ETest;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.b2c.prototype.util.Constant.ITEM_TYPE_SERVICE_ID;

public class ItemTypeControllerE2ETest extends AbstractConstantControllerE2ETest {

    @Test
    public void testCreateConstantEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("Clothes")
                .value("Clothes")
                .build();

        postConstantEntity(constantPayloadDto,
                ITEM_TYPE_SERVICE_ID,
                "/datasets/dao/item/item_type/emptyItemTypeDataSet.yml",
                "/datasets/dao/item/item_type/saveItemTypeDataSet.yml");
    }

    @Test
    public void testUpdateConstantEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("Clothes")
                .value("Update Clothes")
                .build();

        putConstantEntity(constantPayloadDto,
                ITEM_TYPE_SERVICE_ID,
                "Clothes",
                "/datasets/dao/item/item_type/testItemTypeDataSet.yml",
                "/datasets/dao/item/item_type/updateItemTypeDataSet.yml");
    }

    @Test
    public void testPatchConstantEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("Clothes")
                .value("Update Clothes")
                .build();

        patchConstantEntity(constantPayloadDto,
                ITEM_TYPE_SERVICE_ID,
                "Clothes",
                "/datasets/dao/item/item_type/testItemTypeDataSet.yml",
                "/datasets/dao/item/item_type/updateItemTypeDataSet.yml");
    }

    @Test
    public void testDeleteConstantEntity() {
        deleteConstantEntity(
                ITEM_TYPE_SERVICE_ID,
                "Clothes",
                "/datasets/dao/item/item_type/testItemTypeDataSet.yml",
                "/datasets/dao/item/item_type/emptyItemTypeDataSet.yml");
    }

    @Test
    public void testGetConstantEntities() {
        List<ConstantPayloadDto> constantPayloadDtoList = List.of(
                ConstantPayloadDto.builder()
                        .label("Clothes1")
                        .value("Clothes1")
                        .build(),
                ConstantPayloadDto.builder()
                        .label("Clothes2")
                        .value("Clothes2")
                        .build());

        MvcResult mvcResult = getConstantEntities(ITEM_TYPE_SERVICE_ID,
                "/datasets/dao/item/item_type/testAllItemTypeDataSet.yml");
        assertMvcListResult(mvcResult, constantPayloadDtoList, new TypeReference<>() {});
    }

    @Test
    public void testGetConstantEntity() {
        ConstantPayloadDto dto = ConstantPayloadDto.builder()
                .label("Clothes")
                .value("Clothes")
                .build();

        MvcResult mvcResult = getConstantEntity(ITEM_TYPE_SERVICE_ID,
                "Clothes",
                "/datasets/dao/item/item_type/testItemTypeDataSet.yml");
        assertMvcResult(mvcResult, dto);
    }



}
