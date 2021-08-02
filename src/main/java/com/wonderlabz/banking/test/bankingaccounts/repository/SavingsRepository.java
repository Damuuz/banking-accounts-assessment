package com.wonderlabz.banking.test.bankingaccounts.repository;

import com.wonderlabz.banking.test.bankingaccounts.model.Savings;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SavingsRepository extends MongoRepository<Savings, String> {
    Optional<Savings> findByUserId(String userId);
}
