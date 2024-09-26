import React from "react";
import defaultALT from '../../_image/defaultALT.png'
import axios from "axios";

const NoticeboardCommit = ({ data, delButton, idStats , postId }) => {
    const arrs = data.map(postcommit => ({ ...postcommit, option: postcommit.writer === idStats }));

    const reportContent = async (commit) => {
        if(!window.confirm("해당 컨텐츠를 신고하시겠습니까?")) {
            return;
        }

        const reason = window.prompt("신고 사유 (100자 이내)" + "");
        if(reason.length > 100) {
            alert("신고 사유는 100자 이내로 작성해주세요.");

            return;
        }

        await axios({
            method: "POST",
            url: `/admin/report/content`,
            data: {
                "content" : reason ,
                "reportType" : "COMMENT",
                "target" : {
                    "writer" : commit.writer ,
                    "content" : commit.content
                }
            }
        }).then((e) => {
            alert("신고가 완료되었습니다.");
        }).catch((e) => {
            alert("로그인 후에 사용해주세요.");
        });
    }

    return (
        arrs.map(commit => (
            <div className="userPostCommit" key={commit.commentId}>
                <span className='commitnickname'><img src={commit.writerImage || defaultALT} alt="img" className="profileImageData" />{commit.writerIsDelete ? "탈퇴한 사용자" : commit.writer}</span>
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-clock" viewBox="0 0 16 16">
                    <path d="M8 3.5a.5.5 0 0 0-1 0V9a.5.5 0 0 0 .252.434l3.5 2a.5.5 0 0 0 .496-.868L8 8.71V3.5z" />
                    <path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16zm7-8A7 7 0 1 1 1 8a7 7 0 0 1 14 0z" />
                </svg>
                <span className='contentNumber'>{commit.postDate} </span>
                <svg style={{cursor: "pointer"}} xmlns="http://www.w3.org/2000/svg" onClick={() => reportContent(commit)} width="20" height="20" fill="currentColor" color="red" className="bi bi-bell" viewBox="0 0 16 16">
                    <path style={{ fill: "rgb(255,0,0)" }} d="M8 16a2 2 0 0 0 2-2H6a2 2 0 0 0 2 2zM8 1.918l-.797.161A4.002 4.002 0 0 0 4 6c0 .628-.134 2.197-.459 3.742-.16.767-.376 1.566-.663 2.258h10.244c-.287-.692-.502-1.49-.663-2.258C12.134 8.197 12 6.628 12 6a4.002 4.002 0 0 0-3.203-3.92L8 1.917zM14.22 12c.223.447.481.801.78 1H1c.299-.199.557-.553.78-1C2.68 10.2 3 6.88 3 6c0-2.42 1.72-4.44 4.005-4.901a1 1 0 1 1 1.99 0A5.002 5.002 0 0 1 13 6c0 .88.32 4.2 1.22 6z" />
                </svg>
                {commit.option ? <svg xmlns="http://www.w3.org/2000/svg" onClick={() => delButton(commit.commentId)} width="25" height="25" fill="currentColor" className="bi bi-trash deletecommit" viewBox="0 0 16 16">
                    <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z" />
                    <path fillRule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z" />
                </svg> : null}
                <div className='commitContent'>
                    <span>{commit.content}</span>
                </div>
            </div>
        ))
    )
}

export default React.memo(NoticeboardCommit);