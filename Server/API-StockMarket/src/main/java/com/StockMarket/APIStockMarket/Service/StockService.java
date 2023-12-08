
package com.StockMarket.APIStockMarket.Service;

import com.StockMarket.APIStockMarket.Access.IStockRepository;
import com.stockmarket.commons.domain.Stock;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Katherin Alexandra Zuñiga Morales
 * @author David Santiago Fernandez Dejoy
 * @author David Santiago Giron Muñoz
 * @author Jeferson Castaño Ossa
 */
@Service
public class StockService {
    
    @Autowired
    private IStockRepository stockDataBase;
    
    /**
     * Get all Stocks of "database"
     * @return Array of stocks
     */
    public ArrayList<Stock> getAllStocks(){
        return stockDataBase.getAllStocks();
    }
    
    /**
     * Get a stock by id
     * @param stockId
     * @return Stock with stockId or in case error null
     */
    public Stock getStockById(String stockId){
        return stockDataBase.getStockById(stockId);
    }
      
}
