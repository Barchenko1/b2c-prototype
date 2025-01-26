package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.AbstractConstantControllerE2ETest;
import com.b2c.prototype.modal.dto.payload.ConstantPayloadDto;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.b2c.prototype.util.Constant.DELIVERY_TYPE_SERVICE_ID;

public class DeliveryTypeControllerE2ETest extends AbstractConstantControllerE2ETest {

    @Test
    public void testCreateBrand() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("Post")
                .value("Post")
                .build();

        postConstantEntity(constantPayloadDto,
                DELIVERY_TYPE_SERVICE_ID,
                "/datasets/delivery/delivery_type/emptyDeliveryTypeDataSet.yml",
                "/datasets/delivery/delivery_type/saveDeliveryTypeDataSet.yml");
    }

    @Test
    public void testUpdateBrand() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("Post")
                .value("Update Post")
                .build();

        putConstantEntity(constantPayloadDto,
                DELIVERY_TYPE_SERVICE_ID,
                "Post",
                "/datasets/delivery/delivery_type/testDeliveryTypeDataSet.yml",
                "/datasets/delivery/delivery_type/updateDeliveryTypeDataSet.yml");
    }

    @Test
    public void testPatchBrand() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("Post")
                .value("Update Post")
                .build();

        patchConstantEntity(constantPayloadDto,
                DELIVERY_TYPE_SERVICE_ID,
                "Post",
                "/datasets/delivery/delivery_type/testDeliveryTypeDataSet.yml",
                "/datasets/delivery/delivery_type/updateDeliveryTypeDataSet.yml");
    }

    @Test
    public void testDeleteBrand() {
        deleteConstantEntity(
                DELIVERY_TYPE_SERVICE_ID,
                "Post",
                "/datasets/delivery/delivery_type/testDeliveryTypeDataSet.yml",
                "/datasets/delivery/delivery_type/emptyDeliveryTypeDataSet.yml");
    }

    @Test
    public void testGetBrands() {
        List<ConstantPayloadDto> constantPayloadDtoList = List.of(
                ConstantPayloadDto.builder()
                        .label("Post")
                        .value("Post")
                        .build(),
                ConstantPayloadDto.builder()
                        .label("Courier")
                        .value("Courier")
                        .build());

        MvcResult mvcResult = getConstantEntities(DELIVERY_TYPE_SERVICE_ID,
                "/datasets/delivery/delivery_type/testAllDeliveryTypeDataSet.yml");
        assertMvcListResult(mvcResult, constantPayloadDtoList, new TypeReference<>() {});
    }

    @Test
    public void testGetBrand() {
        ConstantPayloadDto dto = ConstantPayloadDto.builder()
                .label("Post")
                .value("Post")
                .build();

        MvcResult mvcResult = getConstantEntity(DELIVERY_TYPE_SERVICE_ID,
                "Post",
                "/datasets/delivery/delivery_type/testDeliveryTypeDataSet.yml");
        assertMvcResult(mvcResult, dto);
    }

}
