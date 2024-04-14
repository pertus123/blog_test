package com.common.project.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.common.project.dao.CommentDao;
import com.common.project.dao.CourseDao;
import com.common.project.dao.LikeDao;
import com.common.project.dao.MemberDao;
import com.common.project.dao.PostDao;
import com.common.project.dao.PostTagDao;
import com.common.project.dao.course.CourseListDao;
import com.common.project.model.comment.Comment;
import com.common.project.model.course.CourseList;
import com.common.project.model.like.Like;
import com.common.project.model.like.LikeOutputPost;
import com.common.project.model.member.Member;
import com.common.project.model.post.InputPost;
import com.common.project.model.post.OutputPostAndTagOriginal;
import com.common.project.model.post.Post;
import com.common.project.model.post.PostDetail;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.common.project.model.post.InfoInputPost;
import com.common.project.model.post.InfoInputPostUrl;


@RestController
@Transactional
@Api(tags = "PostController", description = "게시글 API")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
public class PostController {
	@Autowired
	PostDao postDao;
	@Autowired
	LikeDao likeDao;
	@Autowired
	CommentDao commentDao;
	@Autowired
	MemberDao memberDao;
	@Autowired
	PostTagDao postTagDao;
	@Autowired
	CourseDao courseDao;
	@Autowired
	CourseListDao courseListDao;
	
	@GetMapping("/notice/searchByLike")
	@ApiOperation(value = "좋아요 많은 순으로 가져오기")
	public Object searchByLike(Long uid) { // 내가 좋아요 눌렀는지 판별
		List<Post> postList = postDao.findPostByLike();
		List<LikeOutputPost> likeOutputPost = new ArrayList<LikeOutputPost>();
		
		for(int i = 0; i < postList.size(); i++) {
			Optional<Like> like = likeDao.getLikeByPidAndUid(postList.get(i).getPid(), uid);
			if(like.isPresent()) { 
				likeOutputPost.add(new LikeOutputPost(postList.get(i).getPid(),postList.get(i).getTitle(),postList.get(i).getEmail(),postList.get(i).getContent(),postList.get(i).getLikes(),postList.get(i).getCreate_date(),1, postList.get(i).getPostimage()));
			}
			else { 
				likeOutputPost.add(new LikeOutputPost(postList.get(i).getPid(),postList.get(i).getTitle(),postList.get(i).getEmail(),postList.get(i).getContent(),postList.get(i).getLikes(),postList.get(i).getCreate_date(),0, postList.get(i).getPostimage()));
			}
			Optional<List<Comment>> isComment = commentDao.findByPidAndUid(postList.get(i).getPid());
			if(isComment.isPresent()) { 
				likeOutputPost.get(i).setCommentCnt(isComment.get().size());
			}
			else {
				likeOutputPost.get(i).setCommentCnt(0);
			}
			Member member = memberDao.getMemberByEmail(postList.get(i).getEmail());
			likeOutputPost.get(i).setProfileimage(member.getProfileimage());
			
		}
		return new ResponseEntity<>(likeOutputPost, HttpStatus.OK);
	}

