
package com.agentefinanciero.Subscriber;

import com.agentefinanciero.infra.Observable;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

/**
 * @author Katherin Alexandra Zuñiga Morales
 * @author David Santiago Fernandez Dejoy
 * @author David Santiago Giron Muñoz
 * @author Jeferson Castaño Ossa
 */
public class RabbitMQListener extends Observable {

    private String EXCHANGE_NAME;
    private final ConnectionFactory factory;
    private final String userId;

    public RabbitMQListener(String userId) {
        String host = "localhost";
        Properties properties = new Properties();
        
        this.factory = new ConnectionFactory();
        this.userId = userId;

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties"))  {
            // Cargar el archivo de propiedades
            properties.load(input);

            // Obtener valores específicos
            host = properties.getProperty("publisher.host");
            EXCHANGE_NAME = properties.getProperty("publisher.exchange_name");
        } catch (IOException e) {
            e.printStackTrace();
            EXCHANGE_NAME = "";
        }
        
        factory.setHost(host);
    }

    public void listenNotifications() throws IOException, TimeoutException {

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "direct",true);
        //String queueName = channel.queueDeclare().getQueue();
        String queueName = channel.queueDeclare("main_"+userId, true, false, false, null).getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, userId);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            notificarTodos(message);
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }
}
