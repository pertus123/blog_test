package com.common.project.model.course;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "inflearn_total_list")
public class InflearnTotalList { 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long itlid;
	private int coursetype;
	private String course;
	private String courseurl;
	private String imageurl;
	private String instructor;
	public InflearnTotalList() {
		super();
	}

	public InflearnTotalList(String course, String courseurl, String imageurl, String instructor) {
		super();
		this.course = course;
		this.courseurl = courseurl;
		this.imageurl = imageurl;
		this.instructor = instructor;
	}
	
	public InflearnTotalList(int coursetype, String course, String courseurl, String imageurl, String instructor) {
		super();
		this.coursetype = coursetype;
		this.course = course;
		this.courseurl = courseurl;
		this.imageurl = imageurl;
		this.instructor = instructor;
	}
	
	public InflearnTotalList(int coursetype, String course) {
		super();
		this.coursetype = coursetype;
		this.course = course;
	}
	
	public int getCoursetype() {
		return coursetype;
	}
	public void setCoursetype(int coursetype) {
		this.coursetype = coursetype;
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
