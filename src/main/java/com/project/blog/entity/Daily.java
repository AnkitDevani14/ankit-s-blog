package com.project.blog.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sun.istack.NotNull;

@Entity
public class Daily {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long did;
	
	@NotNull
	private String category;
	
	@NotNull
	private String title;
	
	@NotNull
	private String content;
	
	@NotNull
	private Date date;
	
	public Daily() {
		// TODO Auto-generated constructor stub
	}

	public long getDid() {
		return did;
	}

	public String getCategory() {
		return category;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public Date getDate() {
		return date;
	}

	public void setDid(long did) {
		this.did = did;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Daily(long did, String category, String title, String content, Date date) {
		
		this.did = did;
		this.category = category;
		this.title = title;
		this.content = content;
		this.date = date;
	}

	public Daily(String category, String title, String content, Date date) {
		super();
		this.category = category;
		this.title = title;
		this.content = content;
		this.date = date;
	}
	
	
	
}
