package com.team.mystory.account.profile.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team.mystory.account.profile.domain.Profile;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileResponse {
    private String email;

    private String phone;

    private int options;

    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd" , timezone = "Asia/Seoul")
    private Date joinDate;

    public static ProfileResponse createProfileResponse(Profile profile , Date joinDate) {
        return ProfileResponse.builder()
                .email(profile.getEmail())
                .phone(profile.getPhone())
                .options(profile.getOptions())
                .joinDate(joinDate)
                .build();
    }
}
