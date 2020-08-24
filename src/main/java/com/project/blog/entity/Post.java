package com.project.blog.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sun.istack.NotNull;

@Entity
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long pid;
	
	@NotNull
	private String category;
	
	@NotNull
	private String content;
	
	private String title;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Post(long pid, String category, String content, String title, String username, Timestamp time) {
		super();
		this.pid = pid;
		this.category = category;
		this.content = content;
		this.title = title;
		this.username = username;
		Time = time;
	}

	public Post(String category, String content, String title, String username, Timestamp time) {
		super();
		this.category = category;
		this.content = content;
		this.title = title;
		this.username = username;
		Time = time;
	}

	private String username;
	
	private Timestamp Time;
	
	public Post(String category, String content, String username, String time) {
		super();
		this.category = category;
		this.content = content;
		this.username = username;
		Time = Time;
	}

	public Post(long pid, String category, String content, String username, String time) {
		super();
		this.pid = pid;
		this.category = category;
		this.content = content;
		this.username = username;
		Time = Time;
	}

	public Timestamp getTime() {
		return Time;
	}

	public void setTime(Timestamp ts) {
		Time = ts;
	}

	public Post() {
		// TODO Auto-generated constructor stub
	}

	public long getPid() {
		return pid;
	}

	public String getCategory() {
		return category;
	}

	public String getContent() {
		return content;
	}

	public String getUsername() {
		return username;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Post(long pid, String category, String content, String username) {
		
		this.pid = pid;
		this.category = category;
		this.content = content;
		this.username = username;
	}

	public Post(String category, String content, String username) {
		
		this.category = category;
		this.content = content;
		this.username = username;
	}
	
}
