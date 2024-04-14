package com.common.project.model.post;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "post")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pid;
	private String title;
	private String email;
	private String content;
	private int likes;
	private String postimage;
	@Column(insertable = false, updatable = false)
	private LocalDateTime create_date;

	public Post() {
		super();
	}

	public Post(String title, String email, String content, int likes) {
		this.title = title;
		this.email = email;
		this.content = content;
		this.likes = likes;
	}

	public Post(Long pid, String title, String email, String content, int likes) {
		this.pid = pid;
		this.title = title;
		this.email = email;
		this.content = content;
		this.likes = likes;
	}
	
	public Post(Long pid, String title, String email, String content, int likes, String postimage) {
		this.pid = pid;
		this.title = title;
		this.email = email;
		this.content = content;
		this.likes = likes;
		this.postimage = postimage;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
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

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}
	
	public String getPostimage() {
		return postimage;
	}
	
	public void setPostimage(String postimage) {
		this.postimage = postimage;
	}

	public LocalDateTime getCreate_date() {
		return create_date;
	}
	
	public void setCreate_date(LocalDateTime create_date) {
		this.create_date = create_date;
	}


}
