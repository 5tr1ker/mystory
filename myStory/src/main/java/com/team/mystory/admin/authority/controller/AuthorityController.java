package com.team.mystory.admin.authority.controller;

import com.team.mystory.admin.authority.service.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/authority")
public class AuthorityController {

    private final AuthorityService authorityService;

    @GetMapping
    public ResponseEntity findAllAuthorityUser(Pageable pageable) {
        return ResponseEntity.ok(authorityService.findAllAuthorityUser(pageable));
    }

    @GetMapping("/count")
    public ResponseEntity findNumberOfAllUsers() {
        return ResponseEntity.ok(authorityService.findNumberOfAllUsers());
    }

}
