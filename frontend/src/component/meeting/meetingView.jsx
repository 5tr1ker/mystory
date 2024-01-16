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
import defaultALT from '../../_image/defaultALT.png'
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

  const reportContent = async (meeting, owner) => {
    if (!window.confirm("해당 미팅을 신고하시겠습니까?")) {
      return;
    }

    const reason = window.prompt("신고 사유 (100자 이내)" + "");
    if (reason.length > 100) {
      alert("신고 사유는 100자 이내로 작성해주세요.");

      return;
    }

    await axios({
      method: "POST",
      url: `/admin/report/content`,
      data: {
        "content": reason,
        "reportContentURL": `/meeting/${urlStat[2]}`,
        "reportType": "MEETING",
        "target": {
          "writer": owner.userId,
          "title": meeting.title,
          "content": meeting.description
        }
      }
    }).then((e) => {
      alert("신고가 완료되었습니다.");
    }).catch((e) => {
      alert("로그인 후에 사용해주세요.");
    });
  }

  useEffect(async () => {

    await axios({
      method: "GET",
      mode: "cors",
      url: `/meeting/is-participants/${urlStat[2]}`
    }).then((response) => {
      if (response.data == false) {
        alert("비 정상적인 접근입니다.");

        window.location.replace(`/meeting`);
      }
    }).catch((err) => {

    });

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
    const tmp = member.slice(0, 4);

    return (
      tmp.map(data => (
        <img
          key={data.userKey}
          className="mask-group-2-meetingView"
          alt="img"
          src={data.profileImage || defaultALT}
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
            alt="img"
            src={data.profileImage || defaultALT}
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
        alert(err.response.data.message);
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
            alt="img"
            title={owner.userId}
            src={owner.profileImage || defaultALT}
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
          <ShowReservation reservation={reservation} meeting={meeting} owner={owner} />
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
        <div className="thin-s-wrapper-report" onClick={() => reportContent(meeting, owner)}>
          <svg style={{ cursor: "pointer" }} xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" className="bi bi-bell text-wrapper-88-meetingView" viewBox="0 0 16 16">
            <path d="M8 16a2 2 0 0 0 2-2H6a2 2 0 0 0 2 2zM8 1.918l-.797.161A4.002 4.002 0 0 0 4 6c0 .628-.134 2.197-.459 3.742-.16.767-.376 1.566-.663 2.258h10.244c-.287-.692-.502-1.49-.663-2.258C12.134 8.197 12 6.628 12 6a4.002 4.002 0 0 0-3.203-3.92L8 1.917zM14.22 12c.223.447.481.801.78 1H1c.299-.199.557-.553.78-1C2.68 10.2 3 6.88 3 6c0-2.42 1.72-4.44 4.005-4.901a1 1 0 1 1 1.99 0A5.002 5.002 0 0 1 13 6c0 .88.32 4.2 1.22 6z" />
          </svg>
        </div>
      </div>
    </div>
  )
}

export default MeetingView;