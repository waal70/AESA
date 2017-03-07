package org.waal70.utils.security.aesa.crypto.param;

import org.waal70.utils.security.aesa.crypto.CipherParameters;

public class KeyParameter implements CipherParameters 
{
    private byte[]  key;

    public KeyParameter(
        byte[]  key)
    {
        this(key, 0, key.length);
    }

    public KeyParameter(
        byte[]  key,
        int     keyOff,
        int     keyLen)
    {
        this.key = new byte[keyLen];

        System.arraycopy(key, keyOff, this.key, 0, keyLen);
    }

    public byte[] getKey()
    {
    	//awaal 07-03-2017: changed from
    	// return this.key; into: (findbugs marker)
        return this.key == null ? null : (byte[]) key.clone();
    }
}
