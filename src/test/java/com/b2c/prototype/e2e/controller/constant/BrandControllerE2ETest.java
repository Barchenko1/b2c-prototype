package com.b2c.prototype.e2e.controller.constant;

import com.b2c.prototype.e2e.AbstractConstantControllerE2ETest;
import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.item.Brand;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static com.b2c.prototype.util.Constant.BRAND_SERVICE_ID;

public class BrandControllerE2ETest extends AbstractConstantControllerE2ETest {

    @Test
    public void testCreateBrand() {
        ConstantPayloadDto dto = getConstantPayloadDto();

        postConstantEntity(dto,
                BRAND_SERVICE_ID,
                "/datasets/item/brand/emptyBrandDataSet.yml",
                "/datasets/item/brand/saveBrandDataSet.yml");
    }

    @Test
    public void testUpdateBrand() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("Apple")
                .value("Update Apple")
                .build();

        putConstantEntity(constantPayloadDto,
                BRAND_SERVICE_ID,
                "Apple",
                "/datasets/item/brand/testBrandDataSet.yml",
                "/datasets/item/brand/updateBrandDataSet.yml");
    }

    @Test
    public void testPatchBrand() {
        ConstantPayloadDto constantPayloadDto = ConstantPayloadDto.builder()
                .label("Apple")
                .value("Update Apple")
                .build();

        patchConstantEntity(constantPayloadDto,
                BRAND_SERVICE_ID,
                "Apple",
                "/datasets/item/brand/testBrandDataSet.yml",
                "/datasets/item/brand/updateBrandDataSet.yml");
    }

    @Test
    public void testDeleteBrand() {
        deleteConstantEntity(
                BRAND_SERVICE_ID,
                "Apple",
                "/datasets/item/brand/testBrandDataSet.yml",
                "/datasets/item/brand/emptyBrandDataSet.yml");
    }

    @Test
    public void testGetBrands() {
        List<Brand> constantPayloadDtoList = List.of(
                Brand.builder()
                        .label("Apple")
                        .value("Apple")
                        .build(),
                Brand.builder()
                        .label("Android")
                        .value("Android")
                        .build());

        MvcResult mvcResult = getConstantEntities(BRAND_SERVICE_ID,
                "/datasets/item/brand/testAllBrandDataSet.yml");

        assertMvcListResult(mvcResult, constantPayloadDtoList, new TypeReference<>() {});
    }

    @Test
    public void testGetBrand() {
        ConstantPayloadDto dto = getConstantPayloadDto();

        MvcResult mvcResult = getConstantEntity(BRAND_SERVICE_ID,
                "Apple",
                "/datasets/item/brand/testBrandDataSet.yml");

        assertMvcResult(mvcResult, dto);
    }

    private ConstantPayloadDto getConstantPayloadDto() {
        return ConstantPayloadDto.builder()
                .label("Apple")
                .value("Apple")
                .build();
    }

}
