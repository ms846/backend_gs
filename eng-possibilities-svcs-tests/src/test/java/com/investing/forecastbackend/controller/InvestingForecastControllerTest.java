package com.investing.forecastbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.investing.forecastbackend.model.ForecastRequest;
import com.investing.forecastbackend.service.InvestingForecastService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = {
        InvestingForecastController.class,
        InvestingForecastService.class})
class InvestingForecastControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    InvestingForecastService service;

    @Test
    void getInvestmentOptionsTest() throws Exception {
        mvc.perform(get("/api/v1/forecast")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

        String response = mvc.perform(get("/api/v1/forecast")
                .contentType(APPLICATION_JSON)).andReturn()
                .getResponse()
                .getContentAsString();

        Assert.isTrue(response.contains("Energy"), "Found historical data for Energy returns");
        Assert.isTrue(response.contains("Pharmaceuticals"), "Found historical data for Pharmaceutical returns");
        Assert.isTrue(response.contains("Airline"), "Found historical data for Airline returns");
        Assert.isTrue(response.contains("Gaming"), "Found historical data for Gaming returns");
        Assert.isTrue(response.contains("Technology"), "Found historical data for Technology  Returns");
        Assert.isTrue(response.contains("Financial Services"), "Found historical data for Financial Services returns");
        Assert.isTrue(response.contains("Real Estate"), "Found historical data for Real Estate returns");
        Assert.isTrue(response.contains("Retail"), "Found historical data for Retail returns");

    }

    @Test
    void getForecastTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Double> userRequest = new HashMap<>();
        userRequest.put("Energy", 7.0);
        userRequest.put("Technology", 20.0);
        userRequest.put("Financial Services", 13.0);
        userRequest.put("Airline", 10.0);
        userRequest.put("Retail", 10.0);
        userRequest.put("Gaming", 10.0);
        userRequest.put("Pharmaceuticals", 10.0);
        userRequest.put("Real Estate", 20.0);

        ForecastRequest forecastRequest = new ForecastRequest();
        forecastRequest.setRequest(userRequest);
        String json = mapper.writeValueAsString(forecastRequest);

        String response = mvc.perform(post("/api/v1/forecast")
                .contentType(APPLICATION_JSON).content(json))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assert.isTrue(response.contains("11506.731000000002,13260.976289187,15428.083018646988,17959.798245707985,21077.313512531255,25041.747080053556,29787.470586061496,34998.87920949637,41382.38106095218,48824.9268532954"), "Successfully Calculated Forecast");

    }
}
