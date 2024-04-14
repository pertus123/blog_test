package com.common.project.model.like;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "post_like")
public class Like {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long lid;
	private Long pid;
	private Long uid;
	
	public Long getLid() {
		return lid;
	}
	public void setLid(Long lid) {
		this.lid = lid;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public Like() {
		super();
	}
	public Like(Long lid, Long pid, Long uid) {
		super();
		this.lid = lid;
		this.pid = pid;
		this.uid = uid;
	}
	public Like(Long pid, Long uid) {
		super();
		this.pid = pid;
		this.uid = uid;
	}
}
