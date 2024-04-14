package com.common.project.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.common.project.model.tag.PostTag;

public interface PostTagDao extends JpaRepository<PostTag, Long>{
	
	@Modifying
	@Query(value = "delete from post_tag where pid = ?1 ", nativeQuery = true)
	 public void deleteByPid(Long pid);
	
	@Query(value = "select B.name from post_tag A inner JOIN tag B ON A.pid = ?1 AND A.tid = B.tid ", nativeQuery = true)
	public Optional<List<String>> findPostTagJoinTagByPid(Long pid);

	public Optional<List<PostTag>> findByTid(Long tid);
	
	@Query(value = "select A.* from post_tag A inner JOIN tag B ON A.uid = ?1 AND A.tid = B.tid AND B.name = ?2 ", nativeQuery = true)
	public Optional<List<PostTag>> findByPidAndTid(Long uid, String name);
}
