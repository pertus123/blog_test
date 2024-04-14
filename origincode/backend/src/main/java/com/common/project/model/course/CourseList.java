package com.common.project.model.course;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "course_list")
public class CourseList {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long clid;
	Long uid;
	Long gid;
	String maincategory;
	String middlecategory;
	Long pid;
	
	public Long getClid() {
		return clid;
	}
	public void setClid(Long clid) {
		this.clid = clid;
	}
	public CourseList( Long uid, Long gid,Long pid, String maincategory, String middlecategory, Long clid) {
		super();
		this.clid = clid;
		this.uid = uid;
		this.gid = gid;
		this.maincategory = maincategory;
		this.middlecategory = middlecategory;
		this.pid = pid;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public CourseList() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public CourseList(Long uid, Long gid, String maincategory, String middlecategory, Long pid) {
		super();
		this.uid = uid;
		this.gid = gid;
		this.maincategory = maincategory;
		this.middlecategory = middlecategory;
		this.pid = pid;
	}
	public CourseList(Long uid, Long gid, String maincategory, String middlecategory) {
		super();
		this.uid = uid;
		this.gid = gid;
		this.maincategory = maincategory;
		this.middlecategory = middlecategory;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Long getGid() {
		return gid;
	}
	public void setGid(Long gid) {
		this.gid = gid;
	}
	public String getMaincategory() {
		return maincategory;
	}
	public void setMaincategory(String maincategory) {
		this.maincategory = maincategory;
	}
	public String getMiddlecategory() {
		return middlecategory;
	}
	public void setMiddlecategory(String middlecategory) {
		this.middlecategory = middlecategory;
	}
}
