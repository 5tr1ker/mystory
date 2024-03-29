package com.team.mystory.meeting.reservation.controller;

import com.team.mystory.meeting.meeting.dto.ParticipantResponse;
import com.team.mystory.meeting.reservation.dto.ReservationRequest;
import com.team.mystory.meeting.reservation.dto.ReservationResponse;
import com.team.mystory.meeting.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/meeting")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/{meetingId}/reservation")
    public ResponseEntity createReservation(@RequestBody ReservationRequest request , @CookieValue String accessToken
            , @PathVariable long meetingId) throws AccountException {

        reservationService.createReservation(request , accessToken , meetingId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/reservation/{reservationId}/join")
    public ResponseEntity joinReservation(@PathVariable long reservationId , @CookieValue String accessToken) throws AccountException {
        reservationService.joinReservation(reservationId , accessToken);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/reservation/{reservationId}/leave")
    public ResponseEntity leaveReservation(@PathVariable long reservationId , @CookieValue String accessToken) throws AccountException {
        reservationService.leaveReservation(reservationId , accessToken);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{meetingId}/reservation")
    public ResponseEntity findReservation(@PathVariable long meetingId) {
        List<ReservationResponse> result = reservationService.findReservationByMeetingId(meetingId);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/reservation/{reservationId}")
    public ResponseEntity findParticipantsById(@PathVariable long reservationId) {
        List<ParticipantResponse> result = reservationService.findParticipantsById(reservationId);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
