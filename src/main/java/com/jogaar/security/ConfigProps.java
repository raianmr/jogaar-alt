package com.jogaar.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("jogaar")
public record ConfigProps(String secretKey, Long tokenLifeSpanInMins, String staticDir) {
}
