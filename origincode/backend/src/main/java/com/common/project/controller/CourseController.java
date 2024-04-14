package com.common.project.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.common.project.dao.CourseDao;
import com.common.project.dao.MemberDao;
import com.common.project.dao.PostDao;
import com.common.project.dao.course.CourseInfoClassDao;
import com.common.project.dao.course.CourseListDao;
import com.common.project.dao.course.InflearnTotalListDao;
import com.common.project.model.course.CourseInfo;
import com.common.project.model.course.CourseInfoClass;
import com.common.project.model.course.CourseList;
import com.common.project.model.course.InflearnTotalList;
import com.common.project.model.member.Member;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Transactional
@Api(tags = "CourseController", description = "강의관련 API")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
public class CourseController {
	@Autowired
	 CourseDao courseDao;
	@Autowired
	PostDao postDao;
	@Autowired
	InflearnTotalListDao inflearnTotalListDao;
	@Autowired
	CourseInfoClassDao courseInfoClassDao;
	@Autowired
	CourseListDao courseListDao;
	@Autowired
	MemberDao memberDao;
	
//	1. 프로필 들어오면 수강하는 강의가 카드 리스트들로 보인다. 카드 세션에는 수강하는 사진, 강의 제목, 등등이 노출된다.=> user_course에서 uid로 현재 있는 gid의 정보들을 course_info와 조회하여	정보들을 같이 조회한다.
	@GetMapping("/course/cardlist")
	@ApiOperation(value = "현재 프로필의 uid, 프로필에서 카드리스트 형태로 볼 때", notes = "첫 프로필 화면 수강 강의 카드 리스트. 강의 제목, 저자, 강의 이미지, 인프런 강의 사이트로 이동 주소 정보 반환")
	public Object courseCardList(String email) {
		
		Member member = memberDao.getMemberByEmail(email);
		Optional<List<CourseInfo>> cardList  = courseDao.findUserInfoCourseByUid(member.getUid());// uid로 조회해서 해당하는 gid의 값들을 가져온다.
		
		if(cardList.isPresent()) {
			Collections.reverse(cardList.get());
			return new ResponseEntity<>(cardList, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("", HttpStatus.NOT_ACCEPTABLE);
		}
	}
	

//	+를 눌러서 새로운 강의를 생성한다., 1. 강의를 가져올 플랫폼을 선택 후, 해당 강의 제목을 검색한다. => 1)인프런 선택시, 인프런 토탈 리스트에서 모든 제목 정보를 보내준다. - 뷰에서 드롭다운으로 노출	
	@GetMapping("/course/keywordList")
	@ApiOperation(value = "조회될 강의 키워드 리스트", notes = "타입 1은 인프런. 사용 시 모든 강의 이름 반환")
	public Object keywordList(int coursetype) {
			Optional<List<InflearnTotalList>> course = inflearnTotalListDao.findByAllCourseKeywordList(coursetype);

			if (course.isPresent()) {
				return new ResponseEntity<>(course, HttpStatus.OK);// 존재하면
			}
			else {
				return new ResponseEntity<>("", HttpStatus.OK);// 없으면
			}
	}
	
// 2. 강좌를 생성한다. 생성 파트
	@GetMapping("/course/createCourse")
	@ApiOperation(value = "검색한 키워드로 강의를 생성한다.", notes = "keyword가 강의 명과 동일해야한다.")
	public Object createCourse(Long uid, int coursetype, String keyword) {
		//=>1)강의 제목으로 생성할 때, 현재 내 수강 리스트에 있는지 없는지 체크를핸다.
		Optional<InflearnTotalList> isKeyword = inflearnTotalListDao.findByAllCourseKeyword(coursetype, keyword);

		if(!isKeyword.isPresent()) {
			return new ResponseEntity<>("Wrong Name", HttpStatus.NOT_ACCEPTABLE);// 해당 키워드가 전체 수강 목록에 없다면 글을 잘못 입력한것
		}
		
		// 이제 생성가능한데 채ㅕㄴㄷㄱ_info에 있는지 없는지, 없으면 하나 만들기
		Optional<CourseInfo> courseInfo = courseDao.findByCourseAndCoursetype(keyword, coursetype);
		CourseInfo courseinfo = new CourseInfo();
		if(!courseInfo.isPresent()) { // 만약에 courseinfo에도 없으면. 크롤링
			courseinfo = courseDao.save(new CourseInfo(isKeyword.get().getCoursetype(), isKeyword.get().getCourse(), isKeyword.get().getCourseurl(), isKeyword.get().getImageurl(), isKeyword.get().getInstructor()));
			inflearnCrawling(courseinfo.getGid(), courseinfo.getCourseurl());	// course info class 에 저장한다.
		}
		else {
			courseinfo = courseInfo.get();
		}
		
		//  내 수강 리스트에 있는지 체크.
		Optional<CourseInfo> myIsKeyword = courseDao.findByMyCourseKeyword(uid, coursetype, keyword);
		if(myIsKeyword.isPresent()) {		// 현재 수강 목록에 있는것 . 생성 불가
			return new ResponseEntity<>("Duplicate", HttpStatus.NOT_ACCEPTABLE);
		}
		
		// 그리고 user_courseㅇ에 저장을 한다.
		courseDao.saveUserCourseByUidAndGid(uid, courseinfo.getGid()); // optional이나 중복체크 안해도 되겠지?
		
		// 이제 페이지를 생성해야한다
		List<CourseInfoClass> courseInfoClass = courseInfoClassDao.findCourseInfoClassByGid(courseinfo.getGid());
		
		for(int i = 0; i < courseInfoClass.size(); i++) {//course_list에 생성한다. 전체 리스트
			courseDao.saveCourseList(uid, courseinfo.getGid(), courseInfoClass.get(i).getMaincategory(),  courseInfoClass.get(i).getMiddlecategory());
		}

		return new ResponseEntity<>("success", HttpStatus.OK);
	}
	
	//강의를 본다
	//1. 카드 섹션을 클릭할 때 해당 강의 리스트 출력한다.
	@GetMapping("/course/courseList")
	@ApiOperation(value = "해당 프로필 uid와 클릭한 카드 gid", notes = "해당 카드 섹션의 목차 정보 전송")
	public Object courseList(String email, Long gid) {
		//여기도
		Member member = memberDao.getMemberByEmail(email);
		List<CourseList> courseList = courseListDao.findCourseList(member.getUid(), gid);
		
		List<List<CourseList>> result = new ArrayList<List<CourseList>>();
		
		for(int i  = 0 ;i  < courseList.size(); i++) {
			List<CourseList> courseListClass = new ArrayList<CourseList>();
			String temp = courseList.get(i).getMaincategory();
			CourseList tempList = new CourseList(courseList.get(i).getClid(), courseList.get(i).getUid(), courseList.get(i).getGid(), courseList.get(i).getMaincategory(), temp, courseList.get(i).getPid());
			tempList.setMiddlecategory(temp);
			courseListClass.add(tempList);
			
			for(int j = i; j < courseList.size(); j++) {
				if(temp.equals(courseList.get(j).getMaincategory())){
					courseListClass.add(courseList.get(j));
				}
				else {
					i = j - 1;
					break;
				}
			}
			result.add(courseListClass);
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);// 없으면
	} 
	
	//강의를 삭제한다. - 다시. 카드 리스트에서 삭제한다. 종속된 것 전체 삭제 이건 나중에. 리스트 자체를 삭제한다.
	@DeleteMapping("/course/courseDelete")
	@ApiOperation(value = "삭제. 사용 금지", notes = "카드 삭제")
	public Object courseDelete(String email, Long gid) {
		Member member = memberDao.getMemberByEmail(email); // uid 구함.

		courseListDao.deleteUserCourseByUidAndGid(member.getUid(), gid);
		
		List<CourseList> courseList =  courseListDao.findCourseList(member.getUid(),  gid);
		// 각 페이지 삭제
		for(int i = 0; i < courseList.size(); i++) {
			if(courseList.get(i).getPid() != 0) {
				postDao.deletePostByPid(courseList.get(i).getPid());
			}
		}
		
		courseListDao.deleteByUidAndGid(member.getUid(), gid);
		
		return new ResponseEntity<>("success", HttpStatus.OK);// 없으면
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////
	
	/* 크롤링 파트*/
	public void inflearnCrawling(Long gid,String url) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		Elements element = doc.select("div.cd-accordion__section-cover");
		Iterator<Element> ie1 = element.select("span.cd-accordion__section-title").iterator();
	
		int cnt = 0;
		while(ie1.hasNext()) {
			String bigClass = ie1.next().text();
			Iterator<Element> ie2 = element.get(cnt++).select("span.ac-accordion__unit-title").iterator();
			while(ie2.hasNext()) {
				courseDao.insertCourseInfoClass(gid,bigClass, ie2.next().text());
			}
		}
	}
	
	///////////// DB에 넣을 인프런 전체 강의 목록가져오기 -> 1회 -> 나중에 주 1회 등. 가져왔으니 주석처리.
	@GetMapping("/course/inflearn")
	@ApiOperation(value = "인프런 모든 강의 크롤링. 사용 금지")
	public Object inflearnTotalSave() {
		courseDao.deleteInflearnAll();
		String base_url = "https://www.inflearn.com/courses?order=seq&page=";

		for (int i = 1; i <= 50; i++) {
			String url = base_url + i;
			Document doc = null;
			try {
				doc = Jsoup.connect(url).get();
			} catch (IOException e) {
				e.printStackTrace();
			}

			Elements element = doc.select("div.courses_card_list_body");
			Iterator<Element> ie1 = element.select("div.course_title").iterator(); // 강의 이름
			Iterator<Element> ieUrl = element.select("a.course_card_front").iterator();// 강의 url
			Iterator<Element> ieImgUrl = element.select("img.swiper-lazy").iterator(); // 강의 이미지 url
			Iterator<Element> ieInstructor = element.select("div.instructor").iterator(); // 강사이름
			while (ie1.hasNext()) {
				courseDao.insertInflearn(1, ie1.next().text(), "https://www.inflearn.com" + ieUrl.next().attr("href"), ieImgUrl.next().attr("src"), ieInstructor.next().text());
			}
		}
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
}