package org.zzh.robinmvc.render;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class Render {

	protected String contentType;

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public abstract void render(ServletContext context, HttpServletRequest request, HttpServletResponse response) throws Exception;
	
}
