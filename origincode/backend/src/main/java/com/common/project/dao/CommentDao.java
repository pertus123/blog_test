package com.common.project.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.common.project.model.comment.Comment;

public interface CommentDao  extends JpaRepository<Comment, Long>{
	Optional<Comment> deleteBycid(Long cid);
	@Query(value = "select * from comment where comment.pid = ?1 order by create_date", nativeQuery = true)
	List<Comment> getByPidOrderByCreateDateAsc(Long pid);
	
	@Query(value = "select * from comment where comment.pid = ?1", nativeQuery = true)
	Optional<List<Comment>> findByPidAndUid(Long Pid);
}
