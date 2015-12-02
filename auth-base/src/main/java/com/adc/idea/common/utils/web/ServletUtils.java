package com.adc.idea.common.utils.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.net.HttpHeaders;

/**
 * Servlet工具类
 */
@SuppressWarnings("rawtypes")
public class ServletUtils {

	private static final Logger logger = LoggerFactory.getLogger(ServletUtils.class);

	// -- Content Type 定义 --//
	/** text type. */
	public static final String TEXT_TYPE = "text/plain";
	/** json type. */
	public static final String JSON_TYPE = "application/json";
	/** xml type. */
	public static final String XML_TYPE = "text/xml";
	/** html type. */
	public static final String HTML_TYPE = "text/html";
	/** js type. */
	public static final String JS_TYPE = "text/javascript";
	/** excel type. */
	public static final String EXCEL_TYPE = "application/vnd.ms-excel";
	/** stream type. */
	public static final String STREAM_TYPE = "application/octet-stream";

	private static final String UNKNOWN = "unknown";

	private static final String[] ADDR_HEADER = { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
			"X-Real-IP" };

	public static final long ONE_YEAR_SECONDS = 60 * 60 * 24 * 365;

	/**
	 * 获得真实IP地址。在使用了反向代理时，直接用HttpServletRequest.getRemoteAddr()无法获取客户真实的IP地址。
	 * 
	 * @param request
	 * @return
	 */
	public static String getRemoteAddr(ServletRequest request) {
		String addr = null;
		if (request instanceof HttpServletRequest) {
			HttpServletRequest hsr = (HttpServletRequest) request;
			for (String header : ADDR_HEADER) {
				if (StringUtils.isBlank(addr) || UNKNOWN.equalsIgnoreCase(addr)) {
					addr = hsr.getHeader(header);
				} else {
					break;
				}
			}
		}
		if (StringUtils.isBlank(addr) || UNKNOWN.equalsIgnoreCase(addr)) {
			addr = request.getRemoteAddr();
		} else {
			// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按','分割
			int i = addr.indexOf(",");
			if (i > 0) {
				addr = addr.substring(0, i);
			}
		}
		return addr;
	}

	/**
	 * 设置让浏览器弹出下载对话框的Header.
	 * 
	 * @param fileName
	 *            下载后的文件名.
	 */
	public static void setDownloadHeader(HttpServletResponse response, String fileName) {
		// 中文文件名支持
		try {
			String encodedfileName = new String(fileName.getBytes(), "ISO8859-1");
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedfileName + "\"");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置让浏览器弹出下载对话框的Header.
	 * 
	 * @param fileName
	 *            下载后的文件名.
	 */
	public static void setFileDownloadHeader(HttpServletRequest request, HttpServletResponse response, String fileName)
			throws UnsupportedEncodingException {
		// 中文文件名支持
		String encodedFileName = null;
		// 替换空格，否则firefox下有空格文件名会被截断,其他浏览器会将空格替换成+号
		encodedFileName = fileName.trim().replaceAll(" ", "_");

		String agent = request.getHeader("User-Agent");
		boolean isMSIE = ((agent != null) && (agent.toUpperCase().indexOf("MSIE") != -1));

		if (isMSIE) {
			encodedFileName = URLEncoder.encode(encodedFileName, "UTF-8");
		} else {
			encodedFileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
		}

		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFileName + "\"");
	}

