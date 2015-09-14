package org.zzh.robinmvc.config;

import javax.servlet.ServletContext;

/**
 * 根据web.xml获取上下文配置
 * @author zhangzhihao
 *
 */
public interface ContextConfig {

	
	String getInitParameter(String name);
	
	
	ServletContext getServletContext();
	
}
