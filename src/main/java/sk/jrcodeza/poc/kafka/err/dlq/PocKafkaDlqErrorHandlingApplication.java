package sk.jrcodeza.poc.kafka.err.dlq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PocKafkaDlqErrorHandlingApplication {

	public static void main(String[] args) {
		SpringApplication.run(PocKafkaDlqErrorHandlingApplication.class, args);
	}

}
