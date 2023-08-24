/* eslint-disable react/jsx-no-undef */
import React from "react";
import '../../_style/meeting/meeting.css'
import search from '../../_image/search.png'
import pen from '../../_image/pen.png'
import line_3 from '../../_image/line-3.svg'
import PostPointer from "../noticeboard/postpagenation";
import MeetingContent from "./meetingContent.jsx";

const Meeting = () => {

    const newmeeting = () => {
        window.location.href = "/newmeeting";
    }

    return (
      <div className="screen">
      <div className="div">
        <div className="text-wrapper">참여</div>
        <div className="overlap">
          <img className="light-s" alt="Light s" src={search} />
          <div className="text-wrapper-2">지역 혹은 이름을 입력해주세요.</div>
          <div className="rectangle" />
        </div>
        <div className="overlap-group">
          <img className="thin-s" alt="Thin s" src={pen} />
        </div>
        <div className="text-wrapper-7">새로운 모임</div>
        <div className="text-wrapper-8">나의 모임</div>
        <div className="overlap-3">
          <img className="line" alt="Line" src={line_3} />
          <div className="rectangle-3" />
        </div>
        <div className="meetingContentArea">
          <MeetingContent/><MeetingContent/><MeetingContent/><MeetingContent/><MeetingContent/>
        </div>
        <div className="meetingPageNation">
          <nav aria-label="Page navigation example" className="pagenations">
            <ul className="pagination">
              <li className="page-item"><a className="page-link" href="#" style={{ boxShadow: "none" }}>&lt;</a></li>
                <PostPointer pages={10} nowPage={0} setPage={0} />
              <li className="page-item"><a className="page-link" href="#" style={{ boxShadow: "none" }}>&gt;</a></li>
            </ul>
          </nav>
        </div>
      </div>
    </div>
    )
}

export default Meeting;