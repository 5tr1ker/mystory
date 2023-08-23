import React from "react";
import { Link } from "react-router-dom";

const Postcontent = ({ data }) => {
    console.log(data);
    
    return (
        data.map(posts => (
            <Link to={`/viewpost?page=${posts.numbers}`} className="linktopost" key={posts.numbers}>
                <div className="noticedescription userPost">
                    <span>{posts.numbers}</span>
                    <span>{posts.title} <div style={{color: "rgb(60,172,255)", display:"inline"}}>{posts.count ? "["+ posts.count + "] ": null}</div></span>
                    <span><img src={posts.writerImage} className="profileImageData"/> {posts.writer}</span>
                    <span>{posts.postDate}</span>
                    <span>{posts.likes}</span>
                    <span>{posts.views}</span>
                </div>
            </Link>
        ))
    );
};

export default React.memo(Postcontent);