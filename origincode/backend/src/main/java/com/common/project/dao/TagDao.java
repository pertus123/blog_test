package com.common.project.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.common.project.model.tag.Tag;

public interface TagDao extends JpaRepository<Tag, Long> {
	@Query(value = "select * from tag where name = ?1 ", nativeQuery = true)
	 public Optional<Tag> findByName(String name);

	@Query(value = "select B.* from tag B inner JOIN post_tag A ON A.uid = ?1 AND A.tid = B.tid", nativeQuery = true)
	public Optional<List<Tag>> findPostTagJoinTagByUid(Long uid);
}