import { useEffect, useState } from "react";
import '../../_style/meeting/newReservation.css';
import exit from '../../_image/exit.png';
import Calendar from "react-calendar";
import 'react-calendar/dist/Calendar.css'; // css import
import "date-fns";
import moment from 'moment';
import ReservMaps from "../map/mapsReserv";
import axios from "axios";
import TimePicker from 'react-time-picker';
import 'react-time-picker/dist/TimePicker.css';
import 'react-clock/dist/Clock.css';

const NewReservation = () => {
  const urlStat = window.location.pathname.split("/");
  const [arrs, setArrs] = useState([]);
  const [locate, setLocate] = useState();
  const [address, setAddress] = useState();
  const [detailAddress, setDetailAddress] = useState();
  const [description, setDescription] = useState("");
  const [participant, setParticipant] = useState(1);

  const [calander, setCalander] = useState(new Date());
  const [time, setTime] = useState('10:00');

  const onChangeDetailAddress = (e) => {
    setDetailAddress(e);
  }

  const onChangeDescription = (e) => {
    setDescription(e.target.value)
  }

  const onChangeParticipant = (e) => {
    setParticipant(e.target.value)
  }

  useEffect(() => {
    let data = [];
    for (let i = 1; i <= 20; i++) {
      data.push(<option key={i} value={i} style={{ fontWeight: "600" }}>{i}</option>);
    }

    setArrs(data);
  }, [])

  const goBack = () => {
    window.history.back();
  }

  const request = async () => {
    if (description.length == 0) {
      alert("설명을 입력해주세요.");
      return;
    } else if (locate == undefined) {
      alert("위치를 선택해주세요.");
      return;
    } else if (detailAddress == undefined) {
      alert("상세 주소를 입력해주세요.");
      return;
    }

    const jsonPostData = {
      "date": moment(calander).format("YYYY-MM-DD") + "T" + time , "description": description, "detailAddress": detailAddress, "address": address,
      "locateX": locate.locateX, "locateY": locate.locateY, "maxParticipants": participant , "time" : time
    };

    await axios({
      method: "POST",
      mode: "cors",
      url: `/meeting/${urlStat[2]}/reservation`,
      headers: { "Content-Type": "application/json" },
      data: JSON.stringify(jsonPostData)
    })
      .then((response) => {
        alert("예약이 성공적으로 등록되었습니다.");
        window.location.replace(`/meeting/${urlStat[2]}`);
      })
      .catch((e) => alert(e.response.data.message));
  }

return (
  <div className="screen-newReservation">
    <div className="div-newReservation">
      <div className="text-wrapper-newReservation">모임 예약하기</div>
      <div className="overlap-newReservation">
        <div className="rectangle-newReservation" />
      </div>
      <div className="text-wrapper-3-newReservation">모임 설명</div>
      <div className="text-wrapper-5-newReservation">
        모임 지역을 선택해주세요!
      </div>
      <div className="reservMap">
        <ReservMaps locateData={setLocate} addressData={setAddress} detailAddressData={onChangeDetailAddress} detailAddress={detailAddress} address={address} />
      </div>
      <textarea className="rectangle-2-newReservation" onChange={onChangeDescription} />
      <img
        className="light-s-newReservation"
        alt="Light s"
        src={exit}
        onClick={goBack}
      />
      <div className="howMeetText-01">언제 몇시에 만날까요?</div>
      <div className="howMeetText-02">모임 날짜와 시간을 선택해주세요.</div>
      <Calendar onChange={setCalander} value={calander} className="calanderData" />
      <div className="calanderSubData">{moment(calander).format("YYYY 년 MM 월 DD 일")}</div>
      <div className="timePicker">
      <TimePicker onChange={setTime} value={time} />
      </div>
      <div className="text-wrapper-7-newReservation">
        최대 인원 수를 선택해주세요.
      </div>
      <div className="text-wrapper-8-newReservation">
        최대 인원은 20명까지 가능해요.
      </div>
      <div className="overlap-2-newReservation">
        <div className="overlap-group-2-newReservation">
          <select onChange={onChangeParticipant} name="options" key={0} defaultValue={0} style={{ marginTop: "5px", fontWeight: "600", color: "black", cursor: "pointer", boxShadow: "none" }} className="form-select form-select-sm" aria-label=".form-select-sm example">
            {arrs}
          </select>
        </div>
      </div>
      <div className="rectangle-4-newReservation" />
      <div className="rectangle-5-newReservation" />
      <div className="div-wrapper-newReservation" onClick={request}>
        <div className="text-wrapper-12-newReservation">모임 생성</div>
      </div>
    </div>
  </div>
)
}

export default NewReservation;