package org.example.cryptosimulator.repo;

import org.example.cryptosimulator.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByAuthorizationId(Long id);

    Optional<Account> findByAuthorizationLogin(String login);

}
