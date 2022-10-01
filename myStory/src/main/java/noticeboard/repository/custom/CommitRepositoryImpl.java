package noticeboard.repository.custom;

import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

import noticeboard.entity.freeboard.FreeCommit;

public class CommitRepositoryImpl extends QueryDslRepositorySupport {

	public CommitRepositoryImpl() {
		super(FreeCommit.class);
	}
}