	public static Map<String, String[]> parseQueryString(String queryString) {
		if (StringUtils.isBlank(queryString)) {
			return Collections.emptyMap();
		}
		Map<String, String[]> queryMap = new TreeMap<String, String[]>();
		String[] params = StringUtils.split(queryString, '&');
		for (String param : params) {
			int index = param.indexOf('=');
			if (index != -1) {
				String name = param.substring(0, index);
				// name为空值不保存
				if (StringUtils.isBlank(name)) {
					continue;
				}
				String value = param.substring(index + 1);
				try {
					value = URLDecoder.decode(value, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					logger.error("never!", e);
				}
				if (queryMap.containsKey(name)) {
					String[] values = queryMap.get(name);
					queryMap.put(name, ArrayUtils.addAll(values, value));
				} else {
					queryMap.put(name, new String[] { value });
				}
			}
		}
		return queryMap;
	}

	public static String getParameter(HttpServletRequest request, Map<String, String[]> queryMap, String name) {
		String[] values = getParameterValues(request, queryMap, name);
		return ArrayUtils.isNotEmpty(values) ? StringUtils.join(values, ',') : null;
	}

	public static String getParameter(HttpServletRequest request, String name) {
		String[] values = getParameterValues(request, name);
		return ArrayUtils.isNotEmpty(values) ? StringUtils.join(values, ',') : null;
	}

	public static String[] getParameterValues(HttpServletRequest request, Map<String, String[]> queryMap, String name) {
		Validate.notNull(request, "Request must not be null");
		String[] values = queryMap.get(name);
		if (values == null) {
			values = request.getParameterValues(name);
		}
		return values;
	}

	public static String[] getParameterValues(HttpServletRequest request, String name) {
		Validate.notNull(request, "Request must not be null");
		String qs = request.getQueryString();
		Map<String, String[]> queryMap = parseQueryString(qs);
		return getParameterValues(request, queryMap, name);
	}

	public static Map<String, String> getParameterMap(HttpServletRequest request) {
		return getParameterMap(request, null);
	}

	public static Map<String, String> getParameterMap(HttpServletRequest request, String prefix) {
		return getParameterMap(request, prefix, false);
	}

	public static Map<String, String> getParameterMap(HttpServletRequest request, String prefix,
			boolean keyWithPrefix) {
		Validate.notNull(request, "Request must not be null");
		Map<String, String> params = new LinkedHashMap<String, String>();
		if (prefix == null) {
			prefix = "";
		}
		String qs = request.getQueryString();
		Map<String, String[]> queryMap = parseQueryString(qs);
		int len = prefix.length();
		Enumeration paramNames = request.getParameterNames();
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String name = keyWithPrefix ? paramName : paramName.substring(len);
				String value = getParameter(request, queryMap, paramName);
				params.put(name, value);
			}
		}
		return params;
	}

	public static Map<String, String[]> getParameterValuesMap(HttpServletRequest request, String prefix) {
		return getParameterValuesMap(request, prefix, false);
	}

	public static Map<String, String[]> getParameterValuesMap(HttpServletRequest request, String prefix,
			boolean keyWithPrefix) {
		Validate.notNull(request, "Request must not be null");
		Enumeration paramNames = request.getParameterNames();
		Map<String, String[]> params = new LinkedHashMap<String, String[]>();
		if (prefix == null) {
			prefix = "";
		}
		String qs = request.getQueryString();
		Map<String, String[]> queryMap = parseQueryString(qs);
		int len = prefix.length();
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String name = keyWithPrefix ? paramName : paramName.substring(len);
				String[] values = getParameterValues(request, queryMap, paramName);
				if (values != null && values.length > 0) {
					params.put(name, values);
				}
			}
		}
		return params;
	}

	/**
	 * 设置客户端缓存过期时间 的Header.
	 */
	public static void setExpiresHeader(HttpServletResponse response, long expiresSeconds) {
		// Http 1.0 header
		response.setDateHeader("Expires", System.currentTimeMillis() + expiresSeconds * 1000);
		// Http 1.1 header
		response.setHeader("Cache-Control", "private, max-age=" + expiresSeconds);
	}

	/**
	 * 设置禁止客户端缓存的Header.
	 */
	public static void setNoCacheHeader(HttpServletResponse response) {
		// Http 1.0 header
		response.setDateHeader("Expires", 1L);
		response.addHeader("Pragma", "no-cache");
		// Http 1.1 header
		response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
	}

	/**
	 * 输出html。并禁止客户端缓存。输出json也可以用这个方法。
	 * 
	 * contentType:text/html;charset=utf-8。
	 * 
	 * @param response
	 * @param s
	 */
	public static void writeHtml(HttpServletResponse response, String s) {
		response.setContentType("text/html;charset=utf-8");
		setNoCacheHeader(response);
		try {
			response.getWriter().write(s);
		} catch (IOException ex) {
			logger.error(ex.getMessage(), ex);
		}
	}

	public static String getCookie(HttpServletRequest request, String name) {
		Assert.notNull(request, "Request must not be null");
		Cookie cookies[] = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (name.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	public static boolean validateUrl(String url, Set<String> validDomains) {
		if (StringUtils.isBlank(url)) {
			return true;
		}
		UriComponentsBuilder ucb = UriComponentsBuilder.fromUriString(url);
		UriComponents uc = ucb.build();
		String host = uc.getHost();
		if (StringUtils.isBlank(host) || validDomains.contains(host)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 设置LastModified Header.
	 */
	public static void setLastModifiedHeader(HttpServletResponse response, long lastModifiedDate) {
		response.setDateHeader("Last-Modified", lastModifiedDate);
	}

	/**
	 * 设置Etag Header.
	 */
	public static void setEtag(HttpServletResponse response, String etag) {
		response.setHeader("ETag", etag);
	}

	/**
	 * 根据浏览器If-Modified-Since Header, 计算文件是否已被修改.
	 * 
	 * 如果无修改, checkIfModify返回false ,设置304 not modify status.
	 * 
	 * @param lastModified
	 *            内容的最后修改时间.
	 */
	public static boolean checkIfModifiedSince(HttpServletRequest request, HttpServletResponse response,
			long lastModified) {
		long ifModifiedSince = request.getDateHeader("If-Modified-Since");
		if ((ifModifiedSince != -1) && (lastModified < ifModifiedSince + 1000)) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return false;
		}
		return true;
	}

	/**
	 * 根据浏览器 If-None-Match Header, 计算Etag是否已无效.
	 * 
	 * 如果Etag有效, checkIfNoneMatch返回false, 设置304 not modify status.
	 * 
	 * @param etag
	 *            内容的ETag.
	 */
	public static boolean checkIfNoneMatchEtag(HttpServletRequest request, HttpServletResponse response, String etag) {
		String headerValue = request.getHeader("If-None-Match");
		if (headerValue != null) {
			boolean conditionSatisfied = false;
			if (!"*".equals(headerValue)) {
				StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");

				while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
					String currentToken = commaTokenizer.nextToken();
					if (currentToken.trim().equals(etag)) {
						conditionSatisfied = true;
					}
				}
			} else {
				conditionSatisfied = true;
			}

			if (conditionSatisfied) {
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				response.setHeader("ETag", etag);
				return false;
			}
		}
		return true;
	}
}
