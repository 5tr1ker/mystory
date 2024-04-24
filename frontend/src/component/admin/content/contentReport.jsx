/* eslint-disable react-hooks/exhaustive-deps */
import axios from "axios";
import { useEffect, useRef, useState } from "react";
import PostPointer from "../../noticeboard/postpagenation";

const ContentReportList = ({ list, renders }) => {

    const switchContentType = (contentType) => {
        if (contentType === "POST") {
            return "게시글";
        }
        else if (contentType === "COMMENT") {
            return "댓글";
        }
        else if (contentType === "CHAT") {
            return "채팅";
        }
        else if (contentType === "MEETING") {
            return "미팅";
        }
        else if (contentType === "RESERVATION") {
            return "미팅 예약";
        }
    }

    const toggleSolved = async (id) => {
        await axios({
            method: "PATCH",
            url: `/admin/report/content/${id}`
        })
            .then((response) => {
                renders();
            })
            .catch((e) => alert(e.response.data.message));
    }

    const deleteReport = async (id) => {
        if (window.confirm("정말 해당 보고를 제거하시겠습니까?")) {
            await axios({
                method: "DELETE",
                url: `/admin/report/content/${id}`
            })
                .then((response) => {
                    renders();
                })
                .catch((e) => alert(e.response.data.message));
        }
    }

    return (
        list.map(data => (
            <tr key={data.contentReportId}>
                <td>{data.reporter}</td>
                <td>{data.reportTime}</td>
                <td>{data.reportData.targetId}</td>
                <td>[{data.reportData.title}] {data.reportData.content}</td>
                <td>{data.content}</td>
                <td>{switchContentType(data.contentType)}</td>
                <td>{data.action ? <input type="checkbox" onClick={() => toggleSolved(data.contentReportId)} defaultChecked={true} /> : <input type="checkbox" onClick={() => toggleSolved(data.contentReportId)} />}</td>
                <td className="deleteContentStyle" onClick={() => deleteReport(data.contentReportId)}>[제거]</td>
            </tr>
        ))
    )
}

const ContentReport = () => {
    const [pages, setPages] = useState(1); // 현재 페이지
    const maxPages = useRef(1);
    const [contentReport, setContentReport] = useState([]);
    const [contentReportCount, setContentReportCount] = useState(0);
    const [render, setRender] = useState(false);

    useEffect(async () => {
        await axios({
            method: "GET",
            url: `/admin/report/content/count`
        })
            .then((response) => {
                setContentReportCount(response.data);
            })
            .catch((e) => alert(e.response.data.message));

        await axios({
            method: "GET",
            url: `/admin/report/content?page=${pages - 1}&size=10`
        })
            .then((response) => {
                setContentReport(response.data);
            })
            .catch((e) => alert(e.response.data.message));
    }, [render, pages]);

    const gotoNext = () => {
        if (pages < maxPages.current) {
            setPages(parseInt(pages) + 1);
        }
    }

    const updateRender = () => {
        setRender(render ? false : true);
    }

    const gotoPrevious = () => {
        if (pages > 1) {
            setPages(parseInt(pages) - 1);
        }
    }

    const setNowPages = async (value) => {
        setPages(value);
    }

    const pushData = () => {
        maxPages.current = Math.ceil(contentReportCount / 10);
        const arrs = [];
        for (let i = 1; i <= maxPages.current; i++) {
            arrs.push([i]);
        }

        return <PostPointer pages={arrs} nowPage={pages} setPage={setNowPages} />
    }

    return (
        <main className="col-md-9 ms-sm-auto col-lg-10 px-md-4">
            <div className="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 className="h2">컨텐츠 신고 관리</h1>
            </div>
            <div className="custom-table-height" style={{width:"100%" , overflow:"auto"}}>
                <table className="table table-striped table-sm">
                    <thead>
                        <tr>
                            <th scope="col" width="10%">보고자</th>
                            <th scope="col" width="10%">보고 시간</th>
                            <th scope="col" width="10%">신고된 ID</th>
                            <th scope="col" width="20%">신고된 내용</th>
                            <th scope="col" width="20%">신고자 내용</th>
                            <th scope="col" width="5%">컨텐츠</th>
                            <th scope="col" width="5%">조치 여부</th>
                            <th scope="col" width="5%">제거</th>
                        </tr>
                    </thead>
                    <tbody>
                        <ContentReportList list={contentReport} renders={updateRender} />
                    </tbody>
                </table>
            </div>
            <nav aria-label="Page navigation example" className="pagenations">
                <ul className="pagination">
                    <li className="page-item"><a className="page-link" href="#" onClick={gotoPrevious} style={{ boxShadow: "none" }}>&lt;</a></li>
                    {pushData()}
                    <li className="page-item"><a className="page-link" href="#" onClick={gotoNext} style={{ boxShadow: "none" }}>&gt;</a></li>
                </ul>
            </nav>
        </main>
    )
}

export default ContentReport;
