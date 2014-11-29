package throwable.server.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import throwable.server.enums.CipherType;

public class Security {

	private static Log log = Logs.get();
	//private static String encryptKey2 = UserConf.appProp.get("cas.serverAuth", "wobokey3N-bXvmt-E94kald+cfxPsU/t");
	private static String encryptKeyT = "wobokey3N-bXvmt-E94kald+cfxPsU/t";


	public static String encryptContent(String content, PropertiesProxy appProp) {
		String encryptContent = null;
		String encryptKey = (null==appProp)?encryptKeyT:appProp.get("cas.serverAuth", "wobokey3N-bXvmt-E94kald+cfxPsU/t");
		try {
			// 转换为byte数组
			byte[] bs = content.getBytes("GBK");
			// AES_CBC 加密
			byte[] bb = WoboCipher.newInstance(CipherType.AES_CBC_PKCS5PADDING,
					encryptKey).encrypt(bs);

			// Base64 加密
			encryptContent = new String(Base64.encodeBase64(bb));

		} catch (Exception e) {
			log.error("encrypt content error:"
					+ e.getMessage());
		}

		return encryptContent;
	}
	
	public static String encryptContent(String content) {
		String encryptContent = null;
		String encryptKey = encryptKeyT;
		try {
			// 转换为byte数组
			byte[] bs = content.getBytes("GBK");
			// AES_CBC 加密
			byte[] bb = WoboCipher.newInstance(CipherType.AES_CBC_PKCS5PADDING,
					encryptKey).encrypt(bs);

			// Base64 加密
			encryptContent = new String(Base64.encodeBase64(bb));

		} catch (Exception e) {
			log.error("encrypt content error:"
					+ e.getMessage());
		}

		return encryptContent;
	}

	public static String decryptContent(String content, PropertiesProxy appProp) {
		String decryptContent = null;
		String encryptKey = (null==appProp)?encryptKeyT:appProp.get("cas.serverAuth", "wobokey3N-bXvmt-E94kald+cfxPsU/t");
		try {
			// 转换为byte数组
			byte[] bs = content.getBytes("GBK");

			// Base64 解码
			bs = Base64.decodeBase64(bs);

			// AES_CBC 解码
			byte[] de = WoboCipher.newInstance(CipherType.AES_CBC_PKCS5PADDING,
					encryptKey).decrypt(bs);

			decryptContent = new String(de);
		} catch (Exception e) {
			log.error("decrypt content error:"
					+ e.getMessage());
		}
		return decryptContent;
	}

	public static String md5(String plainText) {
		String str = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			str = buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public static boolean checkMD5(String content,String md5){
		return md5.equals(md5(content));
	}
}
