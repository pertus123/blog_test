package com.common.project.model.temppost;

import java.util.List;

public class TInfoInputPost {
	private Long tpid;
	private String title;
	private String email;
	private String content;
	private List<String> tags;
	private String postimage;
	
	public String getPostimage() {
		return postimage;
	}
	public void setPostimage(String postimage) {
		this.postimage = postimage;
	}
	public TInfoInputPost(Long tpid, String title, String email, String content,String postimage, List<String> tags) {
		super();
		this.tpid = tpid;
		this.title = title;
		this.email = email;
		this.content = content;
		this.postimage = postimage;
		this.tags = tags;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public TInfoInputPost() {
		super();
	}
	public TInfoInputPost(Long tpid, String title, String email, String content,String postimage) {
		super();
		this.tpid = tpid;
		this.title = title;
		this.email = email;
		this.content = content;
		this.postimage = postimage;
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
}