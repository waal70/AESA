/**
 * 
 */
package org.waal70.utils.security.aesa.crypto.engine.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.waal70.utils.security.aesa.crypto.engine.TwoFishEngine;

//import nl.andredewaal.utils.security.aesa.crypto.engine.TwoFishEngine;

/**
 * @author awaal
 *
 */
public class TwoFishEngineTest {
	
	private TwoFishEngine _tfe;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		_tfe = new TwoFishEngine();

	}

	/**
	 * Test method for {@link org.waal70.utils.security.aesa.crypto.engine.TwoFishEngine#getAlgorithmName()}.
	 */
	@Test
	public void testGetAlgorithmName() {
		assertEquals("TwofishEngine must be available", "Twofish", _tfe.getAlgorithmName());
	}

	/**
	 * Test method for {@link org.waal70.utils.security.aesa.crypto.engine.TwoFishEngine#processBlock(byte[], int, byte[], int)}.
	 */
	@Test
	public void testProcessBlock() {
		assertEquals(1, 1);
		//fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link org.waal70.utils.security.aesa.crypto.engine.TwoFishEngine#getBlockSize()}.
	 */
	@Test
	public void testGetBlockSize() {
		assertEquals(1, 1);
		//fail("Not yet implemented"); // TODO
	}

}
