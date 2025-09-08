package com.makson.tasktracker;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

@SpringBootApplication
public class TaskTrackerApplication {

    @SneakyThrows
    public static void main(String[] args) {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        SecretKey secretKey = keyGen.generateKey();
        System.out.println(Base64.getEncoder().encodeToString(secretKey.getEncoded()));

        SpringApplication.run(TaskTrackerApplication.class, args);
    }

}
