import '../../_style/meeting/meetingDetail.css'
import exit from '../../_image/exit.png';
import time from '../../_image/time.png';
import locate from '../../_image/locate.png';
import people from '../../_image/people.svg';

const MeetingDetail = () => {

    return(
        <div className="screen-detail">
      <div className="div-detail">
        <div className="overlap-group-detail">
          <div className="ellips-detaile" />
        </div>
        <div className="text-wrapper-detail">회기 꽃술 6인팟!!</div>
        <p className="element-detail">
          경희대 근처 사시는 20대 남녀 모두 환영합니다.
          <br />
          같이 즐겁게 술 마셔요!
        </p>
        <div className="text-wrapper-2-detail">참여 모임원 5명</div>
        <div className="text-wrapper-3-detail">주 모임 장소</div>
        <div className="text-wrapper-4-detail">마지막 활동</div>
        <p className="p-detail">서울특별시 동대문구 경희대로1길 8-14 1층</p>
        <div className="text-wrapper-5-detail">현재 6명</div>
        <img
          className="img-detail"
          alt="Line"
          src="https://generation-sessions.s3.amazonaws.com/414ebf7288748dd8acff66c202be8e65/img/line-3.svg"
        />
        <img
          className="mask-group-detail"
          alt="Mask group"
          src="https://generation-sessions.s3.amazonaws.com/414ebf7288748dd8acff66c202be8e65/img/mask-group-5@2x.png"
        />
        <img
          className="mask-group-2-detail"
          alt="Mask group"
          src="https://generation-sessions.s3.amazonaws.com/414ebf7288748dd8acff66c202be8e65/img/mask-group-4@2x.png"
        />
        <img
          className="mask-group-3-detail"
          alt="Mask group"
          src="https://generation-sessions.s3.amazonaws.com/414ebf7288748dd8acff66c202be8e65/img/mask-group-3@2x.png"
        />
        <img
          className="mask-group-4-detail"
          alt="Mask group"
          src="https://generation-sessions.s3.amazonaws.com/414ebf7288748dd8acff66c202be8e65/img/mask-group-2@2x.png"
        />
        <img
          className="mask-group-5-detail"
          alt="Mask group"
          src="https://generation-sessions.s3.amazonaws.com/414ebf7288748dd8acff66c202be8e65/img/mask-group-1@2x.png"
        />
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
        <div className="overlap-detail">
          <div className="text-wrapper-7-detail">모임 참가</div>
        </div>
        <div className="text-wrapper-8-detail">6월 23일 오후 6시</div>
        <img
          className="light-s-2-detail"
          alt="Light s"
          src={time}
        />
        <img src={exit} className="exit-meetingDetail"/>
      </div>
    </div>
    )
}

export default MeetingDetail;