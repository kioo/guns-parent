package com.stylefeng.guns.rest;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = {"com.stylefeng.guns.rest"})
@EnableAsync
@EnableDubboConfiguration
// ---- Hystrix 熔断注解 start ------
/*@EnableHystrixDashboard
@EnableCircuitBreaker
@EnableHystrix*/
// ---- Hystrix 熔断注解 end ------
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
