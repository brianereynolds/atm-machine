package com.mybank.atm.repo;

import com.mybank.atm.entity.db.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<Account, Long> {
    List<Account> findByAccountNumber(Long accountNumber);
}
