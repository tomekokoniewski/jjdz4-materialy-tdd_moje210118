package com.infoshare.junit.$6_stubs;

import com.infoshare.junit.banking.Transaction;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransactionStubTest {

    @Test
    public void stub_transaction_amount() {
        Transaction t = mock(Transaction.class);

        // TODO use when(...).thenReturn(...) to make test pass

        assertThat(t.getAmount()).isEqualTo("10");
    }

    @Test
    public void stub_multiple_calls_to_transaction_amount() {
        Transaction t = mock(Transaction.class);

        // TODO use when(...).thenReturn(...) to make test pass

        assertThat(t.getAmount()).isEqualTo("10");
        assertThat(t.getAmount()).isEqualTo("100");
        assertThat(t.getAmount()).isEqualTo("1000");
    }


}
