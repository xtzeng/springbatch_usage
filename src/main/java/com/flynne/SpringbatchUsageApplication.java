package com.flynne;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.flynne"})
@MapperScan("com.flynne.mapper")
public class SpringbatchUsageApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbatchUsageApplication.class, args);
    }

}
