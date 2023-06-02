package com.team.mystory.post.attachment.repository;

import com.team.mystory.post.attachment.domain.FreeAttach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<FreeAttach , Long> {
    @Query("SELECT p.fileName from FreeAttach p where p.changedFile = :postRepos")
    String getFileName(@Param("postRepos")String fileName);

    @Modifying(clearAutomatically = true)
    @Query("DELETE from FreeAttach a where a.changedFile = :name")
    void deleteByFileName(@Param("name") String name);

 //    List<FreeAttach> getAttachment(Long postId);

//    public List<FreeAttach> getAttachment(Long postId) {
//		QFreePost qfp = QFreePost.post;
//
//		JPQLQuery query = from(qfp);
//		Post result = query.where(qfp.numbers.eq(postId)).join(qfp.freeAttach).fetch().uniqueResult(qfp);
//		if (result == null) return null;
//		return result.getFreeAttach();
//    }
}
