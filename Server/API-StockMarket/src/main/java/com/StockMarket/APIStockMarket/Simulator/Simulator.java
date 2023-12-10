/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.StockMarket.APIStockMarket.Simulator;

import com.StockMarket.Ports.Output.Persistence.IStockRepository;
import com.StockMarket.Ports.Output.Persistence.IUserRepository;
import com.stockmarket.commons.domain.Stock;
import com.stockmarket.commons.domain.User;
import java.time.Duration;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Katherin Alexandra Zuñiga Morales
 * @author David Santiago Fernandez Dejoy
 * @author David Santiago Giron Muñoz
 * @author Jeferson Castaño Ossa
 */
public class Simulator extends Thread {
    
    private IStockRepository stocks;
    private IUserRepository users;

    public Simulator(IStockRepository stocks, IUserRepository users) {
        this.stocks = stocks;
        this.users = users;
    }
    
    @Override
    public void run(){
        ArrayList<User> allUsers = users.getAllUsers();
        //stocks.add(new Stock("100","Cafe La Palma",60000.0,null,null,null));
        //stocks.add(new Stock("200","Maiz Fercho",300000.0,null,null,null));
        //stocks.add(new Stock("300","Coca - Cola",200000.0,null,null,null));
        
        allUsers.get(0).addStock(new Stock("100","Cafe La Palma",60000.0,null,50000.0,70000.0));
        allUsers.get(0).addStock(new Stock("200","Maiz Fercho",300000.0,null,290000.0,310000.0));
        allUsers.get(0).addStock(new Stock("300","Coca - Cola",200000.0,null,190000.0,210000.0));
        
        allUsers.get(1).addStock(new Stock("100","Cafe La Palma",60000.0,null,50000.0,80000.0));
        allUsers.get(1).addStock(new Stock("200","Maiz Fercho",300000.0,null,270000.0,320000.0));
        allUsers.get(1).addStock(new Stock("300","Coca - Cola",200000.0,null,190000.0,220000.0));
        
        try {
            simular();
        } catch (InterruptedException ex) {
            Logger.getLogger(Simulator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void simular() throws InterruptedException {
        Stock stockaux;
        //PRIMER CAMBIO
        stockaux= stocks.getStockById("100");
        stockaux.setActPrice(75000.0);
        stockaux= stocks.getStockById("200");
        stockaux.setActPrice(280000.0);
        System.out.println("Primera vuelta");
        Thread.sleep(Duration.ofSeconds(25));
        //SEGUNDO CAMBIO
        stockaux= stocks.getStockById("200");
        stockaux.setActPrice(260000.0);
        stockaux= stocks.getStockById("300");
        stockaux.setActPrice(230000.0);
        System.out.println("Segunda vuelta");
    }
    
}
