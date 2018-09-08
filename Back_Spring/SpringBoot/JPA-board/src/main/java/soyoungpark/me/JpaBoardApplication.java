package soyoungpark.me;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class JpaBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaBoardApplication.class, args);
	}
}
