
package com.stockmarket.commons.domain;

import com.stockmarket.commons.protocol.StatusCode;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Katherin Alexandra Zuñiga Morales
 * @author David Santiago Fernandez Dejoy
 * @author David Santiago Giron Muñoz
 * @author Jeferson Castaño Ossa
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    
    private String id;
    private ArrayList<Stock> myStocks;
    
    public User(String id) {
        this.id = id;
    }
    
    /**
     * Add a stock to myStocks
     * @param stock
     * @return RESPONSE_SUCCESS if the stock is added, ALREADY_EXISTS if the stock was already added
     */
    public StatusCode addStock(Stock stock){
        if(getStockById(stock.getId())!=null)return StatusCode.ALREADY_EXISTS;
        if(myStocks == null){
            myStocks = new ArrayList<>();
        }
        myStocks.add(stock);
        return StatusCode.RESPONSE_SUCCESS;
    }
    
    /**
     * Delete a stock from myStocks
     * @param stockId
     * @return RESPONSE_SUCCESS in case of success, NOT_FOUND if the stock was not in myStocks
     */
    public StatusCode deleteStock(String stockId){
        for(Stock stock:myStocks){
            if(stock.getId().equals(stockId)){
                myStocks.remove(stock);
                return StatusCode.RESPONSE_SUCCESS;
            }
        }
        return StatusCode.NOT_FOUND;
    }
    
    /**
     * Update the lower limit of a stock
     * @param stockId
     * @param newValue
     * @return StatusCode given by updateLowerLimit function, NOT_FOUND if the stock did not exist in myStocks
     */
    public StatusCode updateLowerLimit(String stockId,Double newValue){
        for(Stock stock:myStocks){
            if(stock.getId().equals(stockId)){
                
                return stock.updateLowerLimit(newValue);
            }
        }
        return StatusCode.NOT_FOUND;
    }
    
    /**
     * Update the upper limit of a stock
     * @param stockId
     * @param newValue
     * @return StatusCode given by updateUpperLimit function, NOT_FOUND if the stock did not exist in myStocks
     */
    public StatusCode updateUpperLimit(String stockId,Double newValue){
        for(Stock stock:myStocks){
            if(stock.getId().equals(stockId)){
                return stock.updateUpperLimit(newValue);
            }
        }
        return StatusCode.NOT_FOUND;
    }
    
    /**
     * Get a stock by its id from myStocks
     * @param stockId
     * @return Stock in case of success, null if the stock was not found
     */
    public Stock getStockById(String stockId){
        if(myStocks==null)return null;
        for(Stock stock:myStocks){
            if(stock.getId().equals(stockId))return stock;
        }
        return null;
    }
    
}
