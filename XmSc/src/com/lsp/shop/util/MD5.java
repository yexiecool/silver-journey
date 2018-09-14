package com.lsp.shop.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes(charsetname)));
		} catch (Exception exception) {
		}
		return resultString;
	}

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	
	
	/**
     * MD5算法
     * <p>
     * 实现对密码的加密
     * 
     * @param original String 密码明文
     * @return String 加密后的密文
     * @see [类、类#方法、类#成员]
     */
    public static String md5(String original)
    {
        // 16进制排列
        char hexDigits[] =
        { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        
        // 获取明文的byte值
        byte[] strTemp = original.getBytes();

        // 进行MD5加密
        MessageDigest mdTemp = null;
        try
        {
            mdTemp = MessageDigest.getInstance("MD5");
        } 
        catch (NoSuchAlgorithmException e)
        {
            
        }
        String st = "";
        char str[]= null;
        if(mdTemp != null){
        mdTemp.update(strTemp);

        byte[] md = mdTemp.digest();

        str = new char[md.length * 2];

        for (int i = 0, k = 0; i < md.length; i++)
        {
            byte byte0 = md[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
            st = new String(str);
        }
        return st;
    }
    
    /**
  	 * 获取MD5加密值
  	 * 
  	 * @param plainText
  	 * @return
  	 */
  	public static String getMd5Str(String plainText) {
  		String re_md5 = new String();
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
  			re_md5 = buf.toString();
  		} catch (NoSuchAlgorithmException e) {
  			e.printStackTrace();
  		}
  		return re_md5;
  	}


}
