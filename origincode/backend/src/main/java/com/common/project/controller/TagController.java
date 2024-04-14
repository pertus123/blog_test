package com.common.project.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.common.project.dao.CommentDao;
import com.common.project.dao.LikeDao;
import com.common.project.dao.MemberDao;
import com.common.project.dao.PostDao;
import com.common.project.dao.PostTagDao;
import com.common.project.dao.TagDao;
import com.common.project.model.comment.Comment;
import com.common.project.model.like.Like;
import com.common.project.model.like.LikeOutputPost;
import com.common.project.model.member.Member;
import com.common.project.model.post.OutputPostAndTag;
import com.common.project.model.post.Post;
import com.common.project.model.tag.PostTag;
import com.common.project.model.tag.Tag;
import com.common.project.model.tag.TagInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Transactional
@Api(tags = "TagController", description = "태그 API")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
public class TagController {
	
	@Autowired
	private TagDao tagDao;
	@Autowired
	private PostTagDao postTagDao;
	@Autowired
	private PostDao postDao;
	@Autowired
	private MemberDao memberDao;
	@Autowired
	LikeDao likeDao;
	@Autowired
	CommentDao commentDao;
	
//	1.	태그를 post에 쓰는것 : 입력한 태그가  tag에 있는지 없는지 체크하고, 없다면 생성. 그리고 tid 정보와 해당 게시글 쓴자의 정보와 페이지 정보	입력한 태그가 post_tag에 저장된다. 
	@PostMapping("/label/write")
	@ApiOperation(value = "태그 입력", notes="게시글을 쓸 때 같이 호출. 기존에 있던 것은 자동으로 삭제된다.")
	public Object write(@RequestBody TagInput tagInput) { //String tags[],
	postTagDao.deleteByPid(tagInput.getPid());  // 중복이 되지 않기 위해 기존 post_tag 에 있는 태그들 다 삭제한다. pid 기준이다.
	
	for(int i  = 0; i < tagInput.getTags().size(); i++) {
		Optional<Tag> isTag = tagDao.findByName(tagInput.getTags().get(i)); // 1 현재 DB에 태그가 있는지 체크 - tag 테이블에서
		if(!isTag.isPresent()) {	// 2. tag 테이블에 없으면 새로운 태그와 이름을 만들어 넣는다.
			Tag tag = new Tag();
			tag.setName(tagInput.getTags().get(i));
			tagDao.save(tag);
		}
		
		// 3. 위 작업으로 무조건 tag에 있게 된다. tid와 pid의 정보를 post_tag에 저장.
		PostTag postTag = new PostTag();
		postTag.setUid(tagInput.getUid());
		postTag.setPid(tagInput.getPid());
		Optional<Tag> tag = tagDao.findByName(tagInput.getTags().get(i));
		postTag.setTid(tag.get().getTid()); // 이 부분 수정
		postTagDao.save(postTag); //p_t에 내용을 저장한다.
	}
	
	return new ResponseEntity<>("success", HttpStatus.OK);
}
	
//	2.	해당 페이지의 태그를 보는 것 : 페이지를 클릭 시, 해당 pid 정보로 post_tag에서 tag 정보를 조인하여 데이터를 가져온다. 그리고 출력
	@GetMapping("/label/detailLabel")
	@ApiOperation(value = "해당 페이지 태그 가져오기", notes="게시글을 볼 때 호출.태그 반환")
	public Object detailLabel(Long pid) {
	Optional<List<String>> tagName = postTagDao.findPostTagJoinTagByPid(pid);//  pid 기준 전체 노출
	if(tagName.isPresent()){
		return new ResponseEntity<>(tagName, HttpStatus.OK); // 값이 존재하면 반환 
	}
	else {
		return new ResponseEntity<>("failed", HttpStatus.OK); // 존재하지 않으면 X
	}
}
	
//	3.	태그를 클릭했을 때 해당 태그가 포함된 글이 다 보이는 것 : 해당 문자열 tid가 일치할 경우, 그 page를 반환한다. 해당 string으로 태그를 다 검색해서  얻은 pid로 postDao에서 값을 가져오게
	@GetMapping("/label/pageByLabel")
	@ApiOperation(value = "태그 기준 페이지 가져오기", notes="해당 페이지 전체 조회. post 전체 반환, uid는 로그인된 사용자의 uid")
	public Object pageByLabel(String tagName, Long uid) {

	Optional<Tag> tag = tagDao.findByName(tagName);	//  pid 기준 전체 노출
	if(!tag.isPresent()) { // 값이 비어있으면
		return new ResponseEntity<>("faild", HttpStatus.NOT_ACCEPTABLE);
	}
	Optional<List<PostTag>> postTag = postTagDao.findByTid(tag.get().getTid()); // all pid info. 해당 tag에 관련된 postTag 전부 호출
	
	if(!postTag.isPresent()) return new ResponseEntity<>("failed", HttpStatus.NOT_ACCEPTABLE); // 값이 비어있으면. 가져올 수 잆으면
	
	List<Post> postList = new ArrayList<Post>();
	for(int i = 0; i  < postTag.get().size(); i++) { // 가져올 수 있으면, 전체 포스트 반환
		postList.add(postDao.findPostByPid(postTag.get().get(i).getPid()));
	}
	
	//좋아요 댓글, 등등 추가
	List<LikeOutputPost> likeOutputPost = new ArrayList<LikeOutputPost>();
	for(int i = 0; i < postList.size(); i++) {
		Optional<Like> like = likeDao.getLikeByPidAndUid(postList.get(i).getPid(), uid);
		if(like.isPresent()) { // 있으면
			likeOutputPost.add(new LikeOutputPost(postList.get(i).getPid(),postList.get(i).getTitle(),postList.get(i).getEmail(),postList.get(i).getContent(),postList.get(i).getLikes(),postList.get(i).getCreate_date(),1, postList.get(i).getPostimage()));
		}
		else { // 없으면 
			likeOutputPost.add(new LikeOutputPost(postList.get(i).getPid(),postList.get(i).getTitle(),postList.get(i).getEmail(),postList.get(i).getContent(),postList.get(i).getLikes(),postList.get(i).getCreate_date(),0, postList.get(i).getPostimage()));
		}
		Optional<List<Comment>> isComment = commentDao.findByPidAndUid(postList.get(i).getPid());
		if(isComment.isPresent()) { // 댓글 있으면
			likeOutputPost.get(i).setCommentCnt(isComment.get().size()); // 개수
		}
		else {
			likeOutputPost.get(i).setCommentCnt(0);
		}
		Member member = memberDao.getMemberByEmail(postList.get(i).getEmail());
		likeOutputPost.get(i).setProfileimage(member.getProfileimage());
	}
	
	return new ResponseEntity<>(likeOutputPost, HttpStatus.OK); /// 포스트 정보 반환
}

//	4.	내가 쓴 상위 몇개의 태그들을 가져오는 것. uid 기준으로 개수를 센다음, 프론트로 반환한다.
	@GetMapping("/label/myLabel")
	@ApiOperation(value = "내가 쓴 태그 가져오기", notes="태그 가져오기 (태그문자 - 개수) 형태 반환. 개수가 많은 순으로")
	public Object myLabel(String email) {// 이메일로 받기 위해
		Member member = memberDao.getMemberByEmail(email);
		Optional<List<Tag>> userTagName = tagDao.findPostTagJoinTagByUid(member.getUid()); // 사용자 기준으로 tag 가져오기(문자열정보)(tag_post와 조인) uid = uid, tid = tid
		
		if(userTagName.isEmpty()) { // 비어 있으면?
			return new ResponseEntity<>("failed", HttpStatus.NOT_ACCEPTABLE);
		}
		
		List<String> name = new ArrayList<String>();
		
		for(int i = 0;i < userTagName.get().size(); i++) {
			name.add(userTagName.get().get(i).getName()); // 해당 문자열을 a,a,b,b,b,aaa,aaa 이런식으로 만듬
		}
		Collections.sort(name); // 그것을 정렬
		
		List<AllTag> allTag = new ArrayList<AllTag>(); //AllTag 는 String int 태그 - 개수로 이뤄진 것

		for(int i = 0;i < userTagName.get().size(); i++) {
			String temp = name.get(i);
			int cnt = 0;
			for(int j = i; j < userTagName.get().size(); j++) {
				if(temp.equals(name.get(j))) {
					cnt++;
				}
				else {
					i = j - 1;
					break;
				}
			}
			allTag.add(new AllTag(temp ,cnt));
		}
		
		allTag.sort(new Comparator<AllTag>() { // 큰 것이 위로올라오게 정렬
			@Override
			public int compare(AllTag arg0, AllTag arg1) {
				int num0 = arg0.getNum();
				int num1 = arg1.getNum();
				return Integer.compare(num1, num0); // 내림차순.
			}
		});
		
		return new ResponseEntity<>(allTag, HttpStatus.OK); // 반환
	}
	
