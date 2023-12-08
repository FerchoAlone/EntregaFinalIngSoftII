package com.StockMarket.APIStockMarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.StockMarket"})
public class ApiStockMarketApplication {

	public static void main(String[] args) {
            SpringApplication.run(ApiStockMarketApplication.class, args);
	}   

}
