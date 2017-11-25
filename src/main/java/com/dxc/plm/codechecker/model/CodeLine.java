package com.dxc.plm.codechecker.model;

import java.util.List;

public class CodeLine {
	public CodeLine() {}
	public CodeLine(String content) {
		this.content = content;
	}
	private String type;
	private String content;
	private List<String> elements;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<String> getElements() {
		return elements;
	}
	public void setElements(List<String> elements) {
		this.elements = elements;
	}
	
	
}
