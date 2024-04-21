package com.team.mystory.meeting.meeting.controller;

import com.team.mystory.account.user.domain.User;
import com.team.mystory.account.user.service.LoginService;
import com.team.mystory.meeting.meeting.dto.MeetingMemberResponse;
import com.team.mystory.meeting.meeting.dto.MeetingRequest;
import com.team.mystory.meeting.meeting.dto.MeetingResponse;
import com.team.mystory.meeting.meeting.repository.meeting.MeetingRepository;
import com.team.mystory.meeting.meeting.service.MeetingService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.AccountException;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/meeting")
public class MeetingController {

    private final MeetingService meetingService;
    private final LoginService loginService;
    private final MeetingRepository meetingRepository;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE , MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity createMeeting(@RequestPart MeetingRequest meeting , @RequestPart MultipartFile image
            , @CookieValue String accessToken) throws IOException {

        meetingService.createMeeting(meeting , image , accessToken);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{meetingId}")
    public ResponseEntity joinMeeting(@PathVariable long meetingId, @CookieValue String accessToken) {
        User user = loginService.findUserByAccessToken(accessToken);
        meetingService.joinMeeting(meetingId , user);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{meetingId}")
    public ResponseEntity modifyMeeting(@RequestPart MeetingRequest meeting , @RequestPart(required = false) MultipartFile image
            , @CookieValue String accessToken , @PathVariable long meetingId) throws IOException {

        meetingService.modifyMeeting(meeting , image , accessToken , meetingId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{meetingId}")
    public ResponseEntity deleteMeeting(@CookieValue String accessToken , @PathVariable long meetingId) {
        meetingService.deleteMeetingById(accessToken , meetingId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/participants/{meetingId}")
    public ResponseEntity leaveMeeting(@CookieValue String accessToken , @PathVariable long meetingId) {
        meetingService.leaveMeeting(accessToken , meetingId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity findAllMeeting(Pageable pageable) {
        List<MeetingResponse> meetingList = meetingService.findAllMeeting(pageable);

        return ResponseEntity.ok().body(meetingList);
    }

    @GetMapping("/{meetingId}/user")
    public ResponseEntity findMeetingByMeetingIdAndUser(@CookieValue String accessToken, @PathVariable long meetingId) {
        MeetingResponse result = meetingService.findMeetingByMeetingIdAndUser(accessToken, meetingId);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{meetingId}")
    public ResponseEntity findMeetingByMeetingId(@PathVariable long meetingId) {
        MeetingResponse result = meetingService.findMeetingByMeetingId(meetingId);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/count")
    public ResponseEntity findAllMeetingCount() {
        return ResponseEntity.ok().body(meetingRepository.countAllMeeting());
    }

    @GetMapping("/user")
    public ResponseEntity findAllMeetingByUserId(Pageable pageable , @CookieValue String accessToken) {
        List<MeetingResponse> meetingList = meetingService.findAllMeetingByUserId(pageable , accessToken);

        return ResponseEntity.ok().body(meetingList);
    }

    @GetMapping("/user/count")
    public ResponseEntity findAllMeetingByUserIdCount(@CookieValue String accessToken) {
        return ResponseEntity.ok().body(meetingService.findAllMeetingByUserIdCount(accessToken));
    }

    @GetMapping("/title-address")
    public ResponseEntity findMeetingByTitleOrAddress(Pageable pageable , @PathParam(value = "data") String data) {
        List<MeetingResponse> meetingList = meetingService.findMeetingByTitleOrAddress(pageable , data);

        return ResponseEntity.ok().body(meetingList);
    }

    @GetMapping("/title-address/count")
    public ResponseEntity findMeetingByTitleOrAddressCount(@PathParam(value = "data") String data) {

        return ResponseEntity.ok().body(meetingService.findMeetingByTitleOrAddressCount(data));
    }

    @GetMapping("/participants/{meetingId}")
    public ResponseEntity findParticipantsByMeetingId(@PathVariable long meetingId) {
        MeetingMemberResponse result = meetingService.findMeetingMemberByMeetingId(meetingId);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/is-participants/{meetingId}")
    public ResponseEntity IsMeetingParticipant(@PathVariable long meetingId , @CookieValue String accessToken) {
        boolean result = meetingService.IsMeetingParticipant(meetingId , accessToken);

        return ResponseEntity.ok().body(result);
    }

}
