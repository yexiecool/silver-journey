package com.lsp.shop.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

 




import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.lsp.pub.util.MyX509TrustManager;
import com.lsp.pub.util.Struts2Utils;
import com.lsp.shop.util.AccessToken;

 

/**
 * 通用工具类
 * 
 * @author zhupan
 * @date 2018-08-24
 */
public class WXCommonUtil {
	private static Logger log = Logger.getLogger(WXCommonUtil.class);
	// 凭证获取access_token（GET）
	public final static String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	//创建菜单
	public final static String createmenu_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	
	
	
	
	public final static String AppID= "wx521568b6b7d32768";
	public final static String AppSecret="e87f9f8d3e2103ff15d210905cd32342";
	
	public final static String mchID = "1509032041";
	//-----wxtoken 需要更换
	public final static String partnerkey  ="1qaz2wsx3edc4rfv5tgb6yhn7ujm8ik9";
	public final static String template_id="wlK7fJqPH2mvoGHmDIi2GSWsE7228uvqt85ARJ0Bj8E";  //模版编号
				
	public final static String wxpwd = "SX81N0S5XH"; //平台接口密钥
	/**
	 * 发送https请求
	 * 
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(ssf);
			
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			conn.setRequestMethod(requestMethod);

			// 当outputStr不为null时向输出流写数据
			if (null != outputStr) {
				OutputStream outputStream = conn.getOutputStream();
				// 注意编码格式
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 从输入流读取返回内容
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			conn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
		} catch (Exception e) {
		}
		return jsonObject;
	}

	/**
	 * 获取接口访问凭证
	 * 
	 * @param appid 凭证
	 * @param appsecret 密钥
	 * @return
	 */

	public static AccessToken  getAccessToken(String appid, String appsecret)
	{
		AccessToken accessToken = null;
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET".replace("APPID", appid).replace("APPSECRET", appsecret);
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
		if (jsonObject != null)
			try
			{
			    System.out.println(jsonObject.toString());
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
			}
			catch (JSONException e)
			{
				accessToken = null;
				log.info ("获取token失败 errcode:{} errmsg:{}"+"--"+ Integer.valueOf(jsonObject.getInt("errcode"))+"--"+ jsonObject.getString("errmsg"));
			}
		return accessToken;
	}
	
 
	 
    
	
	
	/**
	 * 创建自定义菜单
	 */
	public static boolean CreateMenu(String accessToken,String param)
	{
		boolean rst =false;
		String url="https://api.weixin.qq.com/cgi-bin/menu/get?access_token="+ accessToken;  
	    JSONObject jsonObject = httpsRequest(url, "GET", null);
	    String jsonStr =jsonObject.toString() ;
	    return rst;	  
	 }
	
	
	 /**
     * 查询菜单
     */
    public static String getMenuInfo(String accessToken) throws Exception {  
        String url="https://api.weixin.qq.com/cgi-bin/menu/get?access_token=" + accessToken;  
    	JSONObject jsonObject = httpsRequest(url, "GET", null);
    	String jsonStr =jsonObject.toString() ;
        return jsonStr;  
    } 
    
    /** 
     * 删除菜单 
     */  
    public static String deleteMenuInfo(String accessToken) throws Exception {  
        String url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=" + accessToken;  
        JSONObject jsonObject = httpsRequest(url, "GET", null);
    	String jsonStr =jsonObject.toString() ;
        return jsonStr;  
    }  
    
    
    /**
     * 获取用户openid
     */
    public static String GetOpenID(String code){
      //获取用户openid
        String openId ="";
        String URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+AppID+"&secret="+AppSecret+"&code="+code+"&grant_type=authorization_code";
        
        JSONObject jsonObject = httpsRequest(URL, "GET", null);
        if (null != jsonObject) {
            openId = jsonObject.getString("openid");
        }
        log.info("GetOpenID --:"+openId);
        return openId;
    }
    
    
    /**
     * 获取用户openid
     */
    public static JSONObject GetWXUser(String openid,String access_token){
        
        String URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+access_token+"&openid="+openid+"&lang=zh_CN";
        int subscribe =0;  //0 标识没有关注 1 标识已关注
        JSONObject jsonObject = httpsRequest(URL, "GET", null);
        System.out.println("获取用户信息:"+jsonObject.toString());
        return jsonObject ;
    }
    
