import { useEffect, useState } from "react";
import '../../_style/meeting/newReservation.css';
import exit from '../../_image/exit.png';

const NewReservation = () => {

    const [arrs , setArrs] = useState([]);

    useEffect(() => {
        let data = [];
        for(let i = 1; i <= 20; i++) {
            data.push(<option key={i} value={i} style={{ fontWeight: "600" }}>{i}</option>);
        }

        setArrs(data);
    } , [])

    const goBack = () => {
        window.history.back();
    }

    return(
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
        <textarea className="rectangle-2-newReservation" />
        <img
          className="light-s-newReservation"
          alt="Light s"
          src={exit}
          onClick={goBack}
        />
        <img
          className="IMG-newReservation"
          alt="Img"
          src="https://generation-sessions.s3.amazonaws.com/89c072f30457b4615fe7d1fda014350b/img/img-7110-1@2x.png"
        />
        <div className="howMeetText-01">언제 몇시에 만날까요?</div>
        <div className="howMeetText-02">모임 날짜와 시간을 선택해주세요.</div>
        <div className="text-wrapper-7-newReservation">
          최대 인원 수를 선택해주세요.
        </div>
        <div className="text-wrapper-8-newReservation">
          최대 인원은 20명까지 가능해요.
        </div>
        <div className="overlap-2-newReservation">
          <div className="overlap-group-2-newReservation">
                <select name="options" key={0} defaultValue={0} style={{ marginTop: "5px", fontWeight: "600", color: "black", cursor: "pointer", boxShadow: "none" }} className="form-select form-select-sm" aria-label=".form-select-sm example">
                    {arrs}
                </select>
          </div>
        </div>
        <div className="rectangle-4-newReservation" />
        <div className="rectangle-5-newReservation" />
        <div className="div-wrapper-newReservation">
          <div className="text-wrapper-12-newReservation">모임 생성</div>
        </div>
      </div>
    </div>
    )
}

export default NewReservation;