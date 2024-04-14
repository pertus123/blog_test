package com.common.project.model.comment;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "comment")
public class Comment {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	Long cid;
	Long pid;
	String email;
	String content;
	@Column(insertable = false, updatable = false)
	private LocalDateTime create_date;
	public Comment() {
		super();
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
	public Comment(Long cid, Long pid, String email, String content, LocalDateTime create_date) {
		super();
		this.cid = cid;
		this.pid = pid;
		this.email = email;
		this.content = content;
		this.create_date = create_date;
	}
	public Comment(Long pid, String email, String content) {
		super();
		this.pid = pid;
		this.email = email;
		this.content = content;
	}
	
	public Comment(Long cid, String content) {
		super();
		this.cid = cid;
		this.content = content;
	}
	
	public Comment(Long cid, Long pid, String email, String content) {
		super();
		this.cid = cid;
		this.pid = pid;
		this.email = email;
		this.content = content;
	}
}