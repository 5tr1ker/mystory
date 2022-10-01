package noticeboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import noticeboard.entity.freeboard.FreeTag;

public interface TagRepository extends JpaRepository<FreeTag, Long>  { 

	@Query("SELECT f.tagData , COUNT(f) from FreeTag f group by f.tagData order by COUNT(f) DESC")
	public List<String> findAllTagData();

}
