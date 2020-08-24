package com.project.blog.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.blog.entity.Daily;

@Repository
public interface DailyRepo extends JpaRepository<Daily, Long>{

	List<Daily> findFirstByOrderByDateAsc();



}
