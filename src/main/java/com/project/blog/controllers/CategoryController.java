package com.project.blog.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.blog.entity.Category;

import com.project.blog.repo.CategoryRepo;

@Controller
public class CategoryController {
	@Autowired
	CategoryRepo categoryRepo;
	
	@GetMapping("/addCategory")
	public String addCategory(Model model) {
		model.addAttribute("cat",categoryRepo.findAll());
		return "category/addCategory";
	}
	
	@PostMapping("/addCategory")
	public String Category(Model model,Category category) {
		
		
		categoryRepo.save(category);
		model.addAttribute("success","Category Added..");
		
		return "category/addCategory";
	}
	
	@GetMapping("/categoryDelete{id}")
	public String TextDelete(Model model,@PathVariable String id) {
		categoryRepo.deleteById(id);
		
		model.addAttribute("errorDelte","Post Deleted Successfully!!");
		
		
		
		model.addAttribute("cat",categoryRepo.findAll());
		return "category/addCategory";
	}
}
