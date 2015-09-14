package interceptor;

import org.zzh.robinmvc.annotation.InterceptorURL;
import org.zzh.robinmvc.interceptor.Interceptor;

@InterceptorURL("/test/testInterceptor")
public class Interceptor1 implements Interceptor {

	@Override
	public boolean preInterceptor() {
		
		System.out.println("do preInterceptor of Interceptor1");
		return true;
	}

	@Override
	public void afterInterceptor() {
		System.out.println("do afterInterceptor of Interceptor1");

	}

}
