package com.common.project.model.tag;

import java.util.List;

public class TagInput {
	private Long pid;
	private List<String> tags;
	private Long uid;
	public TagInput() {
		super();
	}
	public TagInput(Long pid, List<String> tags, Long uid) {
		super();
		this.pid = pid;
		this.tags = tags;
		this.uid = uid;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
}
