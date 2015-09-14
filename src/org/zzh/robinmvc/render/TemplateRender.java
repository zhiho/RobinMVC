package org.zzh.robinmvc.render;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zzh.robinmvc.template.ForwardType;
import org.zzh.robinmvc.template.TemplateFactory;

public class TemplateRender extends Render{
	
	private String path;
	private ForwardType type;
	public Map<String, Object> models;

	public TemplateRender(String path, ForwardType type, Map<String, Object> models){
		this.path = path;
		this.type = type;
		this.models = models;
	}
	
	public TemplateRender(String path){
		this(path, null, null);
		models = new HashMap<String, Object>();
	}

	public TemplateRender(String path, ForwardType type){
		this(path, type, null);
		models = new HashMap<String, Object>();
	} 
	
	public void addModel(String name, Object value){
		models.put(name, value);
	}

	@Override
	public void render(ServletContext context, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		TemplateFactory.getInstance().initTemplate(path, type)
		.handlerRender(request, response, models);
		
	}
	
	
	
	
	
	
	
	
	
}
