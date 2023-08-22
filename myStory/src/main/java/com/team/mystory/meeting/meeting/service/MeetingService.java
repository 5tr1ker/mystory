package com.team.mystory.meeting.meeting.service;

import com.team.mystory.account.user.domain.User;
import com.team.mystory.account.user.repository.LoginRepository;
import com.team.mystory.meeting.meeting.domain.Meeting;
import com.team.mystory.meeting.meeting.dto.MeetingRequest;
import com.team.mystory.meeting.meeting.dto.MeetingResponse;
import com.team.mystory.meeting.meeting.exception.MeetingException;
import com.team.mystory.meeting.meeting.repository.MeetingRepository;
import com.team.mystory.s3.service.S3Service;
import com.team.mystory.security.jwt.support.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.AuthenticationException;
import javax.security.auth.login.AccountException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private final MeetingRepository meetingRepository;

    private final S3Service s3Service;

    private final JwtTokenProvider jwtTokenProvider;

    private final LoginRepository loginRepository;

    @Transactional
    public void createMeeting(MeetingRequest meeting, MultipartFile image , String accessToken) throws IOException, AccountException {
        String userPk = jwtTokenProvider.getUserPk(accessToken);
        User user = loginRepository.findById(userPk)
                .orElseThrow(() -> new AccountException("사용자 장보를 찾을 수 없습니다."));

        Meeting meetingEntity = Meeting.createMeetingEntity(meeting , user);
        String url = s3Service.uploadImageToS3(image);

        meetingEntity.updateMeetingImage(url);

        meetingRepository.save(meetingEntity);
    }

    public List<MeetingResponse> findAllMeeting() {
        return meetingRepository.findAllMeeting();
    }

    public List<MeetingResponse> findMeetingByAddress(String address) {
        return meetingRepository.findMeetingByAddress(address);
    }

    public List<MeetingResponse> findMeetingByTitle(String title) {
        return meetingRepository.findMeetingByTitle(title);
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

}