package io.pivotal.microservices.services.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import io.pivotal.microservices.services.registration.RegistrationServer;


@SpringBootApplication(exclude = { HibernateJpaAutoConfiguration.class, //
		DataSourceAutoConfiguration.class })
@EnableDiscoveryClient
@ComponentScan(useDefaultFilters = false) // Disable component scanner
public class WebServer {

	
	public static final String ACCOUNTS_SERVICE_URL = "http://ACCOUNTS-SERVICE";

	
	public static void main(String[] args) {
		// Default to registration server on localhost
		if (System.getProperty(RegistrationServer.REGISTRATION_SERVER_HOSTNAME) == null)
			System.setProperty(RegistrationServer.REGISTRATION_SERVER_HOSTNAME, "localhost");

		// Tell server to look for web-server.properties or web-server.yml
		System.setProperty("spring.config.name", "web-server");
		SpringApplication.run(WebServer.class, args);
	}

	
	@LoadBalanced
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	
	@Bean
	public WebAccountsService accountsService() {
		return new WebAccountsService(ACCOUNTS_SERVICE_URL);
	}

	
	@Bean
	public WebAccountsController accountsController() {
		return new WebAccountsController(accountsService());
	}

	@Bean
	public HomeController homeController() {
		return new HomeController();
	}
}
