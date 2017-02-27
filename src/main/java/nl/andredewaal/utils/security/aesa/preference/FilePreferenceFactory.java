package nl.andredewaal.utils.security.aesa.preference;


import java.io.File;
import java.util.prefs.Preferences;
import java.util.prefs.PreferencesFactory;
import org.apache.log4j.Logger;
 
/**
 * PreferencesFactory implementation that stores the preferences in a user-defined file. To use it,
 * set the system property <tt>java.util.prefs.PreferencesFactory</tt> to
 * <tt>net.infotrek.util.prefs.FilePreferencesFactory</tt>
 * <p/>
 * The file defaults to [user.home]/.fileprefs, but may be overridden with the system property
 * <tt>net.infotrek.util.prefs.FilePreferencesFactory.file</tt>
 *
 * @author David Croft (<a href="http://www.davidc.net">www.davidc.net</a>)
 * @version $Id: FilePreferencesFactory.java 282 2009-06-18 17:05:18Z david $
 */
public class FilePreferenceFactory implements PreferencesFactory
{
  private static final Logger log = Logger.getLogger(FilePreferenceFactory.class);
 
  AESAPreferences rootPreferences;
  public static final String SYSTEM_PROPERTY_FILE =
    "nl.andredewaal.utils.security.aesa.preference.FilePreferenceFactory.file";
 
  public Preferences systemRoot()
  {
	  log.debug("systemRoot()");
   //return userRoot();
	  return null;
  }
 
  public AESAPreferences userRoot()
  {
    if (rootPreferences == null) {
      log.debug("Instantiating root preferences");
 
      rootPreferences = new AESAPreferences(null, "");
      log.debug("End root preference");
    }
    return rootPreferences;
  }
 
  private static File preferencesFile;
 
  public static void setPreferencesFile(File preferencesFile) {
	FilePreferenceFactory.preferencesFile = preferencesFile;
}

public static File getPreferencesFile()
  {
    if (preferencesFile == null) {
      String prefsFile = System.getProperty(SYSTEM_PROPERTY_FILE);
      if (prefsFile == null || prefsFile.length() == 0) {
        prefsFile = System.getProperty("user.home") + File.separator + ".aesa";
      }
      setPreferencesFile(new File(prefsFile).getAbsoluteFile());
      log.debug("Preferences file is " + preferencesFile);
    }
    return preferencesFile;
  }
 
}