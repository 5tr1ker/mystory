import React from "react";
import defaultALT from '../../_image/defaultALT.png'

const NoticeboardCommit = ({ data, delButton, idStats }) => {
    const arrs = data.map(postcommit => ({ ...postcommit, option: postcommit.writer === idStats }));

    return (
        arrs.map(commit => (
            <div className="userPostCommit" key={commit.commentId}>
                <span className='commitnickname'><img src={commit.writerImage || defaultALT} alt="img" className="profileImageData"/>{commit.writer}</span>
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-clock" viewBox="0 0 16 16">
                    <path d="M8 3.5a.5.5 0 0 0-1 0V9a.5.5 0 0 0 .252.434l3.5 2a.5.5 0 0 0 .496-.868L8 8.71V3.5z" />
                    <path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16zm7-8A7 7 0 1 1 1 8a7 7 0 0 1 14 0z" />
                </svg>
                <span className='contentNumber'>{commit.postDate} </span>
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