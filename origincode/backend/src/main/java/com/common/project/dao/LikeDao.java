package com.common.project.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.common.project.model.like.Like;

public interface LikeDao extends JpaRepository<Like, Long> {
	
	//좋아요
	@Query(value = "select * from post_like p where p.pid = ?1 and p.uid =?2", nativeQuery = true)
	 Optional<Like> getLikeByPidAndUid(Long pid, Long uid);
	
	// 안좋아요-> 좋아요
	@Modifying
	@Transactional
	@Query(value = "insert into post_like (pid, uid) values (?1, ?2)", nativeQuery = true)
	 void insertLikeByPidAndUid(Long pid, Long uid);
	
	// 좋아요 -> 안좋아요
	@Modifying
	@Transactional
	@Query(value = "delete FROM post_like where pid = ?1 and uid =?2", nativeQuery = true)
	void removeLikeByPidAndUid(Long pid, Long uid);

}
