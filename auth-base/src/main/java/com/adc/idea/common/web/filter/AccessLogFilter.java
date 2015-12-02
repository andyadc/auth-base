package com.adc.idea.common.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.adc.idea.common.utils.LogUtils;


public class AccessLogFilter extends BaseFilter {

	@Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //LogUtils.logAccess(request);
        //LogUtils.logPageError(request);
        chain.doFilter(request, response);
    }
	
}
