package com.team.mystory.meeting.reservation.service;

import com.team.mystory.account.user.domain.User;
import com.team.mystory.account.user.repository.LoginRepository;
import com.team.mystory.meeting.meeting.domain.Meeting;
import com.team.mystory.meeting.meeting.dto.ParticipantResponse;
import com.team.mystory.meeting.meeting.exception.MeetingException;
import com.team.mystory.meeting.meeting.repository.MeetingRepository;
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

import javax.security.auth.login.AccountException;
import java.util.List;

import static com.team.mystory.meeting.reservation.entity.ReservationParticipants.createReservationParticipants;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final ReservationParticipantsRepository reservationParticipantsRepository;

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
            throw new MeetingException("모임 장만 예약할 수 있습니다.");
        }

        Reservation reservation = Reservation.createReservation(request);

        meeting.addReservation(reservation);
    }


    @Transactional
    public void joinReservation(long reservationId, String accessToken) throws AccountException {
        String userPk = jwtTokenProvider.getUserPk(accessToken);
        User user = loginRepository.findById(userPk)
                .orElseThrow(() -> new AccountException("사용자 정보를 찾을 수 없습니다."));

        Reservation reservation = reservationRepository.findReservationAndParticipantsById(reservationId)
                .orElseThrow(() -> new ReservationException("해당 모임을 찾을 수 없습니다."));

        if(reservationRepository.findReservationByIdAndUserId(reservationId , userPk).isPresent()) {
            throw new ReservationException("이미 모임에 참여하고 있습니다.");
        }

        if(reservation.getParticipates().size() >= reservation.getMaxParticipants()) {
            throw new ReservationException("인원이 가득 찼습니다.");
        }


        reservationParticipantsRepository.save(createReservationParticipants(user , reservation));
    }

    @Transactional
    public void leaveReservation(long reservationId, String accessToken) {
        String userPk = jwtTokenProvider.getUserPk(accessToken);

        reservationRepository.findReservationBeforeExpiry(reservationId , userPk)
                .orElseThrow(() -> new ReservationException("모임에 참여 중이지 않거나 , 이전 모임은 나갈 수 없습니다."));

        reservationRepository.leaveReservationById(reservationId , userPk);
    }

    public List<ReservationResponse> findReservationByMeetingId(long meetingId) {
        return reservationRepository.findAllReservationByMeetingId(meetingId);
    }

    public List<ParticipantResponse> findParticipantsById(long reservationId) {
        return reservationRepository.findParticipantsById(reservationId);
    }
}
