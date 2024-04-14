package com.common.project.model.tag;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tag")
public class Tag {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tid;
	private String name;
	public Tag() {
		super();
	}
	public Tag(Long tid, String name) {
		super();
		this.tid = tid;
		this.name = name;
	}
	public void setTid(Long tid) {
		this.tid = tid;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getTid() {
		return tid;
	}
	public String getName() {
		return name;
	}
}
