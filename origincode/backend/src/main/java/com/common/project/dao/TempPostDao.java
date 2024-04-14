package com.common.project.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.common.project.model.temppost.TempPost;

public interface TempPostDao extends JpaRepository<TempPost, Long> {
	List<TempPost> findByEmail(String email);
	Optional<TempPost> deletePostByTpid(Long tpid);
	
	Optional<TempPost> deleteByTpid(Long tpid);
	
	Optional<List<TempPost>> findByTpid(Long tpid);
}
