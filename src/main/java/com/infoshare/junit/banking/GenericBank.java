package com.infoshare.junit.banking;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class GenericBank implements TransferBank {

    public static Boolean isCautious = false;

    private List<Transaction> transactions = new ArrayList<>();

    private TransactionStore transactionStore = new TreeTransactionStore();

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
            if (isNightTransaction(t) || isHugeTransaction(t)) {
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

    private boolean isHugeTransaction(Transaction t) {
        return t.getStatus()== TransactionStatus.NEW && 1==t.getAmount().compareTo(BigDecimal.valueOf(99999));
    }

    private boolean isNightTransaction(Transaction t) {
        return GenericBank.isCautious && (t.getDate().getHour()>22 || t.getDate().getHour()<2);
    }

}

interface TransactionStore extends Collection<Transaction> {
    void accept(Transaction t);
    void reject(Transaction t);
    Collection<Transaction> get();
}

class TreeTransactionStore extends ArrayList<Transaction> implements TransactionStore {
    @Override
    public void accept(Transaction t) {
        Transaction transaction = new Transaction(t.getAmount(),t.getDate(), t.getSource(), t.getTarget());
        transaction.setStatus(TransactionStatus.SUCCESS);
        add(transaction);
    }

    @Override
    public void reject(Transaction t) {
        Transaction transaction = new Transaction(t.getAmount(),t.getDate(), t.getSource(), t.getTarget());
        transaction.setStatus(TransactionStatus.ON_HOLD);
        add(transaction);
    }

    @Override
    public ImmutableCollection<Transaction> get() {
        return ImmutableList.copyOf(this);
    }

};