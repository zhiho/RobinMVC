package org.zzh.robinmvc.template;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Template {
	
	public void handlerRender(HttpServletRequest req,HttpServletResponse resp,Map<String, Object> models)throws Exception;

}
