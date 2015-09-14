package org.zzh.robinmvc.handler;

import java.lang.reflect.InvocationTargetException;

public class Execution {
	
	private HandlerMethod handlerMethod;
	private Object[] args;
		
	public HandlerMethod getHandlerMethod() {
		return handlerMethod;
	}
	public void setHandlerMethod(HandlerMethod handlerMethod) {
		this.handlerMethod = handlerMethod;
	}
	public Object[] getArgs() {
		return args;
	}
	public void setArgs(Object[] args) {
		this.args = args;
	}
	
	public Execution(HandlerMethod handlerMethod, Object[] args)
	{
		super();
		this.handlerMethod = handlerMethod;
		this.args = args;
	}
	
	/**
	 * 执行真正的方法
	 * @return
	 */
	public Object invokeHandlerMethod()
	{
		try {
			return handlerMethod.getMethod().invoke(handlerMethod.getInstance(), args);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	

}
