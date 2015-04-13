package com.example.myfirstapp;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.*;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;

import android.R.string;
import android.util.Log;

public class HttpClientFYP {
	
	private URL url;
    private HttpURLConnection httpcon;
    public static String cookie;
    protected boolean flag;
    
    public void securePageGrabber() 
    {
        flag = false;
        cookie = null;
    }
    public void setURL(URL url)
    {
        this.url = url;
    }
    /*public void setCookie(String c)
    {
        this.cookie = c;
    }*/
	public void setURL(String s)
    {
        try
        {
            this.url = new URL(s);
        }
        catch (Exception e){}
    }
	
	private String httpconnect(String data) throws Exception
    {
	    String str="1";
        try
        {
            //int s = data.length();
            httpcon = (HttpURLConnection)url.openConnection();
            httpcon.setDoInput(true);
            httpcon.setDoOutput(true);
            httpcon.setRequestMethod("POST");
            httpcon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8" );
            httpcon.setRequestProperty("Content-Length", String.valueOf(data.length()));
            httpcon.setRequestProperty("Connection", "keep-alive");
            httpcon.setRequestProperty("Accept-Language", "zh-TW,zh;q=0.8,en-US;q=0.6,en;q=0.4");
            httpcon.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
            
            
            httpcon.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
            if (null != cookie)
            {
                httpcon.setRequestProperty("Cookie",cookie);   
                /*httpcon.setRequestProperty("Referer", "https://rbs.lib.cuhk.edu.hk/Booking/Secure/FacilityStatusM.aspx");
                httpcon.setRequestProperty("Origin", "https://rbs.lib.cuhk.edu.hk");
                httpcon.setRequestProperty("Host","rbs.lib.cuhk.edu.hk");*/
            }
            /*else
            {
                httpcon.setRequestProperty("Referer", "https://rbs.lib.cuhk.edu.hk/Booking/LoginM.aspx");
                httpcon.setRequestProperty("Origin", "https://rbs.lib.cuhk.edu.hk");
                httpcon.setRequestProperty("Host","rbs.lib.cuhk.edu.hk");
            }*/
                     
            httpcon.setInstanceFollowRedirects(false);
            httpcon.connect(); 
            DataOutputStream out = new DataOutputStream(httpcon.getOutputStream());     
            out.writeBytes(data);
            out.flush();
            out.close();
        }
        catch (Exception e) {
        	e.printStackTrace();
        	throw e;
        } 
        return str;
    }
	public String grab(String data)
    {

        String str = "";
        String line;
        try 
        {
            httpconnect(data);
            //int a = httpcon.getResponseCode();
            if (null == cookie)
            {
                Map<String, List<String>> map = httpcon.getHeaderFields();
                for (String key : map.keySet()) 
                    if (key != null && key.equals("Set-Cookie")) 
                    { 
                        List<String> list = map.get(key); 
                        StringBuilder builder = new StringBuilder(); 
                        for (String s : list) 
                            builder.append(s).toString();  
                       int tmp = builder.toString().indexOf(';');
                       cookie = builder.toString().substring(0, tmp + 1); 
                    }
            }
            BufferedReader outStrm = new BufferedReader(new InputStreamReader(new GZIPInputStream(httpcon.getInputStream())));
            while ((line = outStrm.readLine()) != null)
                str = str + line ; 
      
        }
        catch (Exception e) 
        { 
            System.out.println(e);
            ByteArrayOutputStream a = new ByteArrayOutputStream();
            PrintStream b = new PrintStream(a);
            e.printStackTrace(b);
            b.close();
            String r = a.toString();
            try {
				a.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				r = e1.toString();
			}
        	return r;
        }
        
        
        Log.i("zjl", str);
        return str;
    }

}


