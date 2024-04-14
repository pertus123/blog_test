package com.common.project.model.post;

import java.time.LocalDateTime;

public class PostDetail {
	private Long pid;
	private String title;
	private String email;
	private String content;
	private int likes;
	private String postimage;
	private LocalDateTime create_date;
	private String profileimage;
	public PostDetail() {
		super();
	}
	public PostDetail(Long pid, String title, String email, String content, int likes, String postimage,
			LocalDateTime create_date, String profileimage) {
		super();
		this.pid = pid;
		this.title = title;
		this.email = email;
		this.content = content;
		this.likes = likes;
		this.postimage = postimage;
		this.create_date = create_date;
		this.profileimage = profileimage;
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
	public String getProfileimage() {
		return profileimage;
	}
	public void setProfileimage(String profileimage) {
		this.profileimage = profileimage;
	}
}
