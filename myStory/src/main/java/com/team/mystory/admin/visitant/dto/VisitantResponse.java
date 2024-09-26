package com.team.mystory.admin.visitant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class VisitantResponse {

    private long visitorsCount;

    private LocalDate visitedDate;

}
