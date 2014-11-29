package throwable.server.enums;
/**
 * 加密用的enum
 * @author WaterHsu@xiu8.com
 * @version 2014年8月19日
 */
public enum CipherType {
	/** C 使用的AES 加密 */
	AES_C,
	/** C 使用的 AES 客户端 mcheck 校验加密 */
	AES_C_LOGIN,
	AES_CBC_PKCS5PADDING
}
