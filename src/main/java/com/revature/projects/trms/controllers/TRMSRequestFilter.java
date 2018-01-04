package com.revature.projects.trms.controllers;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

@WebFilter(
  filterName = "TRMSRequestFilter",
  asyncSupported = true,
  urlPatterns = {"/*"}
)
public class TRMSRequestFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    
  }

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
    ((HttpServletResponse) response).addHeader("Access-Control-Allow-Origin", "*");
    ((HttpServletResponse) response).addHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST");
    ((HttpServletResponse) response).addHeader("Access-Control-Allow-Headers", "Content-Type");
    ((HttpServletResponse) response).addHeader("Access-Control-Max-Age", "86400");
    
    // pass the request along the filter chain
    chain.doFilter(request, response);
  }
  
  @Override
  public void destroy() {
    
  }
  
}