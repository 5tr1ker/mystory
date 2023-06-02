package com.team.mystory.post.comment.repository;

import java.util.List;

import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import com.team.mystory.post.comment.domain.FreeCommit;

public interface CommitRepository extends JpaRepository<FreeCommit, Long> {

	@Modifying(clearAutomatically = true)
	@Query("DELETE FROM FreeCommit c where c.writer = :idInfo")
	public void deleteAllCommit(@Param("idInfo") String idInfo);


	
	//@Query(value = "SELECT C from freeCommit C where C IN (SELECT p from post p where p.writer = :idstatus)" , nativeQuery=true)
	/* 내 POST에 적힌 댓글 */
	//@Query(value = "SELECT C.* from FreeCommit C where C.id IN (SELECT p.freepostId from Post p where p.writer = :idStatus) order by C.id DESC" , nativeQuery = true)
	@Query(value = "select c from FreeCommit c where c.writer != :idStatus and c.post =any (select p from Post p where p.writer = :idStatus) order by c.id DESC")
	public List<FreeCommit> getNotifice(@Param("idStatus")String idStatus);


	@QueryHints(value = @QueryHint(name = "org.hibernate.readOnly" ,value = "true"))
	@Query("SELECT c FROM Post p join p.freeCommit c where p.postId = :postNumber")
	List<FreeCommit> getCommit(@Param("postNumber") Long postNumber);


}
