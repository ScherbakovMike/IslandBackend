package com.example.islandbackend.services;

import com.example.islandbackend.models.processes.Session;
import com.example.islandbackend.models.processes.Step;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class StepService {

    private final ApplicationContext context;

    public StepService(ApplicationContext context) {
        this.context = context;
    }

    public Step create(Session session) {
        return (Step) context.getBean("Step", session);
    }
}
