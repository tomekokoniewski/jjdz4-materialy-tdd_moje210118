package com.infoshare.junit.$2_test_fixture;

import com.infoshare.junit.$1_first_test.CarUnitTest;
import com.infoshare.junit.$3_basic_asserts.NewAccountTest;
import com.infoshare.junit.$3_basic_asserts.TransactionTest;
import com.infoshare.junit.$4_matchers.NewAccountMatchersTest;
import com.infoshare.junit.$4_matchers.TransactionMatchersTest;
import com.infoshare.junit.$5_assertj.TransferTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
//        CarUnitTest.class,
        TransactionSetupTest.class,
        TransactionTest.class,
        NewAccountTest.class,
        NewAccountMatchersTest.class,
        TransactionMatchersTest.class,
        TransferTest.class
})
public class AllTests {
}
