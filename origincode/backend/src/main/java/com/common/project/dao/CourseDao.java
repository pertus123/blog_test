package com.common.project.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.common.project.model.course.CourseInfo;
import com.common.project.model.course.CoursePost;

public interface CourseDao extends JpaRepository<CourseInfo, Long> {

	//인프런

	@Modifying
	@Query(value = "delete from inflearn_total_list ", nativeQuery = true)
	 void deleteInflearnAll();
	
	@Modifying
	@Query(value = "insert into inflearn_total_list (coursetype, course, courseurl, imageurl, instructor) values (?1, ?2, ?3, ?4, ?5) ", nativeQuery = true)
	 void insertInflearn(int coursetype, String course, String courseurl, String imageurl, String instructor);
	
	// 강의 정보 카드섹션 부분
	@Query(value = "SELECT A.* FROM course_info A INNER JOIN user_course B ON B.uid = ?1 AND B.gid = A.gid ", nativeQuery = true)
	 Optional<List<CourseInfo>> findUserInfoCourseByUid(Long uid);
	

	// 내 수강 목록에 있는지 체크


	@Query(value = "SELECT B.* FROM course_info B INNER JOIN user_course A ON A.uid = ?1 AND B.gid = A.gid AND B.coursetype = ?2 AND B.course = ?3 ", nativeQuery = true)
	public Optional<CourseInfo> findByMyCourseKeyword(Long uid, int coursetype, String keyword);
	
	
	//course info 에 있는지 확인용
	@Query(value = "SELECT B.* FROM course_info B where B.course = ?1 AND B.coursetype = ?2 ", nativeQuery = true)
	public Optional<CourseInfo> findByCourseAndCoursetype(String course, int coursetype);
	
	//course info class 크롤링해서 저장하는 부분
	@Modifying
	@Query(value = "insert into course_info_class (gid, maincategory, middlecategory) values (?1, ?2, ?3) ", nativeQuery = true)
	public void insertCourseInfoClass(Long gid, String bigClass, String middleClass);
	
	// 이제 만들어진 곳에서 강의 타입과 강의 제 목으로 gid를 찾아 가져온다,
	public CourseInfo findByCoursetypeAndCourse(int coursetype, String course);
	
	//user course에 해당 수강자료가 있다는 것 체크
	@Modifying
	@Query(value = "insert into user_course (uid, gid) values (?1, ?2) ", nativeQuery = true)
	void saveUserCourseByUidAndGid(Long uid, Long gid);

	// 이메일 정보 가져오기 uid로
	@Query(value = "select email from Member where uid = ?1 ", nativeQuery = true)
	String findMemberByEmail(Long uid);
	
	// 강의 목록으로 페이지를 생성한다.
	@Modifying
	@Query(value = "insert into course_post (gid, uid, maincategory, middlecategory, email) values (?1, ?2, ?3, ?4, ?5) ", nativeQuery = true)
	void saveCourseClassPage(Long gid, Long uid, String maincategory,String middlecategory, String email);
	
	
	// courseList에 저장한다. 해당 데이터를 .
	@Modifying
	@Query(value = "insert into course_list (uid, gid, maincategory, middlecategory) values (?1, ?2, ?3, ?4) ", nativeQuery = true)
	void saveCourseList(Long uid, Long gid, String maincategory, String middlecategory);
	
	//해당 페이지를 불러온다.
	@Query(value = "select * from course_post where uid = ?1 and gid = ?2 and maincategory = ?3 and middlecategory = ?4", nativeQuery = true)
	CoursePost findCoursePage(Long uid, Long gid, String maincategory, String middlecategory);
	
	@Query(value = "select * from course_post where uid = ?1 and gid = ?2 and maincategory = ?3 and middlecategory = ?4", nativeQuery = true)
	void updateCoursePage(Long uid, Long gid, String maincattegory, String middlecategory, String email, String content);
	
	//삭제
	@Modifying
	@Query(value = "delete from course_post where uid = ?1 and gid = ?2 ", nativeQuery = true)
	void deleteCoursePost(Long uid, Long gid);
	@Modifying
	@Query(value = "delete from course_list where uid = ?1 and gid = ?2 ", nativeQuery = true)
	void deleteCourseList(Long uid, Long gid);
	@Modifying
	@Query(value = "delete from user_course where uid = ?1 and gid = ?2 ", nativeQuery = true)
	void deleteUserCourse(Long uid, Long gid);
}

