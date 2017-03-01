package org.waal70.utils.security.aesa;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.apache.log4j.Logger;
import org.waal70.utils.security.aesa.crypto.engine.TwoFishEngine;
import org.waal70.utils.security.aesa.crypto.param.KeyParameter;

/**
 * Controls the encryption engine. Helper class, may disappear later on.
 * 
 * @author André de Waal
 * 
 */
public class AESAEngine {

	/** Byte array carrying the (returned) result */
	private byte[] _result;

	/** Contains the private copy of a TwoFishEngine */
	private final TwoFishEngine m_tfe = new TwoFishEngine();

	/** Contains the log4j-logger */
	private static Logger log = Logger.getLogger(AESAEngine.class);

	/**
	 * Performs encryption and decryption on a provided String.<br/>
	 * Test only.
	 * 
	 * @param encryptString
	 *            Any length string containing stuff to be encrypted
	 * @param passPhrase
	 *            The passphrase will be digested/hashed and serves as the key
	 *            for encryption and decryption
	 */
	public void stringTest(String encryptString, String passPhrase) {
		long millis = 0;
		byte[] encryptArray = encryptString.getBytes(Charset.forName("UTF-8"));
		byte[] endResult = null;
		byte[] endResult2 = null;
		byte[] processThis = null;
		int readingOffset = 0;
		int currentEnd = 0;
		int iterationsDesired = Util.calculateIterations(encryptArray.length);
		log.debug("iteraties gewenst: " + iterationsDesired);
		millis = System.currentTimeMillis();
		for (int iterations=1;iterations<=iterationsDesired;iterations++)
		{
			currentEnd = iterations*AESAConstants.DEFAULT_BLOCK_SIZE;
			
			if (currentEnd < encryptArray.length)
				processThis = Arrays.copyOfRange(encryptArray, readingOffset, currentEnd);
			else
				processThis = Arrays.copyOfRange(encryptArray, readingOffset, encryptArray.length);
				
			readingOffset+=AESAConstants.DEFAULT_BLOCK_SIZE;
			_result = applyEngine(false, processThis, passPhrase);
			if (endResult != null) 
				endResult = Util.concatArray(endResult, _result);
			else
				endResult = _result;		
		}
		//_result = applyEngine(false, encryptArray, passPhrase);
		millis = System.currentTimeMillis() - millis;
		log.info("Encrypted phrase is: " + Arrays.toString(endResult));
		log.info("Converted to String: " + new String(endResult));
		log.info("Encrypt took (ms): " + millis);
		millis = System.currentTimeMillis();
		//DECRYPT:
		readingOffset = 0;
		processThis = null;
		_result = null;
		for (int iterations=1;iterations<=iterationsDesired;iterations++)
		{
			currentEnd = iterations*AESAConstants.DEFAULT_BLOCK_SIZE;
			
			if (currentEnd < endResult.length)
				processThis = Arrays.copyOfRange(endResult, readingOffset, currentEnd);
			else
				processThis = Arrays.copyOfRange(endResult, readingOffset, endResult.length);
				
			readingOffset+=AESAConstants.DEFAULT_BLOCK_SIZE;
			_result = applyEngine(true, processThis, passPhrase);
			if (endResult2 != null) 
				endResult2 = Util.concatArray(endResult2, _result);
			else
				endResult2 = _result;		
		}
		
		//_result = applyEngine(true, _result, passPhrase);
		millis = System.currentTimeMillis() - millis;
		log.info("Decrypted phrase is: " + new String(endResult2) + "|->EOL");
		log.info("Decrypt took (ms): " + millis);
	}

	/**
	 * Accessor for {@link AESAEngine#fileAction(String, boolean, String)
	 * fileAction(String, boolean, String)}. <br/>
	 * Sets default file-name and boolean decode to false.
	 * 
	 * @param passPhrase
	 *            "Secret" to use for encryption
	 */
	public void fileEncrypt(String passPhrase, String filename) {
		fileAction(passPhrase, false, filename);

	}

