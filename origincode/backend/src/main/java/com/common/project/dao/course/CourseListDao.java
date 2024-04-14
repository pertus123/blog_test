package com.common.project.dao.course;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.common.project.model.course.CourseList;

public interface CourseListDao extends JpaRepository<CourseList, Long> {
	@Query(value = "select * from course_list where uid = ?1 and gid = ?2", nativeQuery = true)
	List<CourseList> findCourseList(Long uid, Long gid);
	
	@Query(value = "select * from course_list where uid = ?1 and gid = ?2 and maincategory = ?3 and middlecategory = ?4 ", nativeQuery = true)
	CourseList findCourseListAll(Long uid, Long gid, String maincategory, String middlecategory);
	
	// clid로 해당 course_list를 가져온다.
	@Query(value = "select uid, gid,pid, maincategory, middlecategory,clid from course_list where clid = ?1 ", nativeQuery = true)
	Optional<CourseList> findByClid(Long clid);
	@Query(value = "select uid, gid,pid, maincategory, middlecategory,clid from course_list where pid = ?1 ", nativeQuery = true)
	Optional<CourseList> findByPid(Long pid);
	
	//delete 부분.  user course, course_list 삭제
	@Modifying
	@Query(value = "delete from user_course where uid = ?1 and gid = ?2 ", nativeQuery = true)
	void deleteUserCourseByUidAndGid(Long uid, Long gid);
	@Modifying
	@Query(value = "delete from course_list where uid = ?1 and gid = ?2 ", nativeQuery = true)
	void deleteByUidAndGid(Long uid, Long gid);
	
	List<CourseList> findByUidAndGid(Long uid, Long gid);
}
