/**
 * 
 */
package org.waal70.utils.security.aesa;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


/**
 * Superclass for FileReader and FileWriter, provides shared functionality
 * @author awaal
 *
 */
public class FileHandler {
	
	protected String _fileName = null;
	protected final static Logger log = LogManager.getLogger(FileHandler.class);
	static int _blockSize = AESAConstants.DEFAULT_BLOCK_SIZE;
	protected boolean EOF = false;

}