	@GetMapping("/notice/searchAllPage")
	@ApiOperation(value = "전체 검색")
	public Object searchAllPage(Long uid) {
		List<Post> postList = postDao.findAll();
		List<LikeOutputPost> likeOutputPost = new ArrayList<LikeOutputPost>();
		
		for(int i = 0; i < postList.size(); i++) {
			Optional<Like> like = likeDao.getLikeByPidAndUid(postList.get(i).getPid(), uid);
			if(like.isPresent()) { // 좋아요 있으면
				likeOutputPost.add(new LikeOutputPost(postList.get(i).getPid(),postList.get(i).getTitle(),postList.get(i).getEmail(),postList.get(i).getContent(),postList.get(i).getLikes(),postList.get(i).getCreate_date(),1, postList.get(i).getPostimage()));
			}
			else {
				likeOutputPost.add(new LikeOutputPost(postList.get(i).getPid(),postList.get(i).getTitle(),postList.get(i).getEmail(),postList.get(i).getContent(),postList.get(i).getLikes(),postList.get(i).getCreate_date(),0, postList.get(i).getPostimage()));
			}
			Optional<List<Comment>> isComment = commentDao.findByPidAndUid(postList.get(i).getPid());
			if(isComment.isPresent()) { // 댓글 있으면
				likeOutputPost.get(i).setCommentCnt(isComment.get().size()); // 개수
			}
			else {
				likeOutputPost.get(i).setCommentCnt(0);
			}
			
			// 프사 추가
			Member member = memberDao.getMemberByEmail(postList.get(i).getEmail());
			likeOutputPost.get(i).setProfileimage(member.getProfileimage());
			
		}
		return new ResponseEntity<>(likeOutputPost, HttpStatus.OK);
	}

	@GetMapping("/notice/searchByKeyword")
	@ApiOperation(value = "검색이나 블로그 검색 할 때 글 리스트가져오기")
	public Object searchPageByKeyword(@RequestParam(value = "keyword") String keyword, Long uid) {

		List<Post> postList = postDao.findPostByTitleOrContent(keyword);
		List<LikeOutputPost> likeOutputPost = new ArrayList<LikeOutputPost>();
		
		for(int i = 0; i < postList.size(); i++) {
			Optional<Like> like = likeDao.getLikeByPidAndUid(postList.get(i).getPid(), uid);
			if(like.isPresent()) { // 좋아요 있으면
				likeOutputPost.add(new LikeOutputPost(postList.get(i).getPid(),postList.get(i).getTitle(),postList.get(i).getEmail(),postList.get(i).getContent(),postList.get(i).getLikes(),postList.get(i).getCreate_date(),1, postList.get(i).getPostimage()));
			}
			else {
				likeOutputPost.add(new LikeOutputPost(postList.get(i).getPid(),postList.get(i).getTitle(),postList.get(i).getEmail(),postList.get(i).getContent(),postList.get(i).getLikes(),postList.get(i).getCreate_date(),0, postList.get(i).getPostimage()));
			}
			Optional<List<Comment>> isComment = commentDao.findByPidAndUid(postList.get(i).getPid());
			if(isComment.isPresent()) { // 댓글 있으면
				likeOutputPost.get(i).setCommentCnt(isComment.get().size()); // 개수
			}
			else {
				likeOutputPost.get(i).setCommentCnt(0);
			}
			
			// 프사 추가
			Member member = memberDao.getMemberByEmail(postList.get(i).getEmail());
			likeOutputPost.get(i).setProfileimage(member.getProfileimage());
						
		}
		return new ResponseEntity<>(likeOutputPost, HttpStatus.OK);

	}

	@GetMapping("/notice/listUserPage")
	@ApiOperation(value = "사용자 페이지 리스트")
	public Object listUserPage(@RequestParam String email) {
		List<Post> postList = postDao.findByEmail(email);
		List<OutputPostAndTagOriginal> outputPostAndTag = new ArrayList<OutputPostAndTagOriginal>();
		for(int i = 0; i < postList.size(); i++) {
			outputPostAndTag.add(new OutputPostAndTagOriginal(postList.get(i).getPid(), postList.get(i).getTitle(), postList.get(i).getEmail(), postList.get(i).getContent(), postList.get(i).getLikes(), postList.get(i).getPostimage(), postList.get(i).getCreate_date(), postTagDao.findPostTagJoinTagByPid(postList.get(i).getPid())));	
		}
		
		return new ResponseEntity<>(outputPostAndTag, HttpStatus.OK);
	}

