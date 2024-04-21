/* eslint-disable react-hooks/exhaustive-deps */
/* eslint-disable react/jsx-no-undef */
import React, { useEffect, useRef, useState } from "react";
import '../../_style/meeting/meetingList.css'
import search from '../../_image/search.png'
import pen from '../../_image/pen.png'
import line_3 from '../../_image/line-3.svg'
import PostPointer from "../noticeboard/postpagenation";
import MeetingContent from "./meetingContent.jsx";
import MeetingDetail from "./meetingDetail";
import axios from "axios";

const MeetingList = ({idStatus}) => {

  const newmeeting = () => {
    window.location.href = "/newmeeting";
  }

  const [meetingOption, setMeetingOption] = useState(false); // false -> 전채 , true -> 나의 모임
  const setTrueOption = () => {
    if(idStatus == undefined) {
      alert("로그인 후 사용할 수 있습니다.");

      return;
    }
    
    setMeetingOption(true);
  }
  const setFalseOption = () => {
    setMeetingOption(false);
  }
  const [searchData , setSearch] = useState(""); // 탐색
  const onChangeSearchData = (e) => {
    setSearch(e.target.value);
  }
  const searchRequest = async (e) => {
    await axios({ // 컨텐츠 갯수
      method: "GET",
      mode: "cors",
      url: `/meeting/title-address/count?data=${searchData}`
    }).then((response) => {
      console.log(response.data);
      setPageAmount(response.data);
    }).catch((err) => {
      console.log(err)
    });

    if(e.key == 'Enter') {
      await axios({
        method: "GET",
        mode: "cors",
        url: `/meeting/title-address?data=${searchData}&page=${pages - 1}&size=6`
      }).then((response) => {
        setMeetingList(response.data);
      }).catch((err) => {
        console.log(err)
      });

      
    }
  }

  // 미팅 상세 정보
  const [meetingDetail , setMeetingDetail] = useState(false); // false -> 비공개 , -> true -> 공개
  const [detailNumber , setDetailNumber] = useState(0);
  const showMeetingDetail = (number) => {
    setMeetingDetail(false);
    setMeetingDetail(true);

    setDetailNumber(number);
  }
  const exitMeetingDetail = () => {
    setMeetingDetail(false);
  }

  const [meetingList, setMeetingList] = useState();
  const [pageAmount, setPageAmount] = useState(0); // 전체 컨텐츠 수
  const [pages, setPages] = useState(1); // 현재 페이지
  const maxPages = useRef(1);

  // PageNation
  const gotoNext = () => {
    if (pages < maxPages.current) {
      setPages(parseInt(pages) + 1);
    }
  }

  const setNowPages = async (value) => {
    setPages(value);
  }

  const gotoPrevious = () => {
    if (pages > 1) {
      setPages(parseInt(pages) - 1);
    }
  }

  const pushData = () => {
    maxPages.current = Math.ceil(pageAmount / 6);
    const arrs = [];
    for (let i = 1; i <= maxPages.current; i++) {
      arrs.push([i]);
    }
    return <PostPointer pages={arrs} nowPage={pages} setPage={setNowPages} />
  }

  useEffect(async () => {
    if (meetingOption) {
      await axios({ // 컨텐츠 갯수
        method: "GET",
        mode: "cors",
        url: `/meeting/user/count`
      }).then((response) => {
        setPageAmount(response.data);
      }).catch((err) => {
        console.log(err)
      });

      await axios({
        method: "GET",
        mode: "cors",
        url: `/meeting/user?page=${pages - 1}&size=6`
      }).then((response) => {
        setMeetingList(response.data);
      }).catch((err) => {
        console.log(err)
      });
    } else {
      await axios({ // 컨텐츠 갯수
        method: "GET",
        mode: "cors",
        url: `/meeting/count`
      }).then((response) => {
        setPageAmount(response.data);
      }).catch((err) => {
        console.log(err)
      });

      await axios({
        method: "GET",
        mode: "cors",
        url: `/meeting?page=${pages - 1}&size=6`
      }).then((response) => {
        setMeetingList(response.data);
      }).catch((err) => {
        console.log(err)
      });
    }
  }, [meetingOption , pages])

  return (
    <div className="screen">
        {meetingDetail ? <div className="meetingDetails"><MeetingDetail hideDetail={exitMeetingDetail} detailNumber={detailNumber} meetingOption={meetingOption}/></div> : null}
      <div className="div">
        <div className="text-wrapper">참여</div>
        <div className="overlap">
          {meetingOption ? null :
            <div>
              <img className="light-s" alt="Light s" src={search} />
              <input className="rectangle" onChange={onChangeSearchData} onKeyDown={searchRequest} placeholder="지역 혹은 이름을 입력해주세요." />
            </div>
          }
        </div>
        {meetingOption ? 
          null :
          <div className="overlap-group" onClick={newmeeting}>
            <img className="thin-s" alt="Thin s" src={pen} />
          </div>
        }
        <div className="text-wrapper-7" onClick={setFalseOption}>새로운 모임</div>
        <div className="text-wrapper-8" onClick={setTrueOption}>나의 모임</div>
        <div className="overlap-3">
          <img className="line" alt="Line" src={line_3} />
          {meetingOption ? <div className="rectangle-3-mine" /> : <div className="rectangle-3" />}
        </div>
        <div className="meetingContentArea">
          <MeetingContent data={meetingList} showDetail={showMeetingDetail} />
        </div>
        <div className="meetingPageNation">
          <nav aria-label="Page navigation example" className="pagenations">
            <ul className="pagination">
              <li className="page-item"><a className="page-link" onClick={gotoPrevious} href="#" style={{ boxShadow: "none" }}>&lt;</a></li>
              {pushData()}
              <li className="page-item"><a className="page-link" href="#" onClick={gotoNext} style={{ boxShadow: "none" }}>&gt;</a></li>
            </ul>
          </nav>
        </div>
      </div>
    </div>
  )
}

export default MeetingList;