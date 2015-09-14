package org.zzh.robinmvc.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public final class ControllerContext {

	private static final ThreadLocal<ControllerContext> threadLocalControllerContext = new ThreadLocal<ControllerContext>();
	
	private ServletContext servletContext;
	private HttpServletRequest httpServletRequest;
	private HttpServletResponse httpServletResponse;
	
	public ServletContext getServletContext() {
		return servletContext;
	}
	
	public HttpServletRequest getHttpServletRequest() {
		return httpServletRequest;
	}
	
	public HttpServletResponse getHttpServletResponse() {
		return httpServletResponse;
	}
	
	public HttpSession getHttpSession(){
		return httpServletRequest.getSession();
	}
	
	public static void setControllerContext(ServletContext context, HttpServletRequest request, HttpServletResponse response)
	{
		ControllerContext cc = new ControllerContext();
		cc.servletContext = context;
		cc.httpServletRequest = request;
		cc.httpServletResponse = response;
		threadLocalControllerContext.set(cc);
	}
	
	public static void removeControllerContext()
	{
		threadLocalControllerContext.remove();
	}
	
	
	
}
