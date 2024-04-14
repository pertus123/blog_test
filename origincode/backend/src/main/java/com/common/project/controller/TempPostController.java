package com.common.project.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.common.project.dao.MemberDao;
import com.common.project.dao.PostDao;
import com.common.project.dao.TagDao;
import com.common.project.dao.TempPostDao;
import com.common.project.dao.TempPostTagDao;
import com.common.project.model.member.Member;
import com.common.project.model.tag.Tag;
import com.common.project.model.tag.TempPostTag;
import com.common.project.model.temppost.TInfoInputPost;
import com.common.project.model.temppost.TempPost;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Transactional
@Api(tags = "TempPostController", description = "임시글 API")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
public class TempPostController {

	@Autowired
	PostDao postDao;
	@Autowired
	TempPostDao tempPostDao;
	@Autowired
	TagDao tagDao;
	@Autowired
	TempPostTagDao tempPostTagDao;
	@Autowired
	MemberDao memberDao;
	
	
	///////////////////////////////////////////////////////////////////
	@PostMapping("/temp/write")
	@ApiOperation(value = "임시 저장하기", httpMethod = "POST")
	public Object twriteTemp(@RequestBody TInfoInputPost tinfoinputPost) {
		
		tempPostTagDao.deleteByPid(tinfoinputPost.getTpid()); // 일단 모든 테그 삭제
		Member member = memberDao.getMemberByEmail(tinfoinputPost.getEmail());
		// 1차 저장
		TempPost temppost = new TempPost(tinfoinputPost.getTpid(), tinfoinputPost.getTitle(), tinfoinputPost.getEmail(),tinfoinputPost.getContent(), tinfoinputPost.getPostimage());
		TempPost saveTempPost = tempPostDao.save(temppost);
		
		for(int i  = 0; i < tinfoinputPost.getTags().size(); i++) {
			 // 1 현재 DB에 태그가 있는지 체크 - tag 테이블에서
			Optional<Tag> isTag = tagDao.findByName(tinfoinputPost.getTags().get(i));
			if(!isTag.isPresent()) {	// 2. tag 테이블에 없으면 새로운 태그와 이름을 만들어 넣는다.
				Tag tag = new Tag();
				tag.setName(tinfoinputPost.getTags().get(i));
				tagDao.save(tag);
			}
			
			// 3. 위 작업으로 무조건 tag에 있게 된다. tid와 pid의 정보를 post_tag에 저장.
			TempPostTag postTag = new TempPostTag();
			postTag.setUid(member.getUid());
			postTag.setTpid(saveTempPost.getTpid());
			Optional<Tag> tag = tagDao.findByName(tinfoinputPost.getTags().get(i));
			postTag.setTid(tag.get().getTid()); // 이 부분 수정
			tempPostTagDao.save(postTag); //p_t에 내용을 저장한다.
		}
		
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
	
	@GetMapping("/temp/tempListPage")
	@ApiOperation(value = "혹시나 태그 정보도 필요하시면 말해주세요")
	public Object tlistUserPage(@RequestParam String email) {
		System.out.println("tempListPage");
		List<TempPost> tpostList = tempPostDao.findByEmail(email);
		return new ResponseEntity<>(tpostList, HttpStatus.OK);
	}
	
	@DeleteMapping("/temp/delete/{tpid}")
	@ApiOperation(value = "임시 글삭제")
	public Object tdeletePage(@PathVariable Long tpid) {
		Optional<TempPost> tpostOpt = tempPostDao.deleteByTpid(tpid);
		if (tpostOpt.isPresent()) {
			return new ResponseEntity<>("success", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("fail", HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/temp/detail")
	@ApiOperation(value = "임시저장 상세페이지 불러오기")
	public Object tdetailTemp(@RequestParam Long tpid) {
		Optional<List<TempPost>> tempDetail = tempPostDao.findByTpid(tpid);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Optional<List<String>> tags = tempPostTagDao.findTempPostTagJoinTagByPid(tpid);
		
		resultMap.put("data", tempDetail);
		resultMap.put("tags", tags);
		
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
	
	@PostMapping("/temp/uploadImg")
	@ApiOperation(value = "파일첨부", httpMethod = "POST", produces = "multipart/form-data")
	public Object tuploadimg(MultipartFile img) {

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
}