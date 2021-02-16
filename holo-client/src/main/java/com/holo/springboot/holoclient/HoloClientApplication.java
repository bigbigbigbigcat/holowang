package com.holo.springboot.holoclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@SpringBootApplication
public class HoloClientApplication {

    public static void main(String[] args) throws IOException {
//        SpringApplication.run(HoloClientApplication.class, args);
        SpringApplication springApplication = new SpringApplication(HoloClientApplication.class);

        Properties properties = new Properties();
        //自己指定配置文件
        InputStream myConfigAsStream = HoloClientApplication.class.getClassLoader().getResourceAsStream("myConfig.properties");
        InputStream redisAsStream = HoloClientApplication.class.getClassLoader().getResourceAsStream("redis.properties");
        properties.load(myConfigAsStream);
        properties.load(redisAsStream);
        springApplication.setDefaultProperties(properties);
        springApplication.run(args);
    }

}
