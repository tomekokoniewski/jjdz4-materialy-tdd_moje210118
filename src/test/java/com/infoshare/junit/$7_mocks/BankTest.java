package com.infoshare.junit.$7_mocks;

import com.infoshare.junit.banking.*;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Observer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class BankTest {

    private TransferBank bank;
    private Account sourceAccount;
    private Account targetAccount;

    @Test
    public void account_monitors_are_notified_about_transactions() throws Exception {

        bank = new GenericBank();
        Observer accountMonitor = Mockito.mock(Observer.class);
        LoggingActivityMonitor loggingMonitor = new LoggingActivityMonitor();

        sourceAccount = bank.getAccountFor("Kent Beck");
        sourceAccount.addObserver(accountMonitor);
        sourceAccount.addObserver(loggingMonitor);

        targetAccount = bank.getAccountFor("Dan North");
        targetAccount.addObserver(accountMonitor);
        targetAccount.addObserver(loggingMonitor);

        assertThat(sourceAccount.countObservers()).isEqualTo(2);
        assertThat(targetAccount.countObservers()).isEqualTo(2);

        sourceAccount.register(new Transaction(BigDecimal.valueOf(10000), LocalDateTime.now()));
        targetAccount.register(new Transaction(BigDecimal.valueOf(10000), LocalDateTime.now()));

        verify(accountMonitor, times(2)).update(any(), any());
    }

    @Test
    public void bank_registers_transactions_on_both_accounts() throws Exception {
        // given
        TransferBank bank = new GenericBank();
        Account sourceAccount = mock(Account.class);
        Account targetAccount = mock(Account.class);
        LocalDateTime transactionDate = LocalDateTime.of(2016, Month.OCTOBER, 2, 0, 0);
        when(sourceAccount.transferTo(isA(Account.class), isA(BigDecimal.class), isA(LocalDateTime.class)))
                .thenReturn( new Transaction(BigDecimal.valueOf(500), transactionDate, sourceAccount, targetAccount));

        // when
        Transaction t = sourceAccount.transferTo(targetAccount, BigDecimal.ZERO, LocalDateTime.now());
        bank.register(t);
        bank.process();

        // then
        assertThat(t.getAmount()).isEqualTo("500");
        verify(sourceAccount, times(1)).register(any());
        verify(targetAccount, times(1)).register(any());
    }

    @Test
    public void should_not_change_source_balance_when_target_account_cant_register_transaction() throws Exception {
        TransferBank bank = new GenericBank();
        Account sourceAccount = mock(Account.class);
        Account targetAccount = mock(Account.class);
        Transaction t = new Transaction(BigDecimal.valueOf(500), LocalDateTime.now(), sourceAccount, targetAccount);
        bank.register(t);
        bank.process();
        doThrow(new InvalidTransactionException()).when(targetAccount).register(isA(Transaction.class));
        verify(sourceAccount, never().description("should never register failed transaction")).register(any());
    }

}
