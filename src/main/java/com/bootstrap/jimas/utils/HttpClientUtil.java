package com.bootstrap.jimas.utils;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * @ClassName: HttpClientUtil
 * @Description: post请求工具类
 * @author Zivs.Zheng
 * @date 2015年10月9日 下午5:56:13
 * @version V0.0.1
 */
public class HttpClientUtil {
    
    private final static Logger Postlogger = Logger.getLogger(HttpClientUtil.class);
    
    /**
     * @Title: HttpClientUtil.java
     * @Description: post请求[contentType=application/json]
     * @param url 请求路径
     * @param stringJsonPrama json格式字符串参数
     * @param encode 字符编码
     * @return
     * @throws
     */
    public static String doJsonPost(String url,String stringJsonPrama,String encode){
        return doPost(url, stringJsonPrama, "application/json", encode);
    }

	public static String doPost(String url, String stringJsonPrama, String contentType, String encode) {
		HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        if (null == stringJsonPrama || null == url || null == encode) {
            return "Parameters error";
        }
        try{
            if (null == httpClient)
                httpClient = new SSLClient();
            if (null == httpPost)
                httpPost = new HttpPost(url);
            
            //设置参数  
            StringEntity sen = new StringEntity(stringJsonPrama, encode);
            sen.setContentEncoding(encode);
            sen.setContentType(contentType);
            httpPost.setEntity(sen);
            HttpResponse response = httpClient.execute(httpPost);
            if(response != null){
                try {
                    HttpEntity resEntity = response.getEntity(); 
                    if(resEntity != null){  
                        result = EntityUtils.toString(resEntity,encode);
                    }
                }catch(Exception  e){
                    Postlogger.error("http pos throw Exception",e);
                    e.printStackTrace();
                }
            }  
        } catch (ConnectionPoolTimeoutException e) {
            Postlogger.error("http pos throw ConnectionPoolTimeoutException(wait time out)",e);
        } catch (ConnectTimeoutException e) {
            Postlogger.error("http pos throw ConnectTimeoutException", e);
        } catch (SocketTimeoutException e) {
            Postlogger.error("http pos throw SocketTimeoutException", e);
        } catch (ClientProtocolException e) {
            Postlogger.error("http pos throw ClientProtocolException", e);
        } catch (IOException e) {
            Postlogger.error("http pos throw IOException", e);
        }catch (Exception e) {
            Postlogger.error("http pos throw Exception", e);
        }finally{
        	if(null!=httpClient){
        		httpClient.getConnectionManager().shutdown();
        	}
        }
        return result;
	}  
	
	public static String doPost(String url, Map<String,Object> params, String contentType, String encode) {
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		if (null == params || params.isEmpty() || null == url || null == encode) {
			throw new RuntimeException("Parameters is empty");
		}
		try{
			if (null == httpClient)
				httpClient = new SSLClient();
			if (null == httpPost)
				httpPost = new HttpPost(url);
			
			//设置参数  
			if (params != null && !(params.isEmpty())) {
				List<NameValuePair> values = new ArrayList<NameValuePair>();
				for(String param : params.keySet()){
					Object value = params.get(param);
					BasicNameValuePair pare = new BasicNameValuePair(param, null!=value?value.toString():"");
					values.add(pare);
				}
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(values);
				entity.setContentEncoding(encode);
				entity.setContentType(contentType);
				httpPost.setEntity(entity);
			}
			
			HttpResponse response = httpClient.execute(httpPost);
			if(response != null){
				try {
					HttpEntity resEntity = response.getEntity(); 
					if(resEntity != null){  
						result = EntityUtils.toString(resEntity,encode);
					}
				}catch(Exception  e){
					Postlogger.error("http pos throw Exception",e);
					e.printStackTrace();
				}
			}  
		} catch (ConnectionPoolTimeoutException e) {
			Postlogger.error("http pos throw ConnectionPoolTimeoutException(wait time out)",e);
		} catch (ConnectTimeoutException e) {
			Postlogger.error("http pos throw ConnectTimeoutException", e);
		} catch (SocketTimeoutException e) {
			Postlogger.error("http pos throw SocketTimeoutException", e);
		} catch (ClientProtocolException e) {
			Postlogger.error("http pos throw ClientProtocolException", e);
		} catch (IOException e) {
			Postlogger.error("http pos throw IOException", e);
		}catch (Exception e) {
			Postlogger.error("http pos throw Exception", e);
		}finally{
        	if(null!=httpClient){
        		httpClient.getConnectionManager().shutdown();
        	}
        }
		return result;
	}    
    
