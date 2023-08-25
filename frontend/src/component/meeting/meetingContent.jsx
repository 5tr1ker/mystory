import '../../_style/meeting/meetingContent.css'
import testImage from '../../_image/testImage.jpg'

const MeetingContent = () => {

    return (
      <div className="overlap-group-2">
        <div className="imageSession">
        <img className="mask-group" alt="Mask group" src={testImage} />
        </div>
          <div className="rectangle-2" />
          <div className="text-wrapper-3">간단한 설명 정도</div>
          <div className="text-wrapper-4">경기도 김포시 사우동</div>
          <div className="text-wrapper-5">참여자 수 0</div>
          <div className="text-wrapper-6">방 제목</div>
        </div>
    )
}

export default MeetingContent;