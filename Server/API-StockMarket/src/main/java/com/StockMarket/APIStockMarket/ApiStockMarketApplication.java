package com.StockMarket.APIStockMarket;

import com.StockMarket.Adapters.Output.Persistence.StockRepositoryArray;
import com.StockMarket.Adapters.Output.Persistence.UserRepositoryArray;
import com.StockMarket.Adapters.Output.Publisher.RabbitMQNotifier;
import com.StockMarket.Domain.Publisher.NotificationManager;
import com.StockMarket.Ports.Output.Persistence.IStockRepository;
import com.StockMarket.Ports.Output.Persistence.IUserRepository;
import com.StockMarket.Ports.Output.Publisher.INotifier;
import com.stockmarket.commons.domain.Stock;
import java.util.ArrayList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = {"com.StockMarket"})
public class ApiStockMarketApplication {

	public static void main(String[] args) {
            ConfigurableApplicationContext EX = SpringApplication.run(ApiStockMarketApplication.class, args);
            IStockRepository stocksRepo = EX.getBean(StockRepositoryArray.class);
            IUserRepository usersRepo = EX.getBean(UserRepositoryArray.class);
            
            INotifier RabbitMQNotifier = new RabbitMQNotifier("localhost");
            
            
            NotificationManager notificationM= new NotificationManager(stocksRepo,usersRepo, RabbitMQNotifier);
            notificationM.start();
            
	}   

}
