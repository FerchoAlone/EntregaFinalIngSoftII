
package com.StockMarket.Adapters.Input.Controller;

import com.StockMarket.Ports.Input.IStock;
import com.stockmarket.commons.domain.Stock;
import com.stockmarket.commons.protocol.ResponseProtocol;
import com.stockmarket.commons.protocol.StatusCode;
import com.StockMarket.Domain.Service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Katherin Alexandra Zuñiga Morales
 * @author David Santiago Fernandez Dejoy
 * @author David Santiago Giron Muñoz
 * @author Jeferson Castaño Ossa
 */
@RestController
@RequestMapping("/stocks")
public class StockController implements IStock {
    
    @Autowired
    private StockService stockService;
    
    @Operation(summary = "get all stocks")
    @ApiResponse(responseCode = "RESPONSE_SUCCESS", description = "-No stocks available- OR -All stocks were sent successfully-", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseProtocol.class)))
    @GetMapping
    @Override
    public ResponseProtocol getAllStocks(){
        ResponseProtocol<Stock> rp = new ResponseProtocol<>();
        ArrayList<Stock> stocks;
        stocks = stockService.getAllStocks();
        if(stocks.isEmpty()){
            rp.setMessage("No stocks available");
            rp.setStatus(StatusCode.RESPONSE_SUCCESS); 
        }else{
            rp.setValues(stocks);
            rp.setMessage("All stocks were sent successfully");
            rp.setStatus(StatusCode.RESPONSE_SUCCESS); 
        }
        
        return rp;
    }
    
}
