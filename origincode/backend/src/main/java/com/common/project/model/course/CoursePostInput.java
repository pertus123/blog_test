package com.common.project.model.course;

public class CoursePostInput {
	private Long gid;
	private Long uid;
	private String maincategory;
	private String middlecategory;
	private String email;
	private String content;
	private int likes;
	public CoursePostInput() {
		super();
	}
	public CoursePostInput(Long gid, Long uid, String maincategory, String middlecategory, String email, String content,
			int likes) {
		super();
		this.gid = gid;
		this.uid = uid;
		this.maincategory = maincategory;
		this.middlecategory = middlecategory;
		this.email = email;
		this.content = content;
		this.likes = likes;
	}
	public Long getGid() {
		return gid;
	}
	public void setGid(Long gid) {
		this.gid = gid;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getMaincategory() {
		return maincategory;
	}
	public void setManicategory(String maincategory) {
		this.maincategory = maincategory;
	}
	public String getMiddlecategory() {
		return middlecategory;
	}
	public void setMiddlecategory(String middlecategory) {
		this.middlecategory = middlecategory;
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
