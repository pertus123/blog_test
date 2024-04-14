package com.common.project.model.course;

public class CardList {
	private String courseTitle;
	private String courseurl;
	private String imageurl;
	private String instructor;
	
	public CardList() {
		super();
	}
	public CardList(String courseTitle, String courseurl, String imageurl, String instructor) {
		super();
		this.courseTitle = courseTitle;
		this.courseurl = courseurl;
		this.imageurl = imageurl;
		this.instructor = instructor;
	}
	public String getCourseTitle() {
		return courseTitle;
	}
	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
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
