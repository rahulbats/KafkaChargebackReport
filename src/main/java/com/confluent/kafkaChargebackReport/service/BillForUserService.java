package com.confluent.kafkaChargebackReport.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
@Service
public class BillForUserService {
    final Log logger = LogFactory.getLog(getClass());
    @Value("${prometheus_url}")
    private String prometheusURL;

    ObjectMapper mapper = new ObjectMapper();

    public Long getFetchedBytes(String user, int numberOfDays) throws IOException {
        //long fetchedBytes = 0;
        String prometheusURLWithParams= prometheusURL+"?query=sum(avg_over_time(kafka_server_fetch_byte_rate{user=\""+user+"\",}["+numberOfDays+"h]))";
        URL url = new URL(prometheusURLWithParams);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        InputStream is = con.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
        String line;
        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }

        rd.close();
        JsonNode responseNode = mapper.readTree(response.toString());
        Long dataBytes = ((ArrayNode)responseNode.get("data").get("result").get(0).get("value")).get(0).asLong();
        return dataBytes;

    }


    public Long getProducedBytes(String user, int numberOfDays) throws IOException {
        //long fetchedBytes = 0;
        String prometheusURLWithParams= prometheusURL+"?query=sum(avg_over_time(kafka_server_produce_byte_rate{user=\""+user+"\",}["+numberOfDays+"h]))";
        URL url = new URL(prometheusURLWithParams);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        InputStream is = con.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
        String line;
        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }

        rd.close();
        JsonNode responseNode = mapper.readTree(response.toString());
        Long dataBytes = ((ArrayNode)responseNode.get("data").get("result").get(0).get("value")).get(0).asLong();
        return dataBytes;

    }
}
