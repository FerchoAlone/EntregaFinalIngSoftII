package com.StockMarket.APIStockMarket.Controller;

import com.StockMarket.APIStockMarket.Access.IStockRepository;
import com.stockmarket.commons.protocol.ResponseProtocol;
import com.stockmarket.commons.protocol.StatusCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockmarket.commons.domain.Stock;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author Katherin Alexandra Zuñiga Morales
 * @author David Santiago Fernandez Dejoy
 * @author David Santiago Giron Muñoz
 * @author Jeferson Castaño Ossa
 */

@SpringBootTest
@WebAppConfiguration
public class StockControllerTest {
    
    private MockMvc mockMvc;
    
    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private IStockRepository stockRepositoryMock;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /**
     * Test of getAllStocks method, of class StockController.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetAllStocks_EmptyArray() throws Exception {
        when(stockRepositoryMock.getAllStocks()).thenReturn(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders
                .get("/stocks").accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("RESPONSE_SUCCESS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("No stocks available"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.values").isEmpty());
    }
    
    @Test
    public void testGetAllStocksSucceed() throws Exception {
        ArrayList<Stock> stockList = new ArrayList<>(List.of(
                new Stock("100","Cafe La Palma",60000.0,null,null,null), 
                new Stock("200","Maiz Fercho",300000.0,null,null,null)));
        
        when(stockRepositoryMock.getAllStocks()).thenReturn(stockList);

        ResponseProtocol rp = new ResponseProtocol(StatusCode.RESPONSE_SUCCESS, "All stocks were sent successfully", stockList);
        
        mockMvc.perform(MockMvcRequestBuilders
                .get("/stocks").accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapToJson(rp)));
    }
    
    private static String mapToJson(final Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}