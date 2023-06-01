package com.team.mystory.account.profile.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileRequest {
    private String userId;
    private String email;
    private String phone;
    private Integer option2;

} 
