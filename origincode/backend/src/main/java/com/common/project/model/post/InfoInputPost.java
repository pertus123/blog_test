package com.common.project.model.post;


import org.springframework.web.multipart.MultipartFile;

public class InfoInputPost {
	private Long pid;
	private String title;
	private String email;
	private String content;
	private int postSwitch;

	private int likes;
	private MultipartFile postimage;

	public InfoInputPost() {
		super();
	}

	public InfoInputPost(Long pid, String title, String email, String content, int likes, MultipartFile postimage) {
		this.pid = pid;
		this.title = title;
		this.email = email;
		this.content = content;
		this.likes = likes;
		this.postimage = postimage;
	}

	public InfoInputPost(Long pid, String title, String email, String content, int postSwitch, int likes,
			MultipartFile postimage) {
		super();
		this.pid = pid;
		this.title = title;
		this.email = email;
		this.content = content;
		this.postSwitch = postSwitch;
		this.likes = likes;
		this.postimage = postimage;
	}

	public int getPostSwitch() {
		return postSwitch;
	}
	
	public void setPostSwitch(int postSwitch) {
		this.postSwitch = postSwitch;
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

	public MultipartFile getPostimage() {
		return postimage;
	}

	public void setPostimage(MultipartFile postimage) {
		this.postimage = postimage;
	}
}