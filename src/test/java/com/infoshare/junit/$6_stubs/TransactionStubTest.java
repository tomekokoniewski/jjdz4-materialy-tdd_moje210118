package com.infoshare.junit.$6_stubs;

import com.infoshare.junit.banking.Transaction;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class TransactionStubTest {

    @Test
    public void stub_transaction_amount() {
        Transaction t = mock(Transaction.class);

        // TODO use when(...).thenReturn(...) to make test pass

        assertThat(t.getAmount()).isEqualTo(10);
    }

    @Test
    public void stub_multiple_calls_to_transaction_amount() {
        Transaction t = mock(Transaction.class);

        // TODO use when(...).thenReturn(...) to make test pass

        assertThat(t.getAmount()).isEqualTo(10);
        assertThat(t.getAmount()).isEqualTo(100);
        assertThat(t.getAmount()).isEqualTo(1000);
    }


}
