package com.infoshare.junit.$6_stubs;

import com.infoshare.junit.banking.Transaction;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransactionTimeTest {

    @Test
    public void stub_transaction_amount() {
        Transaction t = mock(Transaction.class);
        when(t.getAmount()).thenReturn(BigDecimal.TEN);
        Assertions.assertThat(t.getAmount()).isEqualTo("10");
    }

    @Test
    public void stub_multiple_calls_to_transaction_amount() {
        Transaction t = mock(Transaction.class);
        when(t.getAmount()).thenReturn(BigDecimal.TEN, BigDecimal.valueOf(100), BigDecimal.valueOf(1000));
        Assertions.assertThat(t.getAmount()).isEqualTo("10");
        Assertions.assertThat(t.getAmount()).isEqualTo("100");
        Assertions.assertThat(t.getAmount()).isEqualTo("1000");
    }


}
