/* eslint-disable react-hooks/exhaustive-deps */
import axios from "axios";
import { useEffect, useRef, useState } from "react";
import PostPointer from "../../noticeboard/postpagenation";
import '../../../_style/admin/admin.css'

const BugContentList = ({ list, renders }) => {

    const toggleSolved = async (id) => {
        await axios({
            method: "PATCH",
            url: `/admin/report/bug/${id}`
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
                url: `/admin/report/bug/${id}`
            })
                .then((response) => {
                    renders();
                })
                .catch((e) => alert(e.response.data.message));
        }
    }

    return (
        list.map(data => (
            data.solved ?
                <tr key={data.bugReportId}>
                    <td>{data.bugReportId}</td>
                    <td>{data.reporter}</td>
                    <td>{data.reportTime}</td>
                    <td style= {{textDecoration: "line-through"}}>{data.content}</td>
                    <td>{data.solved ? <input type="checkbox" onClick={() => toggleSolved(data.bugReportId)} defaultChecked={true} /> : <input onClick={() => toggleSolved(data.bugReportId)} type="checkbox" />}</td>
                    <td className="deleteContentStyle" onClick={() => deleteReport(data.bugReportId)}>[제거]</td>
                </tr> :
                <tr key={data.bugReportId}>
                    <td>{data.bugReportId}</td>
                    <td>{data.reporter}</td>
                    <td>{data.reportTime}</td>
                    <td>{data.content}</td>
                    <td>{data.solved ? <input type="checkbox" onClick={() => toggleSolved(data.bugReportId)} defaultChecked={true} /> : <input onClick={() => toggleSolved(data.bugReportId)} type="checkbox" />}</td>
                    <td className="deleteContentStyle" onClick={() => deleteReport(data.bugReportId)}>[제거]</td>
                </tr>
        ))
    )
}

const AdminBug = () => {

    const [pages, setPages] = useState(1); // 현재 페이지
    const maxPages = useRef(1);
    const [bugContentCount, setBugContentCount] = useState(0);
    const [bugContent, setBugContent] = useState([]);
    const [render, setRender] = useState(false);

    useEffect(async () => {
        await axios({
            method: "GET",
            url: `/admin/report/bug/count`
        })
            .then((response) => {
                setBugContentCount(response.data);
            })
            .catch((e) => alert(e.response.data.message));

        await axios({
            method: "GET",
            url: `/admin/report/bug?page=${pages - 1}&size=10`
        })
            .then((response) => {
                setBugContent(response.data);
            })
            .catch((e) => alert(e.response.data.message));
    }, [render, pages]);

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

    const setNowPages = async (value) => {
        setPages(value);
    }

    const updateRender = () => {
        setRender(render ? false : true);
    }

    const pushData = () => {
        maxPages.current = Math.ceil(bugContentCount / 10);
        const arrs = [];
        for (let i = 1; i <= maxPages.current; i++) {
            arrs.push([i]);
        }

        return <PostPointer pages={arrs} nowPage={pages} setPage={setNowPages} />
    }

    return (
        <main className="col-md-9 ms-sm-auto col-lg-10 px-md-4">
            <div className="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 className="h2">버그 신고 관리</h1>
            </div>

            <div className="custom-table-height">
                <table className="table table-striped table-sm">
                    <thead>
                        <tr>
                            <th scope="col" width="5%">인덱스</th>
                            <th scope="col" width="5%">보고자</th>
                            <th scope="col" width="15%">보고 시간</th>
                            <th scope="col" width="65%">보고 내용</th>
                            <th scope="col" width="5%">처리 여부</th>
                            <th scope="col" width="5%">제거</th>
                        </tr>
                    </thead>
                    <tbody>
                        <BugContentList list={bugContent} renders={updateRender} />
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

export default AdminBug;