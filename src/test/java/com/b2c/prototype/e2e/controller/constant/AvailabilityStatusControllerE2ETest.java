package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.AbstractConstantControllerE2ETest;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.item.AvailabilityStatus;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.b2c.prototype.util.Constant.AVAILABILITY_STATUS_ID;

public class AvailabilityStatusControllerE2ETest extends AbstractConstantControllerE2ETest {

    @Test
    public void testCreateAvailabilityStatus() {
        ConstantPayloadDto dto = getConstantPayloadDto();

        postConstantEntity(dto,
                AVAILABILITY_STATUS_ID,
                "/datasets/item/availability_status/emptyAvailabilityStatusDataSet.yml",
                "/datasets/item/availability_status/saveAvailabilityStatusDataSet.yml");
    }

    @Test
    public void testUpdateAvailabilityStatus() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("Available")
                .value("Update Available")
                .build();

        putConstantEntity(constantPayloadDto,
                AVAILABILITY_STATUS_ID,
                "Available",
                "/datasets/item/availability_status/testAvailabilityStatusDataSet.yml",
                "/datasets/item/availability_status/updateAvailabilityStatusDataSet.yml");
    }

    @Test
    public void testPatchAvailabilityStatus() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("Available")
                .value("Update Available")
                .build();

        patchConstantEntity(constantPayloadDto,
                AVAILABILITY_STATUS_ID,
                "Available",
                "/datasets/item/availability_status/testAvailabilityStatusDataSet.yml",
                "/datasets/item/availability_status/updateAvailabilityStatusDataSet.yml");
    }

    @Test
    public void testDeleteAvailabilityStatus() {
        deleteConstantEntity(
                AVAILABILITY_STATUS_ID,
                "Available",
                "/datasets/item/availability_status/testAvailabilityStatusDataSet.yml",
                "/datasets/item/availability_status/emptyAvailabilityStatusDataSet.yml");
    }

    @Test
    public void testGetAllAvailabilityStatus() {
        List<AvailabilityStatus> constantPayloadDtoList = List.of(
                AvailabilityStatus.builder()
                        .label("Available")
                        .value("Available")
                        .build(),
                AvailabilityStatus.builder()
                        .label("Not Available")
                        .value("Not Available")
                        .build());

        MvcResult mvcResult = getConstantEntities(AVAILABILITY_STATUS_ID,
                "/datasets/item/availability_status/testAllAvailabilityStatusDataSet.yml");

        assertMvcListResult(mvcResult, constantPayloadDtoList, new TypeReference<>() {});
    }

    @Test
    public void testGetAvailabilityStatus() {
        ConstantPayloadDto dto = getConstantPayloadDto();

        MvcResult mvcResult = getConstantEntity(AVAILABILITY_STATUS_ID,
                "Available",
                "/datasets/item/availability_status/testAvailabilityStatusDataSet.yml");

        assertMvcResult(mvcResult, dto);
    }

    private ConstantPayloadDto getConstantPayloadDto() {
        return ConstantPayloadDto.builder()
                .label("Available")
                .value("Available")
                .build();
    }

}
