package com.common.project.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.common.project.model.post.Post;

public interface PostDao extends JpaRepository<Post, Long> {
	Post findPostByPid(Long pid);
	Optional<Post> deletePostByPid(Long pid);
	List<Post> findByEmail(String email);
	@Query("SELECT p FROM Post p WHERE p.title like %:keyword% "
			+ "or p.content like %:keyword% ORDER BY p.pid desc ")
    public List<Post> findPostByTitleOrContent(String keyword);
	
	@Query("SELECT p FROM Post p ORDER BY p.likes desc ")
    public List<Post> findPostByLike();
	
	//월별 가져오기
	@Query(value = "SELECT count(*) FROM post where create_date like ?1 and email = ?2 ", nativeQuery = true)
	public int getByEmailAndDateTime(String date, String email);
	
	
	
}
