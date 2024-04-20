/* eslint-disable react-hooks/exhaustive-deps */
import '../../_style/meeting/meetingDetail.css'
import exit from '../../_image/exit.png';
import locate from '../../_image/locate.png';
import people from '../../_image/people.svg';
import { Fragment, useEffect, useState } from 'react';
import axios from 'axios';

const MeetingDetail = ({hideDetail , detailNumber , meetingOption}) => {
  let sessionUserId = localStorage.getItem("userId");
  const [meeting , setMeeting] = useState(undefined);

  useEffect(async() => {
    await axios({ // 컨텐츠 갯수
      method: "GET",
      mode: "cors",
      url: `/meeting/${detailNumber}`
    }).then((response) => {
      if(response.data == undefined) {
        alert("모임을 불러오는데 오류가 발생했습니다.");

        return;
      }
      setMeeting(response.data);
    }).catch((err) => {

    });

    
  } , []);

  const joinMeeting = async () => {
    if(sessionUserId == undefined) {
      alert("로그인 후 사용해주세요.");

      return;
    }

    if(meeting.nowParticipants >= meeting.maxParticipants) {
      alert("모임 내 인원이 가득찼습니다.");
      return;
    }

    await axios({
      method: "POST",
      mode: "cors",
      url: `/meeting/${detailNumber}`
    }).then((response) => {
      alert("모임에 참여하셨습니다. 모임 방은 \" 나의 모임 \" 란에서 이동이 가능합니다.");
      hideDetail();
    }).catch((err) => {
      alert("모임에 이미 참여하고 있습니다.");
    });
  }

  const moveMeeting = () => {
    window.location.replace(`/meeting/${detailNumber}`);
  }

  if(meeting == undefined) {
    return null;
  } else {
    return(
      <Fragment>
      <div className="screen-detail">
        <div className="div-detail">
          <img className="overlap-group-detail" alt="images" src={meeting.meetingImage} />
            <div className="ellips-detaile" />
            <div className="text-wrapper-10-detail">모임 이름</div>
          <div className="text-wrapper-detail">{meeting.title}</div>
          <div className="text-wrapper-11-detail">모임 설명</div>
          <p className="element-detail">
            {meeting.description}
          </p>
          <div className="text-wrapper-3-detail">주 모임 장소</div>
          <p className="p-detail">{meeting.address} / {meeting.detailAddress}</p>
          <div className="text-wrapper-5-detail">현재 {meeting.nowParticipants}명 / 최대 {meeting.maxParticipants} 명</div>
          <img
            className="light-s-detail"
            alt="Light s"
            src={locate}
          />
          <img
            className="vector-detail"
            alt="Vector"
            src={people}
          />
          <div className="text-wrapper-6-detail">참여</div>
          {meetingOption ? 
        <div className="overlap-detail" onClick={moveMeeting}>
          <div className="text-wrapper-7-detail-join">모임 방으로 이동</div>
        </div>
          :
          <div className="overlap-detail" onClick={joinMeeting}>
            <div className="text-wrapper-7-detail">모임 참가</div>
          </div>
          }
          
          <img src={exit} className="exit-meetingDetail" alt="exit" onClick={() => hideDetail()}/>
        </div>
      </div>
      </Fragment>
      )
  }
}

export default MeetingDetail;