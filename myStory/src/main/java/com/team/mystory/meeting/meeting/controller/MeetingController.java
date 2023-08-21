package com.team.mystory.meeting.meeting.controller;

import com.team.mystory.meeting.meeting.dto.MeetingRequest;
import com.team.mystory.meeting.meeting.dto.MeetingResponse;
import com.team.mystory.meeting.meeting.service.MeetingService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/meeting")
public class MeetingController {

    private final MeetingService meetingService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE , MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity createMeeting(@RequestPart MeetingRequest meeting , @RequestPart MultipartFile image) throws IOException {
        meetingService.createMeeting(meeting , image);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{meetingId}")
    public ResponseEntity modifyMeeting(@RequestPart MeetingRequest meeting , @RequestPart(required = false) MultipartFile image
            , @CookieValue String accessToken , @PathVariable long meetingId) throws IOException {
        meetingService.modifyMeeting(meeting , image , accessToken , meetingId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{meetingId}")
    public ResponseEntity createMeeting(@CookieValue String accessToken , @PathVariable long meetingId) throws IOException {
        meetingService.deleteMeetingById(accessToken , meetingId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity findAllMeeting() {
        List<MeetingResponse> meetingList = meetingService.findAllMeeting();

        return ResponseEntity.ok().body(meetingList);
    }

    @GetMapping("/address")
    public ResponseEntity findMeetingByArea(@PathParam(value = "address") String address) {
        List<MeetingResponse> meetingList = meetingService.findMeetingByAddress(address);

        return ResponseEntity.ok().body(meetingList);
    }

    @GetMapping("/title")
    public ResponseEntity findMeetingByTitle(@PathParam(value = "title") String title) {
        List<MeetingResponse> meetingList = meetingService.findMeetingByTitle(title);

        return ResponseEntity.ok().body(meetingList);
    }

}
