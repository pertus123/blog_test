package com.common.project.model.portfolio;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "portfolio")
public class Portfolio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long fid;
	private String email;
	private String content;
	@Column(insertable = false, updatable = false)
	private LocalDateTime create_date;
	
	public Portfolio() {
		super();
	}
	
	public Portfolio(Long fid, String email, String content) {
		this.fid = fid; 
		this.email = email;
		this.content = content;
	}

	public Portfolio(Long fid, String email, String content, LocalDateTime create_date) {

		this.fid = fid;
		this.email = email;
		this.content = content;
		this.create_date = create_date;
	}

	public Long getFid() {
		return fid;
	}

	public void setFid(Long fid) {
		this.fid = fid;
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
}
