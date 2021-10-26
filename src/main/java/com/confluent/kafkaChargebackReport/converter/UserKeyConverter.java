package com.confluent.kafkaChargebackReport.converter;

import com.confluent.kafkaChargebackReport.model.User;
import com.confluent.kafkaChargebackReport.model.UserKey;
import org.springframework.data.rest.webmvc.spi.BackendIdConverter;
import org.springframework.stereotype.Component;

import java.io.Serializable;
@Component
public class UserKeyConverter implements BackendIdConverter {
    @Override
    public Serializable fromRequestId(String id, Class<?> aClass) {
        String[] parts = id.split("_");
        UserKey userKey = new UserKey();
        userKey.setUserId(parts[0]);
        userKey.setClusterId(parts[1]);
        return userKey;
    }

    @Override
    public String toRequestId(Serializable source, Class<?> aClass) {
        UserKey userKey = (UserKey) source;
        return String.format("%s_%s",userKey.getUserId(),userKey.getClusterId());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }
}
