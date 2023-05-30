package com.team.mystory.repository.custom;

import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

import com.team.mystory.entity.freeboard.FreeCommit;

public class CommitRepositoryImpl extends QueryDslRepositorySupport {

	public CommitRepositoryImpl() {
		super(FreeCommit.class);
	}
}
