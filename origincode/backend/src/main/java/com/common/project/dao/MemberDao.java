package com.common.project.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.common.project.model.member.Member;

public interface MemberDao extends JpaRepository<Member, Long>{
	Optional<Member> deleteMemberByUid(Long uid);
	@Query(value = "select * from member where member.email = ?1 and member.password =?2", nativeQuery = true)
	Optional<Member> findMemberByEmailAndPassword(String email, String password);
	Optional<Member> findMemberByEmail(String email);
	Member getMemberByUid(Long uid);
	Member getMemberByEmail(String email);
}
