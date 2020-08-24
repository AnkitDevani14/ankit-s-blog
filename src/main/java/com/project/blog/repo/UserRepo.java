package com.project.blog.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.blog.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{

	User findByUsername(String username);

	User findByEmailIgnoreCase(String email);

}
