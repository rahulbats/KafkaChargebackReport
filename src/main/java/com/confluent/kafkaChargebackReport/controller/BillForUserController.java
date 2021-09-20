package com.confluent.kafkaChargebackReport.controller;

import com.confluent.kafkaChargebackReport.service.BillForUserService;
import com.confluent.kafkaChargebackReport.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class BillForUserController {
    @Autowired
    BillForUserService billForUserService;

    @Autowired
    MailService mailService;

    @GetMapping(path ="/fetchedbytes/{user}/{numberOfDays}", produces = "application/json")
    public Long getFetchedBytes(@PathVariable String user, @PathVariable int numberOfDays) throws IOException {
        mailService.sendBill();
        return billForUserService.getFetchedBytes(user, numberOfDays);
    }

    @GetMapping(path ="/producedbytes/{user}/{numberOfDays}", produces = "application/json")
    public Long getProducedBytes(@PathVariable String user, @PathVariable int numberOfDays) throws IOException {
        return billForUserService.getProducedBytes(user, numberOfDays);
    }

}
