package com.example.islandbackend.services;

import com.example.islandbackend.models.processes.Session;
import com.example.islandbackend.models.processes.SessionDispatcher;
import com.example.islandbackend.models.processes.Step;
import com.example.islandbackend.presentators.JsonBodyGenerator;
import com.example.islandbackend.presentators.StepPresentator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class StepService {
    private final SessionDispatcher sessionDispatcher;

    private final ApplicationContext context;

    @Autowired
    public StepService(ApplicationContext context,
                       SessionDispatcher sessionDispatcher) {
        this.context = context;
        this.sessionDispatcher = sessionDispatcher;
    }

    public Step create(Session session) {
        return (Step) context.getBean("Step", session);
    }

    public String info(String stepId) {
        Step step = sessionDispatcher.getStepById(stepId);
        return JsonBodyGenerator.build(new StepPresentator(step));
    }
}
