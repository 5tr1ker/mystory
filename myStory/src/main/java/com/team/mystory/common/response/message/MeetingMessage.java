package com.team.mystory.common.response.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MeetingMessage {

    NOT_FOUND_MEETING("모임 정보를 찾을 수 없습니다."),
    ONLY_OWNER_RESERVATION("모임 장만 예약할 수 있습니다."),
    FULL_GATHERING("인원이 가득 찼습니다."),
    ALREADY_PARTICIPATED_MEETING("이미 모임에 참여하고 있습니다."),
    CAN_NOT_LEAVE_LAST_PARTICIPATED("모임에 참여 중이지 않거나 , 이전 모임은 나갈 수 없습니다."),
    CAN_NOT_LEAVE_OWNER("모임장은 파티를 나갈 수 없습니다."),
    CAN_MODIFY_ONLY_OWNER("파티장만 수정할 수 있습니다.")
    ;

    private final String message;

}
