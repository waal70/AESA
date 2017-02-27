package nl.andredewaal.utils.security.aesa.crypto;

public class RuntimeCryptoException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7965449697369160722L;

	/**
     * base constructor.
     */
    public RuntimeCryptoException()
    {
    }

    /**
     * create a RuntimeCryptoException with the given message.
     *
     * @param message the message to be carried with the exception.
     */
    public RuntimeCryptoException(
        String  message)
    {
        super(message);
    }

}
