package com.common.project.model.post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class OutputPostAndTag {
	private Long pid;
	private String title;
	private String email;
	private String content;
	private int likes;
	private String profileimage;
	private String thumbimg; 
	private int isLike;
	private int commentCnt;
	private LocalDateTime create_date;
	private Optional<List<String>> tags;
	public OutputPostAndTag() {
		super();
	}
	public OutputPostAndTag(Long pid, String title, String email, String content, int likes,
			String thumbimg, int isLike, LocalDateTime create_date) {
		super();
		this.pid = pid;
		this.title = title;
		this.email = email;
		this.content = content;
		this.likes = likes;
		this.thumbimg = thumbimg;
		this.isLike = isLike;
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
