package com.wonderlabz.banking.test.bankingaccounts.controller;

import com.wonderlabz.banking.test.bankingaccounts.exception.SavingsBalanceError;
import com.wonderlabz.banking.test.bankingaccounts.exception.SavingsExistsError;
import com.wonderlabz.banking.test.bankingaccounts.model.SavingsDTO;
import com.wonderlabz.banking.test.bankingaccounts.model.ServerResponse;
import com.wonderlabz.banking.test.bankingaccounts.service.SavingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping(SavingsController.SAVINGS)
public class SavingsController {
    public static final String HOME = "/";
    public static final String USERID = "id";
    public static final String HOME_USERID = HOME + "{" + USERID + "}";
    public static final String SAVINGS = "savings";
    public static final String WITHDRAWAL = "withdrawal";
    public static final String DEPOSIT = "deposit";

    @Autowired
    private SavingsService savingsService;
    @Autowired
    private MessageSource messageSource;

    @PostMapping
    public ServerResponse openSavingsAccount(@RequestBody SavingsDTO savingsDTO) {
        ServerResponse serverResponse = new ServerResponse();
        String message;
        boolean status = false;

        try {
            savingsService.create(savingsDTO);
            message = messageSource.getMessage("savings.open.success", null, Locale.getDefault());
            status = true;
        } catch (SavingsExistsError | SavingsBalanceError | Exception ex) {
            message = messageSource.getMessage("savings.open.failure", null, Locale.getDefault());
        }
        serverResponse.setMessage(message);
        serverResponse.setStatus(status);

        return serverResponse;
    }

    @PostMapping(WITHDRAWAL)
    public ServerResponse withdrawFromSavings(@RequestBody SavingsDTO savingsDTO) {
        ServerResponse serverResponse = new ServerResponse();
        String message;
        boolean status = false;

        try {
            savingsService.withdraw(savingsDTO);
            message = messageSource.getMessage("savings.withdraw.success", null, Locale.getDefault());
            status = true;
        } catch (SavingsBalanceError | Exception ex) {
            message = messageSource.getMessage("savings.withdraw.failure", null, Locale.getDefault());
        }
        serverResponse.setMessage(message);
        serverResponse.setStatus(status);

        return serverResponse;
    }

    @PostMapping(DEPOSIT)
    public ServerResponse depositIntoSavings(@RequestBody SavingsDTO savingsDTO) {
        ServerResponse serverResponse = new ServerResponse();
        String message;
        boolean status = false;

        try {
            savingsService.deposit(savingsDTO);
            message = messageSource.getMessage("savings.deposit.success", null, Locale.getDefault());
            status = true;
        } catch (Exception ex) {
            message = messageSource.getMessage("savings.deposit.failure", null, Locale.getDefault());
        }
        serverResponse.setMessage(message);
        serverResponse.setStatus(status);

        return serverResponse;
    }

    @GetMapping(HOME_USERID)
    public SavingsDTO getClient(@PathVariable(USERID) String userId) {
        return savingsService.findByUserId(userId);
    }
}
