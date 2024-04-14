package com.common.project.controller;

import java.util.*;

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
import org.springframework.web.bind.annotation.RestController;

import com.common.project.dao.CommentDao;
import com.common.project.dao.MemberDao;
import com.common.project.model.comment.Comment;
import com.common.project.model.comment.CommentDetail;
import com.common.project.model.comment.InputComment;
import com.common.project.model.comment.UpdateComment;
import com.common.project.model.member.Member;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Transactional
@Api(tags = "CommentController", description = "댓글 API")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
public class CommentController {
	@Autowired
	CommentDao commentDao;	
	@Autowired
	MemberDao memberDao;
	
	@PostMapping("/cmt/write")
	@ApiOperation(value = "댓글쓰기")
	public Object writeComment(@RequestBody InputComment inputComment) {
		Comment comment = new Comment(inputComment.getPid(), inputComment.getEmail(), inputComment.getContent());
		
		commentDao.save(comment);
		List<Comment> commentList = commentDao.getByPidOrderByCreateDateAsc(inputComment.getPid());
		return new ResponseEntity<>(commentList, HttpStatus.OK);
	}
	
	@GetMapping("/cmt/detail/{pid}")
	@ApiOperation(value = "댓글보기")
	public Object detailComment(@PathVariable Long pid) {
		List<Comment> commentList = commentDao.getByPidOrderByCreateDateAsc(pid);
		List<CommentDetail> commentDetailList = new ArrayList<CommentDetail>();
		
		for(int i = 0; i < commentList.size(); i++) {
			Member member = memberDao.getMemberByEmail(commentList.get(i).getEmail());
			commentDetailList.add(new CommentDetail(commentList.get(i).getCid(), commentList.get(i).getPid(), commentList.get(i).getEmail(), commentList.get(i).getContent(), commentList.get(i).getCreate_date(), member.getProfileimage()));
		}
		
		return new ResponseEntity<>(commentDetailList, HttpStatus.OK);
	}
	
	@PutMapping("/cmt/update")
	@ApiOperation(value = "댓글수정")
	public Object updateComment(@RequestBody UpdateComment updateComment) {
		Comment comment = new Comment(updateComment.getCid(),updateComment.getPid(), updateComment.getEmail(), updateComment.getContent());
		commentDao.save(comment);
		List<Comment> commentList = commentDao.getByPidOrderByCreateDateAsc(updateComment.getPid());
		return new ResponseEntity<>(commentList, HttpStatus.OK);
	}
	
	@DeleteMapping("/cmt/delete")
	@ApiOperation(value = "댓글삭제")
	public Object deleteComment(Long cid, Long pid) {
		Optional<Comment> memberOpt = commentDao.deleteBycid(cid);
		if(memberOpt.isPresent()) {
			List<Comment> commentList = commentDao.getByPidOrderByCreateDateAsc(pid);
			return new ResponseEntity<>(commentList,  HttpStatus.OK);
		}
		else return new ResponseEntity<>("fail", HttpStatus.NOT_FOUND);
	}
}
