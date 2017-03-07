/**
 * 
 */
package org.waal70.utils.security.aesa;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

/**
 * @author awaal
 *
 */
class AESAInputHelper {
	
	private String pDefaultResponse = "SetupLog.txt";
	private char pDefaultResponseChar = "e".charAt(0);
	private static Logger log = Logger.getLogger(AESAInputHelper.class);
	
	public String getInputStringFromConsole(String pstrPrompt)
	{
		log.debug("Console String input requested");
		String s;
	       BufferedReader br = new BufferedReader(new InputStreamReader(System.in,AESAConstants.ENCODING));
	        System.out.print(pstrPrompt);
	        try {
	        		s = br.readLine();
	        		log.debug("User entered s:" + s);
	        	} 
	        catch (IOException e) {
	        	log.error("IOException! " + e.getLocalizedMessage());
	        	log.error("Using default as response: " + pDefaultResponse);
				s = pDefaultResponse;
			}
		return s;
	}
	public char getCharFromConsole(String pstrPrompt)
	{
		log.debug("Console char input requested");
		char s;
	       BufferedReader br = new BufferedReader(new InputStreamReader(System.in, AESAConstants.ENCODING));
	        System.out.print(pstrPrompt);
	        try {
	        		s = (char)br.read();
	        		log.debug("User entered s:" + s);
	        	} 
	        catch (IOException e) {
	        	log.error("IOException! " + e.getLocalizedMessage());
	        	log.error("Using default as response: " + pDefaultResponseChar);
				s = pDefaultResponseChar;
			}
		return s;
	}

}
