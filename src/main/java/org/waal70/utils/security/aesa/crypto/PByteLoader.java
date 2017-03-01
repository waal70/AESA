package org.waal70.utils.security.aesa.crypto;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.prefs.Preferences;

import org.apache.log4j.Logger;
import org.waal70.utils.security.aesa.preference.FilePreferenceFactory;

/**
 * @author awaal
 * This class populates the P[][] array for TwoFishEngine
 * Based on a textfile
 */
public final class PByteLoader {
	private static Logger log = Logger.getLogger(PByteLoader.class);
	private static InputStream fis;
	private static File _file;
	private static String fileName = "ExampleKeyFile.txt";
	private static Preferences p = Preferences.userNodeForPackage(FilePreferenceFactory.class);
	
	
	/**
	 * Private constructor, we do not appreciate instances of this class
	 */
	private PByteLoader() {
	}
	
	private static void initialize()
	{
		
		//What to do?
		// 1. Check file exists
		// 2. Check file holds enough bytes
		// 3. Check readable
		//fileName = p.get("keyfile", fileName);
		
		fileName = p.get("keyfile", System.getenv("TEMP") + File.separator + fileName);
		log.debug("fileName = " + fileName);
		
		_file = new File(fileName);
		if (!_file.exists() ) {
			log.fatal("Keyfile not found!");
		}
		log.debug("total space = " + _file.length() + " bytes");
		try {
			fis = new FileInputStream(_file);
		} catch (FileNotFoundException e) {
			log.fatal(fileName + " does not exist?" + e.getLocalizedMessage());
		}
		
	}
	
	private static int checkFile(int numberOfBytes, int iterations){
		long requiredSize = 512;
		
		if (_file.canWrite()){
			log.warn(_file.getName() + " is NOT READONLY. Do you understand the danger?");
		}
		
		if (_file.length() > requiredSize){
			//OK to start reading as planned.
			//If not, check whether it will at least take one iteration
			return iterations;
		}
		else
		{
			log.fatal(_file.getName() + " is NOT the required size. Required = "+ requiredSize + ", found: " + _file.length());
			return -1;
		}
	}
	
	private static byte[] getByteChunk(int numberOfBytes, int iterations){
		log.debug("iterations incoming: " + iterations);
		iterations = checkFile(numberOfBytes, iterations);
		if (iterations == -1)
		{
			//This means the selected file does not hold enough bytes to generate bytechunks
			log.warn("Not enough Bytechunks available. Aborting execution.");
			System.exit(1);
		}
		byte[] buffer = new byte[numberOfBytes];
		int bytesRead = 0;
		//advance iterations in the inputstream:
		log.debug("iterations: " +iterations);
		for (int i=0;i<iterations;i++)
		{
			try {
				bytesRead += fis.read();
				log.info("I have skipped to further into file (byte=" + bytesRead + "), because iterations = " + iterations);
			} catch (IOException e) {
				log.error("IOException " + e.getLocalizedMessage());
			}
		}
		try {
			while ((bytesRead = fis.read(buffer)) != -1) 
			{
				if (bytesRead == numberOfBytes)
					{
					//Condition where number of bytes is exactly right
					log.debug("Achieved number of bytes read!");
					return buffer;
					}
			}
		} catch (IOException e) {
			log.fatal("IOException reading file " + fileName);
		}
		
		return null;
		
	}

	public static byte[][] getDualByteChunk(int i, int j) {
		initialize();
		//log.info("get dual byte chunk");
		byte[] part1 = new byte[256];
		byte[] part2 = new byte[256];
		part1=getByteChunk(256, 0);
		part2=getByteChunk(256, 1);
		byte total[][] = {part1,part2};
		return total;
		
	}
	

}
 