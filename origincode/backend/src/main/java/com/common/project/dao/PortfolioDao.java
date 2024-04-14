package com.common.project.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.common.project.model.portfolio.Portfolio;


public interface PortfolioDao extends JpaRepository<Portfolio, Long> {

	Portfolio findPostByFid(Long fid);
	Optional<Portfolio> deletePortfolioByFid(Long fid);
	@Query("SELECT p FROM Post p WHERE p.title like %:keyword% "
			+ "or p.content like %:keyword% ORDER BY p.pid desc")
    public List<Portfolio> findPostByTitleOrContent(String keyword);
	
	@Query(value = "SELECT A.* FROM  portfolio A where email = ?1 ", nativeQuery = true)
	Optional<Portfolio> findByEmail(String email);
	
	@Modifying
	@Query(value = "update portfolio set content = ?1 where fid = ?2 ", nativeQuery = true)
	void updateByFid(String content, Long fid);
}
