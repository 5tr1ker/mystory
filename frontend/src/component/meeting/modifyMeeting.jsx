/* eslint-disable react-hooks/exhaustive-deps */
import { useEffect, useRef, useState } from 'react';
import '../../_style/meeting/newMeeting.css';
import exit from '../../_image/exit.png';
import camera from '../../_image/camera.png';
import Maps from '../map/maps';
import axios from 'axios';

const ModifyMeeting = ({idStatus}) => {
  const urlStat = window.location.pathname.split("/");
  const [arrs, setArrs] = useState([]);
  const [title, setTitle] = useState([]);
  const onChangeTitle = (e) => {
    setTitle(e.target.value);
  }

  const [content, setContent] = useState([]);
  const onChangeContent = (e) => {
    setContent(e.target.value);
  }
  const [participant, setParticipant] = useState(1);
  const onChangeParticipant = (e) => {
    setParticipant(e.target.value);
  }

  const [meetingId , setMeetingId] = useState();
  const [profileImage, setProfileImage] = useState(); // 이미지
  const [address, setAddress] = useState();
  const [locate, setLocate] = useState();
  const [detailAddress, setDetailAddress] = useState();
  const [imageName, setImageName] = useState("모임 대표 사진 이미지를 필수로 선택해주세요. "); // 이미지 이름
  const imageInput = useRef();

  const onChangeDetailAddress = (e) => {
    setDetailAddress(e);
  }

  useEffect(async () => {
    if (idStatus == undefined) {
      alert('로그인 후 사용해주세요.');
      window.history.back();
    }

    let data = [];
    for (let i = 1; i <= 20; i++) {
      data.push(<option key={i} value={i} style={{ fontWeight: "600" }}>{i}</option>);
    }

    setArrs(data);

    await axios({
      method: "GET",
      mode: "cors",
      url: `/meeting/${urlStat[3]}/user`
    }).then((response) => {
      setMeetingId(response.data.meetingId);
      setTitle(response.data.title);
      setLocate({locateX : response.data.locateX , locateY : response.data.locateY});
      setContent(response.data.description);
      setParticipant(response.data.maxParticipants);
      setDetailAddress(response.data.detailAddress);
      setAddress(response.data.address);
    }).catch((err) => {
        alert("모임 장만 수정할 수 있습니다.");
  
        window.location.replace(`/meeting/${urlStat[3]}`);
    });
  }, [])

  const uploadImage = (e) => {
    if (checkValidImage(e)) {
      setProfileImage(e.target.files);
    }
  }

  const request = async () => {
    if (title.length == 0) {
      alert("제목을 입력해주세요.");
      return;
    } else if (content.length == 0) {
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
      "title": title, "description": content, "detailAddress": detailAddress, "address": address ,
      "locateX": locate.locateX, "locateY": locate.locateY, "maxParticipants": participant
    };

    let requestForm = new FormData();
    const blob = new Blob([JSON.stringify(jsonPostData)], { type: "application/json" });
    requestForm.append("meeting", blob);
    if(profileImage != undefined) {
      requestForm.append('image', profileImage[0]);
    }

    await axios({
      method: "PUT",
      mode: "cors",
      url: `/meeting/${meetingId}`,
      headers: { 'Content-Type': 'multipart/form-data' },
      data: requestForm
    })
      .then((response) => {
        alert("모임이 성공적으로 수정되었습니다.");
        window.location.replace("/meeting");
      })
      .catch((e) => {
        alert(e.response.data.message);
        window.history.back();
      });
  }

  const goBack = () => {
    window.history.back();
  }

  const checkValidImage = (e) => {
    if (e.target.files[0].size > 10 * 1024 * 1024) {
      alert("10M가 넘는 파일은 제외되었습니다.");
      return false;
    } else if (e.target.files[0].name.length > 30) {
      alert("파일명이 30자가 넘는 파일은 제외되었습니다.");
      return false;
    } else if (e.target.files[0].name.split('.')[1] == 'jpg' || e.target.files[0].name.split('.')[1] == 'png') {
      setImageName(e.target.files[0].name);
      return true;
    }

    alert("jpg , png 파일만 업로드할 수 있습니다..");
    return false;
  }

  return (
    <div className="screen-newMeeting">
      <div className="div-newMeeting">
        <div className="text-wrapper-newMeeting">우리의 모임 이름은?</div>
        <div className="overlap-newMeeting">
          <div className="rectangle-newMeeting" />
        </div>
        <div className="text-wrapper-2-newMeeting">{imageName}</div>
        <div className="text-wrapper-3-newMeeting">모임 설명 ( 최대 200자 )  </div>
        <div className="text-wrapper-4-newMeeting">모임 제목 ( 최대 30자 ) </div>
        <div className="text-wrapper-5-newMeeting">
          주 활동 지역을 선택해주세요!
        </div>
        <textarea className="rectangle-2-newMeeting" onChange={onChangeContent} maxLength={200} value={content} />
        <input className="rectangle-3-newMeeting" onChange={onChangeTitle} maxLength={30} value={title}/>
        <img
          className="light-s-newMeeting"
          alt="Light s"
          src={exit}
          onClick={goBack}
        />
        <div className="overlap-group-newMeeting">
          <div className="ellipse-newMeeting" onClick={() => imageInput.current.click()} />
          <img
            className="thin-s-newMeeting"
            alt="Thin s"
            src={camera}
            onClick={() => imageInput.current.click()}
          />
          <input type="file" onChange={(e) => uploadImage(e)} name="file" ref={imageInput} style={{ display: "none" }} />
        </div>
        <Maps locateData={setLocate} addressData={setAddress} detailAddressData={onChangeDetailAddress} detailAddress={detailAddress} address={address}/>
        <div className="text-wrapper-7-newMeeting">
          최대 인원 수를 선택해주세요.
        </div>
        <div className="text-wrapper-8-newMeeting">
          최대 인원은 20명까지 가능해요.
        </div>
        <div className="overlap-2-newMeeting">
          <div className="overlap-group-2-newMeeting">
            <select onChange={onChangeParticipant} name="options" key={0} defaultValue={participant} style={{ marginTop: "5px", fontWeight: "600", color: "black", cursor: "pointer", boxShadow: "none" }} className="form-select form-select-sm" aria-label=".form-select-sm example">
              {arrs}
            </select>
          </div>
        </div>
        <div className="rectangle-4-newMeeting" />
        <div className="rectangle-5-newMeeting" />
        <div className="div-wrapper-newMeeting" onClick={request}>
          <div className="text-wrapper-12-newMeeting">모임 수정</div>
        </div>
      </div>
    </div>
  )
}

export default ModifyMeeting;

