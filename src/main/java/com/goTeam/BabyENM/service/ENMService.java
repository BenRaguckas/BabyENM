package com.goTeam.BabyENM.service;

import org.aspectj.apache.bcel.classfile.Field;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

@Service
public class ENMService {

    @KafkaListener(topics = "TEST", groupId="group1")
    public void kafkaListener(
            @Header(KafkaHeaders.RECEIVED_KEY) String key,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_TIMESTAMP) String timestamp,
            String value ) {
        String[] values = key.split("-");
        try {
            File file = new File("./dumps/"+values[0]+"/"+values[1]+"/"+System.currentTimeMillis()+".log");
            file.getParentFile().mkdirs();
            FileWriter fileWriter = new FileWriter(file);

            //HTTP get
            String host = "http://localhost:8080/api/node/" + value + "/cache";
            URL url = new URL(host);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            fileWriter.append(content);
            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
