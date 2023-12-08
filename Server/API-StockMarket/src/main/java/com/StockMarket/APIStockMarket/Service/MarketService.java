
package com.StockMarket.APIStockMarket.Service;

import com.StockMarket.APIStockMarket.Access.IStockRepository;
import com.StockMarket.APIStockMarket.Access.IUserRepository;
import com.stockmarket.commons.domain.Stock;
import com.stockmarket.commons.domain.User;
import com.stockmarket.commons.protocol.StatusCode;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Katherin Alexandra Zuñiga Morales
 * @author David Santiago Fernandez Dejoy
 * @author David Santiago Giron Muñoz
 * @author Jeferson Castaño Ossa
 */
@Service
public class MarketService {
    
    @Autowired
    private IUserRepository userDataBase;
    @Autowired
    private IStockRepository stockDataBase;
    
    /**
     * Update the stocks of user of id userId
     * @param userId
     * @return the array of user's stocks updated
     */
    public ArrayList<Stock> getStocks(String userId){
        ArrayList<Stock> userStocks;
        User user;
        
        user = userDataBase.getUserById(userId);
        if(user==null)return null;
        
        userStocks =user.getMyStocks();
        if(userStocks==null)return null;
        
        Stock stockAux = null;
        
        for(Stock stock:userStocks){
            stockAux = stockDataBase.getStockById(stock.getId());
            stock.setActPrice(stockAux.getActPrice());
        }
  
        return userStocks;
    }
    
    /**
     * Get an updated stock by its Id from a user
     * @param userId
     * @param stockId
     * @return the stock updated
     */
    public Stock getStockById(String userId,String stockId){
        User user;
        Stock userStock;
        
        user=userDataBase.getUserById(userId);
        if(user==null)return null;
        
        userStock = user.getStockById(stockId);
        if(userStock==null)return null;
        Stock stockAux = null;
        
        stockAux = stockDataBase.getStockById(userStock.getId());
        userStock.setActPrice(stockAux.getActPrice());

        return userStock;
    }
    
    /**
     * Add stock with id stockId to the briefcase of user with id userId
     * @param userId
     * @param newStock
     * @return true in succesfull case , false in other case
     */
    public StatusCode addStock(String userId,Stock newStock){
        
        
        Stock stockAux;
        StatusCode returnValue;
        stockAux = stockDataBase.getStockById(newStock.getId());
        if(stockAux==null)return StatusCode.NOT_FOUND;
        
        Stock stockUser = new Stock();
        stockUser.setId(stockAux.getId());
        stockUser.setName(stockAux.getName());
        stockUser.setActPrice(stockAux.getActPrice());
        if(stockUser.updateLowerLimit(newStock.getLowerLimit())==StatusCode.INVALID_LIMIT){
            return StatusCode.BAD_REQUEST;
        }
        if(stockUser.updateUpperLimit(newStock.getUpperLimit())==StatusCode.INVALID_LIMIT){
            return StatusCode.BAD_REQUEST;
        }
        
        User user;
        user = userDataBase.getUserById(userId);
        if(user==null)return StatusCode.NOT_FOUND;
        returnValue=user.addStock(stockUser);
        return returnValue;
    }
    
    /**
     * Delete the stock with id stockId of the briefcase of the user with id userId
     * @param userId
     * @param stockId
     * @return true in succesfull case , false in other case
     */
    public StatusCode deleteStock(String userId,String stockId){
        StatusCode returnValue;
        User user;
        user = userDataBase.getUserById(userId);
        if(user==null)return StatusCode.NOT_FOUND;
        returnValue=user.deleteStock(stockId);
        return returnValue;
    }
    
    /**
     * Update the lowerLimit of Stock with id stockId
     * of the user with id userId with the value of limitValue
     * @param userId
     * @param stockId
     * @param limitValue
     * @return true in succesfull case , false in other case
     */
    public StatusCode updateLowerLimit(String userId,String stockId,Double limitValue){
        StatusCode returnValue;
        User user;
        user = userDataBase.getUserById(userId);
        if(user==null)return StatusCode.NOT_FOUND;
        returnValue=user.updateLowerLimit(stockId, limitValue);
        return returnValue;
    }
    /**
     * Update the upperLimit of Stock with id stockId
     * of the user with id userId with the value of limitValue
     * @param userId
     * @param stockId
     * @param limitValue
     * @return true in succesfull case , false in other case
     */
    public StatusCode updateUpperLimit(String userId,String stockId,Double limitValue){
        StatusCode returnValue;
        User user;
        user = userDataBase.getUserById(userId);
        if(user==null)return StatusCode.NOT_FOUND;
        
        returnValue=user.updateUpperLimit(stockId, limitValue);

        return returnValue;
    }
}
