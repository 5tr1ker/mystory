package noticeboard.repository;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import noticeboard.entity.DTO.ReturnPostDataDTO;
import noticeboard.entity.freeboard.FreeCommit;
import noticeboard.entity.freeboard.FreePost;
import noticeboard.repository.custom.CustomPostRepository;

public interface PostRepository extends JpaRepository<FreePost, Long> , CustomPostRepository {
	
	/*
	 * QueryDSL 로 구현된 몇가지 파일이 있습니다. ( CustomPostRepository )
	 * Commit 가져오기.
	 * WhoLike 명단.
	 */

	@QueryHints(value = @QueryHint(name = "org.hibernate.readOnly" ,value = "true"))
	@Query(value = "SELECT COUNT(c) FROM FreePost c")
	public int getTotalPostDataCount();
	
	@QueryHints(value = @QueryHint(name = "org.hibernate.readOnly" ,value = "true"))
	@Query(value = "SELECT new noticeboard.entity.DTO.ReturnPostDataDTO(f.numbers , f.title , f.writer , f.postTime , f.likes , f.views , COUNT(c)) FROM FreePost f LEFT OUTER JOIN f.freeCommit c group by f.numbers order by f.numbers DESC")
	public List<ReturnPostDataDTO> getPostData(Pageable pageable);
	
	@QueryHints(value = @QueryHint(name = "org.hibernate.readOnly" ,value = "true"))
	@Query("SELECT c FROM FreePost p join p.freeCommit c where p.numbers = :postNumber")
	List<FreeCommit> getCommit(@Param("postNumber") Long postNumber);
	
	/* Post 번호 증감식 */
	@Query(value = "SELECT MAX(f.numbers) FROM FreePost f")
	public Long getPostNumber();
	
	@Query(value = "SELECT f FROM FreePost f where f.numbers = :postId")
	public FreePost getViewPostData(@Param("postId") Long postId);
	
	/* 프록시된 태그 데이터 - 읽기 전용 */
	@QueryHints(value = @QueryHint(name = "org.hibernate.readOnly" ,value = "true"))
	@Query("SELECT f.tagData from FreePost p join p.freeTag f where p.numbers = :postId")
	public List<String> findPostTag(@Param("postId") Long postId);
	
	/* Post 조회수 1 증가 */
	@Modifying(clearAutomatically = true) // 영속성 컨텍스트 초기화
	@Query("UPDATE FreePost p set p.views = p.views + 1 where p.numbers = :postId")
	public Integer updatePostViews(@Param("postId") Long postId);

	/* 수정된 아이디로 전부 변경 */
	@Modifying(clearAutomatically = true)
	@Query("UPDATE FreePost p set p.writer = :changeId where p.writer = :idStatus")
	public void changeWritter(@Param("idStatus") String idStatus , @Param("changeId") String changeId);

	@Query("SELECT p.fileName from FreeAttach p where p.changedFile = :postRepos")
	public String getFileName(@Param("postRepos")String fileName);

	@Modifying(clearAutomatically = true)
	@Query("DELETE from FreeAttach a where a.changedFile = :name")
	public void deleteByFileName(@Param("name") String name);

	void deleteByNumbers(Long numbers);

}