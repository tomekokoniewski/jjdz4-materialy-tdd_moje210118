package com.infoshare.junit.banking;

import com.google.common.collect.ImmutableSet;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;
import java.util.stream.Collectors;

public class Account extends Observable {
    private String owner;
    private BigDecimal balance = BigDecimal.ZERO;
    private Set<Transaction> transactions = new HashSet<>();

    public Account(String owner) {
        this.owner = owner;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Set<Transaction> history() {
        return ImmutableSet.copyOf(transactions);
    }

    public void register(Transaction transaction) throws DuplicatedTransactionException, InvalidTransactionException {
        if (transaction==null) {
            throw new InvalidTransactionException();
        }
        if (transactions.contains(transaction)) {
            throw new DuplicatedTransactionException();
        }
        BigDecimal newBalance = balance.add(transaction.getAmount(), MathContext.DECIMAL32);
        if (-1==newBalance.signum()) {
            throw new InvalidTransactionException();
        }
        transactions.add(transaction);
        balance = newBalance;
        notifyObservers(transaction);
    }

    public List<Transaction> historyBetween(LocalDate start, LocalDate end) {
        return transactions.stream().filter((transaction -> {
            LocalDateTime d = transaction.getDate();
            return d.isAfter(start.atStartOfDay()) && d.isBefore(end.atStartOfDay());
        })).collect(Collectors.toList());
    }

    public Transaction transferTo(Account targetAccount, BigDecimal amount, LocalDateTime nextDate) throws InvalidTransactionException {
        if (amount.signum()<0) {
            throw new InvalidTransactionException();
        }
        return new Transaction(amount, nextDate, this, targetAccount);
    }
}
