package com.wonderlabz.banking.test.bankingaccounts.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.joda.time.DateTime;

import java.io.IOException;

import static com.wonderlabz.banking.test.bankingaccounts.BankingAccountsApplication.FORMATTER;

public class LocalDateDeserializer extends JsonDeserializer {
    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return DateTime.parse(jsonParser.getValueAsString(), FORMATTER);
    }
}

