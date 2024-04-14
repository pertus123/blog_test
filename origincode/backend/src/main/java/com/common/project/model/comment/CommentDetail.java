package com.common.project.model.comment;

import java.time.LocalDateTime;

public class CommentDetail {
	Long cid;
	Long pid;
	String email;
	String content;
	LocalDateTime create_date;
	String profileimage;
	public CommentDetail() {
		super();
	}
	public CommentDetail(Long cid, Long pid, String email, String content, LocalDateTime create_date,
			String profileimage) {
		super();
		this.cid = cid;
		this.pid = pid;
		this.email = email;
		this.content = content;
		this.create_date = create_date;
		this.profileimage = profileimage;
	}
	public CommentDetail(Long cid, Long pid, String email, String content, LocalDateTime create_date) {
		super();
		this.cid = cid;
		this.pid = pid;
		this.email = email;
		this.content = content;
		this.create_date = create_date;
	}
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
	public LocalDateTime getCreate_date() {
		return create_date;
	}
	public void setCreate_date(LocalDateTime create_date) {
		this.create_date = create_date;
	}
	public String getProfileimage() {
		return profileimage;
	}
	public void setProfileimage(String profileimage) {
		this.profileimage = profileimage;
	}
}
