package com.common.project.model.tag;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "post_tag")
public class PostTag {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ptid;
	private Long pid;
	private Long tid;
	private Long uid;
	
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public PostTag() {
		super();
	}
	
	public PostTag(Long pid, Long tid, Long uid) {
		super();
		this.pid = pid;
		this.tid = tid;
		this.uid = uid;
	}
	public PostTag(Long pid, Long tid) {
		super();
		this.pid = pid;
		this.tid = tid;
	}
	public Long getPtid() {
		return ptid;
	}
	public void setPtid(Long ptid) {
		this.ptid = ptid;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public Long getTid() {
		return tid;
	}
	public void setTid(Long tid) {
		this.tid = tid;
	}
}
