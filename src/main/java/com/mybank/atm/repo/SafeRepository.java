package com.mybank.atm.repo;

import com.mybank.atm.entity.db.BankNote;
import com.mybank.atm.entity.db.Safe;
import org.springframework.data.repository.CrudRepository;

public interface SafeRepository extends CrudRepository<Safe, BankNote> {

    Safe getByBankNote(BankNote bankNote);

}
