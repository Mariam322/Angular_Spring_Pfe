package tn.iit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient

@SpringBootApplication
@EnableFeignClients("tn.iit.proxy")
public class DepenseApplication {

	public static void main(String[] args) {
		SpringApplication.run(DepenseApplication.class, args);
	}

}
