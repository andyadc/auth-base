package com.adc.idea.common.utils.web;

import java.io.InputStream;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 带证书的HttpClient客户端
 */
public class HttpClientUtil {
	private static final transient Log logger = LogFactory.getLog(HttpClientUtil.class);

	private CloseableHttpClient httpClient = HttpClients.createDefault();
	private RequestConfig requestConfig = null;
	private int statusCode = 0;
	private String charset = "UTF8";

	public static void main(String[] args) {
		Map<String, String> ret = new HttpClientUtil().doPost("https://127.0.0.1:8443/action_controller",
				"{\"a1\":1232.232,\"a2\":\"xxxx\",\"a3\":32}");
		System.out.println(ret);
	}

	/**
	 * 创建默认的HttpClient客户端
	 */
	public HttpClientUtil() {
	}

	/**
	 * 创建带证书的HttpClient客户端
	 * 
	 * @param KEYstream
	 *            证书流
	 * @param type
	 *            证书类型
	 * @param password
	 *            证书密码
	 */
	public HttpClientUtil(InputStream cerStream, String type, String password) {
		try {
			KeyStore keyStore = KeyStore.getInstance(type);
			keyStore.load(cerStream, password.toCharArray());
			SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, password.toCharArray()).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" },
					null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			this.httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (Exception e) {
			logger.error("证书加载错误");
		}
	}

	/**
	 * HttpClient的POST请求
	 * 
	 * @param url
	 *            请求的地址
	 * @param params
	 *            请求的键值参数
	 * @return 把地址返回的JSON转为Map
	 */
	public Map<String, String> doPost(String url, Map<String, String> params) {
		String JSONstr = doPost(url, params, this.charset);
		return fromJSON(JSONstr);
	}

	/**
	 * HttpClient的POST请求
	 * 
	 * @param url
	 *            请求的地址
	 * @param JSON
	 *            请求的参数为JSON字符串
	 * @return 把地址返回的JSON转为Map
	 */
	public Map<String, String> doPost(String url, String JSON) {
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(EntityBuilder.create().setText(JSON).build());
		return fromJSON(post(httpPost));
	}

	/**
	 * HttpClient的POST请求
	 * 
	 * @param url
	 *            请求的地址
	 * @param binary
	 *            请求的参数为字节数据,会以流传到url
	 * @return 把地址返回的JSON转为Map
	 */
	public Map<String, String> doPost(String url, byte[] binary) {
		String JSONstr = doPost(url, binary, this.charset);
		return fromJSON(JSONstr);
	}

	/**
	 * HttpClient的POST请求
	 * 
	 * @param url
	 *            请求的地址
	 * @param params
	 *            请求的键值参数
	 * @param charset
	 *            要求返回时转换为某字符集
	 * @return 返回请求后的实体内容字符串
	 */
	public String doPost(String url, Map<String, String> params, String charset) {
		HttpEntity httpEntity = buildHttpEntity(params);
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(httpEntity);
		return post(httpPost);
	}

	/**
	 * HttpClient的POST请求
	 * 
	 * @param url
	 *            请求的地址
	 * @param JSON
	 *            请求的参数为JSON字符串
	 * @param charset
	 *            要求返回时转换为某字符集
	 * @return 返回请求后的实体内容字符串
	 */
	public String doPost(String url, String JSON, String charset) {
		Map<String, String> params = fromJSON(JSON);
		return doPost(url, params, charset);
	}

	/**
	 * HttpClient的POST请求
	 * 
	 * @param url
	 *            请求的地址
	 * @param binary
	 *            请求的参数为字节数据,会以流传到url
	 * @param charset
	 *            要求返回时转换为某字符集
	 * @return 返回请求后的实体内容字符串
	 */
	public String doPost(String url, byte[] binary, String charset) {
		HttpEntity httpEntity = buildHttpEntity(binary);
		HttpPost httpPost = new HttpPost(url);
		httpPost.setEntity(httpEntity);
		return post(httpPost);
	}

	/**
	 * 根据键值参数,创建请求的实体
	 * 
	 * @param params
	 *            键值参数
	 * @return 请求的实体HttpEntity
	 */
	private HttpEntity buildHttpEntity(Map<String, String> params) {
		List<NameValuePair> nvp = new ArrayList<NameValuePair>();
		for (String key : params.keySet()) {
			nvp.add(new BasicNameValuePair(key, params.get(key)));
		}
		try {
			return new UrlEncodedFormEntity(nvp, this.charset);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 根据字节数据,创建请求的实体
	 * 
	 * @param binary
	 *            字节数据
	 * @return 请求的实体HttpEntity
	 */
	private HttpEntity buildHttpEntity(byte[] binary) {
		return EntityBuilder.create().setBinary(binary).build();
	}

	/**
	 * 执行POST
	 * 
	 * @param httpPost
	 *            HttpClient执行的httpPost
	 * @return Post请求的结果内容
	 */
	private String post(HttpPost httpPost) {
		try {
			httpPost.setConfig(requestConfig);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode == HttpStatus.SC_OK) {
				HttpEntity httpEntity = httpResponse.getEntity();
				return EntityUtils.toString(httpEntity, this.charset);
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error(this, e);
			return null;
		}
	}

	/**
	 * JSON字符串转为Map对象
	 * 
	 * @param JSON
	 *            JSON字符串
	 * @return 键值对Map对象
	 */
	@SuppressWarnings("unchecked")
	private Map<String, String> fromJSON(String JSON) {
		try {
			return new ObjectMapper().readValue(JSON, Map.class);
		} catch (Exception e) {
			logger.error(this, e);
			return null;
		}
	}

}