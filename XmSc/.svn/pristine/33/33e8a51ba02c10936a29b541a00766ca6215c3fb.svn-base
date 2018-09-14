package com.lsp.shop.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * The Class IpUtils.
 *
 */
public abstract class IpUtils {
	
 

	 public static String httpGet(String url) {
		 
	        StringBuffer buffer = new StringBuffer();
	 
	        try {
	 
	            URLConnection conn = new URL(url).openConnection();
	             
	            conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
	 
	            try (InputStream inputStream = conn.getInputStream();
	                    InputStreamReader streamReader = new InputStreamReader(inputStream);
	                    BufferedReader reader = new BufferedReader(streamReader);) {
	 
	                String line = null;
	 
	                while ((line = reader.readLine()) != null) {
	                    buffer.append(line).append(System.lineSeparator());
	                }
	            }
	 
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 
	        return buffer.toString();
	    }
	 //获取外网ip
	 public static String  getinterip() {
		    String stringhtml = httpGet("https://www.baidu.com/s?wd=ip");
         
	        // 提出IP  <span\sclass="c-gap-right">本机IP:&nbsp;([^<]+)</span>
		    
		    String ip="";
	        Pattern pattern = Pattern.compile("<span\\sclass=\"c-gap-right\">本机IP:&nbsp;([^<]+)</span>");
	        Matcher matcher = pattern.matcher(stringhtml);
	    
	        if (matcher.find()) {
	              ip = matcher.group(1);
	        }
	        if(ip==null || ip.equals("")) {
	        	ip =CustomSystemUtil.INTERNET_IP;
	        }
	        
	        System.out.println("-----------获取到的用户IP为："+ip);
	        return ip;
	 }
	 
	  /*** 
	     * 获取客户端IP地址;这里通过了Nginx获取;X-Real-IP, 
	     * @param request 
	     * @return 
	     */  
	    public static String getClientIP(HttpServletRequest request) {  
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
	        System.out.println("App Client IP: "+ip+", fromSource: "+fromSource);  
	        return ip;  
	    }  
	    
	    
	 
	    public static void main(String[] args) {
	 
	        String html  =getinterip();
	        System.out.println(html);
	         
	    }
	
	
 
}