import { useEffect, useRef, useState } from 'react';
import '../../_style/meeting/newMeeting.css';
import exit from '../../_image/exit.png';
import camera from '../../_image/camera.png';
import Maps from '../map/maps';

const NewMeeting = () => {

    const [arrs , setArrs] = useState([]);
    const imageInput = useRef();

    useEffect(() => {
        let data = [];
        for(let i = 1; i <= 20; i++) {
            data.push(<option key={i} value={i} style={{ fontWeight: "600" }}>{i}</option>);
        }

        setArrs(data);
    } , [])

    const uploadImage = (e) => {
        if(checkValidImage(e)) {
            setProfileImage(e.target.files);
        }
    }
    const [profileImage, setProfileImage] = useState(); // 이미지
    const [imageName , setImageName] = useState("모임 대표 사진 이미지를 필수로 선택해주세요. "); // 이미지 이름

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

    return(
    <div className="screen-newMeeting">
      <div className="div-newMeeting">
        <div className="text-wrapper-newMeeting">우리의 모임 이름은?</div>
        <div className="overlap-newMeeting">
          <div className="rectangle-newMeeting" />
        </div>
        <div className="text-wrapper-2-newMeeting">{imageName}</div>
        <div className="text-wrapper-3-newMeeting">모임 설명</div>
        <div className="text-wrapper-4-newMeeting">모임 제목</div>
        <div className="text-wrapper-5-newMeeting">
          주 활동 지역을 선택해주세요!
        </div>
        <textarea className="rectangle-2-newMeeting" />
        <input className="rectangle-3-newMeeting"/>
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
          <input type="file" onChange={(e) => uploadImage(e)} name="file" ref={imageInput} style={{ display: "none" }}/>
        </div>
        <Maps/>
        <div className="text-wrapper-7-newMeeting">
          최대 인원 수를 선택해주세요.
        </div>
        <div className="text-wrapper-8-newMeeting">
          최대 인원은 20명까지 가능해요.
        </div>
        <div className="overlap-2-newMeeting">
          <div className="overlap-group-2-newMeeting">
                <select name="options" key={0} defaultValue={0} style={{ marginTop: "5px", fontWeight: "600", color: "black", cursor: "pointer", boxShadow: "none" }} className="form-select form-select-sm" aria-label=".form-select-sm example">
                    {arrs}
                </select>
          </div>
        </div>
        <div className="rectangle-4-newMeeting" />
        <div className="div-wrapper-newMeeting">
          <div className="text-wrapper-12-newMeeting">모임 생성</div>
        </div>
      </div>
    </div>
    )
}

export default NewMeeting;

