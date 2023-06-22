package com.corrency.divisas.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "currencies")
@PropertySource("classpath:application.properties")
@EnableScheduling
@ConditionalOnProperty(name = "scheduler.enabled", matchIfMissing = true)
@Getter
@Setter
public class CurrenciesProperties {
    @Value("${currency.apikey}")
    private String apikey;

    @Value("${currency.tiempo-peticion}")
    private int tiempoPeticion;

    @Value("${currency.request-timeout}")
    private int timeout;
}
