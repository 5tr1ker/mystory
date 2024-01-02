/* eslint-disable react-hooks/exhaustive-deps */
import axios from "axios";
import { useEffect, useRef, useState } from "react";
import PostPointer from "../../noticeboard/postpagenation";

const AuthorityList = (authority) => {

    if (authority.authority === undefined) {
        return null;
    }

    return (
        authority.authority.map(list => (
            <tr key={list.id}>
                <td>{list.id}</td>
                <td>{list.joinDate}</td>
                <td>{list.lastLoginDate}</td>
                <td>{list.suspention ? list.suspensionDate + "일 까지 정지" : "정상 사용자"}</td>
                <td>{list.userRole === "USER" ? "일반 사용자" : "관리자"}</td>
                <td>
                    <select name="options" key={list.id} defaultValue={list.userRole === "USER" ? 0 : 1} style={{ marginTop: "5px", fontWeight: "600", color: "black", cursor: "pointer", boxShadow: "none" }} className="form-select form-select-sm" aria-label=".form-select-sm example">
                        {[
                            <option key={list.id + 1} value={0} style={{ fontWeight: "600" }}>일반 사용자</option>,
                            <option key={list.id + 2} value={1} style={{ fontWeight: "600" }}>관리자</option>]}
                    </select>
                </td>
                <td>
                    <select name="options" key={list.id} defaultValue={0} style={{ marginTop: "5px", fontWeight: "600", color: "black", cursor: "pointer", boxShadow: "none" }} className="form-select form-select-sm" aria-label=".form-select-sm example">
                        {[
                            <option key={list.id + 1} value={0} style={{ fontWeight: "600" }}>제재 일수</option>,
                            <option key={list.id + 2} value={1} style={{ fontWeight: "600" }}>1 일 추가</option>,
                            <option key={list.id + 3} value={7} style={{ fontWeight: "600" }}>7 일 추가</option>,
                            <option key={list.id + 4} value={30} style={{ fontWeight: "600" }}>30 일 추가</option>,
                            <option key={list.id + 5} value={9999999} style={{ fontWeight: "600" }}>영구 정지</option>]}
                    </select>
                </td>
            </tr>
        ))
    );
}

const PeopleEdit = () => {
    const [pages, setPages] = useState(1); // 현재 페이지
    const maxPages = useRef(1);
    const [authority, setAuthority] = useState([]);
    const [authorityCount, setAuthorityCount] = useState(0);

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

    const pushData = () => {
        maxPages.current = Math.ceil(authorityCount / 10);
        const arrs = [];
        for (let i = 1; i <= maxPages.current; i++) {
            arrs.push([i]);
        }

        return <PostPointer pages={arrs} nowPage={pages} setPage={setNowPages} />
    }

    useEffect(async () => {
        await axios({
            method: "GET",
            url: `/admin/authority/count`
        })
            .then((response) => {
                setAuthorityCount(response.data);
            })
            .catch((e) => alert(e.response.data.message));

        await axios({
            method: "GET",
            url: `/admin/authority?page=${0}&size=10`
        })
            .then((response) => {
                setAuthority(response.data);
            })
            .catch((e) => alert(e.response.data.message));
    }, []);

    return (
        <main className="col-md-9 ms-sm-auto col-lg-10 px-md-4">
            <div className="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 className="h2">사용자 관리</h1>
            </div>

            <div className="custom-table-height">
                <table className="table table-striped table-sm">
                    <thead>
                        <tr>
                            <th scope="col" width="5%">아이디</th>
                            <th scope="col" width="5%">가입일자</th>
                            <th scope="col" width="5%">최근 활동 날짜</th>
                            <th scope="col" width="5%">상태</th>
                            <th scope="col" width="5%">직급</th>
                            <th scope="col" width="5%">직급 변경</th>
                            <th scope="col" width="5%">제재</th>
                        </tr>
                    </thead>
                    <tbody>
                        <AuthorityList authority={authority} />
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

export default PeopleEdit;
