package com.common.project.model.portfolio;

public class InputPortfolio {
	private Long fid;
	private String email;
	private String content;
	
	public InputPortfolio(Long fid, String email, String content) {
		this.fid = fid;
		this.email = email;
		this.content = content;
	}

	public Long getFid() {
		return fid;
	}

	public void setFid(Long fid) {
		this.fid = fid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
