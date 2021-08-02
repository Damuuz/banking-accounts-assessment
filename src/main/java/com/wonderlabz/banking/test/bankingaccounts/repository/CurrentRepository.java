package com.wonderlabz.banking.test.bankingaccounts.repository;

import com.wonderlabz.banking.test.bankingaccounts.model.Current;
import com.wonderlabz.banking.test.bankingaccounts.model.Savings;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface   CurrentRepository extends MongoRepository<Current, String> {
    Optional<Current> findByUserId(String userId);
}
