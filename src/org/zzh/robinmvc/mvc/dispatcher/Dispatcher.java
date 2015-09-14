package org.zzh.robinmvc.mvc.dispatcher;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zzh.robinmvc.annotation.RequestMapping;
import org.zzh.robinmvc.annotation.RequestParam;
import org.zzh.robinmvc.config.ContextConfig;
import org.zzh.robinmvc.exception.NoParameterException;
import org.zzh.robinmvc.handler.Execution;
import org.zzh.robinmvc.handler.HandlerExecution;
import org.zzh.robinmvc.handler.HandlerMethod;
import org.zzh.robinmvc.handler.URL;
import org.zzh.robinmvc.interceptor.Interceptor;
import org.zzh.robinmvc.ioc.factory.IocFactory;
import org.zzh.robinmvc.render.Render;
import org.zzh.robinmvc.render.TextRender;
import org.zzh.robinmvc.switcher.TypeSwitcher;
import org.zzh.robinmvc.template.TemplateFactory;
import org.zzh.robinmvc.util.ControllerContext;
import org.zzh.robinmvc.util.FactoryUtil;

public class Dispatcher {
	
	private final Log log=LogFactory.getLog(getClass());
	private ServletContext servletContext;
	private TypeSwitcher typeSwitcher = new TypeSwitcher();
	private Map<URL, HandlerMethod> url_handlermethod = new HashMap<URL, HandlerMethod>();
	private Map<String, Interceptor> url_interceptor = new HashMap<String, Interceptor>();
	private Interceptor[] match_interceptors = null;
	private static final String JSPTEMPLATE = "org.zzh.robinmvc.template.JspTemplateFactory";
	
	
	void init(ContextConfig contextConfig) throws ServletException  
	{
		this.servletContext = contextConfig.getServletContext();
		try {
			initStrategies(contextConfig);
			log.info("init controller and interceptor");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initStrategies(ContextConfig contextConfig) throws Exception 
	{
		String iocContainer = contextConfig.getInitParameter("container");
		
		if(iocContainer == null ||iocContainer.equals(""))
		{
			throw new NoParameterException("Missing init container!!!");
		}
		
		IocFactory iocFactory = FactoryUtil.createIocFactory(iocContainer); 
		iocFactory.init(servletContext);	
		log.debug("Init IocFactory");
		
		initHandlerMappings(iocFactory);
		initInterceptors(iocFactory);
		initTemplates(contextConfig);
	}
	
	protected boolean doDispatch(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException
	{
		String reqUrl = req.getServletPath();
		URL url = new URL(reqUrl);
		if(!url_handlermethod.containsKey(url)){
			return false;
		}
		
		Execution execution = null;
		HandlerMethod handlerMethod = url_handlermethod.get(url);
		
		Method	method = handlerMethod.getMethod();
		Map<String, String[]> reqParameterMap = req.getParameterMap();
		if(reqParameterMap.size() == 0){
			execution = new Execution(handlerMethod, null);
		}else{
			
			Class<?>[] parameterClazz = method.getParameterTypes();
			Object[] parameters = new Object[parameterClazz.length];
			Annotation[][] an = method.getParameterAnnotations();
			
			if(an.length != parameterClazz.length) 
					throw new ServletException("Parameter missiing RequestParam");
			
			for(int i=0;i<parameterClazz.length;i++)
			{
				RequestParam rp = (RequestParam)an[i][0];
				String name = rp.value();
				String args = (String)reqParameterMap.get(name)[0];
				if(parameterClazz[i].equals(String.class)){
					parameters[i] = args;
				}else{
					try {
						parameters[i] = typeSwitcher.switcher(parameterClazz[i], args);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
			
			execution = new Execution(handlerMethod, parameters);	
		}
		
		if(execution!=null){
			match_interceptors = url.getMatchInterceptor(url_interceptor);
			handlerExecution(req,resp,execution);
		}
		
		return execution!=null;
	}
	
	private void handlerExecution(HttpServletRequest req,
			HttpServletResponse resp, Execution execution)
	{
		ControllerContext.setControllerContext(servletContext, req, resp);
		HandlerExecution handlerExecution = new HandlerExecution(execution, match_interceptors);
		
		try {
			Object result = handlerExecution.invokeHandlerExecution();
			handlerResult(req,resp,result);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			ControllerContext.removeControllerContext();
		}
		
	}
	
	
	private void handlerResult(HttpServletRequest req,
			HttpServletResponse resp, Object result) throws Exception
	{
		if(result == null)
		{
			return;
		}
		if(result instanceof Render)
		{
			Render render = (Render)result;
			render.render(servletContext, req, resp);
			return;
		}
		if(result instanceof String)
		{
			new TextRender((String)result).render(servletContext, req, resp);
			return;
		}
		
	}
	
	private void initInterceptors(IocFactory iocFactory)
	{
		log.debug("Init Interceptors form application context");
		List<Object> interceptorBeans = iocFactory.getInterceptors();
		int size = interceptorBeans.size();
		for(int i = 0; i<size; i++)
		{
			Interceptor interceptor = (Interceptor)interceptorBeans.get(i);
			org.zzh.robinmvc.annotation.InterceptorURL interceptorUrl = 
					interceptor.getClass().getAnnotation(org.zzh.robinmvc.annotation.InterceptorURL.class);
			String 	annotationUrl = interceptorUrl.value();
			url_interceptor.put(annotationUrl, interceptor);
		}
	}
	
	private void initTemplates(ContextConfig contextConfig) throws Exception
	{
		String template = contextConfig.getInitParameter("template");
		
		if(template == null || template.equals(""))
		{
			log.info("Missing init template, default use JSP Template");
			template = JSPTEMPLATE;
		}
		
		TemplateFactory templateFactory = FactoryUtil.createTemplateFactory(template);
		templateFactory.init(contextConfig);
		templateFactory.setInstance(templateFactory);	
	}
	
	private void initHandlerMappings(IocFactory iocFactory)throws Exception 
	{	
		log.debug("Init Controllers form application context");
		
		List<Object> controllerBeans = iocFactory.getControllers();
		int size = controllerBeans.size();
		
		for(int i=0 ; i<size ; i++)
		{
			Object obj = controllerBeans.get(i);
			initHandlerMethod(obj);			
		}
	
	}
	
	private void initHandlerMethod(Object obj){
		Class clazz = obj.getClass();
		Method[] method = clazz.getMethods();
		
		for(int i = 0; i < method.length ; i++)
		{
			if(isLegalMethod(method[i]))
			{
				String  requestUrl = method[i].getAnnotation(RequestMapping.class).value();
				HandlerMethod handlerMethod = new HandlerMethod(obj, method[i]);
				URL url = new URL(requestUrl);
				url_handlermethod.put(url, handlerMethod);
			}
			
		}
		
	}
	
	private boolean isLegalMethod(Method method){
		
		//注解检查，要有RequestMapping注解
		RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
		if(requestMapping==null||requestMapping.value().length()==0){
			return false;
		}
		//不能为静态方法
		if(Modifier.isStatic(method.getModifiers())){
			return false;
		}
		
		//参数类型检查
		Class<?> [] parameterTypes = method.getParameterTypes();
		for(Class<?> clazz : parameterTypes)
		{
			if(!typeSwitcher.isBaseTypeOrString(clazz)){
				return false;
			}			
		}
		
		Class<?> returnType = method.getReturnType();
		if(returnType.equals(void.class)||
				returnType.equals(String.class)||
				returnType.equals(Render.class)){
			return true;
		}
		
		return false;
		
	}
	
	
	
	
	
	
	
	
	
	
}
