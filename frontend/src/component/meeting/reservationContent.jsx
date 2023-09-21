import line from '../../_image/line-3.svg';
import defaultALT from '../../_image/defaultALT.png'
import locate from '../../_image/locate.png';
import time from '../../_image/time.png';
import people from '../../_image/people.svg';
import axios from 'axios';
import { Fragment, useEffect, useState } from 'react';

const ShowReservation = ({ reservation }) => {
    const joinReserv = async (reservationId , maxPart , nowPart) => {
        if(nowPart >= maxPart) {
            alert("인원이 꽉 찬 모임입니다.");
            return;
        }

        await axios({
            method: "POST",
            mode: "cors",
            url: `/meeting/reservation/${reservationId}/join`
        }).then((response) => {
            alert("모임에 참가하셨습니다.");
            window.location.reload();
        }).catch((err) => {
            alert(err.response.data.message);
        });
    }

    const leaveReserv = async (reservationId) => {
        await axios({
            method: "DELETE",
            mode: "cors",
            url: `/meeting/reservation/${reservationId}/leave`
        }).then((response) => {
            alert("일정에 나갔습니다.");
            window.location.reload();
        }).catch((err) => {
            alert(err.response.data.message);
        });
    }
    const ToogleReserv = ({id}) => {
        const [toogle, setToogle] = useState(false);
        const [participant, setParticipant] = useState([]);

        const setToogleFunc = () => {
            toogle ? setToogle(false) : setToogle(true);
        }

        const showParticipant = async (reservationId) => {
            await axios({
                method: "GET",
                mode: "cors",
                url: `/meeting/reservation/${reservationId}`
            }).then((response) => {
                setParticipant(response.data);
            }).catch((err) => {
                alert(err.response.data.message);
            });
        }

        useEffect(() => {
            showParticipant(id);
        } , []);

        return (
            <Fragment>
                <span className="showParticipants" onClick={() => setToogleFunc()}>참여자 보기</span>
                {toogle ? <div className="showParticipantsElement">
                    <ShowMember member={participant} />
                </div>
                    : null}
            </Fragment>
        )
    }

    const ShowMember = ({ member }) => {
        return (
            member.map(data => (
                <div key={data.userKey}>
                    <img
                        className="mask-group-2-meetingView-viewMore"
                        alt={defaultALT}
                        src={data.profileImage}
                        title={data.userId}
                    />
                    <span className="memberName">{data.userId}</span>
                    <div className="memberLine"></div>
                </div>
            ))
        )
    }

    return (
        reservation.map(data => (
            <div className="reservation" key={data.reservationId}>
                <div className="text-wrapper-122-meetingView">모임 설명</div>
                <div className="text-wrapper-2-meetingView">장소 및 일정</div>
                <img
                    className="light-s-meetingView"
                    alt="Light s"
                    src={time}
                />
                <div className="text-wrapper-3-meetingView">{data.date}</div>
                <img
                    className="light-s-2-meetingView"
                    alt="Light s"
                    src={locate}
                />
                <p className="p-meetingView">{data.address} <br /> {data.detailAddress}</p>
                <img
                    className="vector-meetingView"
                    alt="Vector"
                    src={people}
                />
                <p className="text-wrapper-4-meetingView">현재 {data.userCount} 명 / 최대 {data.maxParticipants}명</p>
                <ToogleReserv id={data.reservationId}/>
                <p className="element-2-meetingView">
                    {data.description}
                </p>
                <img
                    className="line-4-meetingView"
                    alt="Line"
                    src={line}
                />
                <img
                    className="line-5-meetingView"
                    alt="Line"
                    src={line}
                />
                <div className="div-wrapper-meetingView" onClick={() => joinReserv(data.reservationId , data.maxParticipants , data.userCount)}>
                    <div className="text-wrapper-7-meetingView">일정 참가</div>
                </div>
                <div className="div-wrapper-meetingView-remove" onClick={() => leaveReserv(data.reservationId)}>
                    <div className="text-wrapper-7-meetingView-remove">일정 탈퇴</div>
                </div>
            </div>
        ))
    )
}

export default ShowReservation;