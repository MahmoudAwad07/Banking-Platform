package com.mycompany.banking.platform.webservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.mycompany.banking.platform")
public class BankingPlatformWebserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingPlatformWebserviceApplication.class, args);
	}

}
