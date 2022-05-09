package com.example.islandbackend.services;

import com.example.islandbackend.models.processes.Session;
import com.example.islandbackend.models.processes.SessionDispatcher;
import com.example.islandbackend.models.processes.Step;
import com.example.islandbackend.presentators.JsonBodyGenerator;
import com.example.islandbackend.presentators.SessionPresentator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    private final ApplicationContext context;

    private static final Logger logger = LoggerFactory.getLogger(SessionService.class);
    private final StepService stepService;
    private final SessionDispatcher sessionDispatcher;

    @Autowired
    public SessionService(StepService stepService,
                          SessionDispatcher sessionDispatcher,
                          ApplicationContext context) {
        this.stepService = stepService;
        this.sessionDispatcher = sessionDispatcher;
        this.context = context;
    }

    public String init() {
        try {
            Session session = (Session) context.getBean("Session");
            Step step = stepService.create(session);
            session.setCurrentStep(step);
            sessionDispatcher.getSessions().put(session.getId(), session);
            sessionDispatcher.getSteps().put(step.getId(), step);
            sessionDispatcher.getIslands().put(step.getIslandState().getId(), step.getIslandState());
            return JsonBodyGenerator.build(new SessionPresentator(session));
        } catch (Exception e) {
            logger.error(getClass().getName(), e);
            throw new RuntimeException(e);
        }
    }

    public String info(String sessionId) {
        Session session = sessionDispatcher.getSessionById(sessionId);
        return JsonBodyGenerator.build(new SessionPresentator(session));
    }
}
