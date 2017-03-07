package org.waal70.utils.security.aesa.test;

import static org.junit.Assert.*;

import java.io.InputStream;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.waal70.utils.security.aesa.Main;
import org.waal70.utils.security.aesa.Messages;

public class MainTest {

	@Test
	public void testMain() {
		
		//This test class takes care of all the plumbing for AESA
		//test log4J setup
		//test preferences setup
		//test messages setup, etcetera
		
		//fail("Not yet implemented");
	}
	@Test
	public void testLog4JSetup()
	{
		InputStream is = Main.class.getResourceAsStream("/log4j.properties");
		assertNotNull("log4j property-file not found or not in the corrent location!", is);
		PropertyConfigurator.configure(is);
		Logger log = Logger.getLogger(MainTest.class);
		assertNotNull("logger not instantiated", log);
		log.debug("Testing Logger");
		assertTrue("Minimum INFO level not set on log4j", log.isInfoEnabled());
	}
	@Test
	public void testPreferences()
	{
		 final String SYSTEM_PROPERTY_FILE =
				    "org.waal70.utils.security.aesa.preference.FilePreferenceFactory.file";
		assertNull("Previous value for PreferenceFactory found!", System.setProperty("java.util.prefs.PreferencesFactory", "org.waal70.utils.security.aesa.preference.FilePreferenceFactory"));
	    assertNull("Previous value for SYSTEM_PROPERTY_FILE found!", System.setProperty(SYSTEM_PROPERTY_FILE, "aesa.preferences"));
	    
	}
	@Test
	public void testMessages()
	{
		assertEquals("Message bundle not found", "Testerdetest", Messages.getString("Main.welcome"));
	}

}
