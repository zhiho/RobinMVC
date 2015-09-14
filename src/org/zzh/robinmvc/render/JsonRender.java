package org.zzh.robinmvc.render;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;


public class JsonRender extends TextRender {

	private static final String mime_javascript = "application/json";
	private Map models;
	
	public JsonRender(){
		setContentType(mime_javascript);
	}

	public JsonRender(Map models){
		this.models = models;
	}
	
	public JsonRender(String text){
		super(text);
		setContentType(mime_javascript);
	}
	
	public JsonRender(String text, String characterEncoding){
		super(text, characterEncoding);
		setContentType(mime_javascript);
	}
	
	
	
	@Override
	public void render(ServletContext context, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("application/json;charset=utf-8");
		JSONObject json =  JSONObject.fromObject(models);
		String result = json.toString();
		PrintWriter pw = response.getWriter();
		pw.write(result);
		pw.flush();
		
		
	}
}
