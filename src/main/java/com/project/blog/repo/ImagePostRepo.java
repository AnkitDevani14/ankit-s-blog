package com.project.blog.repo;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.blog.entity.ImagePost;

@Repository
public interface ImagePostRepo extends JpaRepository<ImagePost, Long>{

	List<ImagePost> findByUsername(String username, Sort sort);

	List<ImagePost> findByicategory(String icategory, Sort sort);

}
