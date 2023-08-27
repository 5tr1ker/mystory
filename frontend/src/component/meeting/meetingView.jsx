import '../../_style/meeting/meetingView.css';
import line from '../../_image/line-3.svg';
import etc from '../../_image/etc.svg';
import locate from '../../_image/locate.png';
import time from '../../_image/time.png';
import people from '../../_image/people.svg';
import pen from '../../_image/pen.png';

const MeetingView = () => {
    return (
        <div className="screen-meetingView">
      <div className="div-meetingView">
        <div className="partyDescription">
        <div className="overlap-group-meetingView">
          <div className="ellipse-meetingView" />
          <div className="ellipse-2-meetingView" />
          <div className="ellipse-3-meetingView" />
        </div>
        <div className="text-wrapper-meetingView">참여 모임원 5명</div>
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
            <img
                className="mask-group-2-meetingView"
                alt="Mask group"
                src="https://generation-sessions.s3.amazonaws.com/a477b538cf49b12447b89548b3062c3c/img/mask-group-10@2x.png"
            />
            <img
                className="mask-group-2-meetingView"
                alt="Mask group"
                src="https://generation-sessions.s3.amazonaws.com/a477b538cf49b12447b89548b3062c3c/img/mask-group-10@2x.png"
            />
            <img
                className="mask-group-2-meetingView"
                alt="Mask group"
                src="https://generation-sessions.s3.amazonaws.com/a477b538cf49b12447b89548b3062c3c/img/mask-group-10@2x.png"
            />
        </div>
        <img
          className="vector-3-meetingView"
          alt="Vector"
          src={etc}
        />
        <div className="text-wrapper-15-meetingView">회기 꽃술 6인팟!!</div>
        <p className="element-meetingView">
          경희대 근처 사시는 20대 남녀 모두 환영합니다.
          <br />
          같이 즐겁게 술 마셔요!
        </p>
        
        {/* 파티 장 이미지 */}
        <img 
          className="mask-group-3-meetingView"
          alt="meeting Owner"
          src="https://generation-sessions.s3.amazonaws.com/a477b538cf49b12447b89548b3062c3c/img/mask-group-10@2x.png"
        />
        <div className="overlap-meetingView">
          <div className="text-wrapper-5-meetingView">참여</div>
          <div className="rectangle-meetingView" />
          <div className="text-wrapper-6-meetingView">일정 추가</div>
        </div>
        <div className="text-wrapper-8-meetingView">주 모임 장소</div>
        <div className="text-wrapper-9-meetingView">모임장</div>
        <p className="text-wrapper-10-meetingView">서울특별시 동대문구 경희대로1길 8-14 1층</p>
        <div className="text-wrapper-11-meetingView">현재 6명</div>
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
            <div className="text-wrapper-13-meetingView">종료된 모임</div> {/* "rectangle-2-meetingView" 선택 CSS */}
            <div className="overlap-2-meetingView">
            <img
                    className="line-3-meetingView"
                    alt="Line"
                    src={line}
                />
            </div>
        {/* 모임 예약 데이터 */}
        <div className='reservationArea'>
        <div className="reservation">
        <div className="text-wrapper-14-meetingView">참여 모임원 5명</div>
        <div className="partyGroup-reservation">
            <img
                className="mask-group-2-meetingView"
                alt="Mask group"
                src="https://generation-sessions.s3.amazonaws.com/a477b538cf49b12447b89548b3062c3c/img/mask-group-10@2x.png"
            />
            <img
                className="mask-group-2-meetingView"
                alt="Mask group"
                src="https://generation-sessions.s3.amazonaws.com/a477b538cf49b12447b89548b3062c3c/img/mask-group-10@2x.png"
            />
            <img
                className="mask-group-2-meetingView"
                alt="Mask group"
                src="https://generation-sessions.s3.amazonaws.com/a477b538cf49b12447b89548b3062c3c/img/mask-group-10@2x.png"
            />
        </div>
        <img
          className="vector-4-meetingView-reservation"
          alt="Vector"
          src={etc}
        />
        <div className="text-wrapper-2-meetingView">장소 및 일정</div>
        <img
          className="light-s-meetingView"
          alt="Light s"
          src={time}
        />
        <div className="text-wrapper-3-meetingView">6월 23일 오후 6시</div>
        <img
          className="light-s-2-meetingView"
          alt="Light s"
          src={locate}
        />
        <p className="p-meetingView">서울특별시 동대문구 경희대로1길 8-14 1층</p>
        <img
          className="vector-meetingView"
          alt="Vector"
          src={people}
        />
        <p className="text-wrapper-4-meetingView">최대 6명 / 현재 0명</p>
        <p className="element-2-meetingView">
          경희대 근처 사시는 20대 남녀 모두 환영합니다.
          <br />
          같이 즐겁게 술 마셔요!
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
        <div className="div-wrapper-meetingView">
          <div className="text-wrapper-7-meetingView">일정 참가</div>
        </div>
        </div>
        <div className="reservation">
        <div className="text-wrapper-14-meetingView">참여 모임원 5명</div>
        <div className="partyGroup-reservation">
            <img
                className="mask-group-2-meetingView"
                alt="Mask group"
                src="https://generation-sessions.s3.amazonaws.com/a477b538cf49b12447b89548b3062c3c/img/mask-group-10@2x.png"
            />
            <img
                className="mask-group-2-meetingView"
                alt="Mask group"
                src="https://generation-sessions.s3.amazonaws.com/a477b538cf49b12447b89548b3062c3c/img/mask-group-10@2x.png"
            />
            <img
                className="mask-group-2-meetingView"
                alt="Mask group"
                src="https://generation-sessions.s3.amazonaws.com/a477b538cf49b12447b89548b3062c3c/img/mask-group-10@2x.png"
            />
        </div>
        <img
          className="vector-4-meetingView-reservation"
          alt="Vector"
          src={etc}
        />
        <div className="text-wrapper-2-meetingView">장소 및 일정</div>
        <img
          className="light-s-meetingView"
          alt="Light s"
          src={time}
        />
        <div className="text-wrapper-3-meetingView">6월 23일 오후 6시</div>
        <img
          className="light-s-2-meetingView"
          alt="Light s"
          src={locate}
        />
        <p className="p-meetingView">서울특별시 동대문구 경희대로1길 8-14 1층</p>
        <img
          className="vector-meetingView"
          alt="Vector"
          src={people}
        />
        <p className="text-wrapper-4-meetingView">최대 6명 / 현재 0명</p>
        <p className="element-2-meetingView">
          경희대 근처 사시는 20대 남녀 모두 환영합니다.
          <br />
          같이 즐겁게 술 마셔요!
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
        <div className="div-wrapper-meetingView">
          <div className="text-wrapper-7-meetingView">일정 참가</div>
        </div>
        </div>
        <div className="reservation">
        <div className="text-wrapper-14-meetingView">참여 모임원 5명</div>
        <div className="partyGroup-reservation">
            <img
                className="mask-group-2-meetingView"
                alt="Mask group"
                src="https://generation-sessions.s3.amazonaws.com/a477b538cf49b12447b89548b3062c3c/img/mask-group-10@2x.png"
            />
            <img
                className="mask-group-2-meetingView"
                alt="Mask group"
                src="https://generation-sessions.s3.amazonaws.com/a477b538cf49b12447b89548b3062c3c/img/mask-group-10@2x.png"
            />
            <img
                className="mask-group-2-meetingView"
                alt="Mask group"
                src="https://generation-sessions.s3.amazonaws.com/a477b538cf49b12447b89548b3062c3c/img/mask-group-10@2x.png"
            />
        </div>
        <img
          className="vector-4-meetingView-reservation"
          alt="Vector"
          src={etc}
        />
        <div className="text-wrapper-2-meetingView">장소 및 일정</div>
        <img
          className="light-s-meetingView"
          alt="Light s"
          src={time}
        />
        <div className="text-wrapper-3-meetingView">6월 23일 오후 6시</div>
        <img
          className="light-s-2-meetingView"
          alt="Light s"
          src={locate}
        />
        <p className="p-meetingView">서울특별시 동대문구 경희대로1길 8-14 1층</p>
        <img
          className="vector-meetingView"
          alt="Vector"
          src={people}
        />
        <p className="text-wrapper-4-meetingView">최대 6명 / 현재 0명</p>
        <p className="element-2-meetingView">
          경희대 근처 사시는 20대 남녀 모두 환영합니다.
          <br />
          같이 즐겁게 술 마셔요!
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
        <div className="div-wrapper-meetingView">
          <div className="text-wrapper-7-meetingView">일정 참가</div>
        </div>
        </div>
        
        </div>
        {/* 모임 컨트롤러 */}
        <div className="overlap-3-meetingView">
          <div className="rectangle-3-meetingView" />
          <div className="text-wrapper-16-meetingView">모임 나가기</div>
        </div>
        <div className="thin-s-wrapper-meetingView">
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