package com.StockMarket.APIStockMarket.Controller;

import com.StockMarket.Ports.Output.Persistence.IUserRepository;
import com.stockmarket.commons.domain.User;
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
public class UserControllerTest {
    
    private MockMvc mockMvc;
    
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @MockBean
    private IUserRepository userRepositoryMock;
    
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
    /**
     * Test of registerUser method, of class UserController.
     * @throws java.lang.Exception
     */
    @Test
    public void testRegisterUserSucceed() throws Exception {
        String idUser = "104621011383";
        when(userRepositoryMock.addUser(new User(idUser))).thenReturn(true);
        
        mockMvc.perform(MockMvcRequestBuilders
                .post("/users/"+idUser).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("RESPONSE_SUCCESS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User registered successfully"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.values").isEmpty());
    }
    
    @Test
    public void testRegisterUserAlreadyExists() throws Exception {
        String idUser = "104621011383";
        when(userRepositoryMock.addUser(new User(idUser))).thenReturn(false);
        
        mockMvc.perform(MockMvcRequestBuilders
                .post("/users/"+idUser).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("ALREADY_EXISTS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Id already exists"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.values").isEmpty());
    }
    
}
