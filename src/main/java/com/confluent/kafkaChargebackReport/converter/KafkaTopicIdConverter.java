package com.confluent.kafkaChargebackReport.converter;

import com.confluent.kafkaChargebackReport.model.KafkaTopic;
import com.confluent.kafkaChargebackReport.model.KafkaTopicId;
import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

import java.io.Serializable;
@Component
public class KafkaTopicIdConverter implements BackendIdConverter {
    @Override
    public Serializable fromRequestId(String id, Class<?> aClass) {
        String[] parts = id.split("_");
        KafkaTopicId kafkaTopicId = new KafkaTopicId();
        kafkaTopicId.setClusterId(parts[0]);
        kafkaTopicId.setTopicName(parts[1]);
        return kafkaTopicId;
    }

    @Override
    public String toRequestId(Serializable serializable, Class<?> aClass) {
        KafkaTopicId kafkaTopicId = (KafkaTopicId) serializable;
        return  String.format("%s_%s",kafkaTopicId.getClusterId(),kafkaTopicId.getTopicName());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return KafkaTopic.class.equals(aClass);
    }
}
