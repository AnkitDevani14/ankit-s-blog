package com.project.blog.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.sun.istack.NotNull;



@Entity
public class Category {
	
	@Id
	@NotNull
	private String category;
	
	public Category() {
		// TODO Auto-generated constructor stub
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Category(String category) {
		
		this.category = category;
	}
	
}
