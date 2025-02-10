
package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.AbstractConstantControllerE2ETest;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.b2c.prototype.util.Constant.COUNT_TYPE_SERVICE_ID;

public class CountTypeControllerE2ETest extends AbstractConstantControllerE2ETest {

    @Test
    public void testCreateConstantEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("limited")
                .value("LIMITED")
                .build();

        postConstantEntity(constantPayloadDto,
                COUNT_TYPE_SERVICE_ID,
                "/datasets/store/count_type/emptyCountTypeDataSet.yml",
                "/datasets/store/count_type/saveCountTypeDataSet.yml");
    }

    @Test
    public void testUpdateConstantEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("unlimited")
                .value("UNLIMITED")
                .build();

        putConstantEntity(constantPayloadDto,
                COUNT_TYPE_SERVICE_ID,
                "LIMITED",
                "/datasets/store/count_type/testCountTypeDataSet.yml",
                "/datasets/store/count_type/updateCountTypeDataSet.yml");
    }

    @Test
    public void testPatchConstantEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("unlimited")
                .value("UNLIMITED")
                .build();

        patchConstantEntity(constantPayloadDto,
                COUNT_TYPE_SERVICE_ID,
                "LIMITED",
                "/datasets/store/count_type/testCountTypeDataSet.yml",
                "/datasets/store/count_type/updateCountTypeDataSet.yml");
    }

    @Test
    public void testDeleteConstantEntity() {
        deleteConstantEntity(
                COUNT_TYPE_SERVICE_ID,
                "LIMITED",
                "/datasets/store/count_type/testCountTypeDataSet.yml",
                "/datasets/store/count_type/emptyCountTypeDataSet.yml");
    }

    @Test
    public void testGetConstantEntities() {
        List<ConstantPayloadDto> constantPayloadDtoList = List.of(
                ConstantPayloadDto.builder()
                        .label("limited")
                        .value("LIMITED")
                        .build(),
                ConstantPayloadDto.builder()
                        .label("unlimited")
                        .value("UNLIMITED")
                        .build());

        MvcResult mvcResult = getConstantEntities(COUNT_TYPE_SERVICE_ID,
                "/datasets/store/count_type/testAllCountTypeDataSet.yml");
        assertMvcListResult(mvcResult, constantPayloadDtoList, new TypeReference<>() {});
    }

    @Test
    public void testGetConstantEntity() {
        ConstantPayloadDto dto = ConstantPayloadDto.builder()
                .label("limited")
                .value("LIMITED")
                .build();

        MvcResult mvcResult = getConstantEntity(COUNT_TYPE_SERVICE_ID,
                "LIMITED",
                "/datasets/store/count_type/testCountTypeDataSet.yml");
        assertMvcResult(mvcResult, dto);
    }



}
