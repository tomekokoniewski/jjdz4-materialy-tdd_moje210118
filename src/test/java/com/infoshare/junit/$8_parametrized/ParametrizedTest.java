package com.infoshare.junit.$8_parametrized;

import com.infoshare.junit.banking.Account;
import com.infoshare.junit.banking.GenericBank;
import com.infoshare.junit.banking.Transaction;
import com.infoshare.junit.banking.TransferBank;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class ParametrizedTest {

    @Test
    @Parameters({"0, 0", "1, 1", "100, 100"})
    public void bank_should_transfer_money(int amount, boolean valid) throws Exception {
        TransferBank bank = new GenericBank();
        Account sourceAccount = bank.getAccountFor("Erich Gamma");
        sourceAccount.register(new Transaction(BigDecimal.valueOf(1000), LocalDateTime.now()));
        Account targetAccount = bank.getAccountFor("Dan North");
        Transaction transaction = new Transaction(BigDecimal.valueOf(amount), LocalDateTime.now(), sourceAccount, targetAccount);
        bank.register(transaction);
        bank.process();
        assertThat(targetAccount).hasFieldOrPropertyWithValue("balance", BigDecimal.valueOf(amount));
    }

    @Test
    @Parameters(method = "adultValues")
    public void personIsAdult(int age, boolean valid) throws Exception {
        assertEquals(valid, new Person(age).isAdult());
    }

    private Object[] adultValues() {
        return new Object[]{
                new Object[]{13, false},
                new Object[]{17, false},
                new Object[]{18, true},
                new Object[]{22, true}
        };
    }

}

class Person {
    private int age;

    public Person(int age) {
        this.age = age;
    }

    public boolean isAdult() {
        return age >= 18;
    }

    @Override
    public String toString() {
        return "Person of age: " + age;
    }
}

