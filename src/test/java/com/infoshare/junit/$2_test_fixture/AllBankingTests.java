package com.infoshare.junit.$2_test_fixture;

import com.infoshare.junit.$1_first_test.CarUnitTest;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Categories.class)
@Categories.IncludeCategory(TransactionHistoryTests.class)
@Suite.SuiteClasses({
        CarUnitTest.class,
        TransactionSetupTest.class
})
public class AllBankingTests {
}
