import React from "react";
import { Link } from "react-router-dom";
import defaultALT from '../../_image/defaultALT.png'

const Postcontent = ({ data }) => {

    if(data === null) {
        return(
            <div>

            </div>
        )
    }

    return (
        data.map(posts => (
            <Link to={`/viewpost/${posts.numbers}`} className="linktopost" key={posts.numbers}>
                <div className="noticedescription userPost">
                    <span>{posts.numbers}</span>
                    <span>{posts.title} <div style={{color: "rgb(60,172,255)", display:"inline"}}>{posts.count ? "["+ posts.count + "] ": null}</div></span>
                    <span><img src={posts.writerImage || defaultALT} className="profileImageData" alt="img"/> {posts.writerIsDelete ? "탈퇴한 사용자" : posts.writer}</span>
                    <span>{posts.postDate}</span>
                    <span>{posts.likes}</span>
                    <span>{posts.views}</span>
                </div>
            </Link>
        ))
    );
};

export default React.memo(Postcontent);