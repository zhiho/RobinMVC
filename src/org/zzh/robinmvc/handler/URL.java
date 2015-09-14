package org.zzh.robinmvc.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.zzh.robinmvc.interceptor.Interceptor;

public class URL {
	
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public URL(String url) {
		super();
		this.url = url;
	}
	/**
	 * 找到与当前url匹配的拦截器
	 * @param url_interceptor
	 * @return
	 */
	public Interceptor[] getMatchInterceptor(Map<String, Interceptor> url_interceptor)
	{
		List<Interceptor> interceptorList = new ArrayList<Interceptor>();
		for(String interceptorUrl : url_interceptor.keySet() )
		{
			String matchUrl = match(this.url, interceptorUrl);
			if(matchUrl != null){
				interceptorList.add(url_interceptor.get(matchUrl));
			}
		}
		
		return interceptorList.toArray(new Interceptor[interceptorList.size()]);
	}
	
	public String match(String url, String interceptorUrl)
	{
		if(url.equals(interceptorUrl)) return interceptorUrl;
		if(interceptorUrl.endsWith("/")) return null;
		
		String[] urlArray = url.split("/");
		String[] interceptorArray = interceptorUrl.split("/");
		
		//拦截器url长度小于当前url长度，而且拦截器必须要以*号结尾
		boolean isMatch = true;
		if(interceptorArray.length < urlArray.length)
		{
			if(interceptorArray[interceptorArray.length-1].equals("*"))
			{
				for(int i = 0; i<interceptorArray.length; i++)
				{
					if(!isMatched(urlArray[i],interceptorArray[i]))
					{
						isMatch = false;
						return null;
					}
				}
				if(isMatch) return interceptorUrl;
			}else{
				return null;
			}			
		}
		
		if(interceptorArray.length == urlArray.length)
		{
			for(int i = 0; i<interceptorArray.length; i++)
			{
				if(!isMatched(urlArray[i],interceptorArray[i]))
				{
					isMatch = false;
					return null;
				}
			}
			if(isMatch) return interceptorUrl;
		}
		
		return null;
	}
	
	public boolean isMatched(String urlPart, String interceptorPart){
		return urlPart.equals(interceptorPart)||interceptorPart.equals("*");
	}

	@Override
	public int hashCode() {
		return url.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(this==obj){
			return true;
		}else if(obj instanceof URL){
			return ((URL) obj).url.equals(this.url);
		}
		return false;
	}
	
	
}
