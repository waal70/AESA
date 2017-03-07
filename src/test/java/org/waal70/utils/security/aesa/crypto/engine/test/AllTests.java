package org.waal70.utils.security.aesa.crypto.engine.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.waal70.utils.security.aesa.test.MainTest;

@RunWith(Suite.class)
@SuiteClasses({ TwoFishEngineTest.class, MainTest.class }) // commaseparated list of test classes

public class AllTests {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
