package org.zzh.robinmvc.interceptor;

public interface Interceptor {
	
	public boolean preInterceptor();
	
	public void afterInterceptor();

}
