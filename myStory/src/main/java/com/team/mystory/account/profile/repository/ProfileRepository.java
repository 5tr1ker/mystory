package com.team.mystory.account.profile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.team.mystory.account.profile.domain.ProfileSetting;

public interface ProfileRepository extends JpaRepository<ProfileSetting, Long>{
	
	@Query("SELECT f.idInfoID from IdInfo f where f.id = :idInfo")
	public Long findIdByuserId(@Param("idInfo") String idInfo);

	@Query("SELECT COUNT(f) from FreePost f where f.writer = :idInfo")
	public Long getTotalPost(@Param("idInfo") String idInfo);
	
	@Query("SELECT SUM(f.views) from FreePost f where f.writer = :idInfo")
	public Long getPostView(@Param("idInfo") String idInfo);
	
	@Query("SELECT COUNT(f) from FreeCommit f where f.writer = :idInfo")
	public Long getTotalComment(@Param("idInfo") String idInfo);
	
	@Query("SELECT I.joinDate from IdInfo I where I.id = :idInfo")
	public String getJoinDate(@Param("idInfo") String idInfo);
	
	@Query("SELECT p from IdInfo I join I.profileSetting p where I.id = :idInfo")
	public ProfileSetting findProfileSettings(@Param("idInfo") String idInfo);
}
