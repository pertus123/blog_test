package com.common.project.model.course;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "course_post")
public class CoursePost {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cpid;
	private Long gid;
	private Long uid;
	private String manicategory;
	private String middlecategory;
	private String email;
	private String content;
	private int likes;
	
	@Column(insertable = false, updatable = false)
	private LocalDateTime create_date;
	
	public CoursePost(Long cpid, Long gid, Long uid, String manicategory, String middlecategory, String email,
			String content, int likes) {
		super();
		this.cpid = cpid;
		this.gid = gid;
		this.uid = uid;
		this.manicategory = manicategory;
		this.middlecategory = middlecategory;
		this.email = email;
		this.content = content;
		this.likes = likes;
	}
	
	public CoursePost( Long gid, Long uid, String manicategory, String middlecategory, String email,
			String content, int likes) {
		super();
		this.gid = gid;
		this.uid = uid;
		this.manicategory = manicategory;
		this.middlecategory = middlecategory;
		this.email = email;
		this.content = content;
		this.likes = likes;
	}

	public Long getCpid() {
		return cpid;
	}

	public void setCpid(Long cpid) {
		this.cpid = cpid;
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

	public String getManicategory() {
		return manicategory;
	}

	public void setManicategory(String manicategory) {
		this.manicategory = manicategory;
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

	public LocalDateTime getCreate_date() {
		return create_date;
	}

	public void setCreate_date(LocalDateTime create_date) {
		this.create_date = create_date;
	}
	
}
