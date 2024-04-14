package com.common.project.model.course;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "course_info")
public class CourseInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long gid;
	private int coursetype;
	private String course;
	private String courseurl;
	private String imageurl;
	private String instructor;
	public CourseInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CourseInfo(Long gid,int coursetype, String course, String courseurl, String imageurl, String instructor) {
		super();
		this.gid = gid;
		this.coursetype = coursetype;
		this.course = course;
		this.courseurl = courseurl;
		this.imageurl = imageurl;
		this.instructor = instructor;
	}
	public CourseInfo(String course, String courseurl, String imageurl, String instructor) {
		super();
		this.course = course;
		this.courseurl = courseurl;
		this.imageurl = imageurl;
		this.instructor = instructor;
	}
	public CourseInfo(int coursetype, String course, String courseurl, String imageurl, String instructor) {
		super();
		this.coursetype = coursetype;
		this.course = course;
		this.courseurl = courseurl;
		this.imageurl = imageurl;
		this.instructor = instructor;
	}
	
	
	public int getCoursetype() {
		return coursetype;
	}
	public void setCoursetype(int coursetype) {
		this.coursetype = coursetype;
	}
	public Long getGid() {
		return gid;
	}
	public void setGid(Long gid) {
		this.gid = gid;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getCourseurl() {
		return courseurl;
	}
	public void setCourseurl(String courseurl) {
		this.courseurl = courseurl;
	}
	public String getImageurl() {
		return imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	public String getInstructor() {
		return instructor;
	}
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

}
