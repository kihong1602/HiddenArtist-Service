package com.hiddenartist.backend.domain.account.persistence.repository;

import com.hiddenartist.backend.domain.account.persistence.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long>, CustomAccountRepository {

  Optional<Account> findByEmail(String email);

}