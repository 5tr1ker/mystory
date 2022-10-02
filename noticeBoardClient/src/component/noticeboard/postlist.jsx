import axios from "axios";
import React, { useEffect, useState, useRef } from "react";
import Postcontent from "./postlistcontent";
import PostPointer from "./postpagenation";
import qs from 'qs';
import { Link } from "react-router-dom";
import Cookies from "universal-cookie";

const NoticeList = () => {
    const cookies = new Cookies();
    const query = qs.parse(window.location.search, { // ?tag=데이터 로 찾음 query.tag
        ignoreQueryPrefix: true
    });
    
    const maxPages = useRef(1);
    const [postAll, setPostAll] = useState([]); // 모든 post
    const [pages, setPages] = useState(1); // d현재 페이지
    const [totalPost, setTotalPost] = useState(0); // 전체 post
    
    const searchDataInput = async (e) => {
        if (e.key == 'Enter') {
            console.log(e.target.value);
            if (e.target.value !== '') {
                const getPostResult = await axios({ // 게시판 데이터 가져오기
                    method: "GET",
                    mode: "cors",
                    url: `/findPostBySearch/${e.target.value}`,
                });
                setPostAll(getPostResult.data);
            } else {
                const getPostResult = await axios({ // 게시판 데이터 가져오기
                    method: "GET",
                    mode: "cors",
                    url: `/getFreePost?page=0&size=10`,
                });
                setPostAll(getPostResult.data);
            }
        }
    }

    const getPost = async () => { // 일반
        const getPostResult = await axios({ // 게시판 데이터 가져오기
            method: "GET",
            mode: "cors",
            url: `/getFreePost?page=${pages - 1}&size=10`,
        });
        setPostAll(getPostResult.data); // 전체 데이터

        const result = await axios({
            method: "GET",
            mode: "cors",
            url: `/totalPostDataCount`
        });
        setTotalPost(result.data);
    };

    const tagSearch = async () => { // 태그
        if (query.tag === '') {
            window.location.replace("/noticelist");
            return;
        } else {
            const searchresult = await axios({
                method: "GET",
                mode: "cors",
                url: `/findPostBySearchAndTag/${query.tag}`
            });
            setPostAll(searchresult.data);

        }
    }

    const setNowPages = async (value) => {
        setPages(value);
    }

        useEffect(async () => {
        if (query.tag === undefined) { // 태그 검색
            getPost();
        } else {
            tagSearch();
        }
    }, [pages]);

    const gotoNext = () => {
        if (pages < maxPages.current) {
            setPages(parseInt(pages) + 1);
        }
    }

    const gotoPrevious = () => {
        if (pages > 1) {
            setPages(parseInt(pages) - 1);
        }
    }

    const pushData = () => {
        maxPages.current = Math.ceil(totalPost / 10);
        const arrs = [];
        for (let i = 1; i <= maxPages.current; i++) {
            arrs.push([i]);
        }
        return <PostPointer pages={arrs} nowPage={pages} setPage={setNowPages} />
    }

    return (
        <section className="sectionArea setresponsible">
            <article className="noticeFrameArea setresponsiblearticle">
                <div className="freeNotice">
                    <div className="pagedesc">게시판</div>
                    <div className="searchCoordinate">
                        <input type="text" className="searchingArea" placeholder='Search the forum' onKeyDown={searchDataInput} />
                        <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" className="bi bi-search search-icon" viewBox="0 0 16 16">
                            <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z" />
                        </svg>
                    </div>
                </div>
                <div className="PostArea">
                    <div className="noticedescription">
                        <span id="DescHead">번호</span>
                        <span id="DescHead">제목</span>
                        <span id="DescHead">작성자</span>
                        <span id="DescHead">작성일</span>
                        <span id="DescHead">추천</span>
                        <span id="DescHead">조회수</span>
                    </div>
                    <Postcontent data={postAll} />
                </div>
                <nav aria-label="Page navigation example" className="pagenations">
                    <ul className="pagination">
                        <li className="page-item"><a className="page-link" href="#" onClick={gotoPrevious} style={{ boxShadow: "none" }}>&lt;</a></li>
                        {pushData()}
                        <li className="page-item"><a className="page-link" href="#" onClick={gotoNext} style={{ boxShadow: "none" }}>&gt;</a></li>
                    </ul>
                    <Link to={'/newpost'} className="newPost_post">New Post</Link>
                </nav>
            </article>
        </section>
    )
}

export default React.memo(NoticeList);