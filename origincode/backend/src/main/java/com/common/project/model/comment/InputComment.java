package com.common.project.model.comment;

public class InputComment {
	Long pid;
	String email;
	String content;
	
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
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
	public InputComment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public InputComment(Long pid, String email, String content) {
		super();
		this.pid = pid;
		this.email = email;
		this.content = content;
	}
	
	

}
