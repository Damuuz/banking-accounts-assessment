package com.wonderlabz.banking.test.bankingaccounts.controller;

import com.wonderlabz.banking.test.bankingaccounts.exception.CurrentAccountExistsError;
import com.wonderlabz.banking.test.bankingaccounts.exception.OverDraftError;
import com.wonderlabz.banking.test.bankingaccounts.exception.SavingsBalanceError;
import com.wonderlabz.banking.test.bankingaccounts.exception.SavingsExistsError;
import com.wonderlabz.banking.test.bankingaccounts.model.Current;
import com.wonderlabz.banking.test.bankingaccounts.model.CurrentDTO;
import com.wonderlabz.banking.test.bankingaccounts.model.SavingsDTO;
import com.wonderlabz.banking.test.bankingaccounts.model.ServerResponse;
import com.wonderlabz.banking.test.bankingaccounts.service.CurrentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping(CurrentController.CURRENT)
public class CurrentController {
    public static final String HOME = "/";
    public static final String USERID = "id";
    public static final String HOME_USERID = HOME + "{" + USERID + "}";
    public static final String CURRENT = "current";
    public static final String WITHDRAWAL = "withdrawal";
    public static final String DEPOSIT = "deposit";

    @Autowired
    private CurrentService currentService;
    @Autowired
    private MessageSource messageSource;

    @PostMapping
    public ServerResponse openCurrentAccount(@RequestBody CurrentDTO currentDTO) {
        ServerResponse serverResponse = new ServerResponse();
        String message;
        boolean status = false;

        try {
            currentService.create(currentDTO);
            message = messageSource.getMessage("current.open.success", null, Locale.getDefault());
            status = true;
        } catch (CurrentAccountExistsError | Exception ex) {
            message = messageSource.getMessage("current.open.failure", null, Locale.getDefault());
        }
        serverResponse.setMessage(message);
        serverResponse.setStatus(status);

        return serverResponse;
    }

    @PostMapping(WITHDRAWAL)
    public ServerResponse withdrawFromCurrent(@RequestBody CurrentDTO currentDTO) {
        ServerResponse serverResponse = new ServerResponse();
        String message;
        boolean status = false;

        try {
            currentService.withdraw(currentDTO);
            message = messageSource.getMessage("current.withdraw.success", null, Locale.getDefault());
            status = true;
        } catch (OverDraftError | Exception ex) {
            message = messageSource.getMessage("current.withdraw.failure", null, Locale.getDefault());
        }
        serverResponse.setMessage(message);
        serverResponse.setStatus(status);

        return serverResponse;
    }

    @PostMapping(DEPOSIT)
    public ServerResponse depositIntoSavings(@RequestBody CurrentDTO currentDTO) {
        ServerResponse serverResponse = new ServerResponse();
        String message;
        boolean status = false;

        try {
            currentService.deposit(currentDTO);
            message = messageSource.getMessage("current.deposit.success", null, Locale.getDefault());
            status = true;
        } catch (Exception ex) {
            message = messageSource.getMessage("current.deposit.failure", null, Locale.getDefault());
        }
        serverResponse.setMessage(message);
        serverResponse.setStatus(status);

        return serverResponse;
    }
}
