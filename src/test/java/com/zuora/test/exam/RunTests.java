package com.zuora.test.exam;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.zuora.test.exam.cases.ContactTest;
import com.zuora.test.exam.cases.DealTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    DealTest.class,
    ContactTest.class
})
public class RunTests {
}
