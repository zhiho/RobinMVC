package org.zzh.robinmvc.render;

import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TextRender extends Render {

	private String characterEncoding;
	private String text;
	
	public TextRender(){
	}
	
	public TextRender(String text){
		this.text = text;
	}
	
	public TextRender(String text, String characterEncoding){
		this.text = text;
		this.characterEncoding = characterEncoding;
	}
	
	public String getCharacterEncoding() {
		return characterEncoding;
	}

	public void setCharacterEncoding(String characterEncoding) {
		this.characterEncoding = characterEncoding;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void render(ServletContext context, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StringBuilder stringBuilder = new StringBuilder(64);
		stringBuilder.append(contentType == null ? "text/html" : contentType)
					 .append(";charset=")
					 .append(characterEncoding == null ? "UTF-8" : characterEncoding);
		response.setContentType(stringBuilder.toString());
		PrintWriter pw = response.getWriter();
		pw.write(text);
		pw.flush();

	}

}
