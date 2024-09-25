package com.victorsantos.customer.transaction;

import java.util.Locale;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.i18n.LocaleContextHolder;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        LocaleContextHolder.setDefaultLocale(Locale.ENGLISH);
        SpringApplication.run(Application.class, args);
    }
}
