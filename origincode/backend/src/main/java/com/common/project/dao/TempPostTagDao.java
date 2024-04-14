package com.common.project.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.common.project.model.tag.TempPostTag;

public interface TempPostTagDao extends JpaRepository< TempPostTag, Long> {
	@Modifying
	@Query(value = "delete from temp_post_tag where tpid = ?1 ", nativeQuery = true)
	 public void deleteByPid(Long tpid);
	
	@Query(value = "select B.name from temp_post_tag A inner JOIN tag B ON A.tpid = ?1 AND A.tid = B.tid ", nativeQuery = true)
	public Optional<List<String>> findTempPostTagJoinTagByPid(Long tpid);
}
