package com.example.islandbackend.controllers;

import com.example.islandbackend.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class SessionController {
    private final SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/sessions/init")
    @ResponseBody
    String sessionsInit() {
        return sessionService.init();
    }

    @PostMapping("/sessions/{id}/info")
    @ResponseBody
    String sessionsInit(@PathVariable("id") String sessionId) {
        return sessionService.info(sessionId);
    }
}
