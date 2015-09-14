package org.zzh.robinmvc.template;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JspTemplate implements Template {
	
	private String url;
	private ForwardType type;

	public JspTemplate(String url, ForwardType type)
	{
		this.url = url;
		this.type = type;
	}
	
	@Override
	public void handlerRender(HttpServletRequest req, HttpServletResponse resp,
			Map<String, Object> models) throws Exception {

		//返回model类
		if(models != null)
		{
			Set<String> keys = models.keySet();
			for(String key : keys)
			{
				req.setAttribute(key, models.get(key));
			}
		}
		
		//判断是重定向还是请求转发
		if(type == ForwardType.Forward){
			req.getRequestDispatcher(url).forward(req, resp);
		}
		else if(type == ForwardType.Redirect){
			resp.sendRedirect(url);
		}
		
	}

}
