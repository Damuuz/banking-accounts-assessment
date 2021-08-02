package com.wonderlabz.banking.test.bankingaccounts.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.DateTime;

import java.io.IOException;

import static com.wonderlabz.banking.test.bankingaccounts.BankingAccountsApplication.FORMATTER;
public class LocalDateSerializer extends JsonSerializer<DateTime> {

    @Override
    public void serialize(DateTime dateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(FORMATTER.print(dateTime));
    }
}
