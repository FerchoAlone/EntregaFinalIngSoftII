/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.StockMarket.Adapters.Output.Publisher;

import com.StockMarket.Ports.Output.Publisher.INotifier;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Katherin Alexandra Zuñiga Morales
 * @author David Santiago Fernandez Dejoy
 * @author David Santiago Giron Muñoz
 * @author Jeferson Castaño Ossa
 */
public class RabbitMQNotifier implements INotifier {
    
    private static final String EXCHANGE_NAME = "direct_manage_notifications";
    private ConnectionFactory factory;
    private String host;

    public RabbitMQNotifier(String host) {
        this.factory = new ConnectionFactory();
        this.host = host;
        factory.setHost(this.host);
    }
    
    
    
    @Override
    public boolean sendNotification(String msg, String userId) {
 
        try (Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "direct",true);
            
            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder().deliveryMode(2).build();
            channel.basicPublish(EXCHANGE_NAME, userId, properties, msg.getBytes());
            
        } catch (IOException | TimeoutException ex) {
            Logger.getLogger(RabbitMQNotifier.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        System.out.println("Enviando:"+msg);
        return true;
    }
    
}
