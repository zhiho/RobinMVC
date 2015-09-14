package org.zzh.robinmvc.handler;

import java.lang.reflect.Method;

public class HandlerMethod {

	
	private Object instance;
	private Method method;
	private Class<?>[] arguments;
	
	public Object getInstance() {
		return instance;
	}
	public void setInstance(Object instance) {
		this.instance = instance;
	}
	public Method getMethod() {
		return method;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
	public Class<?>[] getArguments() {
		return arguments;
	}
	public void setArguments(Class<?>[] arguments) {
		this.arguments = arguments;
	}
	
	public HandlerMethod(Object intsance, Method method, Class<?>[] arguments) {
		this.instance = intsance;
		this.method = method;
		this.arguments = arguments;
	}
	
	public HandlerMethod(Object instance, Method method) {
		this(instance,method,method.getParameterTypes());
	}
}
