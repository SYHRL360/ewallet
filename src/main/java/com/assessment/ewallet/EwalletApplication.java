package com.assessment.ewallet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EwalletApplication {


	private static final Logger log = LoggerFactory.getLogger(EwalletApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(EwalletApplication.class, args);
		log.info("\n=======================================================\n\t" +
				"E-Wallet Application running\n\t" +
				"Swagger Documentation: http://localhost:8080/swagger-ui/index.html\n\t" +
				"==========================================================");
	}

}
