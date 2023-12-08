
package com.StockMarket.APIStockMarket.Controller;

import com.stockmarket.commons.domain.Stock;
import com.stockmarket.commons.protocol.ResponseProtocol;
import com.stockmarket.commons.protocol.StatusCode;
import com.StockMarket.APIStockMarket.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Katherin Alexandra Zuñiga Morales
 * @author David Santiago Fernandez Dejoy
 * @author David Santiago Giron Muñoz
 * @author Jeferson Castaño Ossa
 */
@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Operation(summary = "Register a new user in the app")
    @ApiResponse(responseCode = "RESPONSE_SUCCESS", description = "User registered successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseProtocol.class)))
    @ApiResponse(responseCode = "ALREADY_EXISTS", description = "When the user it's already registered", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseProtocol.class)))
    @PostMapping("/{id}")
    public ResponseProtocol registerUser(@Parameter(description ="ID of new user") @PathVariable String id){
        ResponseProtocol<Stock> rp = new ResponseProtocol<>();
        StatusCode returValue;
        returValue = userService.registerUser(id);
        if(returValue==StatusCode.RESPONSE_SUCCESS){
            rp.setMessage("User registered successfully");
            rp.setStatus(StatusCode.RESPONSE_SUCCESS); 
        }else{
            rp.setMessage("Id already exists");
            rp.setStatus(StatusCode.ALREADY_EXISTS);
        }
        
        return rp;
    }
    
}
