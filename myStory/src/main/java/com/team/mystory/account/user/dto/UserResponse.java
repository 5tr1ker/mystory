package com.team.mystory.account.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team.mystory.account.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private long userKey;

    private String id;

    private String password;

    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd" , timezone = "Asia/Seoul")
    private LocalDate joinDate;

    private String profileImage;

    private int options;

    public static UserResponse createResponse(User user) {
        return UserResponse.builder()
                .userKey(user.getUserKey())
                .id(user.getId())
                .password(user.getPassword())
                .joinDate(user.getJoinDate())
                .profileImage(user.getProfileImage())
                .options(user.getProfile().getOptions())
                .build();
    }
}