    /**
     * @Title: HttpClientUtil.java
     * @Description: get请求
     * @param url 请求路径
     * @param encode 字符编码
     * @return
     * @throws
     */
    public static String doGet(String url,String encode){
        HttpClient httpClient = null;
        HttpGet httpGet = null;
        String result = null;
        if (null == url || null == encode) {
            return "Parameters error";
        }
        try{
            httpClient = new SSLClient();
            httpGet = new HttpGet(url);
            
            HttpResponse response = httpClient.execute(httpGet);
            
            if(response != null){
                try {
                    HttpEntity resEntity = response.getEntity(); 
                    if(resEntity != null){  
                        result = EntityUtils.toString(resEntity,encode);
                    }
                }catch(Exception  e){
                    Postlogger.error("http pos throw Exception",e);
                    e.printStackTrace();
                }
            }  
        } catch (ConnectionPoolTimeoutException e) {
            Postlogger.error("http pos throw ConnectionPoolTimeoutException(wait time out)",e);
        } catch (ConnectTimeoutException e) {
            Postlogger.error("http pos throw ConnectTimeoutException", e);
        } catch (SocketTimeoutException e) {
            Postlogger.error("http pos throw SocketTimeoutException", e);
        } catch (ClientProtocolException e) {
            Postlogger.error("http pos throw ClientProtocolException", e);
        } catch (IOException e) {
            Postlogger.error("http pos throw IOException", e);
        }catch (Exception e) {
            Postlogger.error("http pos throw Exception", e);
        }finally{
        	if(null!=httpClient){
        		httpClient.getConnectionManager().shutdown();
        	}
        }
        return result;
    }  
    
    /**
     * @Title: HttpClientUtil.java
     * @Description: get请求
     * @param url 请求路径
     * @param encode 字符编码
     * @return
     * @throws
     */
    public static String doGet(String url,Map<String,Object> params, String encode){
    	HttpClient httpClient = null;
    	HttpGet httpGet = null;
    	String result = null;
    	if (null == url || null == encode) {
    		return "Parameters error";
    	}
    	try{
    		httpClient = new SSLClient();
    		
			if (params != null && !(params.isEmpty())) {
				List<NameValuePair> values = new ArrayList<NameValuePair>();
				for(String param : params.keySet()){
					Object value = params.get(param);
					BasicNameValuePair pare = new BasicNameValuePair(param, null!=value?value.toString():"");
					values.add(pare);
				}
				String str = URLEncodedUtils.format(values, encode);
				url += ((-1 < url.indexOf("?")?"&":"?") + str);
			}
    		
    		httpGet = new HttpGet(url);
    		
    		HttpResponse response = httpClient.execute(httpGet);
    		
    		if(response != null){
    			try {
    				HttpEntity resEntity = response.getEntity(); 
    				if(resEntity != null){  
    					result = EntityUtils.toString(resEntity,encode);
    				}
    			}catch(Exception  e){
    				Postlogger.error("http pos throw Exception",e);
    				e.printStackTrace();
    			}
    		}  
    	} catch (ConnectionPoolTimeoutException e) {
    		Postlogger.error("http pos throw ConnectionPoolTimeoutException(wait time out)",e);
    	} catch (ConnectTimeoutException e) {
    		Postlogger.error("http pos throw ConnectTimeoutException", e);
    	} catch (SocketTimeoutException e) {
    		Postlogger.error("http pos throw SocketTimeoutException", e);
    	} catch (ClientProtocolException e) {
    		Postlogger.error("http pos throw ClientProtocolException", e);
    	} catch (IOException e) {
    		Postlogger.error("http pos throw IOException", e);
    	}catch (Exception e) {
    		Postlogger.error("http pos throw Exception", e);
    	}finally{
        	if(null!=httpClient){
        		httpClient.getConnectionManager().shutdown();
        	}
        }
    	return result;
    }    
}
