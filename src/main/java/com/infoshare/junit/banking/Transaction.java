package com.infoshare.junit.banking;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Transaction implements Comparable<Transaction> {

    private final Integer amount;
    private final LocalDateTime date;
    private final Account source;
    private final Account target;

    private TransactionStatus status = TransactionStatus.NEW;

    public Transaction(Integer amount, LocalDateTime date, Account source, Account target) {
        this.amount = amount;
        this.date = date;
        this.source = source;
        this.target = target;
    }

    public Transaction(Integer amount, LocalDateTime now) {
        this(amount, now, null, null);
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Integer getAmount() {
        return amount;
    }

    public Account getSource() {
        return source;
    }

    public Account getTarget() {
        return target;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    @Override
    public int compareTo(Transaction o) {
        return o.getDate().compareTo(date);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) {
            return false;
        }

        Transaction that = (Transaction) o;

        if (!Objects.equals(amount,that.amount)) return false;
        if (!Objects.equals(date,that.date)) return false;
        if (!Objects.equals(source,that.source)) return false;
        if (!Objects.equals(target,that.target)) return false;
        return true;

    }

    @Override
    public final int hashCode() {
        int result = Objects.hashCode(amount);
        result = 31 * result + Objects.hashCode(date);
        result = 31 * result + Objects.hashCode(source);
        result = 31 * result + Objects.hashCode(target);
        return result;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + Objects.toString(amount) +
                ", date=" + Objects.toString(date) +
                ", source=" + Objects.toString(source) +
                ", target=" + Objects.toString(target) +
                ", status=" + status +
                '}';
    }
}
