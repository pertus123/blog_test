package com.common.project.model.post;

public class InputPost {
	private Long pid;
	private String title;
	private String email;
	private String content;
	private int likes;

	public InputPost() {
		super();
	}

	public InputPost(Long pid, String title, String email, String content, int likes) {
		this.pid = pid;
		this.title = title;
		this.email = email;
		this.content = content;
		this.likes = likes;
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

}
