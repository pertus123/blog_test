package com.common.project.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RestController;

import com.common.project.dao.MemberDao;
import com.common.project.model.member.SignupInputMember;
import com.common.project.model.member.InfoInputMember;
import com.common.project.model.member.LoginInputMember;
import com.common.project.model.member.Member;
import com.common.project.service.JwtService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Transactional
@Api(tags = "MemberController", description = "회원 API")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
public class MemberController {
	@Autowired
	private JwtService jwtService;
	@Autowired
	MemberDao memberDao;
	
	@PostMapping("/user/login")
	@ApiOperation(value = "로그인", notes="성공시 jwt 토큰을 반환합니다.")
	public Object login(@RequestBody LoginInputMember inputMember) {
		String originalPassword = inputMember.getPassword();
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(inputMember.getPassword().getBytes());
			inputMember.setPassword(String.format("%040x", new BigInteger(1, md.digest())));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		Optional<Member> memberOpt = memberDao.findMemberByEmailAndPassword(inputMember.getEmail(), inputMember.getPassword());
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if(memberOpt.isPresent()) { // 값이 존재하면, 
			Member memberInfoR = memberDao.getMemberByEmail(inputMember.getEmail());

			Member memberInfo = new Member(memberInfoR.getUid(),memberInfoR.getEmail(), originalPassword,memberInfoR.getName(), memberInfoR.getIntroduce()
					,memberInfoR.getProfileimage());
			resultMap.put("auth-token", jwtService.create(memberInfoR));
			return new ResponseEntity<>(resultMap, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(resultMap, HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/user/signup")
	@ApiOperation(value = "가입하기")
	public Object signup(@RequestBody SignupInputMember inputMember) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(inputMember.getPassword().getBytes());
			inputMember.setPassword(String.format("%040x", new BigInteger(1, md.digest())));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		Member member = new Member(inputMember.getEmail(),inputMember.getPassword(), inputMember.getName());
		Optional<Member> memberOpt = memberDao.findMemberByEmail(inputMember.getEmail());
		
		if(!memberOpt.isPresent()) { // 이메일이 존재하지  않으면 -> 중복 X
			memberDao.save(member);
			return new ResponseEntity<>("success", HttpStatus.OK);
		}
		else return new ResponseEntity<>("duplication", HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/user/updateInfo")
	@ApiOperation(value = "회원정보수정", httpMethod="POST", produces="multipart/form-data")
	public Object updateInfo(InfoInputMember infoInputMember) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(infoInputMember.getPassword().getBytes());
			infoInputMember.setPassword(String.format("%040x", new BigInteger(1, md.digest())));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		String saveName = null;
		Member member = new Member(infoInputMember.getName(), infoInputMember.getEmail(), infoInputMember.getPassword(), infoInputMember.getIntroduce());
		member.setUid(infoInputMember.getUid());
		
		if(infoInputMember.getProfileSwitch() == 1) {
			String UPLOAD_PATH = "/var/www/html/dist/spa/profileimg";	//서버
			UUID uuid = UUID.randomUUID();
	        saveName = uuid+"_"+infoInputMember.getProfileimage().getOriginalFilename();
	        File saveFile = new File(UPLOAD_PATH, saveName);
	        try {
				infoInputMember.getProfileimage().transferTo(saveFile);
				 saveName = "http://3.36.125.70/profileimg/" + saveName;
				 member.setProfileimage(saveName);
			}  catch (IOException e) {
				e.printStackTrace();
			}
		}
		
        memberDao.save(member);
        
		return new ResponseEntity<>(saveName, HttpStatus.OK);
	}
	
	//email로 받게 수정
	@GetMapping("/user/profile/{email}")
	@ApiOperation(value = "개인정보가져오기")
	public Object profileInfo(@PathVariable String email) {
		Member member = memberDao.getMemberByEmail(email);
		Member memberInfo = new Member(member.getUid(), member.getEmail(), "", member.getName(), member.getIntroduce(), member.getProfileimage() );
		return new ResponseEntity<>(memberInfo, HttpStatus.OK);
	}
	
	@DeleteMapping("/user/delete/{uid}")
	@ApiOperation(value = "회원탈퇴")
	public Object deleteInfo(@PathVariable Long uid) {
		Optional<Member> memberOpt = memberDao.deleteMemberByUid(uid);
		if(memberOpt.isPresent())return new ResponseEntity<>("success",  HttpStatus.OK);
		else return new ResponseEntity<>("fail", HttpStatus.NOT_FOUND);
	}
}