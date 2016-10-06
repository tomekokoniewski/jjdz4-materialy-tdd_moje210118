package com.infoshare.junit.banking;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;

public class Transaction implements Comparable<Transaction> {

    private final BigDecimal amount;
    private final LocalDateTime date;
    private final Account source;
    private final Account target;

    private TransactionStatus status = TransactionStatus.NEW;

    public Transaction(BigDecimal amount, LocalDateTime date, Account source, Account target) {
        this.amount = amount.round(MathContext.DECIMAL32);
        this.date = date;
        this.source = source;
        this.target = target;
    }

    public Transaction(BigDecimal bigDecimal, LocalDateTime now) {
        this.amount = bigDecimal;
        this.date = now;
        source = target = null;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public BigDecimal getAmount() {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (!amount.equals(that.amount)) return false;
        if (!date.equals(that.date)) return false;
        if (source != null ? !source.equals(that.source) : that.source != null) return false;
        if (target != null ? !target.equals(that.target) : that.target != null) return false;
        return status == that.status;

    }

    @Override
    public int hashCode() {
        int result = amount.hashCode();
        result = 31 * result + date.hashCode();
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + (target != null ? target.hashCode() : 0);
        result = 31 * result + status.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + amount +
                ", date=" + date +
                ", source=" + source +
                ", target=" + target +
                ", status=" + status +
                '}';
    }
}
