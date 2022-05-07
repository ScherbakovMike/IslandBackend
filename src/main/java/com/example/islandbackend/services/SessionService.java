package com.example.islandbackend.services;

import com.example.islandbackend.exceptions.InitSessionException;
import com.example.islandbackend.models.process.Session;
import com.example.islandbackend.repositories.SessionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SessionService {

    private static final Logger logger = LoggerFactory.getLogger(SessionService.class);

    private final SessionRepository sessionRepository;

    @Resource
    @Qualifier("newSession")
    private Session newSession;

    @Autowired
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Session init() {
        try {
            return sessionRepository.save(newSession);
        } catch (Exception e) {
            logger.error(getClass().getName(), e);
            throw new InitSessionException(e);
        }
    }
}
