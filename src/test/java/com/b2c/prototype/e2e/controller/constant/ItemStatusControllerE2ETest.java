package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.AbstractConstantControllerE2ETest;
import com.b2c.prototype.modal.dto.payload.ConstantPayloadDto;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.b2c.prototype.util.Constant.ITEM_STATUS_SERVICE_ID;

public class ItemStatusControllerE2ETest extends AbstractConstantControllerE2ETest {

    @Test
    public void testCreateConstantEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("New")
                .value("New")
                .build();

        postConstantEntity(constantPayloadDto,
                ITEM_STATUS_SERVICE_ID,
                "/datasets/item/item_status/emptyItemStatusDataSet.yml",
                "/datasets/item/item_status/saveItemStatusDataSet.yml");
    }

    @Test
    public void testUpdateConstantEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("Test")
                .value("Update Test")
                .build();

        putConstantEntity(constantPayloadDto,
                ITEM_STATUS_SERVICE_ID,
                "Test",
                "/datasets/item/item_status/testItemStatusDataSet.yml",
                "/datasets/item/item_status/updateItemStatusDataSet.yml");
    }

    @Test
    public void testPatchConstantEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("Test")
                .value("Update Test")
                .build();

        patchConstantEntity(constantPayloadDto,
                ITEM_STATUS_SERVICE_ID,
                "Test",
                "/datasets/item/item_status/testItemStatusDataSet.yml",
                "/datasets/item/item_status/updateItemStatusDataSet.yml");
    }

    @Test
    public void testDeleteConstantEntity() {
        deleteConstantEntity(
                ITEM_STATUS_SERVICE_ID,
                "Test",
                "/datasets/item/item_status/testItemStatusDataSet.yml",
                "/datasets/item/item_status/emptyItemStatusDataSet.yml");
    }

    @Test
    public void testGetConstantEntities() {
        List<ConstantPayloadDto> constantPayloadDtoList = List.of(
                ConstantPayloadDto.builder()
                        .label("Test1")
                        .value("Test1")
                        .build(),
                ConstantPayloadDto.builder()
                        .label("Test2")
                        .value("Test2")
                        .build());

        MvcResult mvcResult = getConstantEntities(ITEM_STATUS_SERVICE_ID,
                "/datasets/item/item_status/testAllItemStatusDataSet.yml");
        assertMvcListResult(mvcResult, constantPayloadDtoList, new TypeReference<>() {});
    }

    @Test
    public void testGetConstantEntity() {
        ConstantPayloadDto dto = ConstantPayloadDto.builder()
                .label("Test")
                .value("Test")
                .build();

        MvcResult mvcResult = getConstantEntity(ITEM_STATUS_SERVICE_ID,
                "Test",
                "/datasets/item/item_status/testItemStatusDataSet.yml");
        assertMvcResult(mvcResult, dto);
    }



}
