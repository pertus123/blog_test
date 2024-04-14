package com.common.project.model.temppost;

public class TInfoInputPostT {
	
	private String title;
	private String email;
	private String content;
	public TInfoInputPostT() {
		super();
	}
	public TInfoInputPostT(String title, String email, String content) {
		super();
		this.title = title;
		this.email = email;
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}	
}
