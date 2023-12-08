
package com.stockmarket.commons.domain;


import com.stockmarket.commons.protocol.StatusCode;
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
public class Stock {
    private String id;
    private String name;
    private Double actPrice;
    private Double oldPrice;
    private Double lowerLimit;
    private Double upperLimit;
    
    /**
     * Set a new value for the stock price, updating its oldPrice
     * @param newValue 
     */
    public void setActPrice(Double newValue){
        this.oldPrice=actPrice;
        this.actPrice=newValue;
    }
    
    /**
     * Update a lowerLimit with validations
     * @param newValue
     * @return RESPONSE_SUCCESS in case of success, INVALID_LIMIT if the newValue is not valid
     */
    public StatusCode updateLowerLimit(Double newValue){
        if(actPrice==null){
           this.lowerLimit=newValue;
            return StatusCode.RESPONSE_SUCCESS; 
        }
        if(newValue>=actPrice)return StatusCode.INVALID_LIMIT;
        this.lowerLimit=newValue;
        return StatusCode.RESPONSE_SUCCESS;
    }
    
    /**
     * Update a upperLimit with validations
     * @param newValue
     * @return RESPONSE_SUCCESS in case of success, INVALID_LIMIT if the newValue is not valid
     */
    public StatusCode updateUpperLimit(Double newValue){
        if(actPrice==null){
            this.upperLimit=newValue;
            return StatusCode.RESPONSE_SUCCESS;
        }
        if(newValue<=actPrice)return StatusCode.INVALID_LIMIT;
        this.upperLimit=newValue;
        return StatusCode.RESPONSE_SUCCESS;
    }
}
