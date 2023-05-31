package com.team.mystory.repository.custom;

import com.team.mystory.entity.freeboard.FreeCommit;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class CommitRepositoryImpl extends QuerydslRepositorySupport {

	public CommitRepositoryImpl() {
		super(FreeCommit.class);
	}
}
