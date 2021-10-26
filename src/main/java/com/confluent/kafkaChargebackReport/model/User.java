package com.confluent.kafkaChargebackReport.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "USER_DATA")
@Entity
@ApiModel(description="All details about the user. ")
public class User {
    @EmbeddedId
    private UserKey userKey;

    @ApiModelProperty(example = "userid_clusterid")
    public UserKey getUserKey() {
        return userKey;
    }

    public void setUserKey(UserKey userKey) {
        this.userKey = userKey;
    }
}