
package com.StockMarket.Adapters.Output.Persistence;

import com.StockMarket.Ports.Output.Persistence.IStockRepository;
import com.stockmarket.commons.domain.Stock;
import java.util.ArrayList;
import org.springframework.stereotype.Repository;

/**
 * @author Katherin Alexandra Zuñiga Morales
 * @author David Santiago Fernandez Dejoy
 * @author David Santiago Giron Muñoz
 * @author Jeferson Castaño Ossa
 */
@Repository
public final class StockRepositoryArray implements IStockRepository{
    
    private static ArrayList<Stock> stocks;

    public StockRepositoryArray() {
        init();
    }
    
    
    /**
     * Initializes the simulated database information
     */
    private void init(){
        stocks = new ArrayList<>();
        stocks.add(new Stock("100","Cafe La Palma",60000.0,null,null,null));
        stocks.add(new Stock("200","Maiz Fercho",300000.0,null,null,null));
        stocks.add(new Stock("300","Coca - Cola",200000.0,null,null,null));
        stocks.add(new Stock("400","Vecol",150000.0,null,null,null));
        stocks.add(new Stock("500","Papa SAS",170000.0,null,null,null));
        stocks.add(new Stock("600","Corabastos",300000.0,null,null,null));
        stocks.add(new Stock("700","Fiduagraria",150000.0,null,null,null));
        stocks.add(new Stock("800","Agencia Nacional de Tierras",30000.0,null,null,null));
        stocks.add(new Stock("900","Cargil",80000.0,null,null,null));
        stocks.add(new Stock("1000","Bunge",250000.0,null,null,null));
    }
    
    @Override
    public ArrayList<Stock> getAllStocks() {
        return stocks;
    }
    
    @Override
    public Stock getStockById(String stockId) {
        for(Stock stock:stocks){
            if(stock.getId().equals(stockId))return stock;
        }
        return null;
    }
    
}
