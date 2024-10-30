package com.web.cartalk.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = "com.web")
@Configuration
public class OpenFeignConfig {

    @Value("${mail.host}")
    private String host;
}
