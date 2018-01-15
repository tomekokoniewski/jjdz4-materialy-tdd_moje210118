package com.infoshare.junit.banking;

import com.google.common.collect.ImmutableSet;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;
import java.util.stream.Collectors;

public class Account extends Observable {

    private String owner;
    private Integer balance = 0;
    private Set<Transaction> transactions = new HashSet<>();

    public Account(String owner) {
        this.owner = owner;
    }

    public Integer getBalance() {
        return balance;
    }

    public Set<Transaction> history() {
        return ImmutableSet.copyOf(transactions);
    }

    public void register(Transaction transaction) throws DuplicatedTransactionException, InvalidTransactionException {
        if (transaction == null) {
            throw new InvalidTransactionException(InvalidTransactionException.EMPTY_TRANSACTION);
        }
        if (transactions.contains(transaction)) {
            throw new DuplicatedTransactionException();
        }
        Integer newBalance = getBalance() + transaction.getAmount();
        if (newBalance<0) {
            throw new InvalidTransactionException(InvalidTransactionException.NOT_ENOUGH_FUNDS);
        }
        setChanged();
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

    public Transaction transferTo(Account targetAccount, Integer amount, LocalDateTime nextDate) throws InvalidTransactionException {
        if (amount < 0) {
            throw new InvalidTransactionException(InvalidTransactionException.NEGATIVE_AMOUNT);
        }
        return new Transaction(amount, nextDate, this, targetAccount);
    }

    public String getOwner() {
        return owner;
    }
}
