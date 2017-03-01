package org.waal70.utils.security.aesa;

import java.io.InputStream;
import java.util.prefs.Preferences;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.waal70.utils.security.aesa.preference.FilePreferenceFactory;

public class Main {

	private static Logger log = Logger.getLogger(Main.class);
	private static AESAInputHelper aih = new AESAInputHelper();
	private static AESAEngine et = null;  //BUGFIX: no new-keyword, as initialize should take place
											//, assignment replaced to main-function

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		initLog4J();
		log.info("==============START NEW EXECUTION RUN====================");
		initPreferences();
		log.debug(System.getProperty("user.home"));
		
		//use writePreferences if currently no keyfile exists
		//writePreferences();

		//Choose operation: (t)est a string or (p)rocess a file:
		et = new AESAEngine();
		char r = aih.getCharFromConsole("Choose mode: (t)est a string or (p)rocess a file: ");
		switch (r)
			{
			case 't': case 'T':
				processTest();
				break;
			case 'p': case 'P':
				processFile();
				break;
			case 'e': case 'E':
				break;
			default:
				processTest();
			}
		log.info ("==============END EXECUTION RUN========================");
	}
	private static void processFile()
	{
		//Choose operation (e)ncrypt or (d)ecrypt:
		char r = aih.getCharFromConsole("Choose operation: (e)ncrypt or (d)ecrypt: ");
		log.info("Will assume TEMP dir: " + System.getenv("TEMP"));
		String filename = aih.getInputStringFromConsole("Give filename to perform this operation on: ");
		String passPhrase = aih.getInputStringFromConsole("Give passphrase: ");
		
		switch (r){
		case 'e': case 'E':
			et.fileEncrypt(passPhrase, filename);
			break;
		case 'd': case 'D':
			et.fileDecrypt(passPhrase, filename);
			break;
		default:
			log.info("Default case invoked");
			//no action
		}
	}
	
	private static void processTest()
	{
		String input = aih.getInputStringFromConsole("Give input to perform encryption on: ");
		String passPhrase = aih.getInputStringFromConsole("Give passphrase: ");
		et.stringTest(input, passPhrase);	
	}
	
	private static void initLog4J()
	{
		InputStream is = Main.class.getResourceAsStream("/log4j.properties");
		PropertyConfigurator.configure(is);
	}
	private static void initPreferences()
	{
		 final String SYSTEM_PROPERTY_FILE =
				    "nl.andredewaal.utils.security.aesa.preference.FilePreferenceFactory.file";
		 log.info("initPreferences()");
	    System.setProperty("java.util.prefs.PreferencesFactory", "nl.andredewaal.utils.security.aesa.preference.FilePreferenceFactory");
	    System.setProperty(SYSTEM_PROPERTY_FILE, "aesa.preferences");
	    log.info("end initPreferences()");
	 
	    //Preferences p = Preferences.userNodeForPackage(FilePreferenceFactory.class);
		
	}
	@SuppressWarnings("unused")
	private static void writePreferences()
	{
		final String SYSTEM_PROPERTY_FILE =
			    "nl.andredewaal.utils.security.aesa.preference.FilePreferenceFactory.file";
		System.setProperty("java.util.prefs.PreferencesFactory", FilePreferenceFactory.class.getName());
	    System.setProperty(SYSTEM_PROPERTY_FILE, "aesa.preferences");
	 
	    Preferences p = Preferences.userNodeForPackage(FilePreferenceFactory.class);
	    //p.putBoolean("hi", true);
	    p.put("keyfile", "C:\\Users\\awaal\\AppData\\Local\\Temp\\PKeyfile.txt");
	    p.put("encoding", "UTF-8");
	}
}
