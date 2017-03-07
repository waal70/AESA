package org.waal70.utils.security.aesa;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public final class AESAConstants {
	public static final int DEFAULT_BLOCK_SIZE = 16;
	public static final String FILE_PREFIX = "enc";
	//awaal 07-03-2017: introduced this new constant for toString()-encoding
	static Charset ENCODING = StandardCharsets.UTF_8;
	

}
