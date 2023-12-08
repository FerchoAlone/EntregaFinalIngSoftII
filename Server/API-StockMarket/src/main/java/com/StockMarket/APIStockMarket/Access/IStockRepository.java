
package com.StockMarket.APIStockMarket.Access;

import com.stockmarket.commons.domain.Stock;
import java.util.ArrayList;

/**
 * @author Katherin Alexandra Zuñiga Morales
 * @author David Santiago Fernandez Dejoy
 * @author David Santiago Giron Muñoz
 * @author Jeferson Castaño Ossa
 */
public interface IStockRepository {
    /**
     * Get all Stocks of "database"
     * @return Array of stocks
     */
    ArrayList<Stock> getAllStocks();
    
    /**
     * Get a stock by its id
     * @param stockId
     * @return Stock with stockId or in case error null
     */
    Stock getStockById(String stockId);
    
}
