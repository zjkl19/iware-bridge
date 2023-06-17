package com.iware.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MD5Encrypt
{
	private static final Logger LOG = LoggerFactory.getLogger(MD5Encrypt.class);

	private static char[] hexDigits = {'0', '1', '2', '3',
		 '4', '5', '6', '7',
		 '8', '9', 'a', 'b',
		 'c', 'd', 'e', 'f'};

	public static String MD5(String s)
	{

	     try {
	       byte[] strTemp = s.getBytes();
	       MessageDigest mdTemp = MessageDigest.getInstance("MD5");
	       mdTemp.update(strTemp);
	       byte[] md = mdTemp.digest();
	       int j = md.length;
	       char[] str = new char[j * 2];
	       int k = 0;
	       for (int i = 0; i < j; i++)
	       {
	         byte byte0 = md[i];
	         str[k++] = hexDigits[byte0 >>> 4 & 0xf];
	         str[k++] = hexDigits[byte0 & 0xf];
	       }
	         return new String(str);
       }
       catch (Exception e)
       {
         return null;
       }
	}
	//add by linxi 从AgentService移植过来，为Unicode版的md5加密，上面的为本地编码的md5加密
	public static String MD5(byte[] source) {
		byte[] te = new byte[source.length * 2];

		for (int i = 0; i < source.length; i++) {
			te[i * 2] = source[i];
			te[i * 2 + 1] = 0x00;
		}
		source = te;
		String s = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(source);
			byte[] tmp = md.digest(); // MD5 的计算结果是一个 128 位的长整数，
			// 用字节表示就是 16 个字节
			char[] str = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
			// 所以表示成 16 进制需要 32 个字符
			int k = 0; // 表示转换结果中对应的字符位置
			for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
				// 转换成 16 进制字符的转换
				byte byte0 = tmp[i]; // 取第 i 个字节
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
				// >>> 为逻辑右移，将符号位一起右移
				str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
			}
			s = new String(str); // 换后的结果转换为字符串

		} catch (Exception e) {
			LOG.error("context:{}", e.getMessage());
		}
		return s;
	}


	public static String toHexString(byte[] b) {
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(hexDigits[(b[i] & 0xf0) >>> 4]);
			sb.append(hexDigits[b[i] & 0x0f]);
		}
		return sb.toString();
	}

}
