package com.confluent.kafkaChargebackReport.model;



import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Embeddable
public class KafkaUserActivityId implements Serializable {
    @NotNull
    @Column(name = "USER_ID")
    private String userId;
    @NotNull
    @Column(name = "CLUSTER_ID")
    private String clusterId;
    @NotNull
    @Column(name = "USAGE_DATE")
    private Date usageDate;

    public KafkaUserActivityId(){}

    public KafkaUserActivityId(String userId, String clusterId, Date usageDate) {
        this.userId = userId;
        this.clusterId = clusterId;
        this.usageDate = usageDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
        KafkaUserActivityId that = (KafkaUserActivityId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(clusterId, that.clusterId) && Objects.equals(usageDate, that.usageDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, clusterId, usageDate);
    }
}
