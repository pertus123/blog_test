package com.common.project.dao.course;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.common.project.model.course.CourseInfoClass;

public interface CourseInfoClassDao extends JpaRepository<CourseInfoClass, Long>{
	
	@Query(value = "select * from course_info_class where gid = ?1 ", nativeQuery = true)
	List<CourseInfoClass> findCourseInfoClassByGid(Long gid);
}
