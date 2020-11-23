package com.fosun.storekeeper;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 项目启动入口
 * @version 
 * @author hengjb 2019年8月20日下午2:44:19
 * @since 1.8
 */
@SpringBootApplication(scanBasePackages = "com.fosun.storekeeper")
@MapperScan({ "com.fosun.storekeeper.mapper" })
public class StorekeeperApplication {

    public static void main(String[] args) {
        SpringApplication.run(StorekeeperApplication.class, args);
    }

}
