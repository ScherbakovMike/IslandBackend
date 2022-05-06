package com.example.islandbackend.repositories;

import com.example.islandbackend.models.process.Session;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends CrudRepository<Session, String> {
}
