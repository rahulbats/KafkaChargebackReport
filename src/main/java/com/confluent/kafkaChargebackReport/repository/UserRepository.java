package com.confluent.kafkaChargebackReport.repository;

import com.confluent.kafkaChargebackReport.model.User;
import com.confluent.kafkaChargebackReport.model.UserKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UserKey> {
}