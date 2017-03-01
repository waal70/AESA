package org.waal70.utils.security.aesa.crypto;

public class DataLengthException extends RuntimeCryptoException {
	   /**
	 * 
	 */
	private static final long serialVersionUID = 6491741433610339827L;

	/**
     * base constructor.
     */
    public DataLengthException()
    {
    }

    /**
     * create a DataLengthException with the given message.
     *
     * @param message the message to be carried with the exception.
     */
    public DataLengthException(
        String  message)
    {
        super(message);
    }

}
