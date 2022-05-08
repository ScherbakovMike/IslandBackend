package com.example.islandbackend.services;

import com.example.islandbackend.models.processes.Session;
import com.example.islandbackend.repositories.SessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public
class SessionService {

    private static final Logger logger = LoggerFactory.getLogger(SessionService.class);

    private final SessionRepository sessionRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Session init() {
        try {
            return sessionRepository.save(Session.newInstance());
        } catch (Exception e) {
            logger.error(getClass().getName(), e);
            throw e;
        }
    }
}
