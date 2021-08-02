package com.wonderlabz.banking.test.bankingaccounts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonderlabz.banking.test.bankingaccounts.builder.SavingsBuilderDTO;
import com.wonderlabz.banking.test.bankingaccounts.model.Savings;
import com.wonderlabz.banking.test.bankingaccounts.model.SavingsDTO;
import com.wonderlabz.banking.test.bankingaccounts.service.SavingsService;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Locale;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SavingsControllerTests {
    private MockMvc mvc;
    @MockBean
    private SavingsService savingsService;
    @Autowired
    private WebApplicationContext context;
    private SavingsDTO savingsDTO;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        savingsDTO = SavingsBuilderDTO.buildASavings(1);
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void openSavingsAccountTest() throws Exception {
        String message = messageSource.getMessage("savings.open.success", null, Locale.getDefault());
        this.mvc.perform(post(SavingsController.HOME + SavingsController.SAVINGS)
                .content(objectMapper.writeValueAsBytes(savingsDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(true)))
                .andExpect(jsonPath("$.message", is(message)))
                .andDo(print());
    }

    @Test
    public void withdrawFromSavingsAccountTest() throws Exception {
        String message = messageSource.getMessage("savings.withdraw.success", null, Locale.getDefault());
        this.mvc.perform(post(SavingsController.HOME + SavingsController.SAVINGS + SavingsController.HOME + SavingsController.WITHDRAWAL)
                .content(objectMapper.writeValueAsBytes(savingsDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(true)))
                .andExpect(jsonPath("$.message", is(message)))
                .andDo(print());
    }

    @Test
    public void depositToSavingsAccountTest() throws Exception {
        String message = messageSource.getMessage("savings.deposit.success", null, Locale.getDefault());
        this.mvc.perform(post(SavingsController.HOME + SavingsController.SAVINGS + SavingsController.HOME + SavingsController.DEPOSIT)
                .content(objectMapper.writeValueAsBytes(savingsDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(true)))
                .andExpect(jsonPath("$.message", is(message)))
                .andDo(print());
    }

}
