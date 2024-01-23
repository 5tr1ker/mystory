package com.team.mystory.meeting.reservation.service;

import com.team.mystory.account.user.domain.User;
import com.team.mystory.account.user.service.LoginService;
import com.team.mystory.meeting.meeting.domain.Meeting;
import com.team.mystory.meeting.meeting.dto.ParticipantResponse;
import com.team.mystory.meeting.meeting.exception.MeetingException;
import com.team.mystory.meeting.meeting.repository.meeting.MeetingRepository;
import com.team.mystory.meeting.reservation.dto.ReservationRequest;
import com.team.mystory.meeting.reservation.dto.ReservationResponse;
import com.team.mystory.meeting.reservation.entity.Reservation;
import com.team.mystory.meeting.reservation.exception.ReservationException;
import com.team.mystory.meeting.reservation.repository.ReservationParticipantsRepository;
import com.team.mystory.meeting.reservation.repository.ReservationRepository;
import com.team.mystory.security.jwt.support.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.team.mystory.common.response.message.MeetingMessage.*;
import static com.team.mystory.meeting.reservation.entity.ReservationParticipants.createReservationParticipants;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationParticipantsRepository reservationParticipantsRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final LoginService loginService;
    private final MeetingRepository meetingRepository;

    @Transactional
    public void createReservation(ReservationRequest request, String accessToken , long meetingId) {
        User user = loginService.findUserByAccessToken(accessToken);

        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new MeetingException(NOT_FOUND_MEETING));

        if(meeting.getMeetingOwner().getUserKey() != user.getUserKey()) {
            throw new MeetingException(ONLY_OWNER_RESERVATION);
        }

        Reservation reservation = Reservation.createReservation(request);

        meeting.addReservation(reservation);
    }


    @Transactional
    public void joinReservation(long reservationId, String accessToken) {
        User user = loginService.findUserByAccessToken(accessToken);

        Reservation reservation = reservationRepository.findReservationAndParticipantsById(reservationId)
                .orElseThrow(() -> new ReservationException(NOT_FOUND_MEETING));

        if(reservationRepository.findReservationByIdAndUser(reservationId , user).isPresent()) {
            throw new ReservationException(ALREADY_PARTICIPATED_MEETING);
        }

        if(reservation.getParticipates().size() >= reservation.getMaxParticipants()) {
            throw new ReservationException(FULL_GATHERING);
        }


        reservationParticipantsRepository.save(createReservationParticipants(user , reservation));
    }

    @Transactional
    public void leaveReservation(long reservationId, String accessToken) {
        String userPk = jwtTokenProvider.getUserPk(accessToken);

        reservationRepository.findReservationBeforeExpiry(reservationId , userPk)
                .orElseThrow(() -> new ReservationException(CAN_NOT_LEAVE_LAST_PARTICIPATED));

        reservationRepository.leaveReservationById(reservationId , userPk);
    }

    public List<ReservationResponse> findReservationByMeetingId(long meetingId) {
        return reservationRepository.findAllReservationByMeetingId(meetingId);
    }

    public List<ParticipantResponse> findParticipantsById(long reservationId) {
        return reservationRepository.findParticipantsById(reservationId);
    }
}