	@GetMapping("/notice/detailPage/{pid}")
	@ApiOperation(value = "상세페이지")
	public Object detailPage(@PathVariable Long pid) {
		Post onePost = postDao.findPostByPid(pid);
		
		Member member = memberDao.getMemberByEmail(onePost.getEmail());
		PostDetail postDetail = new PostDetail(onePost.getPid(), onePost.getTitle(), onePost.getEmail(), onePost.getContent(), onePost.getLikes(), onePost.getPostimage(), onePost.getCreate_date(), member.getProfileimage());

		return new ResponseEntity<>(postDetail, HttpStatus.OK);
	}

	@PostMapping("/notice/writePage")
	@ApiOperation(value = "글쓰기", httpMethod = "POST", produces = "multipart/form-data")
	public Object writePage(InfoInputPost infoinputPost, Long clid) {

		Post post = new Post(infoinputPost.getTitle(), infoinputPost.getEmail(),
				infoinputPost.getContent(),	infoinputPost.getLikes());
		String saveName = null;
		// post 사진
		if(infoinputPost.getPostSwitch() == 1) {
			String UPLOAD_PATH = "/var/www/html/dist/spa/thumbimg"; // 서버
			UUID uuid = UUID.randomUUID();
			saveName = uuid + "_" + infoinputPost.getPostimage().getOriginalFilename();
			File saveFile = new File(UPLOAD_PATH, saveName);
			try {
				infoinputPost.getPostimage().transferTo(saveFile);
				saveName = "http://3.36.125.70/thumbimg/" + saveName;
				post.setPostimage(saveName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		post.setPostimage(saveName);
		
		//////// 새로추가
		if(infoinputPost.getPid() != null) {
			post.setPid(infoinputPost.getPid());
			if(infoinputPost.getPostSwitch() == 0) {
				Post postImage = postDao.findPostByPid(infoinputPost.getPid());
				if(postImage.getPostimage() != null) { // 기존 이미지가 있으면
					post.setPostimage(postImage.getPostimage());
				}
			}
		}
		/////////
		
		Post postResult = postDao.save(post);
		
		// clid 값이 존재할 경우 -> 강의 목차와 연결 시킴
		if(clid != 0) {
			Optional<CourseList> courseList = courseListDao.findByClid(clid);
			if(courseList.isPresent()) {
				courseList.get().setPid(postResult.getPid());
			}
		}

		return new ResponseEntity<>(post, HttpStatus.OK);
	}
	
	//////////////////// url 받을 때 새로 추가
	@PostMapping("/notice/writePageUrl")
	@ApiOperation(value = "url 따로 포함된 글쓰기", httpMethod = "POST")
	public Object writePageUrl(@RequestBody InfoInputPostUrl infoinputPost, Long clid) {

		Post post;
		if(infoinputPost.getPostSwitch() == 1) {
		 post = new Post(infoinputPost.getPid(), infoinputPost.getTitle(), infoinputPost.getEmail(),
				infoinputPost.getContent(),	infoinputPost.getLikes(), "http://3.36.125.70/thumbimg/" +infoinputPost.getPostimage());
		}
		else {
			 post = new Post(infoinputPost.getPid(), infoinputPost.getTitle(), infoinputPost.getEmail(),
					infoinputPost.getContent(),	infoinputPost.getLikes());
		}
		
		Post postResult = postDao.save(post);
		
		// clid 값이 존재할 경우
		if(clid != 0) {
			Optional<CourseList> courseList = courseListDao.findByClid(clid);
			if(courseList.isPresent()) {
				courseList.get().setPid(postResult.getPid());
			}
		}

		return new ResponseEntity<>(post, HttpStatus.OK);
	}
	//////////////
	
	@PostMapping("/notice/uploadImg")
	@ApiOperation(value = "파일첨부", httpMethod = "POST", produces = "multipart/form-data")
	public Object uploadimg(MultipartFile img) {

		String saveName = null;
		String UPLOAD_PATH = "/var/www/html/dist/spa/postimg"; // 서버
		UUID uuid = UUID.randomUUID();
		saveName = uuid + "_" + img.getOriginalFilename();
		File saveFile = new File(UPLOAD_PATH, saveName);
		try {
			saveName = "http://3.36.125.70/postimg/" + saveName;
			img.transferTo(saveFile);//저장완료
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(saveName, HttpStatus.OK);
	}
	
	@DeleteMapping("/notice/deletePage/{pid}")
	@ApiOperation(value = "글삭제")
	public Object deletePage(@PathVariable Long pid) {
		Optional<Post> postOpt = postDao.deletePostByPid(pid);
		if (postOpt.isPresent()) {
			
			//삭제 할 때,  해당 pid 값으로 찾아서 null로 바꿔줘야함.
			Optional<CourseList> courseList = courseListDao.findByPid(pid);
			if(courseList.isPresent()) {
				courseList.get().setPid((long) 0);
			}

			return new ResponseEntity<>("success", HttpStatus.OK);
		} else
			return new ResponseEntity<>("fail", HttpStatus.NOT_FOUND);
	}

	@PutMapping("/notice/updatePage")
	@ApiOperation(value = "글 수정")
	public Object updatePage(@RequestBody InputPost inputPost) {
		Post post = new Post(inputPost.getPid(), inputPost.getTitle(), inputPost.getEmail(), inputPost.getContent(),
				inputPost.getLikes());
		postDao.save(post);
		return new ResponseEntity<>("success", HttpStatus.OK);
	}

	@Transactional
	@GetMapping("/notice/likeSwitch")
	@ApiOperation(value = "좋아요1&안좋아요0 누를 때 반응")
	public Object likePage(Long pid, Long uid) {
		Optional<Like> like = likeDao.getLikeByPidAndUid(pid,uid);
		Post onePost = postDao.findPostByPid(pid);
		if(like.isPresent()) { // 값이 존재하면, 좋아요 -> 안좋아요
			likeDao.removeLikeByPidAndUid(pid,uid);
			onePost.setLikes(onePost.getLikes() -1);
			postDao.save(onePost);
		}
		else{  // 값이 없어 -> 안좋아요 -> 좋아요
			likeDao.insertLikeByPidAndUid(pid,uid);
			onePost.setLikes(onePost.getLikes() + 1);
			postDao.save(onePost);
		}
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
	
	// 날짜로 정보 가져오기
	@GetMapping("/notice/MonthlyPosts")
	@ApiOperation(value = "월별 글 수. 현재 날짜 기준.")
	public Object MonthlyPosts(String email) {
		List<DateModel> dateModel = new ArrayList<DateModel>();
		// 현재 날짜 정보
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(new Date());
		String finishDate = date.substring(0, 7); // 0부터 7전까지
		for(int i = 0; i < 7; i++) {
			int num = postDao.getByEmailAndDateTime(finishDate+"%", email);
			dateModel.add(new DateModel(finishDate,num));
			// finisgDate 조작
			int year = Integer.parseInt(finishDate.substring(0,4));
			int month = Integer.parseInt(finishDate.substring(5, 7));
			--month;
			if(month == 0) {
				month = 12;
				year--;
			}
			if(month < 10) {
				finishDate = year+"-0"+month;
			}
			else {
				finishDate = year+"-"+month;
			}
		}
		return new ResponseEntity<>(dateModel, HttpStatus.OK);
	}
	
	public class DateModel{ // myLabel 에서 사용하기 위한 것
		String dateTime;
		int num;
		DateModel(){
			super();
		}
		DateModel(String dateTime ,int num){
			super();
			this.dateTime = dateTime;
			this.num = num;
		}
		public int getNum() {
			return num;
		}
		public void setNum(int num) {
			this.num = num;
		}
		public String getDateTime() {
			return dateTime;
		}
		public void setDateTime(String dateTime) {
			this.dateTime = dateTime;
		}
	}
}
