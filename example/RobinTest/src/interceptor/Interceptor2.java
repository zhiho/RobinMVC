package interceptor;

import org.zzh.robinmvc.annotation.InterceptorURL;
import org.zzh.robinmvc.interceptor.Interceptor;


@InterceptorURL("/test/*")
public class Interceptor2 implements Interceptor {

	@Override
	public boolean preInterceptor() {
		System.out.println("do preInterceptor of Interceptor2");
		return true;
	}

	@Override
	public void afterInterceptor() {
		System.out.println("do afterInterceptor of Interceptor2");
	}

}
