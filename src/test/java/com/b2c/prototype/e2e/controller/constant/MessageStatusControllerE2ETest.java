package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.AbstractConstantControllerE2ETest;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.b2c.prototype.util.Constant.MESSAGE_STATUS_SERVICE_ID;

public class MessageStatusControllerE2ETest extends AbstractConstantControllerE2ETest {

    @Test
    public void testCreateConstantEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("New")
                .value("New")
                .build();

        postConstantEntity(constantPayloadDto,
                MESSAGE_STATUS_SERVICE_ID,
                "/datasets/message/message_status/emptyMessageStatusDataSet.yml",
                "/datasets/message/message_status/saveMessageStatusDataSet.yml");
    }

    @Test
    public void testUpdateConstantEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("New")
                .value("Update New")
                .build();

        putConstantEntity(constantPayloadDto,
                MESSAGE_STATUS_SERVICE_ID,
                "New",
                "/datasets/message/message_status/testMessageStatusDataSet.yml",
                "/datasets/message/message_status/updateMessageStatusDataSet.yml");
    }

    @Test
    public void testPatchConstantEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("New")
                .value("Update New")
                .build();

        patchConstantEntity(constantPayloadDto,
                MESSAGE_STATUS_SERVICE_ID,
                "New",
                "/datasets/message/message_status/testMessageStatusDataSet.yml",
                "/datasets/message/message_status/updateMessageStatusDataSet.yml");
    }

    @Test
    public void testDeleteConstantEntity() {
        deleteConstantEntity(
                MESSAGE_STATUS_SERVICE_ID,
                "New",
                "/datasets/message/message_status/testMessageStatusDataSet.yml",
                "/datasets/message/message_status/emptyMessageStatusDataSet.yml");
    }

    @Test
    public void testGetConstantEntities() {
        List<ConstantPayloadDto> constantPayloadDtoList = List.of(
                ConstantPayloadDto.builder()
                        .label("New")
                        .value("New")
                        .build(),
                ConstantPayloadDto.builder()
                        .label("Read")
                        .value("Read")
                        .build());

        MvcResult mvcResult = getConstantEntities(MESSAGE_STATUS_SERVICE_ID,
                "/datasets/message/message_status/testAllMessageStatusDataSet.yml");
        assertMvcListResult(mvcResult, constantPayloadDtoList, new TypeReference<>() {});
    }

    @Test
    public void testGetConstantEntity() {
        ConstantPayloadDto dto = ConstantPayloadDto.builder()
                .label("New")
                .value("New")
                .build();

        MvcResult mvcResult = getConstantEntity(MESSAGE_STATUS_SERVICE_ID,
                "New",
                "/datasets/message/message_status/testMessageStatusDataSet.yml");
        assertMvcResult(mvcResult, dto);
    }



}
