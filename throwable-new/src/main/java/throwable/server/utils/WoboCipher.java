package throwable.server.utils;

import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import throwable.server.enums.CipherType;

public class WoboCipher {
	final CipherType cipherType;
	final byte[] key;
	
	public static WoboCipher newInstance(CipherType type, String key) {
		return new WoboCipher(type, key);
	}
	
	private WoboCipher(CipherType type, String key) {
		this.cipherType = type;
		this.key = key == null ? null : key.getBytes();
		if (key == null || key.length() < 16) {
			throw new IllegalArgumentException("key is not null and key's length must be greater than or equal 16");
		}
	}
	
	public byte[] decrypt(byte[] bs) throws Exception {
		return decrypt(cipherType, key, bs);
	}
	
	public byte[] encrypt(byte[] bs) throws Exception {
		return encrypt(cipherType, key, bs);
	}
	
	private static byte[] encrypt(CipherType type, byte[] key, byte[] bs) throws Exception {
		switch (type) {
		case AES_C:
			return encryptAesEcbNopadding(key, bs);
		case AES_C_LOGIN:
			return convert(encryptAesEcbNopadding(key, bs));
		case AES_CBC_PKCS5PADDING:
			return encryptAesCbcPKCS5Padding(key, Arrays.copyOfRange(key, 0, 16), bs);
		default:
			throw new Exception("加密类型错误！" + type);
		}
	}
	
	private static byte[] decrypt(CipherType type, byte[] key, byte[] bs) throws Exception {
		switch (type) {
		case AES_C:
			return decryptAesEcbNopadding(key, bs);
		case AES_C_LOGIN:
			return decryptAesEcbNopadding(key, convert(bs));
		case AES_CBC_PKCS5PADDING:
			return decryptAesCbcPKCS5Padding(key, Arrays.copyOfRange(key, 0, 16), bs);
		default:
			throw new Exception("解密类型错误！" + type);
		}
	}
	
	/**
	 * 解密 AES 算法 ECB NoPadding 模式
	 */
	private static byte[] decryptAesEcbNopadding(byte[] key, byte[] bs) throws Exception {
    	SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
    	Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");//"算法/模式/补码方式"
    	cipher.init(Cipher.DECRYPT_MODE, skeySpec);
    	return cipher.doFinal(bs);
	}
	
	/**
	 * AES 加密  ECB NoPadding 模式 
	 */
	private static byte[] encryptAesEcbNopadding(byte[] key, byte[] bs) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		return cipher.doFinal(bs);
	}
	
	/**
	 * 解密 AES 算法 CBC PKCS5Padding 模式
	 */
	private static byte[] decryptAesCbcPKCS5Padding(byte[] key, byte[] iv, byte[] bs) throws Exception {
    	SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
    	Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//"算法/模式/补码方式"
    	IvParameterSpec ips = new IvParameterSpec(iv);
    	cipher.init(Cipher.DECRYPT_MODE, skeySpec, ips);
    	return cipher.doFinal(bs);
	}
	
	/**
	 * AES 加密  CBC PKCS5Padding 模式 
	 */
	private static byte[] encryptAesCbcPKCS5Padding(byte[] key, byte[] iv, byte[] bs) throws Exception {
		
		SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec ips = new IvParameterSpec(iv);
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ips);
		return cipher.doFinal(bs);
	}
	
	private static byte[] convert(byte[] bs) {
		int half = bs.length / 2;
		for (int i = 0; i < half; i+= 2) {
			swap(bs, i, i + half);
		}
		return bs;
	}
	
	private static void swap(byte[] bs, int index1, int index2) {
		byte b = bs[index1];
		bs[index1] = bs[index2];
		bs[index2] = b;
	}
}
