package org.example.cryptosimulator.repo;

import org.example.cryptosimulator.model.Authorization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationRepository extends JpaRepository<Authorization, Long> {
    Authorization findByLogin(String login);
}
