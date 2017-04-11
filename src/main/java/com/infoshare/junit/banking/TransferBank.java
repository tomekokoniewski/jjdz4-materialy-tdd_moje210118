package com.infoshare.junit.banking;

import java.util.Collection;

public interface TransferBank {
    void register(Transaction transaction);
    Collection<Transaction> process();
    Account getAccountFor(String owner);
    default int getCommercialAccountsCount() {
        return 0;
    }
}
