package edu.univalle.librohub.loan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication(scanBasePackages = "edu.univalle.librohub")
public class LoanServiceApplication {
	private static final Logger logger = LoggerFactory.getLogger(LoanServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(LoanServiceApplication.class, args);
		logger.info("LoanServiceApplication started");
	}
}
