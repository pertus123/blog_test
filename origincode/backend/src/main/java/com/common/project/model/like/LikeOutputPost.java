package com.common.project.model.like;

import java.time.LocalDateTime;

public class LikeOutputPost {
	private Long pid;
	private String title;
	private String email;
	private String content;
	private int likes;
	private String postimage;
	private LocalDateTime create_date;
	private int isLike;
	private int commentCnt;
	private String thumbimg; 
	private String profileimage;
	public String getProfileimage() {
		return profileimage;
	}
	public void setProfileimage(String profileimage) {
		this.profileimage = profileimage;
	}
	
	public String getThumbimg() {
		return thumbimg;
	}
	public void setThumbimg(String thumbimg) {
		this.thumbimg = thumbimg;
	}
	
	public LikeOutputPost(Long pid, String title, String email, String content, int likes, LocalDateTime create_date,
			int isLike, String thumbimg) {
		super();
		this.pid = pid;
		this.title = title;
		this.email = email;
		this.content = content;
		this.likes = likes;
		this.create_date = create_date;
		this.isLike = isLike;
		this.thumbimg = thumbimg;
	}
	
	public LikeOutputPost() {
		super();
	}
	
	public LikeOutputPost(Long pid, String title, String email, String content, int likes, LocalDateTime create_date,
			int isLike) {
		super();
		this.pid = pid;
		this.title = title;
		this.email = email;
		this.content = content;
		this.likes = likes;
		this.create_date = create_date;
		this.isLike = isLike;
	}
	
	
	public LikeOutputPost(Long pid, String title, String email, String content, int likes, LocalDateTime create_date,
			int isLike, int commentCnt) {
		super();
		this.pid = pid;
		this.title = title;
		this.email = email;
		this.content = content;
		this.likes = likes;
		this.create_date = create_date;
		this.isLike = isLike;
		this.commentCnt = commentCnt;
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
	public LocalDateTime getCreate_date() {
		return create_date;
	}
	public void setCreate_date(LocalDateTime create_date) {
		this.create_date = create_date;
	}
	public int getIsLike() {
		return isLike;
	}
	public void setIsLike(int isLike) {
		this.isLike = isLike;
	}
	public int getCommentCnt() {
		return commentCnt;
	}
	public void setCommentCnt(int commentCnt) {
		this.commentCnt = commentCnt;
	}
}
