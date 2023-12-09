/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.StockMarket.Domain.Publisher;

import com.StockMarket.Ports.Output.Persistence.IStockRepository;
import com.StockMarket.Ports.Output.Persistence.IUserRepository;
import com.StockMarket.Ports.Output.Publisher.INotifier;
import com.stockmarket.commons.domain.Stock;
import com.stockmarket.commons.domain.User;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Katherin Alexandra Zuñiga Morales
 * @author David Santiago Fernandez Dejoy
 * @author David Santiago Giron Muñoz
 * @author Jeferson Castaño Ossa
 */
public class NotificationManager extends Thread {

    private IStockRepository stocks;
    private IUserRepository users;
    private INotifier notifier;

    public NotificationManager(IStockRepository stocks, IUserRepository users, INotifier notifier) {
        this.stocks = stocks;
        this.users = users;
        this.notifier = notifier;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Verificando...");
            checkStockValues();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(NotificationManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private boolean verifyValueWithLimits(Stock userStock, Stock globalStock) {
        return true;
    }

    private void checkStockValues() {
        //1 REVISTAR VALORES DE ACCIONES
        ArrayList<Stock> currentStocks = stocks.getAllStocks();
        //2 IR USUARIO POR USUARIO Y VER SI TIENE LA ACCION REGISTRADA
        ArrayList<User> currentUsers = users.getAllUsers();
        StringBuilder msg = new StringBuilder();
        for (User user : currentUsers) {
            if(user.getMyStocks()==null)continue;
            for (Stock userStock : user.getMyStocks()) {
                for (Stock globalStock : currentStocks) {

                    if (userStock.getId().equals(globalStock.getId())) {
                        //  2.1 SI LA TIENE REGISTRADA , VERIFICAR SI SE SALIO DE LOS LIMITES
                        if (verifyValueWithLimits(userStock, globalStock)) {
                            //  2.2 SI SE SALIO DE LOS LIMITES , ENVIAR NOTIFICACION
                            msg.append("Oye ").append(user.getId()).append(" , el valor de la accion ").append(userStock.getName()).append("(").append(userStock.getId()).append(") HA SALIDO DE TUS LIMITES, Revisa!! ");
                            notifier.sendNotification(msg.toString());
                            msg.delete(0, msg.length());
                        }

                    }
                }
            }

        }
    }

}
