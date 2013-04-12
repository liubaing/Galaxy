package com.liubaing.shiny_liubaing.repository.jpa;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.liubaing.shiny_liubaing.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>  {

	public List<Comment> findByParentidOrderByIdDesc(int commentID);
	
	public List<Comment> findByCourseidAndParentidOrderByIdDesc(int courseID, int parentID, Pageable pageable);
	
	public List<Comment> findByCourseidAndUseridOrderByIdDesc(int courseID, int userID, Pageable pageable);
	
	@Query("select count(c) from Comment c where c.courseid = ?1 and c.parentid = ?2")
	public long countByCourseid(int courseID, int parentID);
	
	@Query("select count(c) from Comment c where c.parentid = ?1")
	public long countById(int commentID);
	
	@Modifying
	@Query("delete Comment where id = ?1 or parentid = ?1")
	public void deleteByIdOrParentid(int commentID);
	
}
