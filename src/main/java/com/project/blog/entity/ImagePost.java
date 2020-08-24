package com.project.blog.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.br.TituloEleitoral;

import com.sun.istack.NotNull;

@Entity
public class ImagePost {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long pimageid;
	
	@NotNull
	private String icategory;
	
	@NotNull
	private String icontent;
	
	@NotNull
	private String ititle;
	
	@NotNull
	private String ipath;
	
	@NotNull
	private String username;
	

	private Timestamp itime;
	
	public ImagePost() {
		// TODO Auto-generated constructor stub
	}

	public long getPimageid() {
		return pimageid;
	}

	public String getIcategory() {
		return icategory;
	}

	public String getIcontent() {
		return icontent;
	}

	public String getItitle() {
		return ititle;
	}

	public String getIpath() {
		return ipath;
	}

	public String getUsername() {
		return username;
	}

	public Timestamp getItime() {
		return itime;
	}

	public void setPimageid(long pimageid) {
		this.pimageid = pimageid;
	}

	public void setIcategory(String icategory) {
		this.icategory = icategory;
	}

	public void setIcontent(String icontent) {
		this.icontent = icontent;
	}

	public void setItitle(String ititle) {
		this.ititle = ititle;
	}

	public void setIpath(String ipath) {
		this.ipath = ipath;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setItime(Timestamp itime) {
		this.itime = itime;
	}

	public ImagePost(long pimageid, String icategory, String icontent, String ititle, String ipath, String username,
			Timestamp itime) {
		
		this.pimageid = pimageid;
		this.icategory = icategory;
		this.icontent = icontent;
		this.ititle = ititle;
		this.ipath = ipath;
		this.username = username;
		this.itime = itime;
	}

	public ImagePost(String icategory, String icontent, String ititle, String ipath, String username, Timestamp itime) {
		
		this.icategory = icategory;
		this.icontent = icontent;
		this.ititle = ititle;
		this.ipath = ipath;
		this.username = username;
		this.itime = itime;
	}
	
}
