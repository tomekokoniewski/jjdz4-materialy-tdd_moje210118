package com.infoshare.junit.banking;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;

public class Transaction {

    private final BigDecimal amount;
    private final LocalDateTime date;

    public Transaction(BigDecimal amount, LocalDateTime date) {
        this.amount = amount.round(MathContext.DECIMAL32);
        this.date = date;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

}
