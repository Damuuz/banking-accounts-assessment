package com.wonderlabz.banking.test.bankingaccounts.service;


import com.wonderlabz.banking.test.bankingaccounts.BankingAccountsApplicationTests;
import com.wonderlabz.banking.test.bankingaccounts.builder.SavingsBuilderDTO;
import com.wonderlabz.banking.test.bankingaccounts.exception.SavingsBalanceError;
import com.wonderlabz.banking.test.bankingaccounts.exception.SavingsExistsError;
import com.wonderlabz.banking.test.bankingaccounts.model.SavingsDTO;
import com.wonderlabz.banking.test.bankingaccounts.repository.SavingsRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SavingsServiceTests extends BankingAccountsApplicationTests {
    @Autowired
    SavingsService savingsService;
    @Autowired
    SavingsRepository savingsRepository;
    private SavingsDTO savingsDTO;

    private Log log = LogFactory.getLog(this.getClass().getName());

    @Before
    public void setUp() {
        log.info("SetUp --> Savings MS");
        savingsDTO = SavingsBuilderDTO.buildASavings(1);
    }

    @After
    public void tearDown() {
        log.info("Tear down ---> Savings MS");
        savingsRepository.deleteAll();
    }

    @Test
    public void testOpenSavingsAccount() throws SavingsExistsError, SavingsBalanceError {
        savingsService.create(savingsDTO);
        assertNotNull(savingsDTO.get_id());
    }

    @Test(expected = SavingsBalanceError.class)
    public void testOpenSavingsAccountFail() throws SavingsBalanceError, SavingsExistsError {
        savingsDTO.setTransactionAmount(100.0);
        savingsService.create(savingsDTO);
    }

    @Test
    public void testSavingsWithdrawal() throws SavingsBalanceError, SavingsExistsError {
        savingsService.create(savingsDTO);
        savingsDTO.setTransactionAmount(200.0);
        savingsService.withdraw(savingsDTO);
        assertNotNull(savingsDTO.get_id());
    }

    @Test
    public void testSavingsDeposit() throws SavingsBalanceError, SavingsExistsError {
        savingsService.create(savingsDTO);
        savingsService.deposit(savingsDTO);
        assertNotNull(savingsDTO.get_id());
    }

    @Test
    public void testFindByUserId() throws SavingsBalanceError, SavingsExistsError {
        savingsService.create(savingsDTO);

        SavingsDTO currentSavingsDTO = savingsService.findByUserId(savingsDTO.getUserId());
        assertEquals(currentSavingsDTO.getUserId(), savingsDTO.getUserId());
    }

}
