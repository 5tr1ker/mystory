package com.team.mystory.meeting.reservation.service;

import com.team.mystory.account.user.domain.User;
import com.team.mystory.account.user.repository.LoginRepository;
import com.team.mystory.meeting.meeting.domain.Meeting;
import com.team.mystory.meeting.meeting.exception.MeetingException;
import com.team.mystory.meeting.meeting.repository.MeetingRepository;
import com.team.mystory.meeting.reservation.dto.ReservationRequest;
import com.team.mystory.meeting.reservation.dto.ReservationResponse;
import com.team.mystory.meeting.reservation.entity.Reservation;
import com.team.mystory.meeting.reservation.exception.ReservationException;
import com.team.mystory.meeting.reservation.repository.ReservationRepository;
import com.team.mystory.security.jwt.support.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final LoginRepository loginRepository;

    private final MeetingRepository meetingRepository;

    @Transactional
    public void createReservation(ReservationRequest request, String accessToken , long meetingId) throws AccountException {
        String userPk = jwtTokenProvider.getUserPk(accessToken);
        User user = loginRepository.findById(userPk)
                .orElseThrow(() -> new AccountException("사용자 정보를 찾을 수 없습니다."));

        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new MeetingException("미팅방을 찾을 수 없습니다."));

        if(meeting.getMeetingOwner().getUserKey() != user.getUserKey()) {
            throw new MeetingException("권한이 없습니다.");
        }

        Reservation reservation = Reservation.createReservation(request);

        meeting.getReservations().add(reservation);
    }


    @Transactional
    public void joinReservation(long reservationId, String accessToken) throws AccountException {
        String userPk = jwtTokenProvider.getUserPk(accessToken);
        User user = loginRepository.findById(userPk)
                .orElseThrow(() -> new AccountException("사용자 정보를 찾을 수 없습니다."));

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationException("해당 모임을 찾을 수 없습니다."));

        reservation.getParticipates().add(user);
    }

    public void leaveReservation(long reservationId, String accessToken) throws AccountException {
        String userPk = jwtTokenProvider.getUserPk(accessToken);
        User user = loginRepository.findById(userPk)
                .orElseThrow(() -> new AccountException("사용자 정보를 찾을 수 없습니다."));

        Reservation reservation = reservationRepository.findReservationBeforeExpiry(reservationId)
                .orElseThrow(() -> new ReservationException("모임 정보를 찾을 수 없거나 , 이전 모임은 나갈 수 없습니다."));

        reservation.leaveReservation(user);
    }

    public List<ReservationResponse> findReservation(long meetingId) {
        return reservationRepository.findAllReservationByMeetingId(meetingId);
    }

    public void deleteReservation(long reservationId,  String accessToken) throws AccountException {
        String userPk = jwtTokenProvider.getUserPk(accessToken);
        User user = loginRepository.findById(userPk)
                .orElseThrow(() -> new AccountException("사용자 정보를 찾을 수 없습니다."));

        long result = reservationRepository.deleteReservation(reservationId , user.getUserKey());

        if(result == 0) {
            throw new ReservationException("비정상적인 접근입니다.");
        }
    }
}
