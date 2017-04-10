package com.infoshare.junit.banking;

import java.util.Collection;

interface TransactionStore extends Collection<Transaction> {
    void accept(Transaction t);
    void reject(Transaction t);
    Collection<Transaction> get();
}
