package org.zzh.robinmvc.handler;

import org.zzh.robinmvc.interceptor.Interceptor;

public class HandlerExecution {

	private Execution execution;
	private Interceptor[] interceptors;
	int index = -1;
	
	public Execution getExecution() {
		return execution;
	}
	public void setExecution(Execution execution) {
		this.execution = execution;
	}
	public Interceptor[] getInterceptors() {
		return interceptors;
	}
	public void setInterceptors(Interceptor[] interceptors) {
		this.interceptors = interceptors;
	}
	
	public HandlerExecution(Execution execution, Interceptor[] interceptor)
	{
		super();
		this.execution = execution;
		this.interceptors = interceptor;
	}
	
	/**
	 * 在方法执行前后加入过滤器
	 * 返回执行结果
	 * @return
	 */
	public Object invokeHandlerExecution()
	{
		boolean flag = true; 
		for(int i = 0;i < interceptors.length ; i++)
		{
			index = i;//方法后执行的过滤器反向实行
			//如果执行结果返回false，后面的过滤器就不执行,通过index来控制
			if(!interceptors[i].preInterceptor()){
				flag = false;
				break;
			}
		}
		
		//执行方法 
		Object result = execution.invokeHandlerMethod();
		
		for(int i = index; i>=0 ; i--)
		{
			interceptors[i].afterInterceptor();
		}
		
		return result;
	}
	
	
	
	
}
