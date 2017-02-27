package nl.andredewaal.utils.security.aesa;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class FileReader extends FileHandler{
	
	private InputStream is = null;
	private int bytesRead=0;
	private byte[] b = AESAHelper.getEmptyByteArray();
	private byte[] l;
	private double currentOffset = 0;

	public FileReader(){
		this(_blockSize);
	}
	public FileReader(int requestedBlockSize) {
		this(requestedBlockSize, "SetupLog.txt");
	}

	public FileReader(int requestedBlockSize, String fileName){
		super();
		_blockSize = requestedBlockSize;
		_fileName = System.getenv("TEMP") + "\\" + fileName;
		b = Arrays.copyOf(b, _blockSize);
		try {
			is = new FileInputStream(_fileName);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	


	
	public byte[] nextChunk()
	{
		log.debug(_fileName);
		
		//TODO: decide what to read. Let's read in BLOCKSIZE?
		
		try {
			bytesRead = is.read(b, 0, _blockSize);
			//this.
			log.debug("bytesRead: " + bytesRead);
			if (bytesRead == -1){
				//number of bytes read is -1,
				//means EOF.
				log.debug("Encountered an EOF-situation");
				// resize final buffer to exact size:
				//l = Arrays.copyOf(b, bytesRead);
				this.EOF = true;
				return l;
			}
			currentOffset += bytesRead;
			log.debug("currentOffset = " + currentOffset);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return Arrays.copyOf(b,  bytesRead);
		
	}

}
