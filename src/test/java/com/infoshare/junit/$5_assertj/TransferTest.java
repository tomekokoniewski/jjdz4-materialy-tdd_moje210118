package com.infoshare.junit.$5_assertj;

import com.infoshare.junit.$2_test_fixture.TransactionsBuilder;
import com.infoshare.junit.banking.*;
import org.assertj.core.api.Condition;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.catchThrowable;

@FixMethodOrder(MethodSorters.JVM)
public class TransferTest {

    private static TransferBank bank;

    private Account sourceAccount;
    private Account targetAccount;
    private final Condition<? super Transaction> isHugeTransfer = new Condition<Transaction>(){
        @Override
        public boolean matches(Transaction value) {
            return 1==value.getAmount().compareTo(BigDecimal.valueOf(99999));
        }
    };
    private Account richAccount;
    private TransactionsBuilder bigTransactions;

    @BeforeClass
    public static void setupBank() {
        bank = new GenericBank();
    }

    @Before
    public void setup() throws Exception {

        // comment to see random test failures
        bank = new GenericBank();

        sourceAccount = bank.getAccountFor("Kent Beck");
        sourceAccount.register(new Transaction(BigDecimal.valueOf(10000), LocalDateTime.now()));

        targetAccount = bank.getAccountFor("Dan North");
        targetAccount.register(new Transaction(BigDecimal.valueOf(10000), LocalDateTime.now()));

        richAccount = bank.getAccountFor("Mark Zuckerberg");
        richAccount.register(new Transaction(BigDecimal.valueOf(1000000), LocalDateTime.now()));

        bigTransactions = new TransactionsBuilder()
                .totalOf(10)
                .valueBetween(50000, 150000)
                .after(LocalDateTime.of(2016, Month.JANUARY, 1, 0, 0))
                .before(LocalDateTime.of(2016, Month.APRIL, 1, 0, 0))
                .using(bank);
    }

    @Test
    public void bank_should_sucessfully_transfer_between_accounts() throws Exception {
        // given
        BigDecimal initialTargetBalance = targetAccount.getBalance();
        Transaction t = sourceAccount.transferTo(targetAccount, BigDecimal.valueOf(500), LocalDateTime.now());
        // when
        bank.register(t);
        bank.process();
        // then
        assertThat(sourceAccount.getBalance()).isEqualTo("9500");
        assertThat(targetAccount.getBalance()).isEqualTo("10500");
    }

    @Test
    public void processing_without_registered_transactions_should_not_change_accounts() throws Exception {
        // given
        BigDecimal initialTargetBalance = targetAccount.getBalance();
        Transaction t1, t2;
        // when
        t1 = sourceAccount.transferTo(targetAccount, BigDecimal.valueOf(500), LocalDateTime.of(2016,Month.OCTOBER,2,0,0));
        bank.register(t1);
        bank.process();
        t2 = sourceAccount.transferTo(targetAccount, BigDecimal.valueOf(1500), LocalDateTime.now());
        bank.register(t2);
        bank.process();
        bank.process();

        // then
        SoftAssertions softly = new SoftAssertions();

        // change any values to see how soft assertions report errors
        softly.assertThat(t1).isNotEqualByComparingTo(t2);
        softly.assertThat(bank.process()).hasSize(2);
        softly.assertThat(sourceAccount.getBalance()).isEqualTo("8000");
        softly.assertThat(targetAccount.getBalance()).isEqualTo("12000");

        softly.assertAll();
    }

    @Test
    public void bank_should_hold_transfers_above_99999() throws Exception {
        // given
        BigDecimal initialRichBalance = richAccount.getBalance();
        BigDecimal initialTargetBalance = targetAccount.getBalance();
        bigTransactions.transferBetween(richAccount, targetAccount);

        // when
        Collection<Transaction> processedTransactions = bank.process();

        // then
        assertThat(richAccount.getBalance()).isLessThan(initialRichBalance);
        assertThat(targetAccount.getBalance()).isGreaterThan(initialTargetBalance);
        assertThat(processedTransactions)
                .filteredOn(t ->t.getAmount().compareTo(BigDecimal.valueOf(99999))==1)
                .extracting("status")
                .containsOnly(TransactionStatus.ON_HOLD);
    }

    @Test
    public void bank_should_hold_huge_transfers() throws Exception {
        //GenericBank.isCautious = false;
        // given
        bigTransactions.transferBetween(richAccount, targetAccount);

        // when
        Collection<Transaction> processedTransactions = bank.process();

        // then
        assertThat(processedTransactions)
                .filteredOn(isHugeTransfer)
                .extracting("status")
                .containsOnly(TransactionStatus.ON_HOLD);
    }

    @Test
    public void cautious_bank_should_block_all_transfers_at_night() throws Exception {
        // given

        // uncomment to see random test failures
        // GenericBank.isCautious = true;
        bigTransactions.transferBetween(richAccount, targetAccount);

        // when
        Collection<Transaction> processedTransactions = bank.process();

        // then
        assertThat(processedTransactions)
                .filteredOn(isHugeTransfer)
                .extracting("status")
                .containsOnly(TransactionStatus.ON_HOLD);
    }

    @Test
    public void should_not_transfer_negative_amounts() {
        assertThatExceptionOfType(InvalidTransactionException.class).isThrownBy(() -> {
            sourceAccount.transferTo(targetAccount, BigDecimal.valueOf(-500), LocalDateTime.of(2016, Month.OCTOBER, 2, 0, 0));
        });
    }

}
