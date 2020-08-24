package com.project.blog.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.blog.entity.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, String>{

}
