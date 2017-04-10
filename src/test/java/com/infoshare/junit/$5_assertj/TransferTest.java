package com.infoshare.junit.$5_assertj;

import com.infoshare.junit.$2_test_fixture.TransactionsBuilder;
import com.infoshare.junit.banking.*;
import org.assertj.core.api.Condition;
import org.assertj.core.api.SoftAssertions;
import org.junit.*;
import org.junit.runners.MethodSorters;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.fail;

@FixMethodOrder(MethodSorters.JVM)
public class TransferTest {

    private static TransferBank bank;

    private Account sourceAccount;
    private Account targetAccount;
    private final Condition<? super Transaction> isHugeTransfer = new Condition<Transaction>() {
        @Override
        public boolean matches(Transaction value) {
            return 1 == value.getAmount().compareTo(BigDecimal.valueOf(99999));
        }
    };
    private final Condition<? super Transaction> isNightTransfer = new Condition<Transaction>() {
        @Override
        public boolean matches(Transaction value) {
            int hour = value.getDate().getHour();
            return hour > 22 || hour < 2;
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
        // TODO use Assertions.assertThat to verify correct transfer
        fail();
    }

    @Test
    public void processing_without_registered_transactions_should_not_change_accounts() throws Exception {
        // given
        BigDecimal initialTargetBalance = targetAccount.getBalance();
        Transaction t1, t2;
        // when
        t1 = sourceAccount.transferTo(targetAccount, BigDecimal.valueOf(500), LocalDateTime.of(2016, Month.OCTOBER, 2, 0, 0));
        bank.register(t1);
        bank.process();
        t2 = sourceAccount.transferTo(targetAccount, BigDecimal.valueOf(1500), LocalDateTime.now());
        bank.register(t2);
        bank.process();
        bank.process();

        // then
        SoftAssertions softly = new SoftAssertions();

        // TODO use SoftAssertions.assertThat to verify that only registered transactions were processed
        // change any values to see how soft assertions report errors
        fail();
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
        // TODO use Assertions.assertThat to verify test
        fail();
    }

    @Test
    public void bank_should_hold_huge_transfers() throws Exception {
        // given
        bigTransactions.transferBetween(richAccount, targetAccount);

        // when
        Collection<Transaction> processedTransactions = bank.process();

        // then
        // TODO use Assertions.assertThat and a Condition object to verify test
        fail();
    }

    @Test
    public void cautious_bank_should_block_all_transfers_at_night() throws Exception {
        // given

        //GenericBank.isCautious = true;
        bigTransactions.transferBetween(richAccount, targetAccount);

        // when
        Collection<Transaction> processedTransactions = bank.process();

        // then
        // TODO use Assertions.assertThat and a Condition object to verify test
        // TODO do you notice any problems?
        fail();
    }

    @Test
    public void should_not_transfer_negative_amounts() {
        // TODO use Assertions.assertThatExceptionOfType to verify that negative transaction throws InvalidTransactionException
        fail();
    }

}
