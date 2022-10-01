package noticeboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import noticeboard.entity.userdata.IdInfo;

public interface LoginRepository extends JpaRepository<IdInfo, Long> {
	public IdInfo findById(String id);
}
