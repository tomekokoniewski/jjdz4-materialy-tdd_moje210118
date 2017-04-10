package com.infoshare.junit.$4_matchers;

import com.infoshare.junit.banking.Account;
import com.infoshare.junit.banking.Transaction;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

public class NewAccountMatchersTest {

    private Account emptyAccount;

    @Before
    public void setUp() throws Exception {
        emptyAccount = new Account("Erich Gamma");
    }

    @Test
    public void new_account_should_have_empty_history_1() {
//        assertSame(0, emptyAccount.history().size());
        // TODO express assert above using hamcrest matchers
        fail();
    }

    @Test
    public void new_account_should_have_empty_history_2() {
        assertArrayEquals(new Transaction[]{}, emptyAccount.history().toArray());
        // TODO express assert above using hamcrest matchers
        fail();
    }

}
