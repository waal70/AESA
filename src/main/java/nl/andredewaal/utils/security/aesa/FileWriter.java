package nl.andredewaal.utils.security.aesa;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileWriter extends FileHandler{


	public FileWriter() {
		this("SetupLogEncrypted.txt");
	}
	public FileWriter(String fileName)
	{
		super();
		_fileName = System.getenv("TEMP") + "\\" + fileName;
		try {
			os = new FileOutputStream(_fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private OutputStream os = null;
	public void writeChunk(byte[] _result) {
	
		try {
			os.write(_result);
		} catch (IOException e) {
			log.error("IOException!" + e.getLocalizedMessage());
		}
		
		
		
	}
}
