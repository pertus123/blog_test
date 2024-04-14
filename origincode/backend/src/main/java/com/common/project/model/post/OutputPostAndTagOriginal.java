package com.common.project.model.post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class OutputPostAndTagOriginal {
	private Long pid;
	private String title;
	private String email;
	private String content;
	private int likes;
	private String postimage;
	private LocalDateTime create_date;
	private Optional<List<String>> tags;
	public OutputPostAndTagOriginal() {
		super();
	}
	public OutputPostAndTagOriginal(Long pid, String title, String email, String content, int likes, String postimage,
			LocalDateTime create_date, Optional<List<String>> tags) {
		super();
		this.pid = pid;
		this.title = title;
		this.email = email;
		this.content = content;
		this.likes = likes;
		this.postimage = postimage;
		this.create_date = create_date;
		this.tags = tags;
	}
	public OutputPostAndTagOriginal(Long pid, String title, String email, String content, int likes, String postimage,
			LocalDateTime create_date) {
		super();
		this.pid = pid;
		this.title = title;
		this.email = email;
		this.content = content;
		this.likes = likes;
		this.postimage = postimage;
		this.create_date = create_date;
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
	public Optional<List<String>> getTags() {
		return tags;
	}
	public void setTags(Optional<List<String>> tags) {
		this.tags = tags;
	}
}
