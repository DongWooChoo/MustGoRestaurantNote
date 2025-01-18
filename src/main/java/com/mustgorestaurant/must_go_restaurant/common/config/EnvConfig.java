package com.mustgorestaurant.must_go_restaurant.common.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvConfig {

    @Bean
    public Dotenv EnvConfig() {
        return Dotenv.configure()
                .directory("./")
                .load();
    }
}
