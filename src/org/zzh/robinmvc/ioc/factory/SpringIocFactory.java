package org.zzh.robinmvc.ioc.factory;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.zzh.robinmvc.annotation.Controller;
import org.zzh.robinmvc.annotation.InterceptorURL;

public class SpringIocFactory implements IocFactory {

	private final Log log = LogFactory.getLog(getClass());
	private ApplicationContext applicationContext;
	private List<Object> controllerBeans = new ArrayList<Object>();
	private List<Object> interceptorBeans = new ArrayList<Object>();
	

	@Override
	public void init(ServletContext servletContext) {
		log.info("init context...");
		applicationContext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(servletContext);
		
		//从配置文件中获取controller和interceptor
		initBeans();
	}

	@Override
	public List<Object> getControllers() {
		return controllerBeans;
	}

	@Override
	public List<Object> getInterceptors() {
		return interceptorBeans;
	}

	@SuppressWarnings("unchecked")
	private void initBeans()
	{
		String[] beanNames = applicationContext.getBeanDefinitionNames();
		for(String beanName : beanNames)
		{
			if(applicationContext.getType(beanName).isAnnotationPresent(Controller.class)==true)
			{
				controllerBeans.add(applicationContext.getBean(beanName));
				log.info("init controller bean:" + beanName);
			}
			if(applicationContext.getType(beanName).isAnnotationPresent(InterceptorURL.class)==true)
			{
				interceptorBeans.add(applicationContext.getBean(beanName));
				log.info("init interceptor bean:" + beanName);
			}
			
		}
		
	}
	
	
	
	@Override
	public void destory() {
		// TODO Auto-generated method stub

	}
	
	
}
