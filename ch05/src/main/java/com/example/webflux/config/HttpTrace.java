package com.example.webflux.config;

import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpTrace {

    @Bean
    HttpTraceRepository traceRepository() {
        return new InMemoryHttpTraceRepository();
    }
}
