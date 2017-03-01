package org.waal70.utils.security.aesa;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.log4j.Logger;

/**
 * Helper class for the AESA project.<br/>
 * Contains functionality to digest Strings into byte[]
 * @author awaal
 *
 */
public final class AESAHelper {
	
	private static MessageDigest m_sha;
	private static final Logger log = Logger.getLogger(AESAHelper.class);
	private static final byte[] emptyByteArray = new byte[0];

	private AESAHelper() {
		//Helper class, so should never be instantiated...
	}
	
	public static byte[] digestGet(String input) {
		return digestGet(input, true);
	}
	/**
	 * Combines a digest with a GetBytes(). <br/>
	 * Encoding as defined in properties-file <br/>
	 * Digest as defined in properties-file (SHA-1 or SHA-256)
	 * @param input The string to convert into bytes and, if doDigest is true, to also digest
	 * @param doDigest Whether or not to apply a digest to the input
	 * @return The processed input. Minimally a byte[], maximally also digested
	 */
	public static byte[] digestGet(String input, boolean doDigest) {
		byte[] resultArray = null;
		if (input == null || input.length() == 0) {
			// No valid input to digest
			log.fatal(Messages.getString("Fatal.DigestGet"));
			return resultArray;
		}
		log.debug("Parameter check OK. Going to apply the following digest: "
				+ Messages.getString("Digest.default"));
		try {
			m_sha = MessageDigest.getInstance(Messages
					.getString("Digest.default"));
		} catch (NoSuchAlgorithmException e) {
			log.error(Messages.getString("Error.NoSuchAlgorithmException")
					+ e.getLocalizedMessage());
			log.error(Messages.getString("Error.DigestGet"));
			return resultArray;
		}

		try {
			resultArray = input
					.getBytes(Messages.getString("Encoding.default"));
		} catch (UnsupportedEncodingException e) {
			log.error(Messages.getString("Error.UnsupportedEncodingException")
					+ e.getLocalizedMessage());
			log.error(Messages.getString("Error.DigestGet"));
			return resultArray;
		}
		if (doDigest) {
			resultArray = m_sha.digest(resultArray);
		}

		log.trace("About to return byte-array: " + Arrays.toString(resultArray));

		return resultArray;

	}

	/**
	 * Helper method to initalize and return an empty byte array
	 * @return
	 */
	public static byte[] getEmptyByteArray() {
		
		return emptyByteArray;
	}


}
