/**
 * @Author: Rajiv Kumar
 * @CreatedDate : 30-Apr-2019
 */
package com.sanlark.apps.util;

import java.util.Base64;
import java.util.Random;

public class SecuriyUtil {
	private static final int RANDOM_SEED = 1000;
	private static final String DELIMETER = "-";
	
	public static Long decryptId(String encryptedId) {
		return unpad(Base64.getDecoder().decode(encryptedId));
	}

	public static String encryptId(Long recordId) {
		return Base64.getEncoder().withoutPadding().encodeToString(pad(recordId));
	}

	private static Random random = new Random(RANDOM_SEED);
	
	private static byte[] pad(Long recordId) {
		return (random.nextInt(recordId.intValue()) + DELIMETER + recordId).getBytes();
	}
	
	private static Long unpad(byte[] decryptedBytes) {
		return new Long(new String(decryptedBytes).split(DELIMETER)[1]);
	}
}