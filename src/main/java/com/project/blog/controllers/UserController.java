package com.project.blog.controllers;

import javax.persistence.PostRemove;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.project.blog.entity.Category;
import com.project.blog.repo.CategoryRepo;
import com.project.blog.repo.DailyRepo;
import com.project.blog.repo.postRepo;

public class UserController {
	
	@Autowired
	CategoryRepo categoryRepo;
	
	@Autowired
	postRepo postRepo;
	
	@Autowired
	DailyRepo dailyRepo;
	
	
	
	
	
	
}
