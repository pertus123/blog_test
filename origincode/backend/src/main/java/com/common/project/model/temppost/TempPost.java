package com.common.project.model.temppost;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "temppost")
public class TempPost {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tpid;
	private String title;
	private String email;
	private String content;
	private String postimage;
	@Column(insertable = false, updatable = false)
	private LocalDateTime create_date;
	
	
	public String getPostimage() {
		return postimage;
	}
	public void setPostimage(String postimage) {
		this.postimage = postimage;
	}
	public TempPost() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getTpid() {
		return tpid;
	}
	public void setTpid(Long tpid) {
		this.tpid = tpid;
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
	public LocalDateTime getCreate_date() {
		return create_date;
	}
	public void setCreate_date(LocalDateTime create_date) {
		this.create_date = create_date;
	}
	public TempPost(Long tpid, String title, String email, String content, String postimage, LocalDateTime create_date) {
		super();
		this.tpid = tpid;
		this.title = title;
		this.email = email;
		this.content = content;
		this.postimage = postimage;
		this.create_date = create_date;
	}
	public TempPost(Long tpid, String title, String email, String content, String postimage) {
		super();
		this.tpid = tpid;
		this.title = title;
		this.email = email;
		this.content = content;
		this.postimage = postimage;
	}
}