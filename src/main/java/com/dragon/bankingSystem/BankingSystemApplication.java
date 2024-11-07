package com.dragon.bankingSystem;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@OpenAPIDefinition(
		info = @Info(
				title = "Bank System Management",
				version = "v1.0",
				description = "This is a Bank system API documentation",
				contact = @Contact(name = "Rana Tarek", email = "ranatarek138@gmail.com")
//				license = @License(name = "ryuu", url = "https://github.com/ranatarek-rt/Banking-System")

		),
		externalDocs = @ExternalDocumentation(
		description = "Find more info here",
		url = "https://github.com/ranatarek-rt/Banking-System")
)
@SpringBootApplication
public class BankingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingSystemApplication.class, args);
	}

}
