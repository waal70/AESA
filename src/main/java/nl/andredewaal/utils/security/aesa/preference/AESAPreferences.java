package nl.andredewaal.utils.security.aesa.preference;

import java.util.*;
import java.util.prefs.AbstractPreferences;
import java.util.prefs.BackingStoreException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileOutputStream;

import org.apache.log4j.Logger;
 
/**
 * Preferences implementation that stores to a user-defined file. See AESAPreferencesFactory.
 *
 * @author David Croft (<a href="http://www.davidc.net">www.davidc.net</a>)
 * @version $Id: AESAPreferences.java 283 2009-06-18 17:06:58Z david $
 */
public class AESAPreferences extends AbstractPreferences
{
  private static final Logger log = Logger.getLogger(AESAPreferences.class);
 
  private Map<String, String> root;
  private Map<String, AESAPreferences> children=null;
  private boolean isRemoved = false;
 
  public AESAPreferences(AbstractPreferences parent, String name)
  {
	super(parent, name);
 
    log.debug("Instantiating node " + name);
 
    root = new TreeMap<String, String>();
    children = new TreeMap<String, AESAPreferences>();
 
    try {
      sync();
    }
    catch (BackingStoreException e) {
      log.warn("Unable to sync on creation of node " + name, e);
    }
  }
 
  protected void putSpi(String key, String value)
  {
    root.put(key, value);
    try {
      flush();
    }
    catch (BackingStoreException e) {
      log.warn("Unable to flush after putting " + key, e);
    }
  }
 
  protected String getSpi(String key)
  {
    return root.get(key);
  }
 
  protected void removeSpi(String key)
  {
    root.remove(key);
    try {
      flush();
    }
    catch (BackingStoreException e) {
      log.warn("Unable to flush after removing " + key, e);
    }
  }
 
  protected void removeNodeSpi() throws BackingStoreException
  {
    isRemoved = true;
    flush();
  }
 
  protected String[] keysSpi() throws BackingStoreException
  {
    return root.keySet().toArray(new String[root.keySet().size()]);
  }
 
  protected String[] childrenNamesSpi() throws BackingStoreException
  {
    return children.keySet().toArray(new String[children.keySet().size()]);
  }
 
  protected AESAPreferences childSpi(String name)
  {
	log.debug("childspi");
    AESAPreferences child = children.get(name);
    if (child == null || child.isRemoved()) {
      child = new AESAPreferences(this, name);
      children.put(name, child);
    }
    return child;
  }
 
 
  protected void syncSpi() throws BackingStoreException
  {
    if (isRemoved()) return;
    log.debug("syncSpi");
    final File file = FilePreferenceFactory.getPreferencesFile();
 
    if (!file.exists()) return;
 
    synchronized (file) {
      Properties p = new Properties();
      try {
        p.load(new FileInputStream(file));
 
        StringBuilder sb = new StringBuilder();
        getPath(sb);
        String path = sb.toString();
 
        final Enumeration<?> pnen = p.propertyNames();
        while (pnen.hasMoreElements()) {
          String propKey = (String) pnen.nextElement();
          if (propKey.startsWith(path)) {
            String subKey = propKey.substring(path.length());
            // Only load immediate descendants
            if (subKey.indexOf('.') == -1) {
              root.put(subKey, p.getProperty(propKey));
            }
          }
        }
      }
      catch (IOException e) {
        throw new BackingStoreException(e);
      }
    }
  }
 
  private void getPath(StringBuilder sb)
  {
    final AESAPreferences parent = (AESAPreferences) parent();
    if (parent == null) return;
 
    parent.getPath(sb);
    sb.append(name()).append('.');
  }
 
  protected void flushSpi() throws BackingStoreException
  {
    final File file = FilePreferenceFactory.getPreferencesFile();
 
    synchronized (file) {
      Properties p = new Properties();
      try {
 
        StringBuilder sb = new StringBuilder();
        getPath(sb);
        String path = sb.toString();
 
        if (file.exists()) {
          p.load(new FileInputStream(file));
 
          List<String> toRemove = new ArrayList<String>();
 
          // Make a list of all direct children of this node to be removed
          final Enumeration<?> pnen = p.propertyNames();
          while (pnen.hasMoreElements()) {
            String propKey = (String) pnen.nextElement();
            if (propKey.startsWith(path)) {
              String subKey = propKey.substring(path.length());
              // Only do immediate descendants
              if (subKey.indexOf('.') == -1) {
                toRemove.add(propKey);
              }
            }
          }
 
          // Remove them now that the enumeration is done with
          for (String propKey : toRemove) {
            p.remove(propKey);
          }
        }
 
        // If this node hasn't been removed, add back in any values
        if (!isRemoved) {
          for (String s : root.keySet()) {
            p.setProperty(path + s, root.get(s));
          }
        }
 
        p.store(new FileOutputStream(file), "AESAPreferences");
      }
      catch (IOException e) {
        throw new BackingStoreException(e);
      }
    }
  }
}