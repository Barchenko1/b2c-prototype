package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.AbstractConstantControllerE2ETest;
import com.b2c.prototype.modal.dto.payload.ConstantPayloadDto;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.b2c.prototype.util.Constant.MESSAGE_TYPE_SERVICE_ID;

public class MessageTypeControllerE2ETest extends AbstractConstantControllerE2ETest {

    @Test
    public void testCreateConstantEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("InMail")
                .value("InMail")
                .build();

        postConstantEntity(constantPayloadDto,
                MESSAGE_TYPE_SERVICE_ID,
                "/datasets/message/message_type/emptyMessageTypeDataSet.yml",
                "/datasets/message/message_type/saveMessageTypeDataSet.yml");
    }

    @Test
    public void testUpdateConstantEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("InMail")
                .value("Update InMail")
                .build();

        putConstantEntity(constantPayloadDto,
                MESSAGE_TYPE_SERVICE_ID,
                "InMail",
                "/datasets/message/message_type/testMessageTypeDataSet.yml",
                "/datasets/message/message_type/updateMessageTypeDataSet.yml");
    }

    @Test
    public void testPatchConstantEntity() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("InMail")
                .value("Update InMail")
                .build();

        patchConstantEntity(constantPayloadDto,
                MESSAGE_TYPE_SERVICE_ID,
                "InMail",
                "/datasets/message/message_type/testMessageTypeDataSet.yml",
                "/datasets/message/message_type/updateMessageTypeDataSet.yml");
    }

    @Test
    public void testDeleteConstantEntity() {
        deleteConstantEntity(
                MESSAGE_TYPE_SERVICE_ID,
                "InMail",
                "/datasets/message/message_type/testMessageTypeDataSet.yml",
                "/datasets/message/message_type/emptyMessageTypeDataSet.yml");
    }

    @Test
    public void testGetConstantEntities() {
        List<ConstantPayloadDto> constantPayloadDtoList = List.of(
                ConstantPayloadDto.builder()
                        .label("InMail")
                        .value("InMail")
                        .build(),
                ConstantPayloadDto.builder()
                        .label("InApp")
                        .value("InApp")
                        .build());

        MvcResult mvcResult = getConstantEntities(MESSAGE_TYPE_SERVICE_ID,
                "/datasets/message/message_type/testAllMessageTypeDataSet.yml");
        assertMvcListResult(mvcResult, constantPayloadDtoList, new TypeReference<>() {});
    }

    @Test
    public void testGetConstantEntity() {
        ConstantPayloadDto dto = ConstantPayloadDto.builder()
                .label("InMail")
                .value("InMail")
                .build();

        MvcResult mvcResult = getConstantEntity(MESSAGE_TYPE_SERVICE_ID,
                "InMail",
                "/datasets/message/message_type/testMessageTypeDataSet.yml");
        assertMvcResult(mvcResult, dto);
    }



}
