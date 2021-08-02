package com.wonderlabz.banking.test.bankingaccounts.service;

import com.wonderlabz.banking.test.bankingaccounts.exception.SavingsBalanceError;
import com.wonderlabz.banking.test.bankingaccounts.model.Savings;
import com.wonderlabz.banking.test.bankingaccounts.model.SavingsDTO;
import com.wonderlabz.banking.test.bankingaccounts.model.Transaction;
import com.wonderlabz.banking.test.bankingaccounts.repository.SavingsRepository;
import org.joda.time.DateTime;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wonderlabz.banking.test.bankingaccounts.exception.SavingsExistsError;

import java.util.Optional;

@Service
public class SavingsService {
    @Autowired
    SavingsRepository savingsRepository;
    @Autowired
    TransactionService transactionService;

    public void create(SavingsDTO savingsDTO) throws SavingsExistsError, SavingsBalanceError {
        Savings savings = convertToEntity(savingsDTO);
        double balance = savingsDTO.getTransactionAmount();
        savings.setBalance(balance);
        throwExceptionIfSavingsAccountExist(savings);
        throwExceptionIfBalanceIsLess(savings);
        savings.setDateOpened(new DateTime());

        Transaction transaction = new Transaction();
        transaction.set_id(savings.getTransactionId());
        transaction.setDateCreated(new DateTime());
        transactionService.save(transaction);

        savingsRepository.save(savings);
    }

    public void save(SavingsDTO savingsDTO) {
        Savings savings = convertToEntity(savingsDTO);
//        Transaction transaction = new Transaction();
//        transaction.set_id(savings.getTransactionId());
//        transaction.setDateCreated(new DateTime());
        savingsRepository.save(savings);
    }

    private void throwExceptionIfSavingsAccountExist(Savings savings) throws SavingsExistsError {
        Optional<Savings> existingSavings = savingsRepository.findByUserId(savings.getUserId());
        if (!existingSavings.isEmpty()) {
            throw new SavingsExistsError("Savings Account with UserId  " + savings.getUserId() + "already exists");
        }
    }

    private void throwExceptionIfBalanceIsLess(Savings savings) throws SavingsBalanceError {
        if (savings.getBalance() < 1000.0) {
            throw new SavingsBalanceError("Savings account must have a minimum balance of R1000");
        }
    }

    public void withdraw(SavingsDTO savingsDTO) throws SavingsBalanceError {
        Savings savings = convertToEntity(savingsDTO);
        Optional<Savings> existingSavings = savingsRepository.findByUserId(savings.getUserId());
        double balance = existingSavings.get().getBalance() - savingsDTO.getTransactionAmount();
        savings.setBalance(balance);
        throwExceptionIfBalanceIsLess(savings);
        savings.setDateUpdated(new DateTime());

        Transaction transaction = new Transaction();
        transaction.set_id(savings.getTransactionId());
        transaction.setDateCreated(new DateTime());
        transactionService.save(transaction);

        savingsRepository.save(savings);

    }

    public void deposit(SavingsDTO savingsDTO) {
        Savings savings = convertToEntity(savingsDTO);
        Optional<Savings> existingSavings = savingsRepository.findByUserId(savings.getUserId());
        double balance = existingSavings.get().getBalance() + savingsDTO.getTransactionAmount();
        savings.setBalance(balance);
        savings.setDateUpdated(new DateTime());

        Transaction transaction = new Transaction();
        transaction.set_id(savings.getTransactionId());
        transaction.setDateCreated(new DateTime());
        transactionService.save(transaction);

        savingsRepository.save(savings);
    }


    private SavingsDTO convertToDTO(Optional<Savings> savings) {
        SavingsDTO savingsDTO = new SavingsDTO();
        savingsDTO.set_id(savings.get().get_id());
        savingsDTO.setBalance(savings.get().getBalance());
        savingsDTO.setUserId(savings.get().getUserId());
        savingsDTO.setTransactionAmount(savings.get().getTransactionAmount());
        savingsDTO.setDateOpened(savings.get().getDateOpened());
        savingsDTO.setDateUpdated(savings.get().getDateUpdated());

        return savingsDTO;
    }

    private Savings convertToEntity(SavingsDTO savingsDTO) {
        Savings savings = new Savings();
        savings.set_id(savingsDTO.get_id());
        savings.setBalance(0.0);
        savings.setUserId(savingsDTO.getUserId());
        savings.setTransactionAmount(savingsDTO.getTransactionAmount());
        savings.setDateOpened(savingsDTO.getDateOpened());
        savings.setDateUpdated(savingsDTO.getDateUpdated());

        return savings;
    }


    public SavingsDTO findByUserId(String userId) {
        Optional<Savings> savings = savingsRepository.findByUserId(userId);
        return convertToDTO(savings);
    }
}
