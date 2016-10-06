package com.infoshare.junit.$2_test_fixture;

import com.infoshare.junit.banking.*;
import org.junit.*;
import org.junit.experimental.categories.Category;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * przykład użycia before/after, beforeClass/afterClass
 */
public class TransactionSetupTest {

    private static final int TRANSACTION_COUNT = 10;

    private LoggingActivityMonitor accountActivity;
    private Account account;
    private static AccountMonitor activityMonitor;

    @BeforeClass
    public static void createTransactionLog() {
        activityMonitor = new LoggingActivityMonitor();
        activityMonitor.connect();
    }

    @Before
    public void setUp() throws Exception {
        account = new Account("Kent Beck");
        account.addObserver(activityMonitor);
        new TransactionsBuilder()
                .after(LocalDateTime.of(2015, Month.DECEMBER, 1, 0, 0))
                .before(LocalDateTime.of(2016, Month.APRIL, 30, 0, 0))
                .valueBetween(1,100000)
                .totalOf(TRANSACTION_COUNT)
                .register(account);
    }

    @Test
    public void new_account_should_not_have_any_transactions() {
        Account emptyAccount = new Account("Erich Gamma");
        assertEquals(emptyAccount.history().size(), 0);
    }

    @Test
    public void should_handle_huge_amount_of_transactions() {
        Account intensiveAccount = new Account("Martin Fowler");
        TestTransactions.hugeAmountOfTransactionsFor(intensiveAccount);
        assertEquals(intensiveAccount.history().size(), TestTransactions.HUGE_AMOUNT);
    }

    @Category(TransactionHistoryTests.class)
    @Test
    public void should_find_all_transactions() {
        assertEquals(account.history().size(), TRANSACTION_COUNT);
    }

    @Category(TransactionHistoryTests.class)
    @Test
    public void should_find_transactions_from_specific_period() {
        LocalDate start = LocalDate.of(2016, 3, 20);
        LocalDate end = LocalDate.of(2016, 4, 1);
        assertTrue(account.historyBetween(start, end).size()>0);
    }

    @Test(expected = DuplicatedTransactionException.class)
    public void should_not_register_same_transaction_twice() throws DuplicatedTransactionException, InvalidTransactionException {
        Transaction duplicate = account.history().iterator().next();
        account.register(duplicate);
    }

    @AfterClass
    public static void closeTransactionLog() {
        activityMonitor.close();
    }

}
