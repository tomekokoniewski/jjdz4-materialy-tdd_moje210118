package com.infoshare.junit.$6_stubs;

import com.infoshare.junit.banking.Account;
import com.infoshare.junit.banking.GenericBank;
import com.infoshare.junit.banking.TransferBank;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransferBankTest {

    @Test
    public void stub_account_owners() {
        TransferBank bank = mock(GenericBank.class);
        when(bank.getAccountFor(any())).thenReturn(new Account("Kent Beck"), new Account("Erich Gamma"));

        Account account1 = bank.getAccountFor("1");
        Account account2 = bank.getAccountFor("2");

        SoftAssertions softAssert = new SoftAssertions();
        softAssert.assertThat(account1.getOwner()).isEqualTo("Kent Beck");
        softAssert.assertThat(account2.getOwner()).isEqualTo("Erich Gamma");
        softAssert.assertAll();
    }

    @Test
    public void verify_private_field() {
        TransferBank bank = mock(GenericBank.class);
        when(bank.getAccountFor(any())).thenReturn(new Account("Kent Beck"), new Account("Erich Gamma"));

        Account account1 = bank.getAccountFor("1");
        Account account2 = bank.getAccountFor("2");

        SoftAssertions softAssert = new SoftAssertions();
        softAssert.assertThat(account1).extracting("owner").contains("Kent Beck");
        softAssert.assertThat(account2).extracting("owner").contains("Erich Gamma");
        softAssert.assertAll();
    }
}
