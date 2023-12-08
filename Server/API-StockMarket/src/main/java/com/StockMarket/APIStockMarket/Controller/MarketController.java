
package com.StockMarket.APIStockMarket.Controller;

import com.stockmarket.commons.domain.Stock;
import com.stockmarket.commons.protocol.PatchRequest;
import com.stockmarket.commons.protocol.ResponseProtocol;
import com.stockmarket.commons.protocol.StatusCode;
import com.StockMarket.APIStockMarket.Service.MarketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Katherin Alexandra Zuñiga Morales
 * @author David Santiago Fernandez Dejoy
 * @author David Santiago Giron Muñoz
 * @author Jeferson Castaño Ossa
 */
@RestController
@RequestMapping("/market")
public class MarketController {
    
    @Autowired
    private MarketService marketService;
    
    @Operation(summary = "Get all stocks registered by an user")
    @ApiResponse(responseCode = "NOT_FOUND", description = "User does not exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseProtocol.class)))
    @ApiResponse(responseCode = "RESPONSE_SUCCESS", description = "-No stocks added yet- or -User's stocks sent-", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseProtocol.class)))
    @GetMapping("/{userId}")
    public ResponseProtocol getStocks(@Parameter(description ="ID of user")@PathVariable String userId){
        ResponseProtocol<Stock> rp = new ResponseProtocol<>();
        ArrayList<Stock> stocksUser;
        stocksUser = marketService.getStocks(userId);
        if(stocksUser==null){
            rp.setValues(new ArrayList<Stock>());
            rp.setMessage("User does not exist");
            rp.setStatus(StatusCode.NOT_FOUND);
            return rp;
        }
        rp.setValues(stocksUser);
        if(stocksUser.isEmpty()){
            rp.setMessage("No stocks added yet");
        }else{
            rp.setMessage("User's stocks sent");
        }    
        rp.setStatus(StatusCode.RESPONSE_SUCCESS);
        return rp;
    }
    
    @Operation(summary = "Get a specific stock registered by an user")
    @ApiResponse(responseCode = "NOT_FOUND", description = "ERROR, Stock with id {stockId}  doesn't exist OR User with id {userId} doesn't exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseProtocol.class)))
    @ApiResponse(responseCode = "RESPONSE_SUCCESS", description = "User's stocks sent", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseProtocol.class)))
    @GetMapping("/{userId}/{stockId}")
    public ResponseProtocol getStockById(@Parameter(description ="ID of user")@PathVariable String userId,
            @Parameter(description ="ID of stock")@PathVariable String stockId){
        ResponseProtocol<Stock> rp = new ResponseProtocol<>();
        Stock stockUser;
        stockUser = marketService.getStockById(userId, stockId);
        
        if(stockUser==null){
            rp.setValues(new ArrayList<>());
            rp.setMessage("ERROR, Stock with id "+stockId+ " doesn't exist OR"
                    + " User with id "+userId+" doesn't exist");
            rp.setStatus(StatusCode.NOT_FOUND);
        }else{
            rp.addValue(stockUser);
            rp.setMessage("User's stocks sent");
            rp.setStatus(StatusCode.RESPONSE_SUCCESS);
        }
        
        return rp;
    }
    
    @Operation(summary = "Add a stock to an existing user")
    @ApiResponse(responseCode = "NOT_FOUND", description = "ERROR, Stock with id {stockId}  doesn't exist OR User with id {userId} doesn't exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseProtocol.class)))
    @ApiResponse(responseCode = "BAD_REQUEST", description = "-The protocol format is wrong- or -ERROR, LowerLimit must me lower than the actual price and UpperLimit must the greater than the actual price.-", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseProtocol.class)))
    @ApiResponse(responseCode = "ALREADY_EXISTS", description = "Stock already exists", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseProtocol.class)))
    @ApiResponse(responseCode = "RESPONSE_SUCCESS", description = "Stock {newStock.getId()} added to user with id {userId}", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseProtocol.class)))
    @PostMapping("/{userId}")
    public ResponseProtocol addStock(@Parameter(description ="ID of user")@PathVariable String userId,@Parameter(description ="Data about new stock")@RequestBody Stock newStock){
        ResponseProtocol<Stock> rp = new ResponseProtocol<>();
        
        if(newStock.getId()==null ||
                newStock.getLowerLimit()==null || 
                newStock.getUpperLimit()==null){
            rp.setStatus(StatusCode.BAD_REQUEST);
            rp.setMessage("The protocol format is wrong");
            return rp;
        }
        
        StatusCode opStatus;
        opStatus = marketService.addStock(userId, newStock);
        
        rp.setStatus(opStatus);
        if(opStatus==StatusCode.NOT_FOUND){
            rp.setMessage("ERROR, Stock with id "+newStock.getId()+ " doesn't exist OR "
                 + "User with id "+userId+" doesn't exist");
            return rp;
        } 
        if(opStatus==StatusCode.BAD_REQUEST){
            rp.setMessage("ERROR, LowerLimit must me lower than the actual price "
               + "and UpperLimit must the greater than the actual price.");
            return rp;
        }
        if(opStatus==StatusCode.ALREADY_EXISTS){
            rp.setMessage("Stock already exists");
            return rp;
        }
        if(opStatus==StatusCode.RESPONSE_SUCCESS){
            rp.setMessage("Stock(" + newStock.getId() + ") added to user with id " + userId);
        }        
        return rp;
    }
    
    
    @Operation(summary = "delete a user's stock")
    @ApiResponse(responseCode = "NOT_FOUND", description = "ERROR, Stock with id {stockId} doesn't exist OR "+ "User with id {userId} doesn't exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseProtocol.class)))
    @ApiResponse(responseCode = "RESPONSE_SUCCESS", description = "Stock {stockId} deleted successfully from the user with id {userId}", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseProtocol.class)))
    @DeleteMapping("/{userId}/{stockId}")
    public ResponseProtocol deleteStock(@Parameter(description ="ID of user")@PathVariable String userId,@Parameter(description ="ID of stock")@PathVariable String stockId){
        ResponseProtocol<Stock> rp = new ResponseProtocol<>();
        StatusCode opStatus;
        opStatus = marketService.deleteStock(userId, stockId);
        rp.setStatus(opStatus);
        if(opStatus==StatusCode.NOT_FOUND){
            rp.setMessage("ERROR, Stock with id "+stockId+" doesn't exist OR "
                    + "User with id "+userId+" doesn't exist");
            return rp;
        }
        if(opStatus==StatusCode.RESPONSE_SUCCESS){
            rp.setMessage("Stock "+stockId+" deleted successfully from the user with id "+userId);
        }  
        
        return rp;
    }
    
    
    
    @Operation(summary = "Patches a new value for upper or lower limit")
    @ApiResponse(responseCode = "RESPONSE_SUCCESS", description = "Limit was updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseProtocol.class)))
    @ApiResponse(responseCode = "BAD_REQUEST", description = "When: 1. The protocol format is wrong 2. The type of value is wrong "
            + "3. The patch request parameter is invalid 4. Limits are incorrect", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseProtocol.class)))
    @ApiResponse(responseCode = "NOT_FOUND", description = "Stock doesn't exist OR User doesn't exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseProtocol.class)))
    @PatchMapping("/{userId}")
    public ResponseProtocol updateStock(@Parameter(description ="User ID") @PathVariable String userId, @Parameter(description ="PatchRequest body for Patch") @RequestBody PatchRequest request){
        ResponseProtocol<Double> rp = new ResponseProtocol<>();
        StatusCode opStatus=null;
        if(request.getId()==null 
                || request.getParam()==null 
                || request.getValue() ==null ){
            rp.setValues(new ArrayList<>());
            rp.setMessage("The protocol format is wrong");
            rp.setStatus(StatusCode.BAD_REQUEST);
            return rp;
        }
        double newValue;
        try{
            newValue = Double.parseDouble(request.getValue());
        }catch(Exception e){
            rp.setValues(new ArrayList<>());
            rp.setMessage("The type of values is wrong");
            rp.setStatus(StatusCode.BAD_REQUEST);
            return rp;
        }
        
        if(!request.getParam().equalsIgnoreCase("lowerlimit") && !request.getParam().equalsIgnoreCase("upperlimit")){
            rp.setValues(new ArrayList<>());
            rp.setStatus(StatusCode.BAD_REQUEST);
            rp.setMessage("The patch request parameter is invalid");            
            return rp;
        }
        
        if(request.getParam().equalsIgnoreCase("lowerlimit")){
            opStatus=marketService.updateLowerLimit(userId,request.getId(),newValue); 
            rp.setMessage("ERROR, Limit of stock with id ("+request.getId()+") can not be updated. LowerLimit must be lower than UpperLimit");
        }
        if(request.getParam().equalsIgnoreCase("upperlimit")){
            opStatus=marketService.updateUpperLimit(userId,request.getId(),newValue); 
            rp.setMessage("ERROR, Limit of stock with id ("+request.getId()+") can not be updated. UpperLimit must be greater than LowerLimit");
        
        }
        
        rp.setValues(new ArrayList<>());
        rp.setStatus(opStatus);
        if(opStatus==StatusCode.NOT_FOUND){
            rp.setMessage("ERROR, Stock with id "+request.getId()+ " doesn't exist OR "
                 + "User with id "+userId+" doesn't exist");
            return rp;
        }
        if(opStatus==StatusCode.INVALID_LIMIT){
            rp.setStatus(StatusCode.BAD_REQUEST);
            return rp;
        }
         
        if(opStatus==StatusCode.BAD_REQUEST){
            rp.setMessage("ERROR, Stock with id "+request.getId()+ " doesn't exist OR "
                 + "User with id "+userId+" doesn't exist");
            return rp;
        }
        if(opStatus==StatusCode.RESPONSE_SUCCESS){
            rp.addValue(newValue);
            rp.setMessage("Limit updated of stock with id ("+request.getId()+") of the user with id "+userId);
        } 
        return rp;
    }
    
    
}
