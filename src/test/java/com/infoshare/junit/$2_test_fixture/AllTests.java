package com.infoshare.junit.$2_test_fixture;

import com.infoshare.junit.$3_basic_asserts.NewAccountTest;
import com.infoshare.junit.$3_basic_asserts.TransactionTest;
import com.infoshare.junit.$4_matchers.NewAccountMatchersTest;
import com.infoshare.junit.$4_matchers.TransactionMatchersTest;
import com.infoshare.junit.$5_assertj.TransferTest;
import com.infoshare.junit.$6_stubs.TransactionStubTest;
import com.infoshare.junit.$6_stubs.TransferBankTest;
import com.infoshare.junit.$7_mocks.BankTest;
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
        TransferTest.class,
        TransactionStubTest.class,
        TransferBankTest.class,
        BankTest.class
})
public class AllTests {
}
