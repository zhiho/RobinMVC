package controller;

import java.util.HashMap;
import java.util.Map;

import org.zzh.robinmvc.annotation.Controller;
import org.zzh.robinmvc.annotation.RequestMapping;
import org.zzh.robinmvc.annotation.RequestParam;
import org.zzh.robinmvc.render.JsonRender;
import org.zzh.robinmvc.render.Render;
import org.zzh.robinmvc.render.TemplateRender;
import org.zzh.robinmvc.template.ForwardType;


@Controller
public class TestController {
	
	@RequestMapping(value = "/test1")
	public void test1(@RequestParam("s")String s, @RequestParam("i")int i)
	{
		System.out.println("OK!!!");
		
	}
	
	@RequestMapping(value = "/testString")
	public String testString(@RequestParam("name")String name,
			@RequestParam("pass")String pass)
	{
		return "name:"+ name + "pass:" + pass;
	}
	
	@RequestMapping(value = "/testJson")
	public Render testJson(@RequestParam("name")String name)
	{
		Map<String,String> map = new HashMap<String,String>();
		map.put("name", name);
		
		return new JsonRender(map);
		
	}
	
	@RequestMapping(value = "/testForward1")
	public Render testForward1(@RequestParam("name")String name)
	{
		return new TemplateRender("/testString", ForwardType.Forward);
	}

	@RequestMapping(value = "/testForward2")
	public Render testForward2(@RequestParam("name")String name)
	{
		return new TemplateRender("/testJSP.jsp", ForwardType.Forward);
	}
	
	@RequestMapping(value = "/testRedirect")
	public Render testRedirect()
	{
		return new TemplateRender("/testString", ForwardType.Redirect);
	}
	
	
}
