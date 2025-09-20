
package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.AbstractConstantControllerE2ETest;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.b2c.prototype.util.Constant.OPTION_GROUP_SERVICE_ID;

public class OptionGroupControllerE2ETest extends AbstractConstantControllerE2ETest {

    @Test
    public void testCreateConstantEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("Color")
                .value("Color")
                .build();

        postConstantEntity(constantPayloadDto,
                OPTION_GROUP_SERVICE_ID,
                "/datasets/dao/option/option_group/emptyOptionGroupDataSet.yml",
                "/datasets/dao/option/option_group/saveOptionGroupDataSet.yml");
    }

    @Test
    public void testUpdateConstantEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("Color")
                .value("Update Color")
                .build();

        putConstantEntity(constantPayloadDto,
                OPTION_GROUP_SERVICE_ID,
                "Color",
                "/datasets/dao/option/option_group/testOptionGroupDataSet.yml",
                "/datasets/dao/option/option_group/updateOptionGroupDataSet.yml");
    }

    @Test
    public void testPatchConstantEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("Color")
                .value("Update Color")
                .build();

        patchConstantEntity(constantPayloadDto,
                OPTION_GROUP_SERVICE_ID,
                "Color",
                "/datasets/dao/option/option_group/testOptionGroupDataSet.yml",
                "/datasets/dao/option/option_group/updateOptionGroupDataSet.yml");
    }

    @Test
    public void testDeleteConstantEntity() {
        deleteConstantEntity(
                OPTION_GROUP_SERVICE_ID,
                "Color",
                "/datasets/dao/option/option_group/testOptionGroupDataSet.yml",
                "/datasets/dao/option/option_group/emptyOptionGroupDataSet.yml");
    }

    @Test
    public void testGetConstantEntities() {
        List<ConstantPayloadDto> constantPayloadDtoList = List.of(
                ConstantPayloadDto.builder()
                        .label("Color")
                        .value("Color")
                        .build(),
                ConstantPayloadDto.builder()
                        .label("Size")
                        .value("Size")
                        .build());

        MvcResult mvcResult = getConstantEntities(OPTION_GROUP_SERVICE_ID,
                "/datasets/dao/option/option_group/testAllOptionGroupDataSet.yml");
        assertMvcListResult(mvcResult, constantPayloadDtoList, new TypeReference<>() {});
    }

    @Test
    public void testGetConstantEntity() {
        ConstantPayloadDto dto = ConstantPayloadDto.builder()
                .label("Color")
                .value("Color")
                .build();

        MvcResult mvcResult = getConstantEntity(OPTION_GROUP_SERVICE_ID,
                "Color",
                "/datasets/dao/option/option_group/testOptionGroupDataSet.yml");
        assertMvcResult(mvcResult, dto);
    }



}
