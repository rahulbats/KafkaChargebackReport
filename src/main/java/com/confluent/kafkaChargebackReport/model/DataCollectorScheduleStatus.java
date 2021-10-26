package com.confluent.kafkaChargebackReport.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "DATA_COLLECTOR_SCHEDULE_STATUS")
@Entity
public class DataCollectorScheduleStatus {
    @Column(name = "RUN_DATE", nullable = false)
    @Id
    @Temporal(TemporalType.DATE)
    private Date runDate;

    @Column(name = "STATUS", nullable = false)
    private Integer status;

    public Date getRunDate() {
        return runDate;
    }

    public void setRunDate(Date runDate) {
        this.runDate = runDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


}