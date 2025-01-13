package com.b2c.prototype.e2e;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test", "unittest"})
public class BrandControllerE2ETest extends BasicE2ETest{

    @Test
    void testGetBrands() throws Exception {
        loadDataSet("/datasets/item/brand/testAllBrandDataSet.yml");
        mockMvc.perform(get("/api/v1/singlevalue/all")
                        .header("serviceId", "brand"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].label", is("Apple")))
                .andExpect(jsonPath("$[0].value", is("Apple")))
                .andExpect(jsonPath("$[1].label", is("Android")))
                .andExpect(jsonPath("$[1].value", is("Android")));
    }

}
