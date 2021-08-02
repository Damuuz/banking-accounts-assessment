package com.wonderlabz.banking.test.bankingaccounts.repository;

import com.wonderlabz.banking.test.bankingaccounts.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
}
