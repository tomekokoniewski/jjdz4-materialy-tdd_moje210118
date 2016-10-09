package com.infoshare.junit.$3_basic_asserts;

import com.infoshare.junit.$2_test_fixture.TransactionsBuilder;
import com.infoshare.junit.banking.Account;
import com.infoshare.junit.banking.Transaction;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.Set;

import static org.junit.Assert.*;

public class TransactionTest {

    private static final int TRANSACTION_COUNT = 100;

    private static Account account;

    @Before
    public void setUp() throws Exception {
        account = new Account("Kent Beck");
        new TransactionsBuilder()
                .after(LocalDateTime.of(2015, Month.DECEMBER, 1, 0, 0))
                .before(LocalDateTime.of(2016, Month.APRIL, 30, 0, 0))
                .valueBetween(1, 100000)
                .totalOf(TRANSACTION_COUNT)
                .register(account);
    }

    @Test
    public void account_balance_should_be_equal_to_sum_of_transactions() throws Exception {
        BigDecimal historyBalance = sumOfTransactions(account.history());
// uncomment to see error message
//        account.register(new Transaction(BigDecimal.TEN,LocalDateTime.now()));
        assertEquals(historyBalance, account.getBalance());
    }

    @Test
    public void registering_transaction_should_change_balance_1() throws Exception {
        // given
        BigDecimal originalBalance = account.getBalance();
        // when
        new TransactionsBuilder().value(100).totalOf(1).register(account);
        // then
        assertFalse(account.getBalance().equals(originalBalance));
    }

    @Test
    public void registering_transaction_should_change_balance_2() throws Exception {
        // given
        BigDecimal originalBalance = account.getBalance();
        // when
        new TransactionsBuilder().value(100).totalOf(1).register(account);
        // then
        assertTrue(account.getBalance().doubleValue()>originalBalance.doubleValue());
    }

    @Test
    public void registering_transaction_should_change_history() throws Exception {
        //given
        Set<Transaction> originalHistory = account.history();
        // when
        new TransactionsBuilder().valueBetween(100, 1000).totalOf(1).register(account);
        // then
        assertNotEquals(originalHistory, account.history());
    }

    @Test
    public void account_should_not_change_history_without_adding_transaction() {
        Set<Transaction> historyOne = account.history();
        Set<Transaction> historyTwo = account.history();

// uncomment to fail test - assertSame asserts that two objects refer to the same object
//        assertSame("history shouldn't change", historyOne, historyTwo);

// assertEquals - asserts that two objects are equals
        assertEquals("history shouldn't change", historyOne, historyTwo);
    }

    private BigDecimal sumOfTransactions(Collection<Transaction> transactions) {
        return transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, (acc, value) -> acc.add(value, MathContext.DECIMAL32));
    }
}

