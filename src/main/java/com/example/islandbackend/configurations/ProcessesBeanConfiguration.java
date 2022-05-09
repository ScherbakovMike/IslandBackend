package com.example.islandbackend.configurations;

import com.example.islandbackend.models.areas.Island;
import com.example.islandbackend.models.processes.Session;
import com.example.islandbackend.models.processes.SessionDispatcher;
import com.example.islandbackend.models.processes.Step;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Date;

@Configuration
@NoArgsConstructor
public class ProcessesBeanConfiguration {
    @Bean(name = "SessionDispatcher")
    @Scope("singleton")
    public static SessionDispatcher createSessionDispatcher() {
        return new SessionDispatcher();
    }

    @Bean(name = "Session")
    @Scope("prototype")
    public static Session createSession() {
        Session result = new Session();
        result.setStartTime(new Date());
        return result;
    }

    @Bean(name = "Step")
    @Scope("prototype")
    public static Step createStep(Session session) {
        Step result = new Step();
        result.setSession(session);
        result.setStepNumber(0);
        result.setIslandState(Island.newInstance());
        result.getIslandState().setCurrentStep(result);
        return result;
    }
}
