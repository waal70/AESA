/**
 * 
 */
package nl.andredewaal.utils.security.aesa;

import org.apache.log4j.Logger;


/**
 * Superclass for FileReader and FileWriter, provides shared functionality
 * @author awaal
 *
 */
public class FileHandler {
	
	protected String _fileName = null;
	protected final static Logger log = Logger.getLogger(FileHandler.class);
	static int _blockSize = AESAConstants.DEFAULT_BLOCK_SIZE;
	protected boolean EOF = false;

}
