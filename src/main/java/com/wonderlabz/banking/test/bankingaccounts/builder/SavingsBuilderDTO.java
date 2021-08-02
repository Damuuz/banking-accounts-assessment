package com.wonderlabz.banking.test.bankingaccounts.builder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wonderlabz.banking.test.bankingaccounts.model.Savings;
import com.wonderlabz.banking.test.bankingaccounts.model.SavingsDTO;
import com.wonderlabz.banking.test.bankingaccounts.serialize.LocalDateDeserializer;
import com.wonderlabz.banking.test.bankingaccounts.serialize.LocalDateSerializer;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;


public class SavingsBuilderDTO {
    private String _id;
    private String transactionId;
    private double balance;
    private String userId;
    private double transactionAmount;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private DateTime dateOpened;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private DateTime dateUpdated;

    public SavingsBuilderDTO(String _id, double balance, String userId, double transactionAmount, DateTime dateOpened, DateTime dateUpdated) {
        this._id = _id;
        this.balance = balance;
        this.userId = userId;
        this.transactionAmount = transactionAmount;
        this.dateOpened = dateOpened;
        this.dateUpdated = dateUpdated;
    }

    public static SavingsDTO buildASavings(int i) {
        String _id = "id" + i;
        double balance = 2000.0 + i;
        String userId = "userId" + i;
        double transactionAmount = 2000.0 + i;
        DateTime dateOpened = new DateTime();
        DateTime dateUpdated = new DateTime();

        return new SavingsBuilderDTO(_id, balance, userId, transactionAmount, dateOpened, dateUpdated).build();
    }

    private SavingsDTO build() {
        SavingsDTO savingsDTO = new SavingsDTO();
        savingsDTO.set_id(_id);
        savingsDTO.setBalance(balance);
        savingsDTO.setUserId(userId);
        savingsDTO.setTransactionAmount(transactionAmount);
        savingsDTO.setDateOpened(dateOpened);
        savingsDTO.setDateUpdated(dateUpdated);

        return savingsDTO;
    }
}
