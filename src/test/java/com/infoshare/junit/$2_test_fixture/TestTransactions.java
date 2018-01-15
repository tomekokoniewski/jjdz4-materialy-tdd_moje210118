package com.infoshare.junit.$2_test_fixture;

import com.infoshare.junit.banking.Account;
import com.infoshare.junit.banking.TransactionsBuilder;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.chrono.ChronoPeriod;
import java.time.temporal.TemporalUnit;

public class TestTransactions {
    private static final int DEFAULT_AMOUNT = 100;
    public static final int HUGE_AMOUNT = 10000;

    public static void smallValueTransactionsFor(Account account) {
        new TransactionsBuilder()
                .after(LocalDateTime.of(2015, Month.DECEMBER, 1, 0, 0))
                .before(LocalDateTime.now())
                .valueBetween(1, 10)
                .totalOf(DEFAULT_AMOUNT)
                .register(account);
    }

    public static void bigValueTransactionsFor(Account account) {
        new TransactionsBuilder()
                .after(LocalDateTime.of(2015, Month.DECEMBER, 1, 0, 0))
                .before(LocalDateTime.now())
                .valueBetween(1000000, 100000000)
                .totalOf(DEFAULT_AMOUNT)
                .register(account);
    }

    public static void hugeAmountOfTransactionsFor(Account account) {
        new TransactionsBuilder()
                .after(LocalDateTime.of(2000, Month.DECEMBER, 1, 0, 0))
                .before(LocalDateTime.now())
                .valueBetween(1, 100000)
                .totalOf(HUGE_AMOUNT)
                .register(account);
    }

    public static void nightTransactionsFor(Account account) {
        new TransactionsBuilder()
                .after(LocalDateTime.now().withHour(22))
                .before(LocalDateTime.now().plusDays(1).withHour(2))
                .valueBetween(1, 1000)
                .register(account);
    }
}
