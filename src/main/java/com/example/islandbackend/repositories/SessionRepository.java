package com.example.islandbackend.repositories;

import com.example.islandbackend.models.process.Session;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<Session, Long> {
}
