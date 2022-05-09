package com.example.islandbackend.models.processes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class SessionDispatcher {
    private Map<String, Session> sessions = new HashMap<>();
}
