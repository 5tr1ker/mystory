package com.team.mystory.post.tag.repository;

import java.util.List;

import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.team.mystory.post.tag.domain.FreeTag;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

public interface TagRepository extends JpaRepository<FreeTag, Long>  { 

	@Query("SELECT f.tagData , COUNT(f) from FreeTag f group by f.tagData order by COUNT(f) DESC")
	List<String> findAllTagData();

}
