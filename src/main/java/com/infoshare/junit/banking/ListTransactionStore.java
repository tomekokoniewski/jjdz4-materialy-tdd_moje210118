package com.infoshare.junit.banking;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;

class ListTransactionStore extends ArrayList<Transaction> implements TransactionStore {

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

}
