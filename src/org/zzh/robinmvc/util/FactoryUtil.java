package org.zzh.robinmvc.util;

import org.zzh.robinmvc.exception.NoIocException;
import org.zzh.robinmvc.exception.NoTemplateException;
import org.zzh.robinmvc.ioc.factory.IocFactory;
import org.zzh.robinmvc.template.TemplateFactory;



public class FactoryUtil {

	public static IocFactory createIocFactory(String className) throws Exception
	{
		IocFactory iocFactory = null;
		Class clazz = null;
		
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new NoIocException("No IocFactory , can not find class name" + className);
		}
		
		Object obj = clazz.newInstance();
		if (obj instanceof IocFactory) {
			iocFactory = (IocFactory) obj;
		}
		else 
			throw new NoIocException("Your class must be a subclass of IocFactory");
		
		return iocFactory;
		
	}
	
	public static TemplateFactory createTemplateFactory(String className) throws Exception
	{
		TemplateFactory templateFactory = null;
		Class clazz = null;
		
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new NoTemplateException("No TemplateFactory , can not find class name" + className);
		}
		
		Object obj = clazz.newInstance();
		if (obj instanceof TemplateFactory) {
			templateFactory = (TemplateFactory) obj;
		}
		else 
			throw new NoTemplateException("Your class must be a subclass of TemplateFactory");
		
		return templateFactory;
		
	}
	
	
	
}
