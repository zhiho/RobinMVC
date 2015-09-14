package org.zzh.robinmvc.mvc.dispatcher;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zzh.robinmvc.config.StrategyConfig;
import org.zzh.robinmvc.exception.NoRequestParamException;

/**
 * 
 * @author zhangzhihao
 *
 */
public class DispatcherServlet extends GenericServlet{

	private final Log log = LogFactory.getLog(getClass());

	private Dispatcher dispatcher;
	
	
	public void init(ServletConfig servletConfig) throws ServletException 
	{
		super.init(servletConfig);
		this.dispatcher = new Dispatcher();
		StrategyConfig strategyConfig = new StrategyConfig(servletConfig);
		dispatcher.init(strategyConfig);
		
	}
	
	
	@Override
	public void service(ServletRequest servReq, ServletResponse servResp)
			throws ServletException, IOException{
		HttpServletRequest req=(HttpServletRequest) servReq;
		HttpServletResponse resp=(HttpServletResponse) servResp;
		String method=req.getMethod();
		 if ("GET".equals(method) || "POST".equals(method)) {
			 if(!dispatcher.doDispatch(req, resp)){		
				 return;
			 }
		 }
		
	}
	
	public void destory()
	{
		log.info("DispatcherServlet distory!!!");
		super.destroy();
	}
	
	
	
}
