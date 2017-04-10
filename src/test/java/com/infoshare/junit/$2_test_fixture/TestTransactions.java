package com.infoshare.junit.$2_test_fixture;

import com.infoshare.junit.banking.Account;

import java.time.LocalDateTime;
import java.time.Month;

public class TestTransactions {
    private static final int DEFAULT_AMOUNT = 100;
    public static final int HUGE_AMOUNT = 1000000;

    public static void smallValueTransactionsFor(Account account) {
        new TransactionsBuilder()
                .after(LocalDateTime.of(2015, Month.DECEMBER, 1, 0, 0))
                .before(LocalDateTime.now())
                .valueBetween(1,10)
                .totalOf(DEFAULT_AMOUNT)
                .register(account);
    }

    public static void bigValueTransactionsFor(Account account) {
        new TransactionsBuilder()
                .after(LocalDateTime.of(2015, Month.DECEMBER, 1, 0, 0))
                .before(LocalDateTime.now())
                .valueBetween(1000000,100000000)
                .totalOf(DEFAULT_AMOUNT)
                .register(account);
    }

    public static void hugeAmountOfTransactionsFor(Account account) {
        new TransactionsBuilder()
                .after(LocalDateTime.of(2000, Month.DECEMBER, 1, 0, 0))
                .before(LocalDateTime.now())
                .valueBetween(1,10000000)
                .totalOf(HUGE_AMOUNT)
                .register(account);
    }
}
