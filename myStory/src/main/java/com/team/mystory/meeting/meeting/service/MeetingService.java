package com.team.mystory.meeting.meeting.service;

import com.team.mystory.account.user.domain.User;
import com.team.mystory.account.user.repository.LoginRepository;
import com.team.mystory.meeting.meeting.domain.Meeting;
import com.team.mystory.meeting.meeting.domain.MeetingParticipant;
import com.team.mystory.meeting.meeting.dto.MeetingRequest;
import com.team.mystory.meeting.meeting.dto.MeetingResponse;
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

        long result = meetingRepository.deleteMeetingById(userPk , meetingId);

        if(result == 0) {
            throw new MeetingException("유효하지 않은 접근입니다.");
        }
    }

    @Transactional
    public void modifyMeeting(MeetingRequest meetingRequest, MultipartFile image, String accessToken, long meetingId) throws IOException {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new MeetingException("해당 미팅을 찾을 수 없습니다."));

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

    public List<MeetingResponse> getMeetingsParticipantIn(String accessToken) {
        String userPk = jwtTokenProvider.getUserPk(accessToken);

        return meetingRepository.getMeetingsParticipantIn(userPk);
    }

    public List<MeetingResponse> findAllMeetingByUserId(Pageable pageable , String accessToken) {
        String userPk = jwtTokenProvider.getUserPk(accessToken);

        return meetingRepository.findAllMeetingByUserId(pageable , userPk);
    }

    public long findAllMeetingByUserIdCount(String accessToken) {
        String userPk = jwtTokenProvider.getUserPk(accessToken);

        return meetingRepository.findAllMeetingByUserIdCount(userPk);
    }
}
