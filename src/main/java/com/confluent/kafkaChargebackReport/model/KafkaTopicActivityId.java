package com.confluent.kafkaChargebackReport.model;

import javax.validation.constraints.NotNull;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Embeddable
public class KafkaTopicActivityId implements Serializable {
    @NotNull
    @Column(name = "TOPIC_NAME")
    private String topic;
    @NotNull
    @Column(name = "CLUSTER_ID")
    private String clusterId;
    @NotNull
    @Column(name = "USAGE_DATE")
    private Date usageDate;

    public KafkaTopicActivityId(){}

    public KafkaTopicActivityId(String topic, String clusterId, Date usageDate) {
        this.topic = topic;
        this.clusterId = clusterId;
        this.usageDate = usageDate;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    public Date getUsageDate() {
        return usageDate;
    }

    public void setUsageDate(Date usageDate) {
        this.usageDate = usageDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KafkaTopicActivityId that = (KafkaTopicActivityId) o;
        return Objects.equals(topic, that.topic) && Objects.equals(clusterId, that.clusterId) && Objects.equals(usageDate, that.usageDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topic, clusterId, usageDate);
    }
}
