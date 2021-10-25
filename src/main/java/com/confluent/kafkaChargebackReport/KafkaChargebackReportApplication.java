package com.confluent.kafkaChargebackReport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableScheduling
@EnableSwagger2
@Import(SpringDataRestConfiguration.class)
public class KafkaChargebackReportApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaChargebackReportApplication.class, args);
	}


}
