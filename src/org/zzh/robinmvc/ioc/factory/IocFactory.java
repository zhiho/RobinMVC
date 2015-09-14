package org.zzh.robinmvc.ioc.factory;

import java.util.List;

import javax.servlet.ServletContext;

public interface IocFactory {

	void init(ServletContext servletContext);
	
	void destory();
	
	List<Object> getControllers();
	
	List<Object> getInterceptors();
	
	
}
