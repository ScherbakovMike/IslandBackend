package com.example.islandbackend.models.process;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

public class SessionFactory {
    @Bean
    @Qualifier("newSession")
    public static Session newSession() {
        return new Session();
    }
}
