package com.common.project.model.post;

public class InfoInputPostUrl {
	private Long pid;
	private String title;
	private String email;
	private String content;
	private String postimage;
	private int postSwitch;
	private int likes;
	public InfoInputPostUrl() {
		super();
	}
	public InfoInputPostUrl(Long pid, String title, String email, String content, String postimage, int postSwitch,
			int likes) {
		super();
		this.pid = pid;
		this.title = title;
		this.email = email;
		this.content = content;
		this.postimage = postimage;
		this.postSwitch = postSwitch;
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
	public String getPostimage() {
		return postimage;
	}
	public void setPostimage(String postimage) {
		this.postimage = postimage;
	}
	public int getPostSwitch() {
		return postSwitch;
	}
	public void setPostSwitch(int postSwitch) {
		this.postSwitch = postSwitch;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
}
