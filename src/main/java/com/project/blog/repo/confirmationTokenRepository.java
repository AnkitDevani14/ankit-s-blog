package com.project.blog.repo;

import org.springframework.data.repository.CrudRepository;

import com.project.blog.controllers.ConfirmationToken;

public interface confirmationTokenRepository extends CrudRepository<ConfirmationToken, String>{
	ConfirmationToken findByConfirmationToken(String confirmationToken);
}
