package com.infoshare.junit.$3_basic_asserts;

import com.infoshare.junit.banking.Account;
import com.infoshare.junit.banking.Transaction;
import com.infoshare.junit.banking.TransactionsBuilder;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class TransactionTest {

    private static final int TRANSACTION_COUNT = 100;

    private static Account account;

    @Before
    public void setUp() throws Exception {
        account = new Account("Kent Beck");
//        new TransactionsBuilder()
//                .after(LocalDateTime.of(2015, Month.DECEMBER, 1, 0, 0))
//                .before(LocalDateTime.of(2016, Month.APRIL, 30, 0, 0))
//                .valueBetween(1, 100000)
//                .totalOf(TRANSACTION_COUNT)
//                .register(account);
    }

    @Test
    public void account_balance_should_be_equal_to_sum_of_transactions() throws Exception {
        // TODO use assertEquals, assertTrue, assertFalse
        fail();
    }

    @Test
    public void registering_transaction_should_change_balance_1() throws Exception {
        // given
        Integer originalBalance = account.getBalance();
        // when
        new TransactionsBuilder().value(100).totalOf(1).register(account);
        // then
        // TODO use assertEquals, assertTrue, assertFalse
        fail();
    }

    @Test
    public void registering_transaction_should_change_balance_2() throws Exception {
        // given
        Integer originalBalance = account.getBalance();
        // when
        new TransactionsBuilder().value(100).totalOf(1).register(account);
        // then
        // TODO use assertEquals, assertTrue, assertFalse
        fail();
    }

    @Test
    public void registering_transaction_should_change_history() throws Exception {
        //given
        Set<Transaction> originalHistory = account.history();
        // when
        new TransactionsBuilder().valueBetween(100, 1000).totalOf(1).register(account);
        // then
        // TODO use assertEquals, assertNotEquals, assertTrue, assertFalse
        fail();
    }

    @Test
    public void account_should_not_change_history_without_adding_transaction() {
        Set<Transaction> historyOne = account.history();
        Set<Transaction> historyTwo = account.history();

        // TODO - why this assertion fails? fix it
        assertSame("history shouldn't change", historyOne, historyTwo);

    }

    @Test
    public void verifyEquals() {
        Transaction[] transactions = new TransactionsBuilder().withRandomDates().value(100).totalOf(20).build().toArray(new Transaction[2]);
        assertThat(transactions).doesNotContainNull();
        EqualsVerifier.forClass(Transaction.class)
                .withIgnoredFields("status") // comment to see error messages
                .withPrefabValues(Transaction.class,transactions[0],transactions[1]).verify();
    }

    private Integer sumOfTransactions(Collection<Transaction> transactions) {
        return transactions.stream().collect(Collectors.summingInt(t->t.getAmount()));
    }
}

