/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.StockMarket.Ports.Input;

import com.stockmarket.commons.domain.Stock;
import com.stockmarket.commons.protocol.PatchRequest;
import com.stockmarket.commons.protocol.ResponseProtocol;

/**
 * @author Katherin Alexandra Zuñiga Morales
 * @author David Santiago Fernandez Dejoy
 * @author David Santiago Giron Muñoz
 * @author Jeferson Castaño Ossa
 */
public interface IMarket {


    ResponseProtocol addStock(String userId, Stock newStock);

    ResponseProtocol deleteStock(String userId,String stockId);

    ResponseProtocol getStockById(String userId,String stockId);

    ResponseProtocol getStocks(String userId);

    ResponseProtocol updateStock(String userId,PatchRequest request);
    
}
