package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.AbstractConstantControllerE2ETest;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.b2c.prototype.util.Constant.ORDER_STATUS_SERVICE_ID;

public class OrderStatusControllerE2ETest extends AbstractConstantControllerE2ETest {

    @Test
    public void testCreateConstantEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("Pending")
                .value("Pending")
                .build();

        postConstantEntity(constantPayloadDto,
                ORDER_STATUS_SERVICE_ID,
                "/datasets/order/order_status/emptyOrderStatusDataSet.yml",
                "/datasets/order/order_status/saveOrderStatusDataSet.yml");
    }

    @Test
    public void testUpdateConstantEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("Pending")
                .value("Update Pending")
                .build();

        putConstantEntity(constantPayloadDto,
                ORDER_STATUS_SERVICE_ID,
                "Pending",
                "/datasets/order/order_status/testOrderStatusDataSet.yml",
                "/datasets/order/order_status/updateOrderStatusDataSet.yml");
    }

    @Test
    public void testPatchConstantEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("Pending")
                .value("Update Pending")
                .build();

        patchConstantEntity(constantPayloadDto,
                ORDER_STATUS_SERVICE_ID,
                "Pending",
                "/datasets/order/order_status/testOrderStatusDataSet.yml",
                "/datasets/order/order_status/updateOrderStatusDataSet.yml");
    }

    @Test
    public void testDeleteConstantEntity() {
        deleteConstantEntity(
                ORDER_STATUS_SERVICE_ID,
                "Pending",
                "/datasets/order/order_status/testOrderStatusDataSet.yml",
                "/datasets/order/order_status/emptyOrderStatusDataSet.yml");
    }

    @Test
    public void testGetConstantEntities() {
        List<ConstantPayloadDto> constantPayloadDtoList = List.of(
                ConstantPayloadDto.builder()
                        .label("Pending")
                        .value("Pending")
                        .build(),
                ConstantPayloadDto.builder()
                        .label("Complete")
                        .value("Complete")
                        .build());

        MvcResult mvcResult = getConstantEntities(ORDER_STATUS_SERVICE_ID,
                "/datasets/order/order_status/testAllOrderStatusDataSet.yml");
        assertMvcListResult(mvcResult, constantPayloadDtoList, new TypeReference<>() {});
    }

    @Test
    public void testGetConstantEntity() {
        ConstantPayloadDto dto = ConstantPayloadDto.builder()
                .label("Pending")
                .value("Pending")
                .build();

        MvcResult mvcResult = getConstantEntity(ORDER_STATUS_SERVICE_ID,
                "Pending",
                "/datasets/order/order_status/testOrderStatusDataSet.yml");
        assertMvcResult(mvcResult, dto);
    }



}
