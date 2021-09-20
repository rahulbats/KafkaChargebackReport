package com.confluent.kafkaChargebackReport;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;

@SpringBootApplication
@EnableScheduling
public class KafkaChargebackReportApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaChargebackReportApplication.class, args);
	}


}
