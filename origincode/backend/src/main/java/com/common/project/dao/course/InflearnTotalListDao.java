package com.common.project.dao.course;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.common.project.model.course.InflearnTotalList;

public interface InflearnTotalListDao extends JpaRepository<InflearnTotalList,Long> {
	@Query(value = "SELECT * FROM inflearn_total_list WHERE coursetype = ?1 AND course = ?2 ", nativeQuery = true)
	Optional<InflearnTotalList> findByAllCourseKeyword(int coursetype, String course);
	
	@Query(value = "SELECT * FROM inflearn_total_list i where i.coursetype = ?1 ", nativeQuery = true)
	Optional<List<InflearnTotalList>> findByAllCourseKeywordList(int check);
}
