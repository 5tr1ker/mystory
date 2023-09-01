package com.team.mystory.meeting.meeting.service;

import com.team.mystory.account.user.domain.User;
import com.team.mystory.account.user.repository.LoginRepository;
import com.team.mystory.meeting.meeting.domain.Meeting;
import com.team.mystory.meeting.meeting.dto.MeetingMemberResponse;
import com.team.mystory.meeting.meeting.dto.MeetingRequest;
import com.team.mystory.meeting.meeting.dto.MeetingResponse;
import com.team.mystory.meeting.meeting.dto.ParticipantResponse;
import com.team.mystory.meeting.meeting.exception.MeetingException;
import com.team.mystory.meeting.meeting.repository.MeetingParticipantRepository;
import com.team.mystory.meeting.meeting.repository.MeetingRepository;
import com.team.mystory.s3.service.S3Service;
import com.team.mystory.security.jwt.support.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.login.AccountException;
import java.io.IOException;
import java.util.List;

import static com.team.mystory.meeting.meeting.domain.MeetingParticipant.createMeetingParticipant;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository meetingRepository;

    private final S3Service s3Service;

    private final JwtTokenProvider jwtTokenProvider;

    private final LoginRepository loginRepository;

    private final MeetingParticipantRepository meetingParticipantRepository;

    @Transactional
    public void createMeeting(MeetingRequest meeting, MultipartFile image , String accessToken) throws IOException, AccountException {
        String userPk = jwtTokenProvider.getUserPk(accessToken);
        User user = loginRepository.findById(userPk)
                .orElseThrow(() -> new AccountException("사용자 정보를 찾을 수 없습니다."));

        Meeting meetingEntity = Meeting.createMeetingEntity(meeting , user);
        String url = s3Service.uploadImageToS3(image);

        meetingEntity.updateMeetingImage(url);

        Meeting result = meetingRepository.save(meetingEntity);
        joinMeeting(result.getMeetingId(), accessToken);
    }

    public MeetingResponse findMeetingByMeetingId(long meetingId) {
        return meetingRepository.findMeetingByMeetingId(meetingId)
                .orElseThrow(() -> new MeetingException("모임 정보를 찾을 수 없습니다."));
    }

    @Transactional
    public void joinMeeting(long meetingId , String accessToken) throws AccountException {
        String userPk = jwtTokenProvider.getUserPk(accessToken);

        if(meetingRepository.findMeetingParticipantByMeetingIdAndUserId(meetingId , userPk).isPresent()) {
            throw new MeetingException("이미 참여하고 있습니다.");
        }

        User user = loginRepository.findById(userPk)
                .orElseThrow(() -> new AccountException("사용자 정보를 찾을 수 없습니다."));

        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new MeetingException("미팅정보를 찾을 수 없습니다."));

        meetingParticipantRepository.save(createMeetingParticipant(meeting , user));
    }

    public List<MeetingResponse> findAllMeeting(Pageable pageable) {
        return meetingRepository.findAllMeeting(pageable);
    }

    public List<MeetingResponse> findMeetingByTitleOrAddress(Pageable pageable , String title) {
        return meetingRepository.findMeetingByTitleOrAddress(pageable , title);
    }

    public long findMeetingByTitleOrAddressCount(String data) {
        return meetingRepository.findMeetingByTitleOrAddressCount(data);
    }

    @Transactional
    public void deleteMeetingById(String accessToken , long meetingId) {
        String userPk = jwtTokenProvider.getUserPk(accessToken);

        checkMeetingOwner(userPk , meetingId);

        meetingRepository.deleteById(meetingId);
    }

    public void checkMeetingOwner(String userPk , long meetingId) {
        MeetingMemberResponse result = meetingRepository.findMeetingOwnerByMeetingId(meetingId);

        if(!result.getUserId().equals(userPk)) {
            throw new MeetingException("모임장은 파티를 나갈 수 없습니다.");
        }
    }

    @Transactional
    public void modifyMeeting(MeetingRequest meetingRequest, MultipartFile image, String accessToken, long meetingId) throws IOException {
        String userPk = jwtTokenProvider.getUserPk(accessToken);
        Meeting meeting = meetingRepository.findMeetingByMeetingIdAndMeetingOwner(meetingId , userPk)
                .orElseThrow(() -> new MeetingException("파티를 찾을 수 없거나 , 파티장만 수정할 수 있습니다."));

        if(image != null) {
            modifyImage(meeting , image);
        }

        meeting.updateData(meetingRequest);
    }

    public void modifyImage(Meeting meeting , MultipartFile image) throws IOException {
        String url = s3Service.uploadImageToS3(image);
        s3Service.deleteFile(meeting.getMeetingImage());

        meeting.updateMeetingImage(url);
    }

    public List<MeetingResponse> findAllMeetingByUserId(Pageable pageable , String accessToken) {
        String userPk = jwtTokenProvider.getUserPk(accessToken);

        return meetingRepository.findAllMeetingByUserId(pageable , userPk);
    }

    public long findAllMeetingByUserIdCount(String accessToken) {
        String userPk = jwtTokenProvider.getUserPk(accessToken);

        return meetingRepository.findAllMeetingByUserIdCount(userPk);
    }

    public List<ParticipantResponse> findParticipantsByMeetingId(long meetingId) {
        return meetingRepository.findParticipantsByMeetingId(meetingId);
    }

    public MeetingMemberResponse findMeetingMemberByMeetingId(long meetingId) {
         MeetingMemberResponse result = meetingRepository.findMeetingOwnerByMeetingId(meetingId);

         result.setParticipantResponses(findParticipantsByMeetingId(meetingId));

         return result;
    }

    @Transactional
    public void leaveMeeting(String accessToken, long meetingId) {
        String userPk = jwtTokenProvider.getUserPk(accessToken);

        MeetingMemberResponse response = meetingRepository.findMeetingOwnerByMeetingId(meetingId);
        if(response.getUserId().equals(userPk)) {
            throw new MeetingException("모임장은 모임을 나갈 수 없습니다.");
        }

        meetingRepository.deleteParticipantsByMeetingIdAndUserId(meetingId , userPk);
    }
}