	//5. 사용자 + 해시태그를 적용한 페이지 정보 반환.
	@GetMapping("/label/skillTreeLabel")
	@ApiOperation(value = "스킬트리; 내가 쓴 해당 태그 가져오기", notes="태그 가져오기 (태그문자 - 개수) 형태 반환. 개수가 많은 순으로")
	public Object skillTreeLabel(String email, String name) {
		Member member = memberDao.getMemberByEmail(email);
		
		Optional<List<PostTag>> postTag = postTagDao.findByPidAndTid(member.getUid(), name);
		
		List<Post> postList = new ArrayList<Post>();
		for(int i = 0; i  < postTag.get().size(); i++) { // 가져올 수 있으면, 전체 포스트 반환
			postList.add(postDao.findPostByPid(postTag.get().get(i).getPid()));
		}
		
		List<OutputPostAndTag> outputPostAndTag = new ArrayList<OutputPostAndTag>();
		
		for(int i = 0; i < postList.size(); i++) {
			Optional<Like> like = likeDao.getLikeByPidAndUid(postList.get(i).getPid(), member.getUid());
			if(like.isPresent()) { // 있으면
				outputPostAndTag.add(new OutputPostAndTag(postList.get(i).getPid(),postList.get(i).getTitle(),postList.get(i).getEmail(),postList.get(i).getContent(),postList.get(i).getLikes(), postList.get(i).getPostimage(), 1, postList.get(i).getCreate_date()));
			}
			else {
				outputPostAndTag.add(new OutputPostAndTag(postList.get(i).getPid(),postList.get(i).getTitle(),postList.get(i).getEmail(),postList.get(i).getContent(),postList.get(i).getLikes(),postList.get(i).getPostimage(), 0, postList.get(i).getCreate_date()));
			}
			Optional<List<Comment>> isComment = commentDao.findByPidAndUid(postList.get(i).getPid());
			if(isComment.isPresent()) { // 댓글 있으면
				outputPostAndTag.get(i).setCommentCnt(isComment.get().size()); // 개수
			}
			else {
				outputPostAndTag.get(i).setCommentCnt(0);
			}
			outputPostAndTag.get(i).setProfileimage(member.getProfileimage());
			outputPostAndTag.get(i).setTags(postTagDao.findPostTagJoinTagByPid(postList.get(i).getPid()));
			}
		
		return new ResponseEntity<>(outputPostAndTag, HttpStatus.OK); // 반환
	}
	
	public class AllTag{ // myLabel 에서 사용하기 위한 것
		String tagName;
		int num;
		AllTag(){
			super();
		}
		AllTag(String tagName ,int num){
			super();
			this.tagName = tagName;
			this.num = num;
		}
		public int getNum() {
			return num;
		}
		public void setNum(int num) {
			this.num = num;
		}
		public String getTagName() {
			return tagName;
		}
		public void setTagName(String tagName) {
			this.tagName = tagName;
		}
	}
}
