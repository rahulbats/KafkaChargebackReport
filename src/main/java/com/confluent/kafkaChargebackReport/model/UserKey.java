package com.confluent.kafkaChargebackReport.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserKey implements Serializable {
    @Column(name = "USER_ID", nullable = false)
    private String userId;

    @Column(name = "CLUSTER_ID", nullable = false)
    private String clusterId;

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}