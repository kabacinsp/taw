package pl.kabacinsp.tawconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class TawConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(TawConfigApplication.class, args);
	}

}
