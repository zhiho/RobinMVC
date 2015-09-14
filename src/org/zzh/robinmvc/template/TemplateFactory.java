package org.zzh.robinmvc.template;

import org.zzh.robinmvc.config.ContextConfig;

public abstract class TemplateFactory {

	private static TemplateFactory instance;

	public static TemplateFactory getInstance() {
		return instance;
	}

	public static void setInstance(TemplateFactory instance) {
		TemplateFactory.instance = instance;
	}
	
	public abstract void init(ContextConfig contextConfig);
	
	public abstract Template initTemplate(String path, ForwardType type) throws Exception;
	
	
}
