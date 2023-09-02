/* eslint-disable react-hooks/exhaustive-deps */
import '../../_style/meeting/meetingView.css';
import line from '../../_image/line-3.svg';
import etc from '../../_image/etc.svg';
import locate from '../../_image/locate.png';
import people from '../../_image/people.svg';
import pen from '../../_image/pen.png';
import trash from '../../_image/trash.svg';
import { useEffect, useState } from 'react';
import axios from 'axios';
import ShowReservation from './reservationContent';

const MeetingView = () => {
  const urlStat = window.location.pathname.split("/");

  const [meeting, setMeeting] = useState(undefined);
  const [participants, setParticipants] = useState([]);
  const [owner, setOwner] = useState({});
  const [moreMember, setMoreMember] = useState(false);
  const [reservation, setReservation] = useState([]);

  const modifyMeeting = () => {
    window.location.replace(`/modify/meeting/${urlStat[2]}`);
  }

  const gotoReserv = () => {
    window.location.replace(`/newMeeting/${urlStat[2]}`);
  }

  useEffect(async () => {
    await axios({
      method: "GET",
      mode: "cors",
      url: `/meeting/${urlStat[2]}`
    }).then((response) => {
      if (response.data == undefined) {
        alert("모임을 불러오는데 오류가 발생했습니다.");

        return;
      }
      setMeeting(response.data);
    }).catch((err) => {

    });

    await axios({
      method: "GET",
      mode: "cors",
      url: `/meeting/participants/${urlStat[2]}`
    }).then((response) => {
      setParticipants(response.data.participantResponses);
      setOwner({ profileImage: response.data.profileImage, userId: response.data.userId })
    }).catch((err) => {

    });

    await axios({
      method: "GET",
      mode: "cors",
      url: `/meeting/${urlStat[2]}/reservation`,
    })
      .then((response) => {
        setReservation(response.data);
      })
      .catch((e) => console.log(e.response.data));
  }, []);

  const ShowMember = ({ member }) => {
    return (
      member.map(data => (
        <img
          key={data.userKey}
          className="mask-group-2-meetingView"
          alt="Mask group"
          src={data.profileImage}
          title={data.userId}
        />
      ))
    )
  }
  const showMoreMember = () => {
    setMoreMember(moreMember ? false : true);
  }

  const MoreMemberContent = ({ member }) => {
    return (
      member.map(data => (
        <div key={data.userKey}>
          <img
            className="mask-group-2-meetingView-viewMore"
            alt="Mask group"
            src={data.profileImage}
            title={data.userId}
          />
          <span className="memberName">{data.userId}</span>
          <div className="memberLine"></div>
        </div>
      ))
    )
  }

  const leaveParty = async () => {
    if (window.confirm("모임을 나가시겠습니까?")) {
      await axios({ // 컨텐츠 갯수
        method: "DELETE",
        mode: "cors",
        url: `/meeting/participants/${urlStat[2]}`
      }).then((response) => {
        window.location.replace("/meeting");
      }).catch((err) => {

      });
    }
  }

  const deleteParty = async () => {
    if (window.confirm("모임을 삭제하시겠습니까?")) {
      await axios({ // 컨텐츠 갯수
        method: "DELETE",
        mode: "cors",
        url: `/meeting/${urlStat[2]}`
      }).then((response) => {
        window.location.replace("/meeting");
      }).catch((err) => {
        alert("모임장만 모임을 지울 수 있습니다.");
      });
    }
  }

  if (meeting == undefined) {
    return null;
  }

  return (
    <div className="screen-meetingView">
      <div className="div-meetingView">
        <div className="partyDescription">
          <img className="overlap-group-meetingView" alt="image" src={meeting.meetingImage} />
          <div className="text-wrapper-meetingView">참여 모임원 {meeting.nowParticipants}명</div>
          <img
            className="line-meetingView"
            alt="Line"
            src={line}
          />
          <img
            className="img-meetingView"
            alt="Line"
            src={line}
          />
          {/* 파티원 */}
          <div className="partyGroup">
            <ShowMember member={participants} />
          </div>
          <div className="vector-3-meetingView-more" onClick={showMoreMember}>
            <img
              className="vector-3-meetingView"
              alt="Vector"
              src={etc}
            />
          </div>
          {moreMember ? <div className="moreMemberArea"><MoreMemberContent member={participants} /></div> : null}

          <div className="text-wrapper-15-meetingView">{meeting.title}</div>
          <p className="element-meetingView">
            {meeting.description}
          </p>

          {/* 파티 장 이미지 */}
          <img
            className="mask-group-3-meetingView"
            alt="meeting Owner"
            title={owner.userId}
            src={owner.profileImage}
          />
          <div className="overlap-meetingView">
            <div className="text-wrapper-5-meetingView">참여</div>
            <div className="rectangle-meetingView" onClick={gotoReserv} />
            <div className="text-wrapper-6-meetingView">일정 추가</div>
          </div>
          <div className="text-wrapper-8-meetingView">주 모임 장소</div>
          <div className="text-wrapper-9-meetingView">모임장</div>
          <p className="text-wrapper-10-meetingView">{meeting.address} / {meeting.detailAddress}</p>
          <div className="text-wrapper-11-meetingView">현재 {meeting.nowParticipants}명 / 최대 {meeting.maxParticipants}명</div>
          <img
            className="line-2-meetingView"
            alt="Line"
            src={line}
          />
          <img
            className="light-s-3-meetingView"
            alt="Light s"
            src={locate}
          />
          <img
            className="vector-2-meetingView"
            alt="Vector"
            src={people}
          />
        </div>
        <div className="text-wrapper-12-meetingView rectangle-2-meetingView">예약된 모임</div>
        <div className="overlap-2-meetingView">
          <img
            className="line-3-meetingView"
            alt="Line"
            src={line}
          />
        </div>
        {/* 모임 예약 데이터 */}
        <div className='reservationArea'>
          <ShowReservation reservation={reservation} />
        </div>
        {/* 모임 컨트롤러 */}
        <div className="overlap-3-meetingView" onClick={leaveParty}>
          <div className="rectangle-3-meetingView" />
          <div className="text-wrapper-16-meetingView">모임 나가기</div>
        </div>
        <div className="overlap-3-meetingDelete" onClick={deleteParty}>
          <img
            className="thin-s-meetingDelete"
            alt="Thin s"
            src={trash}
          />
        </div>
        <div className="thin-s-wrapper-meetingView" onClick={modifyMeeting}>
          <img
            className="thin-s-meetingView"
            alt="Thin s"
            src={pen}
          />
        </div>
      </div>
    </div>
  )
}

export default MeetingView;