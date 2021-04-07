package com.holo.springboot.holoclient;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@SpringBootApplication
@ServletComponentScan
@MapperScan("com.holo.springboot.holoclient.mapper")
public class HoloClientApplication {

    public static void main(String[] args) throws IOException {
//        SpringApplication.run(HoloClientApplication.class, args);
        SpringApplication springApplication = new SpringApplication(HoloClientApplication.class);

        Properties properties = new Properties();
        //自己指定配置文件
        InputStream myConfigAsStream = HoloClientApplication.class.getClassLoader().getResourceAsStream("myConfig.properties");
        InputStream redisAsStream = HoloClientApplication.class.getClassLoader().getResourceAsStream("redis.properties");
//        InputStream druidAsStream = HoloClientApplication.class.getClassLoader().getResourceAsStream("application.yml");
        properties.load(myConfigAsStream);
        properties.load(redisAsStream);
//        properties.load(druidAsStream);
        springApplication.setDefaultProperties(properties);
        springApplication.run(args);
    }

}
