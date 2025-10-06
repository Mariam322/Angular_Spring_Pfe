package tn.iit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient

@SpringBootApplication
@EnableFeignClients("tn.iit.proxy")
public class FacturationApplication {

	public static void main(String[] args) {
		SpringApplication.run(FacturationApplication.class, args);
	}

}
