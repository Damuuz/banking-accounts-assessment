package com.wonderlabz.banking.test.bankingaccounts.service;

import com.wonderlabz.banking.test.bankingaccounts.exception.OverDraftError;
import com.wonderlabz.banking.test.bankingaccounts.exception.SavingsExistsError;
import com.wonderlabz.banking.test.bankingaccounts.model.*;
import com.wonderlabz.banking.test.bankingaccounts.repository.CurrentRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.wonderlabz.banking.test.bankingaccounts.exception.CurrentAccountExistsError;

import java.util.Optional;

@Service
public class CurrentService {
    @Autowired
    CurrentRepository currentRepository;
    @Autowired
    TransactionService transactionService;
    @Value("#{new Double('${current.api.overdraft}')}")
    private Double overdraft;

    public void create(CurrentDTO currentDTO) throws CurrentAccountExistsError {
        Current current = convertToEntity(currentDTO);
        double balance = currentDTO.getTransactionAmount();
        current.setBalance(balance);
        throwExceptionIfCurrentExists(current);
        current.setDateOpened(new DateTime());

        Transaction transaction = new Transaction();
        transaction.set_id(current.getTransactionId());
        transaction.setDateCreated(new DateTime());
        transactionService.save(transaction);
        currentRepository.save(current);
    }

    private void throwExceptionIfCurrentExists(Current current) throws CurrentAccountExistsError{
        Optional<Current> existingCurrent = currentRepository.findByUserId(current.getUserId());
        if (!existingCurrent.isEmpty()) {
            throw new CurrentAccountExistsError("Current Account with UserId  " + current.getUserId() + "already exists");
        }
    }

    public void withdraw(CurrentDTO currentDTO) throws OverDraftError {
        Current current = convertToEntity(currentDTO);
        if (current.getTransactionAmount() < overdraft) {
            Optional<Current> existingCurrent = currentRepository.findByUserId(current.getUserId());
            double balance = existingCurrent.get().getBalance();
            double withdrawal = current.getTransactionAmount();
            throwExceptionIfOverdraftExceeded(balance, withdrawal);
            current.setBalance(balance - withdrawal);
            current.setDateUpdated(new DateTime());

            Transaction transaction = new Transaction();
            transaction.set_id(current.getTransactionId());
            transaction.setDateCreated(new DateTime());
            transactionService.save(transaction);

            currentRepository.save(current);

        }
    }

    private void throwExceptionIfOverdraftExceeded(double balance, double withdrawal) throws OverDraftError {
        if ((withdrawal+ balance) > (overdraft + balance)) {
            throw new OverDraftError("The amount " + "R" + withdrawal + "Exceeds the allowed overdraft on this account");
        }
    }

    public void deposit(CurrentDTO currentDTO) {
        Current current = convertToEntity(currentDTO);
        Optional<Current> existingCurrent = currentRepository.findByUserId(current.getUserId());
        double balance = existingCurrent.get().getBalance() + current.getTransactionAmount();
        current.setBalance(balance);
        current.setDateUpdated(new DateTime());

        Transaction transaction  = new Transaction();
        transaction.set_id(current.getTransactionId());
        transaction.setDateCreated(new DateTime());
        transactionService.save(transaction);

        currentRepository.save(current);

    }

    private Current convertToEntity(CurrentDTO currentDTO) {
        Current current = new Current();
        current.set_id(currentDTO.get_id());
        current.setBalance(0.0);
        current.setUserId(currentDTO.getUserId());
        current.setTransactionAmount(currentDTO.getTransactionAmount());
        current.setDateOpened(currentDTO.getDateOpened());
        current.setDateUpdated(currentDTO.getDateUpdated());

        return current;
    }

}
