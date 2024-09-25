package com.victorsantos.customer.transaction.application.common.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonLogConfiguration {

    @Bean
    public CommonRequestLoggingFilter commonRequestLoggingFilter(ObjectMapper objectMapper) {
        return new CommonRequestLoggingFilter(objectMapper);
    }
}
