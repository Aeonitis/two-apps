package com.calc.math.integration;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MathIntegrationTest {

    public static final String ENDPOINT_BASE = "http://localhost:8080";
    public static final String ENDPOINT_MAX = ENDPOINT_BASE + "/max";
    public static final String ENDPOINT_MIN = ENDPOINT_BASE + "/min";
    public static final String ENDPOINT_AVG = ENDPOINT_BASE + "/avg";
    public static final String ENDPOINT_MEDIAN = ENDPOINT_BASE + "/median";
    public static final String ENDPOINT_PERCENTILE = ENDPOINT_BASE + "/percentile";

    public static final String MESSAGE_INCONGRUENT_ELEMENT_QUANTITY = "The quantity of elements are unequal to amount of elements in the list";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenGetRequestForMaxEndpointWorksCorrectly() throws Exception {
        mockMvc.perform(get(ENDPOINT_MAX)
                .param("numbers", "6, 6.25")
                .param("n", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("6.25"));
    }

    @Test
    public void givenGetRequestForMaxEndpointWhenNIsInvalidThenFails() throws Exception {
        mockMvc.perform(get(ENDPOINT_MAX)
                .param("numbers", "6, 6.25")
                .param("n", "1"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(MESSAGE_INCONGRUENT_ELEMENT_QUANTITY));
    }

    @Test
    public void givenGetRequestForMaxEndpointWhenListSizeExceedsLimitThenFails() throws Exception {
        mockMvc.perform(get(ENDPOINT_MAX)
                .param("numbers", "6, 6.25")
                .param("n", "11"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(MESSAGE_INCONGRUENT_ELEMENT_QUANTITY));
    }

    @Test
    public void givenGetRequestForMinEndpointWorksCorrectly() throws Exception {
        mockMvc.perform(get(ENDPOINT_MIN)
                .param("numbers", "6, 6.25")
                .param("n", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("6.0"));
    }

    @Test
    public void givenGetRequestForMinEndpointWhenNIsInvalidThenFails() throws Exception {
        mockMvc.perform(get(ENDPOINT_MIN)
                .param("numbers", "6, 6.25")
                .param("n", "1"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(MESSAGE_INCONGRUENT_ELEMENT_QUANTITY));
    }

    @Test
    public void givenGetRequestForAverageEndpointWorksCorrectly() throws Exception {
        mockMvc.perform(get(ENDPOINT_AVG)
                .param("numbers", "1, 2, 4.5")
                .param("n", "3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("2.5"));
    }

    @Test
    public void givenGetRequestForAverageEndpointWhenNIsInvalidThenFails() throws Exception {
        mockMvc.perform(get(ENDPOINT_AVG)
                .param("numbers", "1, 2, 4.5")
                .param("n", "1"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(MESSAGE_INCONGRUENT_ELEMENT_QUANTITY));
    }


    @Test
    public void givenGetRequestForMedianEndpointWorksCorrectly() throws Exception {
        mockMvc.perform(get(ENDPOINT_MEDIAN)
                .param("numbers", "1, 2, 4.5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("2.0"));
    }

    @Test
    public void givenGetRequestForMedianEndpointWhenListIsEmptyThenFails() {
        
    }

    @Test
    public void givenGetRequestForPercentileEndpointWorksCorrectly() throws Exception {
        mockMvc.perform(get(ENDPOINT_PERCENTILE)
                .param("numbers", "1, 2, 4.5")
                .param("k", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1.04"));
    }
}
