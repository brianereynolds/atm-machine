package com.mybank.atm.repo;

import com.mybank.atm.BankNote;
import com.mybank.atm.entity.Safe;
import org.springframework.data.repository.CrudRepository;

public interface SafeRepository extends CrudRepository<Safe, BankNote> {

    Safe getByBankNote(BankNote bankNote);

}