	/**
	 * Accessor for {@link AESAEngine#fileAction(String, boolean, String)
	 * fileAction(String, boolean, String)}.<br/>
	 * Sets default file-name and boolean decode to true.
	 * 
	 * @param passPhrase
	 *            "Secret" to use for encryption
	 */
	public void fileDecrypt(String passPhrase, String filename) {
		log.info("fileDecrypt");
		fileAction(passPhrase, true, filename);
	}

	/**
	 * Main action method. Instantiates FileReader and FileWriter.<br/>
	 * It will read/write in so-called "chunks". See {@link FileReader
	 * FileReader}
	 * 
	 * @param passPhrase
	 *            "Secret" to use for encryption
	 * @param decode
	 *            Whether to decode yes or no
	 * @param fileName
	 *            The base file-name to process
	 */
	private void fileAction(String passPhrase, boolean decode, String fileName) {
		FileReader fh;
		FileWriter fw;
		if (decode) {
			fh = new FileReader(AESAConstants.DEFAULT_BLOCK_SIZE, fileName);
			fw = new FileWriter(AESAConstants.FILE_PREFIX + fileName);
		} else {
			fh = new FileReader(AESAConstants.DEFAULT_BLOCK_SIZE, fileName);
			fw = new FileWriter(AESAConstants.FILE_PREFIX + fileName);
		}

		while (!fh.EOF) {
			_result = fh.nextChunk();
			// do encrypt here
			if (_result != null) {
				_result = applyEngine(decode, _result, passPhrase);
				// write result:
				fw.writeChunk(_result);
				log.debug("writing...");
			}
		}
	}

	/**
	 * Wrapper for TwoFishEngine.processBlock
	 * 
	 * @param decode
	 *            Apply the Engine for decoding true or false. Default = false
	 * @param in
	 *            The byte array that the Engine will perform its work on
	 * @param passPhrase
	 *            "Secret" to use for encryption
	 * @return byte[] The byte array after the Engine performed its magic
	 */
	private byte[] applyEngine(boolean decode, byte[] in, String passPhrase) {
		final int BLOCKSIZE = m_tfe.getBlockSize();
		//int iterations = 0, 
		int offSet = 0;
		byte[] out = AESAHelper.getEmptyByteArray();
		final KeyParameter cp = new KeyParameter(
				AESAHelper.digestGet(passPhrase));
		double millis = 0;

		millis = System.currentTimeMillis();
		m_tfe.init(!decode, cp);
		millis = System.currentTimeMillis() - millis;
		log.debug("Engine init took (ms): " + millis);

		in = Arrays.copyOf(in, BLOCKSIZE);

		// initalize the output array to its correct length:
		out = Arrays.copyOf(out, in.length);

		// for (int i = 0; i < iterations; i++) {
		// offSet = i * BLOCKSIZE;
		m_tfe.processBlock(in, offSet, out, offSet);
		// log.debug("Added a block of size: " + o.length);
		// }
		if (decode) {
			// this means IN is byteArray, OUT should be readable:
			// TODO: understand this
			log.trace("IN :" + Arrays.toString(in));
			log.trace("OUT: " + new String(out));
			int i = out.length;
			while (i-- > 0 && out[i] == 0) {
			}
			byte[] output = new byte[i + 1];
			System.arraycopy(out, 0, output, 0, i + 1);
			return output;
		} else {
			log.debug("IN :" + new String(in));
			log.debug("OUT: " + Arrays.toString(out));
		}
		return out;

	}

	/**
	 * Overloaded accessor
	 * 
	 * @see AESAEngine#applyEngine(boolean, byte[], String)
	 * @param decode
	 *            passed through
	 * @param in
	 *            in this overloaded version the String gets converted to
	 *            byte[], by means of a digest
	 * @param passPhrase
	 *            passed through
	 */
	/*private byte[] applyEngine(boolean decode, String in, String passPhrase) {
		// TODO Change so it will also start iterating on long string in...
		return applyEngine(decode, AESAHelper.digestGet(in, false), passPhrase);

	}*/

}
