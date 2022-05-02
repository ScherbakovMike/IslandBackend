package com.example.islandbackend.controllers;

import com.example.islandbackend.models.process.Session;
import com.example.islandbackend.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {

    private final SessionRepository sessionRepository;

    public SessionController(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @PostMapping("/sessions/init")
    Session newSession(@Qualifier("newSession") Session session) {
        return sessionRepository.save(session);
    }
}
