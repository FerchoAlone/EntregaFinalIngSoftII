package com.StockMarket.APIStockMarket.Controller;

import com.StockMarket.Ports.Output.Persistence.IStockRepository;
import com.StockMarket.Ports.Output.Persistence.IUserRepository;
import com.stockmarket.commons.protocol.PatchRequest;
import com.stockmarket.commons.protocol.ResponseProtocol;
import com.stockmarket.commons.protocol.StatusCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockmarket.commons.domain.Stock;
import com.stockmarket.commons.domain.User;
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
public class MarketControllerTest {
    
    private MockMvc mockMvc;
    
    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private IStockRepository stockRepositoryMock;
    
    @MockBean
    private IUserRepository userRepositoryMock;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    /**
     * Test of getStocks method, of class MarketController.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetStocksSucceed_SamePrice() throws Exception {
         ArrayList<Stock> userStockList = new ArrayList<>(List.of(
                new Stock("100","Cafe La Palma",60000.0,null,50000.0,70000.0), 
                new Stock("200","Maiz Fercho",300000.0,null,20000.0,40000.0)));
         
         ArrayList<Stock> stocksList = new ArrayList<>(List.of(
                new Stock("100","Cafe La Palma",60000.0,null,null,null), 
                new Stock("200","Maiz Fercho",300000.0,null,null,null)));
         
         ArrayList<Stock> responseStockList = new ArrayList<>(List.of(
                new Stock("100","Cafe La Palma",60000.0,60000.0,50000.0,70000.0), 
                new Stock("200","Maiz Fercho",300000.0,300000.0,20000.0,40000.0)));

         User user = new User("123");
         user.setMyStocks(userStockList);
         
        
        when(userRepositoryMock.getUserById("123")).thenReturn(user);
        when(stockRepositoryMock.getStockById("100")).thenReturn(stocksList.get(0));
        when(stockRepositoryMock.getStockById("200")).thenReturn(stocksList.get(1));

        ResponseProtocol rp = new ResponseProtocol(StatusCode.RESPONSE_SUCCESS, "User's stocks sent", responseStockList);
        
        mockMvc.perform(MockMvcRequestBuilders
                .get("/market/"+user.getId()).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapToJson(rp)));
    }
    
    @Test
    public void testGetStocksSucceed_NewPrice() throws Exception {
        ArrayList<Stock> userStockList = new ArrayList<>(List.of(
               new Stock("100","Cafe La Palma",60000.0,null,50000.0,70000.0), 
               new Stock("200","Maiz Fercho",300000.0,null,20000.0,40000.0)));

        ArrayList<Stock> stocksList = new ArrayList<>(List.of(
               new Stock("100","Cafe La Palma",80000.0,60000.0,null,null), 
               new Stock("200","Maiz Fercho",100000.0,300000.0,null,null)));

        ArrayList<Stock> responseStockList = new ArrayList<>(List.of(
               new Stock("100","Cafe La Palma",80000.0,60000.0,50000.0,70000.0), 
               new Stock("200","Maiz Fercho",100000.0,300000.0,20000.0,40000.0)));

        User user = new User("123");
        user.setMyStocks(userStockList);


       when(userRepositoryMock.getUserById("123")).thenReturn(user);
       when(stockRepositoryMock.getStockById("100")).thenReturn(stocksList.get(0));
       when(stockRepositoryMock.getStockById("200")).thenReturn(stocksList.get(1));

       ResponseProtocol rp = new ResponseProtocol(StatusCode.RESPONSE_SUCCESS, "User's stocks sent", responseStockList);

       mockMvc.perform(MockMvcRequestBuilders
               .get("/market/"+user.getId()).accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.content().json(mapToJson(rp)));
   }
   @Test
    public void testGetStocksSucceed_UserDoesNotExist() throws Exception {

        ArrayList<Stock> responseStockList = new ArrayList<>();
        
        String randomId = "12334123";


       when(userRepositoryMock.getUserById(randomId)).thenReturn(null);

       ResponseProtocol rp = new ResponseProtocol(StatusCode.NOT_FOUND, "User does not exist", responseStockList);

       mockMvc.perform(MockMvcRequestBuilders
               .get("/market/"+randomId).accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.content().json(mapToJson(rp)));
   }
    @Test
    public void testGetStocksSucceed_NoStocksAdded() throws Exception {
        ArrayList<Stock> userStockList = new ArrayList<>();

        ArrayList<Stock> expectedStockList = new ArrayList<>();

        User user = new User("123");
        user.setMyStocks(userStockList);

       when(userRepositoryMock.getUserById("123")).thenReturn(user);

       ResponseProtocol rp = new ResponseProtocol(StatusCode.RESPONSE_SUCCESS, "No stocks added yet", expectedStockList);

       mockMvc.perform(MockMvcRequestBuilders
               .get("/market/"+user.getId()).accept(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.content().json(mapToJson(rp)));
   }

    /**
     * Test of getStockById method, of class MarketController.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetStockByIdSucceed_SamePrice() throws Exception {
        ArrayList<Stock> userStockList = new ArrayList<>(List.of(
                new Stock("100","Cafe La Palma",60000.0,null,50000.0,70000.0), 
                new Stock("200","Maiz Fercho",300000.0,null,20000.0,40000.0)));
         
         ArrayList<Stock> stocksList = new ArrayList<>(List.of(
                new Stock("100","Cafe La Palma",60000.0,null,null,null), 
                new Stock("200","Maiz Fercho",300000.0,null,null,null)));
         
         ArrayList<Stock> expectedStockList = new ArrayList<>(List.of(
                new Stock("100","Cafe La Palma",60000.0,60000.0,50000.0,70000.0)));

         User user = new User("123");
         user.setMyStocks(userStockList);
         
        
        when(userRepositoryMock.getUserById("123")).thenReturn(user);
        when(stockRepositoryMock.getStockById("100")).thenReturn(stocksList.get(0));

        ResponseProtocol rp = new ResponseProtocol(StatusCode.RESPONSE_SUCCESS, "User's stocks sent", expectedStockList);
        
        mockMvc.perform(MockMvcRequestBuilders
                .get("/market/"+user.getId()+"/100").accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapToJson(rp)));
        
    }
    
    @Test
    public void testGetStockByIdSucceed_NewPrice() throws Exception {
        ArrayList<Stock> userStockList = new ArrayList<>(List.of(
                new Stock("100","Cafe La Palma",60000.0,null,50000.0,70000.0), 
                new Stock("200","Maiz Fercho",300000.0,null,20000.0,40000.0)));
         
         ArrayList<Stock> stocksList = new ArrayList<>(List.of(
                new Stock("100","Cafe La Palma",80000.0,60000.0,null,null), 
                new Stock("200","Maiz Fercho",100000.0,300000.0,null,null)));
         
         ArrayList<Stock> expectedStockList = new ArrayList<>(List.of(
                new Stock("100","Cafe La Palma",80000.0,60000.0,50000.0,70000.0)));

         User user = new User("123");
         user.setMyStocks(userStockList);
         
        
        when(userRepositoryMock.getUserById("123")).thenReturn(user);
        when(stockRepositoryMock.getStockById("100")).thenReturn(stocksList.get(0));

        ResponseProtocol rp = new ResponseProtocol(StatusCode.RESPONSE_SUCCESS, "User's stocks sent", expectedStockList);
        
        mockMvc.perform(MockMvcRequestBuilders
                .get("/market/"+user.getId()+"/100").accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapToJson(rp)));
        
    }
    //TODO Esta prueba da error porque se espera que la lista se devuelva inicializada y empty, pero no null ni tampoco con un primer elemento null added
    @Test
    public void testGetStockById_StockDoesNotExist() throws Exception {
        ArrayList<Stock> userStockList = new ArrayList<>(List.of(
                new Stock("100","Cafe La Palma",60000.0,null,50000.0,70000.0), 
                new Stock("200","Maiz Fercho",300000.0,null,20000.0,40000.0)));
         
         ArrayList<Stock> stocksList = new ArrayList<>(List.of(
                new Stock("100","Cafe La Palma",80000.0,60000.0,null,null), 
                new Stock("200","Maiz Fercho",100000.0,300000.0,null,null)));
         
         ArrayList<Stock> expectedStockList = new ArrayList<>();

         User user = new User("123");
         user.setMyStocks(userStockList);
         
        
        when(userRepositoryMock.getUserById("123")).thenReturn(user);
        when(stockRepositoryMock.getStockById("1234")).thenReturn(null);

        ResponseProtocol rp = new ResponseProtocol(StatusCode.NOT_FOUND, "ERROR, Stock with id 1234 doesn't exist OR"
                    + " User with id "+user.getId()+" doesn't exist", expectedStockList);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/market/"+user.getId()+"/1234").accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapToJson(rp)));
        
    }
    @Test
    public void testGetStockById_UserDoesNotExist() throws Exception {
         
         ArrayList<Stock> stocksList = new ArrayList<>(List.of(
                new Stock("100","Cafe La Palma",80000.0,60000.0,null,null), 
                new Stock("200","Maiz Fercho",100000.0,300000.0,null,null)));
         
         ArrayList<Stock> expectedStockList = new ArrayList<>();

         String userId = "123";
         
        
        when(userRepositoryMock.getUserById(userId)).thenReturn(null);
        when(stockRepositoryMock.getStockById("100")).thenReturn(stocksList.get(0));

        ResponseProtocol rp = new ResponseProtocol(StatusCode.NOT_FOUND, "ERROR, Stock with id 100 doesn't exist OR"
                    + " User with id "+userId+" doesn't exist", expectedStockList);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/market/"+userId+"/100").accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapToJson(rp)));
        
    }
    
    @Test
    public void testGetStockById_UserStockDoesNotExist() throws Exception {
         
         ArrayList<Stock> expectedStockList = new ArrayList<>();
         
         String userId = "123";
         String stockId = "100";
        
        when(userRepositoryMock.getUserById(userId)).thenReturn(null);

        ResponseProtocol rp = new ResponseProtocol(StatusCode.NOT_FOUND, "ERROR, Stock with id "+stockId+" doesn't exist OR"
                    + " User with id "+userId+" doesn't exist", expectedStockList);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/market/"+userId+"/"+stockId).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapToJson(rp)));  
    }
    
    /**
     * Test of addStock method, of class MarketController.
     */
    @Test
    public void testAddStockSucceed() throws Exception {

         ArrayList<Stock> userStockList = new ArrayList<>(List.of(
                new Stock("100","Cafe La Palma",60000.0,null,50000.0,70000.0)));
         
         ArrayList<Stock> stocksList = new ArrayList<>(List.of(
                new Stock("100","Cafe La Palma",60000.0,null,null,null), 
                new Stock("200","Maiz Fercho",300000.0,null,null,null)));
         

         User user = new User("123");
         user.setMyStocks(userStockList);
         
         Stock stock = new Stock();
         stock.setId("200");
         stock.updateLowerLimit(200000.0);
         stock.updateUpperLimit(400000.0);
         
        
        when(userRepositoryMock.getUserById("123")).thenReturn(user);
        when(stockRepositoryMock.getStockById("200")).thenReturn(stocksList.get(1));

        ResponseProtocol rp = new ResponseProtocol(StatusCode.RESPONSE_SUCCESS, "Stock(" + stock.getId() + ") added to user with id " + user.getId(), null);
        
        mockMvc.perform(MockMvcRequestBuilders
                .post("/market/"+user.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(stock))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapToJson(rp)));  
    }
    
    @Test
    public void testAddStock_StockIdMissing() throws Exception {

         ArrayList<Stock> userStockList = new ArrayList<>(List.of(
                new Stock("100","Cafe La Palma",60000.0,null,50000.0,70000.0)));
         
         ArrayList<Stock> stocksList = new ArrayList<>(List.of(
                new Stock("100","Cafe La Palma",60000.0,null,null,null), 
                new Stock("200","Maiz Fercho",300000.0,null,null,null)));
         

         User user = new User("123");
         user.setMyStocks(userStockList);
         
         Stock stock = new Stock();
         stock.setId(null);
         stock.updateLowerLimit(200000.0);
         stock.updateUpperLimit(400000.0);
         
        
        when(userRepositoryMock.getUserById("123")).thenReturn(user);
        when(stockRepositoryMock.getStockById("200")).thenReturn(stocksList.get(1));

        ResponseProtocol rp = new ResponseProtocol(StatusCode.BAD_REQUEST, "The protocol format is wrong", null);
        
        mockMvc.perform(MockMvcRequestBuilders
                .post("/market/"+user.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(stock))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapToJson(rp)));  
    }
    
    @Test
    public void testAddStock_LowerLimitMissing() throws Exception {

         ArrayList<Stock> userStockList = new ArrayList<>(List.of(
                new Stock("100","Cafe La Palma",60000.0,null,50000.0,70000.0)));
         
         ArrayList<Stock> stocksList = new ArrayList<>(List.of(
                new Stock("100","Cafe La Palma",60000.0,null,null,null), 
                new Stock("200","Maiz Fercho",300000.0,null,null,null)));
         

         User user = new User("123");
         user.setMyStocks(userStockList);
         
         Stock stock = new Stock();
         stock.setId("200");
         stock.updateLowerLimit(null);
         stock.updateUpperLimit(400000.0);
         
        
        when(userRepositoryMock.getUserById("123")).thenReturn(user);
        when(stockRepositoryMock.getStockById("200")).thenReturn(stocksList.get(1));

        ResponseProtocol rp = new ResponseProtocol(StatusCode.BAD_REQUEST, "The protocol format is wrong", null);
        
        mockMvc.perform(MockMvcRequestBuilders
                .post("/market/"+user.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(stock))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapToJson(rp)));  
    }
    
    @Test
    public void testAddStock_UpperLimitMissing() throws Exception {

         ArrayList<Stock> userStockList = new ArrayList<>(List.of(
                new Stock("100","Cafe La Palma",60000.0,null,50000.0,70000.0)));
         
         ArrayList<Stock> stocksList = new ArrayList<>(List.of(
                new Stock("100","Cafe La Palma",60000.0,null,null,null), 
                new Stock("200","Maiz Fercho",300000.0,null,null,null)));
         

         User user = new User("123");
         user.setMyStocks(userStockList);
         
         Stock stock = new Stock();
         stock.setId("200");
         stock.updateLowerLimit(200000.0);
         stock.updateUpperLimit(null);
         
        
        when(userRepositoryMock.getUserById("123")).thenReturn(user);
        when(stockRepositoryMock.getStockById("200")).thenReturn(stocksList.get(1));

        ResponseProtocol rp = new ResponseProtocol(StatusCode.BAD_REQUEST, "The protocol format is wrong", null);
        
        mockMvc.perform(MockMvcRequestBuilders
                .post("/market/"+user.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(stock))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapToJson(rp)));  
    }
    
    @Test
    public void testAddStock_InvalidLimits() throws Exception {

         ArrayList<Stock> userStockList = new ArrayList<>(List.of(
                new Stock("100","Cafe La Palma",60000.0,null,50000.0,70000.0)));
         
         ArrayList<Stock> stocksList = new ArrayList<>(List.of(
                new Stock("100","Cafe La Palma",60000.0,null,null,null), 
                new Stock("200","Maiz Fercho",300000.0,null,null,null)));
         

         User user = new User("123");
         user.setMyStocks(userStockList);
         
         Stock stock = new Stock();
         stock.setId("200");
         stock.updateLowerLimit(400000.0);
         stock.updateUpperLimit(200000.0);
         
        
        when(userRepositoryMock.getUserById("123")).thenReturn(user);
        when(stockRepositoryMock.getStockById("200")).thenReturn(stocksList.get(1));

        ResponseProtocol rp = new ResponseProtocol(StatusCode.BAD_REQUEST, "ERROR, LowerLimit must me lower than the actual price "
                                                                                         + "and UpperLimit must the greater than the actual price.", null);
        
        mockMvc.perform(MockMvcRequestBuilders
                .post("/market/"+user.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(stock))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapToJson(rp)));  
    }
    
    @Test
    public void testAddStock_UserDoesNotExist() throws Exception {

        String userId = "123";
         
         Stock stock = new Stock();
         stock.setId("200");
         stock.updateLowerLimit(200000.0);
         stock.updateUpperLimit(400000.0);
         
        
        when(userRepositoryMock.getUserById(userId)).thenReturn(null);

        ResponseProtocol rp = new ResponseProtocol(StatusCode.NOT_FOUND, "ERROR, Stock with id "+stock.getId()+ " doesn't exist OR "
                    + "User with id "+userId+" doesn't exist", null);
        
        mockMvc.perform(MockMvcRequestBuilders
                .post("/market/"+userId)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(stock))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapToJson(rp)));  
    }
    
    @Test
    public void testAddStock_StockDoesNotExist() throws Exception {

         ArrayList<Stock> userStockList = new ArrayList<>(List.of(
                new Stock("100","Cafe La Palma",60000.0,null,50000.0,70000.0)));
         

         User user = new User("123");
         user.setMyStocks(userStockList);
         
         Stock stock = new Stock();
         stock.setId("300");
         stock.updateLowerLimit(200000.0);
         stock.updateUpperLimit(400000.0);
         
        
        when(userRepositoryMock.getUserById("123")).thenReturn(user);
        when(stockRepositoryMock.getStockById("300")).thenReturn(null);

        ResponseProtocol rp = new ResponseProtocol(StatusCode.NOT_FOUND, "ERROR, Stock with id "+stock.getId()+ " doesn't exist OR "
                    + "User with id "+user.getId()+" doesn't exist", null);
        
        mockMvc.perform(MockMvcRequestBuilders
                .post("/market/"+user.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(stock))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapToJson(rp)));  
    }

    /**
     * Test of deleteStock method, of class MarketController.
     */
    @Test
    public void testDeleteStockSucceed() throws Exception {
        
        ArrayList<Stock> userStockList = new ArrayList<>(List.of(
                new Stock("100","Cafe La Palma",60000.0,null,50000.0,70000.0), 
                new Stock("200","Maiz Fercho",300000.0,null,20000.0,40000.0)));
         

         User user = new User("123");
         user.setMyStocks(userStockList);
           
        when(userRepositoryMock.getUserById("123")).thenReturn(user);

        ResponseProtocol rp = new ResponseProtocol(StatusCode.RESPONSE_SUCCESS, "Stock 100 deleted successfully from the user with id "+user.getId(), null);
        
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/market/"+user.getId()+"/100").accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapToJson(rp)));
    }
    
    @Test
    public void testDeleteStock_UserDoesNotExist() throws Exception {
        
        String userId = "123";
           
        when(userRepositoryMock.getUserById(userId)).thenReturn(null);

        ResponseProtocol rp = new ResponseProtocol(StatusCode.NOT_FOUND, "ERROR, Stock with id 100 doesn't exist OR "
                    + "User with id "+userId+" doesn't exist",null);
        
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/market/"+userId+"/100").accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapToJson(rp)));
    }
    
    @Test
    public void testDeleteStock_StockDoesNotExist() throws Exception {
        
        ArrayList<Stock> userStockList = new ArrayList<>(List.of(
                new Stock("100","Cafe La Palma",60000.0,null,50000.0,70000.0), 
                new Stock("200","Maiz Fercho",300000.0,null,20000.0,40000.0)));
         

         User user = new User("123");
         user.setMyStocks(userStockList);
         
         String stockId = "300";
           
        when(userRepositoryMock.getUserById("123")).thenReturn(user);

        ResponseProtocol rp = new ResponseProtocol(StatusCode.NOT_FOUND, "ERROR, Stock with id "+stockId+" doesn't exist OR "
                    + "User with id "+user.getId()+" doesn't exist",null);
        
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/market/"+user.getId()+"/"+stockId).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapToJson(rp)));
    }

    /**
     * Test of updateStock method, of class MarketController.
     */
    @Test
    public void testUpdateStockSucceed_LowerLimit() throws Exception {
        ArrayList<Stock> userStockList = new ArrayList<>(List.of(
                new Stock("100","Cafe La Palma",60000.0,null,50000.0,70000.0), 
                new Stock("200","Maiz Fercho",300000.0,null,20000.0,40000.0)));
         
         ArrayList<Double> expectedResult = new ArrayList<>(List.of(10000.0));
        
         User user = new User("123");
         user.setMyStocks(userStockList);
         
        String stockId = "100";
         
        PatchRequest pr = new PatchRequest(stockId, "lowerLimit", "10000.0");
         
           
        when(userRepositoryMock.getUserById("123")).thenReturn(user);

        ResponseProtocol rp = new ResponseProtocol(StatusCode.RESPONSE_SUCCESS, "Limit updated of stock with id ("+stockId+") of the user with id "+user.getId(),expectedResult);
        
        mockMvc.perform(MockMvcRequestBuilders
                .patch("/market/"+user.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(pr))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapToJson(rp)));
    }
    
    @Test
    public void testUpdateStock_LowerLimitInvalid() throws Exception {
        ArrayList<Stock> userStockList = new ArrayList<>(List.of(
                new Stock("100","Cafe La Palma",60000.0,null,50000.0,70000.0), 
                new Stock("200","Maiz Fercho",300000.0,null,20000.0,40000.0)));
         
         ArrayList<Double> expectedResult = new ArrayList<>();
        
         User user = new User("123");
         user.setMyStocks(userStockList);
         
        String stockId = "100";
         
        PatchRequest pr = new PatchRequest(stockId, "lowerLimit", "80000.0");
         
           
        when(userRepositoryMock.getUserById("123")).thenReturn(user);

        ResponseProtocol rp = new ResponseProtocol(StatusCode.BAD_REQUEST, "ERROR, Limit of stock with id ("+stockId+") can not be updated. LowerLimit must be lower than UpperLimit",expectedResult);
        
        mockMvc.perform(MockMvcRequestBuilders
                .patch("/market/"+user.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(pr))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapToJson(rp)));
    }
    //Devolver la lista vacia. No un null.
    @Test
    public void testUpdateStock_LowerLimitUserDoesNotExist() throws Exception {
         
         ArrayList<Double> expectedResult = new ArrayList<>();
        
        String userId = "123";
         
        String stockId = "100";
         
        PatchRequest pr = new PatchRequest(stockId, "lowerLimit", "40000.0");
         
           
        when(userRepositoryMock.getUserById(userId)).thenReturn(null);

        ResponseProtocol rp = new ResponseProtocol(StatusCode.NOT_FOUND, "ERROR, Stock with id "+stockId+ " doesn't exist OR "
                 + "User with id "+userId+" doesn't exist",expectedResult);
        
        mockMvc.perform(MockMvcRequestBuilders
                .patch("/market/"+userId)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(pr))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapToJson(rp)));
    }
    
    @Test
    public void testUpdateStock_LowerLimitStockDoesNotExist() throws Exception {
        ArrayList<Stock> userStockList = new ArrayList<>(List.of(
                new Stock("100","Cafe La Palma",60000.0,null,50000.0,70000.0), 
                new Stock("200","Maiz Fercho",300000.0,null,20000.0,40000.0)));
         
         ArrayList<Double> expectedResult = new ArrayList<>();
        
         User user = new User("123");
         user.setMyStocks(userStockList);
         
        String stockId = "300";
         
        PatchRequest pr = new PatchRequest(stockId, "lowerLimit", "40000.0");
         
           
        when(userRepositoryMock.getUserById("123")).thenReturn(user);

        ResponseProtocol rp = new ResponseProtocol(StatusCode.NOT_FOUND, "ERROR, Stock with id "+stockId+ " doesn't exist OR "
                 + "User with id "+user.getId()+" doesn't exist",expectedResult);
        
        mockMvc.perform(MockMvcRequestBuilders
                .patch("/market/"+user.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(pr))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapToJson(rp)));
    }
    
    @Test
    public void testUpdateStockSucceed_UpperLimit() throws Exception {
        ArrayList<Stock> userStockList = new ArrayList<>(List.of(
                new Stock("100","Cafe La Palma",60000.0,null,50000.0,70000.0), 
                new Stock("200","Maiz Fercho",300000.0,null,20000.0,40000.0)));
         
         ArrayList<Double> expectedResult = new ArrayList<>(List.of(80000.0));
        
         User user = new User("123");
         user.setMyStocks(userStockList);
         
        String stockId = "100";
         
        PatchRequest pr = new PatchRequest(stockId, "upperLimit", "80000.0");
         
           
        when(userRepositoryMock.getUserById("123")).thenReturn(user);

        ResponseProtocol rp = new ResponseProtocol(StatusCode.RESPONSE_SUCCESS, "Limit updated of stock with id ("+stockId+") of the user with id "+user.getId(),expectedResult);
        
        mockMvc.perform(MockMvcRequestBuilders
                .patch("/market/"+user.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(pr))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapToJson(rp)));
    }
    
    @Test
    public void testUpdateStock_UpperLimitInvalid() throws Exception {
        ArrayList<Stock> userStockList = new ArrayList<>(List.of(
                new Stock("100","Cafe La Palma",60000.0,null,50000.0,70000.0), 
                new Stock("200","Maiz Fercho",300000.0,null,20000.0,40000.0)));
         
         ArrayList<Double> expectedResult = new ArrayList<>();
        
         User user = new User("123");
         user.setMyStocks(userStockList);
         
        String stockId = "100";
         
        PatchRequest pr = new PatchRequest(stockId, "upperLimit", "40000.0");
         
           
        when(userRepositoryMock.getUserById("123")).thenReturn(user);

        ResponseProtocol rp = new ResponseProtocol(StatusCode.BAD_REQUEST, "ERROR, Limit of stock with id ("+stockId+") can not be updated. UpperLimit must be greater than LowerLimit",expectedResult);
        
        mockMvc.perform(MockMvcRequestBuilders
                .patch("/market/"+user.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(pr))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapToJson(rp)));
    }
    //Devolver la lista vacia. No un null.
    @Test
    public void testUpdateStock_UpperLimitUserDoesNotExist() throws Exception {
         
         ArrayList<Double> expectedResult = new ArrayList<>();
        
        String userId = "123";
         
        String stockId = "100";
         
        PatchRequest pr = new PatchRequest(stockId, "upperLimit", "80000.0");
         
           
        when(userRepositoryMock.getUserById(userId)).thenReturn(null);

        ResponseProtocol rp = new ResponseProtocol(StatusCode.NOT_FOUND, "ERROR, Stock with id "+stockId+ " doesn't exist OR "
                 + "User with id "+userId+" doesn't exist",expectedResult);
        
        mockMvc.perform(MockMvcRequestBuilders
                .patch("/market/"+userId)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(pr))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapToJson(rp)));
    }
    
    @Test
    public void testUpdateStock_UpperLimitStockDoesNotExist() throws Exception {
        ArrayList<Stock> userStockList = new ArrayList<>(List.of(
                new Stock("100","Cafe La Palma",60000.0,null,50000.0,70000.0), 
                new Stock("200","Maiz Fercho",300000.0,null,20000.0,40000.0)));
         
         ArrayList<Double> expectedResult = new ArrayList<>();
        
         User user = new User("123");
         user.setMyStocks(userStockList);
         
        String stockId = "300";
         
        PatchRequest pr = new PatchRequest(stockId, "upperLimit", "40000.0");
         
           
        when(userRepositoryMock.getUserById("123")).thenReturn(user);

        ResponseProtocol rp = new ResponseProtocol(StatusCode.NOT_FOUND, "ERROR, Stock with id "+stockId+ " doesn't exist OR "
                 + "User with id "+user.getId()+" doesn't exist",expectedResult);
        
        mockMvc.perform(MockMvcRequestBuilders
                .patch("/market/"+user.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(pr))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapToJson(rp)));
    }
    
    /**
     * Test of updatedStock method, of class MarketController. This applies to both lowerLimit and upperLimit updates
     */
    
    @Test
    public void testUpdateStock_LimitParamInvalid() throws Exception {
        ArrayList<Stock> userStockList = new ArrayList<>(List.of(
                new Stock("100","Cafe La Palma",60000.0,null,50000.0,70000.0), 
                new Stock("200","Maiz Fercho",300000.0,null,20000.0,40000.0)));
         
         ArrayList<Double> expectedResult = new ArrayList<>();
        
         User user = new User("123");
         user.setMyStocks(userStockList);
         
         
        PatchRequest pr = new PatchRequest("100", "Invalid Limit", "10000.0");
         
           
        when(userRepositoryMock.getUserById("123")).thenReturn(user);

        ResponseProtocol rp = new ResponseProtocol(StatusCode.BAD_REQUEST, "The patch request parameter is invalid",expectedResult);
        
        mockMvc.perform(MockMvcRequestBuilders
                .patch("/market/"+user.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(pr))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapToJson(rp)));
    }
    
    @Test
    public void testUpdateStock_LimitNotNumeric() throws Exception {
        ArrayList<Stock> userStockList = new ArrayList<>(List.of(
                new Stock("100","Cafe La Palma",60000.0,null,50000.0,70000.0), 
                new Stock("200","Maiz Fercho",300000.0,null,20000.0,40000.0)));
         
         ArrayList<Double> expectedResult = new ArrayList<>();
        
         User user = new User("123");
         user.setMyStocks(userStockList);
         
        String stockId = "100";
         
        PatchRequest pr = new PatchRequest(stockId, "lowerLimit", "invalid value");
         
           
        when(userRepositoryMock.getUserById("123")).thenReturn(user);

        ResponseProtocol rp = new ResponseProtocol(StatusCode.BAD_REQUEST, "The type of values is wrong",expectedResult);
        
        mockMvc.perform(MockMvcRequestBuilders
                .patch("/market/"+user.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(pr))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapToJson(rp)));
    }
    
    @Test
    public void testUpdateStock_StockIdMissing() throws Exception {
        ArrayList<Stock> userStockList = new ArrayList<>(List.of(
                new Stock("100","Cafe La Palma",60000.0,null,50000.0,70000.0), 
                new Stock("200","Maiz Fercho",300000.0,null,20000.0,40000.0)));
         
         ArrayList<Double> expectedResult = new ArrayList<>();
        
         User user = new User("123");
         user.setMyStocks(userStockList);
         
         
        PatchRequest pr = new PatchRequest(null, "lowerLimit", "invalid value");
         
           
        when(userRepositoryMock.getUserById("123")).thenReturn(user);

        ResponseProtocol rp = new ResponseProtocol(StatusCode.BAD_REQUEST, "The protocol format is wrong",expectedResult);
        
        mockMvc.perform(MockMvcRequestBuilders
                .patch("/market/"+user.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(pr))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapToJson(rp)));
    }
    
    @Test
    public void testUpdateStock_LimitParamMissing() throws Exception {
        ArrayList<Stock> userStockList = new ArrayList<>(List.of(
                new Stock("100","Cafe La Palma",60000.0,null,50000.0,70000.0), 
                new Stock("200","Maiz Fercho",300000.0,null,20000.0,40000.0)));
         
         ArrayList<Double> expectedResult = new ArrayList<>();
        
         User user = new User("123");
         user.setMyStocks(userStockList);
         
         
        PatchRequest pr = new PatchRequest("100", null, "invalid value");
         
           
        when(userRepositoryMock.getUserById("123")).thenReturn(user);

        ResponseProtocol rp = new ResponseProtocol(StatusCode.BAD_REQUEST, "The protocol format is wrong",expectedResult);
        
        mockMvc.perform(MockMvcRequestBuilders
                .patch("/market/"+user.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(pr))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapToJson(rp)));
    }
    
    @Test
    public void testUpdateStock_ValueMissing() throws Exception {
        ArrayList<Stock> userStockList = new ArrayList<>(List.of(
                new Stock("100","Cafe La Palma",60000.0,null,50000.0,70000.0), 
                new Stock("200","Maiz Fercho",300000.0,null,20000.0,40000.0)));
         
         ArrayList<Double> expectedResult = new ArrayList<>();
        
         User user = new User("123");
         user.setMyStocks(userStockList);
         
         
        PatchRequest pr = new PatchRequest("100", "lowerLimit", null);
         
           
        when(userRepositoryMock.getUserById("123")).thenReturn(user);

        ResponseProtocol rp = new ResponseProtocol(StatusCode.BAD_REQUEST, "The protocol format is wrong",expectedResult);
        
        mockMvc.perform(MockMvcRequestBuilders
                .patch("/market/"+user.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(pr))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapToJson(rp)));
    }
    
    @Test
    public void testUpdateStock_ParametersMissing() throws Exception {
        ArrayList<Stock> userStockList = new ArrayList<>(List.of(
                new Stock("100","Cafe La Palma",60000.0,null,50000.0,70000.0), 
                new Stock("200","Maiz Fercho",300000.0,null,20000.0,40000.0)));
         
         ArrayList<Double> expectedResult = new ArrayList<>();
        
         User user = new User("123");
         user.setMyStocks(userStockList);
         
         
        PatchRequest pr = new PatchRequest(null, null, null);
         
           
        when(userRepositoryMock.getUserById("123")).thenReturn(user);

        ResponseProtocol rp = new ResponseProtocol(StatusCode.BAD_REQUEST, "The protocol format is wrong",expectedResult);
        
        mockMvc.perform(MockMvcRequestBuilders
                .patch("/market/"+user.getId())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(mapToJson(pr))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(mapToJson(rp)));
    }
    
    private static String mapToJson(final Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}
