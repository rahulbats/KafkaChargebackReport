package com.confluent.kafkaChargebackReport.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "USER_DATA")
@Entity
public class User {
    @EmbeddedId
    private UserKey userKey;

    public UserKey getUserKey() {
        return userKey;
    }

    public void setUserKey(UserKey userKey) {
        this.userKey = userKey;
    }
}