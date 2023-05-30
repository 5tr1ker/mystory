package com.team.mystory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.team.mystory.entity.freeboard.FreeCommit;

public interface CommitRepository extends JpaRepository<FreeCommit, Long> {
	
	@Modifying(clearAutomatically = true)
	@Query("DELETE FROM FreeCommit c where c.writer = :idInfo")
	public void deleteAllCommit(@Param("idInfo") String idInfo);

	@Modifying(clearAutomatically = true)
	@Query("UPDATE FreeCommit p set p.writer = :changeId where p.writer = :idStatus")
	public void changeWritter(@Param("idStatus") String idStatus , @Param("changeId") String changeId);
	
	//@Query(value = "SELECT C from freeCommit C where C IN (SELECT p from freePost p where p.writer = :idstatus)" , nativeQuery=true)
	/* 내 POST에 적힌 댓글 */
	//@Query(value = "SELECT C.* from FreeCommit C where C.id IN (SELECT p.freepostId from FreePost p where p.writer = :idStatus) order by C.id DESC" , nativeQuery = true)
	@Query(value = "select c from FreeCommit c where c.writer != :idStatus and c.freePost =any (select p from FreePost p where p.writer = :idStatus) order by c.id DESC")
	public List<FreeCommit> getNotifice(@Param("idStatus")String idStatus);

}
