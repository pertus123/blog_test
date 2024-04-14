package com.common.project.model.tag;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "temp_post_tag")
public class TempPostTag {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tptid;
	private Long tpid;
	private Long tid;
	private Long uid;
	
	public TempPostTag() {
		super();
	}

	public TempPostTag(Long tptid, Long tpid, Long tid, Long uid) {
		super();
		this.tptid = tptid;
		this.tpid = tpid;
		this.tid = tid;
		this.uid = uid;
	}

	public Long getTptid() {
		return tptid;
	}

	public void setTptid(Long tptid) {
		this.tptid = tptid;
	}

	public Long getTpid() {
		return tpid;
	}

	public void setTpid(Long tpid) {
		this.tpid = tpid;
	}

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}
}