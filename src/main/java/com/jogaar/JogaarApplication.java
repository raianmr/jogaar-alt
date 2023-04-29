package com.jogaar;

import com.jogaar.security.ConfigProps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
@EnableConfigurationProperties(ConfigProps.class)
public class JogaarApplication {
    public static void main(String[] args) {
        SpringApplication.run(JogaarApplication.class, args);
    }
}
