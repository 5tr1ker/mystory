import '../../_style/meeting/meetingContent.css'

const MeetingContent = ({showDetail  , data}) => {
  if(data == undefined) {
    return (
      <div></div>
    )
  }

    return (
      data.map(posts => (
        <div className="overlap-group-2" key={posts.meetingId} onClick={() => showDetail(posts.meetingId)}>
        <div className="imageSession">
        <img className="mask-group" alt="Mask group" src={posts.meetingImage} />
        </div>
          <div className="rectangle-2" />
          <div className="text-wrapper-3">{posts.description}</div>
          <div className="text-wrapper-4">{posts.address} <br/> {posts.detailAddress}</div>
          <div className="text-wrapper-5">참여자 수 {posts.nowParticipants} / {posts.maxParticipants}</div>
          <div className="text-wrapper-6">{posts.title}</div>
        </div>
      )
    )
)}

export default MeetingContent;