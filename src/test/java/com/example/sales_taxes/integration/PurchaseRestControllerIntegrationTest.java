package com.example.sales_taxes.integration;

import com.example.sales_taxes.dto.PurchaseDtoIn;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.example.sales_taxes.utils.Constant.EMPTY_STRING;
import static com.example.sales_taxes.utils.RouteConstants.BASE_PURCHASE_ROUTE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class PurchaseRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void makePurchase_ValidInput1_Returns200() throws Exception {
        // Given
        PurchaseDtoIn purchaseDto = new PurchaseDtoIn("1 book at 12.49\n1 music CD at 14.99\n1 chocolate bar at 0.85");
        String requestBody = objectMapper.writeValueAsString(purchaseDto);

        // When
        MvcResult mvcResult = mockMvc.perform(post(BASE_PURCHASE_ROUTE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        String response = mvcResult.getResponse().getContentAsString();
        assertThat(response, containsString("1 book: 12.49"));
        assertThat(response, containsString("1 music CD: 16.49"));
        assertThat(response, containsString("1 chocolate bar: 0.85"));
        assertThat(response, containsString("Sales Taxes: 1.50 Total: 29.83"));
    }

    @Test
    public void makePurchase_ValidInput2_Returns200() throws Exception {
        // Given
        PurchaseDtoIn purchaseDto = new PurchaseDtoIn("1 imported box of chocolates at 10.00\n1 imported bottle of perfume at 47.50");
        String requestBody = objectMapper.writeValueAsString(purchaseDto);

        // When
        MvcResult mvcResult = mockMvc.perform(post(BASE_PURCHASE_ROUTE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        String response = mvcResult.getResponse().getContentAsString();
        assertThat(response, containsString("1 imported box of chocolates: 10.50"));
        assertThat(response, containsString("1 imported bottle of perfume: 54.65"));
        assertThat(response, containsString("Sales Taxes: 7.65 Total: 65.15"));
    }


    @Test
    public void makePurchase_ValidInput3_Returns200() throws Exception {
        // Given
        PurchaseDtoIn purchaseDto = new PurchaseDtoIn("1 imported bottle of perfume at 27.99\n1 bottle of perfume at 18.99\n1 packet of headache pills at 9.75\n1 box of imported chocolates at 11.25");
        String requestBody = objectMapper.writeValueAsString(purchaseDto);

        // When
        MvcResult mvcResult = mockMvc.perform(post(BASE_PURCHASE_ROUTE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        String response = mvcResult.getResponse().getContentAsString();
        assertThat(response, containsString("1 imported bottle of perfume: 32.19"));
        assertThat(response, containsString("1 bottle of perfume: 20.89"));
        assertThat(response, containsString("1 packet of headache pills: 9.75"));
        assertThat(response, containsString("1 imported box of chocolates: 11.85"));
        assertThat(response, containsString("Sales Taxes: 6.70 Total: 74.68"));

    }

    @Test
    public void makePurchase_InvalidInput_Returns400() throws Exception {
        // Given
        PurchaseDtoIn purchaseDto = new PurchaseDtoIn(EMPTY_STRING);
        String requestBody = objectMapper.writeValueAsString(purchaseDto);

        // When
        mockMvc.perform(post(BASE_PURCHASE_ROUTE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void makePurchase_InvalidInputFormat_Returns400() throws Exception {
        // Given
        PurchaseDtoIn purchaseDto = new PurchaseDtoIn("1 bottle of perfume 27.99");
        String requestBody = objectMapper.writeValueAsString(purchaseDto);

        // When
        mockMvc.perform(post(BASE_PURCHASE_ROUTE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());

    }

}
