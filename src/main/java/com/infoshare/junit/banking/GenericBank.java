package com.infoshare.junit.banking;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class GenericBank implements TransferBank {

    public static Boolean isCautious = false;

    private List<Transaction> transactions = new ArrayList<>();

    private TransactionStore transactionStore = new ListTransactionStore();

    private HashMap<String, Account> accounts = new HashMap<>();

    public Account getAccountFor(String owner) {
        if(accounts.containsKey(owner)) {
            return accounts.get(owner);
        }
        Account newAccount = new Account(owner);
        accounts.put(owner, newAccount);
        return newAccount;
    }

    public void register(Transaction transaction) {
        transactions.add(transaction);
    }

    public Collection<Transaction> process() {
        final LocalDateTime now = LocalDateTime.now();
        transactions.stream().forEach(t -> {
            if (isFutureTransaction(t) || isNightTransaction(t) || isHugeTransaction(t)) {
                transactionStore.reject(t);
                return;
            }

            Transaction deduction = new Transaction(t.getAmount().negate(), now, t.getSource(), t.getTarget());
            Transaction deposit = new Transaction(t.getAmount(), now, t.getSource(), t.getTarget());
            try {
                t.getSource().register(deduction);
                t.getTarget().register(deposit);
                transactionStore.accept(t);
            } catch (DuplicatedTransactionException | InvalidTransactionException e) {
                e.printStackTrace();
                transactionStore.reject(t);
            }
        });
        transactions = new ArrayList<>();
        return transactionStore.get();
    }

    private boolean isFutureTransaction(Transaction t) {
        return t.getDate().isAfter(LocalDateTime.now());
    }

    private boolean isHugeTransaction(Transaction t) {
        return t.getStatus()== TransactionStatus.NEW
                && 1==t.getAmount().compareTo(BigDecimal.valueOf(99999));
    }

    private boolean isNightTransaction(Transaction t) {
        return GenericBank.isCautious
                && (t.getDate().getHour()>22 || t.getDate().getHour()<2);
    }

}

;