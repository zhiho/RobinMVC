package org.zzh.robinmvc.template;

import org.zzh.robinmvc.config.ContextConfig;

public class JspTemplateFactory extends TemplateFactory {

	@Override
	public void init(ContextConfig contextConfig) {
		// TODO Auto-generated method stub

	}

	@Override
	public Template initTemplate(String path, ForwardType type){
		return new JspTemplate(path, type);
	}

}
