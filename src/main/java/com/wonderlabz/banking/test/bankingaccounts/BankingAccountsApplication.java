package com.wonderlabz.banking.test.bankingaccounts;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankingAccountsApplication {
	public static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");

	public static void main(String[] args) {
		SpringApplication.run(BankingAccountsApplication.class, args);
	}

}
