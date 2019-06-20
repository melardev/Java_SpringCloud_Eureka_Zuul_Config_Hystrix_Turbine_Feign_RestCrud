package com.melardev.spring.config_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
@EnableCircuitBreaker
@EnableDiscoveryClient
public class ConfigServerService {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerService.class, args);
    }
}
