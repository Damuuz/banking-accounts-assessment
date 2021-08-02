package com.wonderlabz.banking.test.bankingaccounts.service;

import com.wonderlabz.banking.test.bankingaccounts.exception.TransactionExistsError;
import com.wonderlabz.banking.test.bankingaccounts.model.Transaction;
import com.wonderlabz.banking.test.bankingaccounts.repository.TransactionRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    public void create(Transaction transaction) throws TransactionExistsError {
        throwExceptionIfTransactionExist(transaction);
        save(transaction);
    }

    public void save(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    private void throwExceptionIfTransactionExist(Transaction transaction) throws TransactionExistsError {
        Optional<Transaction> existingTransaction = transactionRepository.findById(transaction.get_id());
        if (!existingTransaction.isEmpty()) {
            throw new TransactionExistsError("Transaction with id  " + transaction.get_id() + "exists");
        }
    }
}
