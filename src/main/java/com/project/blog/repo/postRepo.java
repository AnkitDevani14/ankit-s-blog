package com.project.blog.repo;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.project.blog.entity.Post;

@Repository
public interface postRepo extends JpaRepository<Post, Long>{

	List<Post> findByUsername(String username, Sort sort);

	List<Post> findByCategory(String categoty, Sort sort);

	



	
	
}
