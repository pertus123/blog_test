package com.common.project.model.comment;

public class UpdateComment {
	Long cid;
	Long pid;
	String email;
	String content;

	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
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
	public UpdateComment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UpdateComment(Long cid, Long pid, String email, String content) {
		super();
		this.cid = cid;
		this.pid = pid;
		this.email = email;
		this.content = content;
	}
	public UpdateComment(Long pid, String email, String content) {
		super();
		this.pid = pid;
		this.email = email;
		this.content = content;
	}
}
