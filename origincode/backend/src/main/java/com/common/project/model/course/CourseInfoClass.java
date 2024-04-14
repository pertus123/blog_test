package com.common.project.model.course;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "course_info_class")
public class CourseInfoClass {	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cicid;
	private Long gid;
	private String maincategory;
	private String middlecategory;
	
	public Long getCicid() {
		return cicid;
	}
	public void setCicid(Long cicid) {
		this.cicid = cicid;
	}
	public CourseInfoClass(Long cicid, Long gid, String maincategory, String middlecategory) {
		super();
		this.cicid = cicid;
		this.gid = gid;
		this.maincategory = maincategory;
		this.middlecategory = middlecategory;
	}
	public CourseInfoClass() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CourseInfoClass(Long gid, String maincategory, String middlecategory) {
		super();
		this.gid = gid;
		this.maincategory = maincategory;
		this.middlecategory = middlecategory;
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
