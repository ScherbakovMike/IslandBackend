package com.example.islandbackend.services;

import com.example.islandbackend.models.areas.Island;
import com.example.islandbackend.models.processes.Session;
import com.example.islandbackend.models.processes.SessionDispatcher;
import com.example.islandbackend.models.processes.Step;
import com.example.islandbackend.presentators.JsonBodyGenerator;
import com.example.islandbackend.presentators.SessionPresentator;
import com.example.islandbackend.presentators.StepPresentator;
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
    private final IslandService islandService;
    private final SessionDispatcher sessionDispatcher;

    @Autowired
    public SessionService(StepService stepService,
                          SessionDispatcher sessionDispatcher,
                          ApplicationContext context, IslandService islandService) {
        this.context = context;
        this.sessionDispatcher = sessionDispatcher;
        this.stepService = stepService;
        this.islandService = islandService;
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

    public String nextStep(String sessionId) {
        Session session = sessionDispatcher.getSessionById(sessionId);
        Step currentStep = session.getCurrentStep();
        Integer currentStepNumber = currentStep.getStepNumber();
        Island currentIslandState = currentStep.getIslandState();
        currentIslandState.feed();

        Step nextStep = new Step();
        nextStep.setSession(session);
        nextStep.setStepNumber(currentStepNumber + 1);
        nextStep.setIslandState(currentIslandState);
        nextStep.getIslandState().setCurrentStep(nextStep);
        session.setCurrentStep(nextStep);

        return JsonBodyGenerator.build(new StepPresentator(nextStep, nextStep.getSession()));
    }
}