    /**
     * 根据用户openid 获取用户的信息 ，此处判断微信是否关注该公众号 
     */
    
    public static boolean  IsFocusOn(String openid,String access_token){
        boolean ret =false;
        String URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+access_token+"&openid="+openid+"&lang=zh_CN";
        int subscribe =0;  //0 标识没有关注 1 标识已关注
        JSONObject jsonObject = httpsRequest(URL, "GET", null);
        System.out.println("获取用户信息:"+jsonObject.toString());
        log.info("获取用户信息:"+jsonObject.toString());
        if (null != jsonObject) {
             subscribe  = jsonObject.getInt("subscribe");
        }
        if(subscribe==1){ //拉去结果已关注
            ret =true;
        }
        return ret;
    }
    
    
    
    
    /**
     * 获取jsapi_ticket
     */
    
    public static String getJsAPI_Ticket(String accessToken) throws Exception { 
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken+"&type=jsapi";  
        JSONObject jsonObject = httpsRequest(url, "GET", null);
    	String jsonStr =jsonObject.getString("ticket") ;
        return jsonStr;  
    }
    
	/**
	 * URL编码（utf-8）
	 * 
	 * @param source
	 * @return
	 */
	public static String urlEncodeUTF8(String source) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 根据内容类型判断文件扩展名
	 * 
	 * @param contentType 内容类型
	 * @return
	 */
	public static String getFileExt(String contentType) {
		String fileExt = "";
		if ("image/jpeg".equals(contentType))
			fileExt = ".jpg";
		else if ("audio/mpeg".equals(contentType))
			fileExt = ".mp3";
		else if ("audio/amr".equals(contentType))
			fileExt = ".amr";
		else if ("video/mp4".equals(contentType))
			fileExt = ".mp4";
		else if ("video/mpeg4".equals(contentType))
			fileExt = ".mp4";
		return fileExt;
	}
	
	/**
     * 获取一定长度的随机字符串
     * @param length 指定字符串长度
     * @return 一定长度的字符串
     */
    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    
    public static  String  sign(String jsapi_ticket, String url,String nonce_str,String timestamp ) {
        
        
        String str;
        String signature = "";
 
        //注意这里参数名必须全部小写，且必须有序
        str = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + nonce_str +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
 
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(str.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
 
        
 
        return signature;
    }
 
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
    
    
    public   String getClientIP(HttpServletRequest request) {  
        String fromSource = "X-Real-IP";  
        String ip = request.getHeader("X-Real-IP");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("X-Forwarded-For");  
            fromSource = "X-Forwarded-For";  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
            fromSource = "Proxy-Client-IP";  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
            fromSource = "WL-Proxy-Client-IP";  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
            fromSource = "request.getRemoteAddr";  
        }   
        return ip;  
    } 
    
    public static String getIpAddr(HttpServletRequest request) { 
        String ip = request.getHeader("x-forwarded-for"); 
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("Proxy-Client-IP"); 
        } 
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("WL-Proxy-Client-IP"); 
        } 
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getRemoteAddr(); 
        } 
        return ip; 
    }
    
    
    public static String getIpAddr2(HttpServletRequest request){  
        String ipAddress = request.getHeader("x-forwarded-for");  
            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
                ipAddress = request.getHeader("Proxy-Client-IP");  
            }  
            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
                ipAddress = request.getHeader("WL-Proxy-Client-IP");  
            }  
            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
                ipAddress = request.getRemoteAddr();  
                if(ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")){  
                    //根据网卡取本机配置的IP  
                    InetAddress inet=null;  
                    try {  
                        inet = InetAddress.getLocalHost();  
                    } catch (UnknownHostException e) {  
                        e.printStackTrace();  
                    }  
                    ipAddress= inet.getHostAddress();  
                }  
            }  
            //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割  
            if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15  
                if(ipAddress.indexOf(",")>0){  
                    ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));  
                }  
            }  
            return ipAddress;   
    }
    
    public static void main(String[] args)
    {
    	 
    }
}