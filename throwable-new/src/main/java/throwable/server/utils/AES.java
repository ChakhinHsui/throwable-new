package throwable.server.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class AES {
	private static final String algorithm = "AES";
	private static final String password = "DP[xNTaU/XBU0chehX[IXoby2q`^i0r3vP;0s[ZiVf`Rw2XnN8KtJj`ZE4qp]>UH".substring(0, 16);
	private static final String bm = "utf-8";

	/**
	 * 加密
	 * 
	 * @param content
	 *            需要加密的内容
	 * @param password
	 *            加密密码
	 * @return
	 */
	public static String encrypt(String content) {
		try {
			SecretKeySpec key = new SecretKeySpec(password.getBytes(bm), algorithm);
			Cipher cipher = Cipher.getInstance(algorithm);// 创建密码器
			byte[] byteContent = content.getBytes(bm);
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);
			return toHexString(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param content
	 *            待解密内容
	 * @param password
	 *            解密密钥
	 * @return
	 */
	public static String decrypt(String content) {
		try {
			SecretKeySpec key = new SecretKeySpec(password.getBytes(bm), algorithm);
			Cipher cipher = Cipher.getInstance(algorithm);// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(hexStringToByte(content));
			return new String(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String toHexString(byte[] hexByte) {
		try {
			return new String(Hex.encodeHex(hexByte));
		} catch (Exception e) {
			return null;
		}
	}

	public static byte[] hexStringToByte(String hexString) throws DecoderException {
		return Hex.decodeHex(hexString.toCharArray());
	}

	/**
	 * MD5信息
	 * 
	 * @param payload
	 * @return
	 */
	private static byte[] getMd5DigestFormStr(String payload) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(payload.getBytes());
			return messageDigest.digest();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 将字节数组转换为16进制字符串
	 * 
	 * @param b
	 * @return
	 */
	private static String getHexString(byte[] b) {
		String re = "";
		for (byte e : b) {
			re += Integer.toString((e & 0xff) + 0x100, 16).substring(1);
		}
		return re;
	}

	public static String AES128(String ciphertext, String secretKey) throws Exception {
		byte[] tmp = DatatypeConverter.parseBase64Binary(ciphertext);
		String secretStr = getHexString(getMd5DigestFormStr(secretKey));
		String key = secretStr.substring(0, 16);
		String ivs = new StringBuffer(secretStr.substring(0, 16)).reverse().toString();
		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
		SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(ivs.getBytes("UTF-8"));
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
		byte[] plaintext = cipher.doFinal(tmp);
		return new String(plaintext, "UTF-8").trim();
	}

	public static void main(String[] args) {
		String userJson = "3762954549a81b617a26adc27d99426bdbe48675589e2d38b18391847ce3a6c4c1c0bdedb1ca02dfa332eca333b2c0aa47437c84d68b49877a474ae4e537e564dafda7432bde5ce26be2a948aec7750f7e301b23f10ed2a3704128f1761be2b7e4719acc124c2a9a1b6f805641e92728b5437d8285870d69d46eac9d9dd5f5101dba0065eed05348273ed7325910696e17ba113192ae37843a835b54072f687b487dfd1384802e7670278a396efcab815deb22f91359e20e6533682682c70fa2765cd9fa7db347115152424dbdb793f261ff77d9ddfd56682a6464468a92623dbbecbd51001081da671a43d41ecd63d0ac3475542f0aebde6138d3262749b5e141b7d5a2d09d3e721b91aa2b004e164412e946f9fbdd4883a4ae3634a9a279a8335779553b59c55e6fa06904293fab8dab0ab724c139f671b0cd3706c93d97ba7f26f7ea2a7cceedb9a972e12cdbb30856f51f82d4ff26661963e8f038f92dedc502d3bac5f98ed19b293b7f029b0f2e13548a926e8cbc6a0e142fdf60fec7d1";
		try {
			String source = decrypt(userJson);
			System.err.println("解密:" + source);
			String encodeSource = encrypt(source);
			System.err.println("加密:" + encodeSource);
			System.err.println("加密和原始文件是否匹配:" + userJson.equals(encodeSource));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}
}
