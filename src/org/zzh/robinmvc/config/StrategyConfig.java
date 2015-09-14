package org.zzh.robinmvc.config;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 * 获取上下文配置
 * @author zhangzhihao
 *
 */

public class StrategyConfig implements ContextConfig {

	private ServletConfig servletConfig;
	
	public StrategyConfig(ServletConfig servletConfig)
	{
		this.servletConfig = servletConfig;
	}

	@Override
	public String getInitParameter(String name) {
		return servletConfig.getInitParameter(name);
	}

	@Override
	public ServletContext getServletContext() {
		return servletConfig.getServletContext();
	}

}
