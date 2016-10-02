package com.infoshare.junit.$2_test_fixture;

import com.infoshare.junit.banking.*;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TransactionSetupTest {

    @Test
    public void new_account_should_not_have_any_transactions() {
        // given
        Account emptyAccount = new Account("Erich Gamma");

        // when

        // then
        assertEquals(emptyAccount.history().size(), 0);
    }

    @Test
    public void should_handle_huge_amount_of_transactions() {
        // given
        AccountMonitor activityMonitor = new LoggingActivityMonitor();
        activityMonitor.connect();
        Account intensiveAccount = new Account("Kent Beck");
        intensiveAccount.addObserver(activityMonitor);

        // when
        TestTransactions.hugeAmountOfTransactionsFor(intensiveAccount);

        // then
        assertEquals(intensiveAccount.history().size(), TestTransactions.HUGE_AMOUNT);
    }

    @Test
    public void should_find_all_transactions() {
        // given
        AccountMonitor activityMonitor = new LoggingActivityMonitor();
        activityMonitor.connect();
        Account account = new Account("Martin Fowler");
        account.addObserver(activityMonitor);

        // when
        for (int i=0;i<100;i++) {
            try {
                account.register(new Transaction(new BigDecimal(100000*Math.random()), LocalDateTime.of(2015,Month.OCTOBER,1,0,0) ));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // then
        assertEquals(account.history().size(), 100);
    }

    @Test
    public void should_find_transactions_from_specific_period() {
        // given
        AccountMonitor activityMonitor = new LoggingActivityMonitor();
        activityMonitor.connect();
        Account account = new Account("Martin Fowler");
        account.addObserver(activityMonitor);

        // when
        LocalDateTime date = LocalDateTime.of(2015, Month.OCTOBER, 1, 0, 0);
        for (int i=0;i<100;i++) {
            try {
                account.register(new Transaction(new BigDecimal(100000*Math.random()), date ));
                date = date.plus(Period.ofDays(3));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // then
        LocalDate start = LocalDate.of(2016, 3, 20);
        LocalDate end = LocalDate.of(2016, 4, 1);
        assertTrue(account.historyBetween(start, end).size()>0);
    }

    @Test(expected = DuplicatedTransactionException.class)
    public void should_not_register_same_transaction_twice() throws Exception {
        // given
        AccountMonitor activityMonitor = new LoggingActivityMonitor();
        activityMonitor.connect();
        Account account = new Account("Robert Martin");
        account.addObserver(activityMonitor);
        for (int i=0;i<100;i++) {
            try {
                account.register(new Transaction(new BigDecimal(100000*Math.random()), LocalDateTime.of(2015,Month.OCTOBER,1,0,0).plus(Period.ofDays(3)) ));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // when
        Transaction duplicate = account.history().iterator().next();
        account.register(duplicate);

        // then throws exception
    }

}
