import React , { useEffect , Fragment } from "react";

const arrs = [];

const PostPointer = ({ pages, nowPage, setPage }) => {
    const buttonCall = () => {
        arrs.length = 0;
        if(pages.length < 7) {
            for (let i = 1; i <= pages.length; i++) {
                if (nowPage === i) {
                    arrs.push([<span className='noticePagenationButtonNow' onClick={() => setPage(i)}>{i}</span>, i]);
                } else {
                    arrs.push([<span className='noticePagenationButton' onClick={() => setPage(i)}>{i}</span>, i]);
                }
            }
        } else {
            let pageInfo = nowPage;
            if (nowPage <= 4) {
                for (let i = nowPage - 1; i > 0; i--) {
                    if (nowPage === i) {
                        arrs.push([<span className='noticePagenationButtonNow' onClick={() => setPage(nowPage - i)}>{nowPage - i}</span>, nowPage - i]);
                    } else {
                        arrs.push([<span className='noticePagenationButton' onClick={() => setPage(nowPage - i)}>{nowPage - i}</span>, nowPage - i]);
                    }
                }
            } else {
                arrs.push([<span className='noticePagenationButton' onClick={() => setPage(1)}>{1}</span>, 'K1']);
                arrs.push([<span className='noticePagenationButton'>...</span>, 'K']);
                for (let i = 2; i > 0; i--) {
                    if(nowPage - i <= 0) {
                        continue;
                    } else {
                        pageInfo = nowPage - i;
                        arrs.push([<span className='noticePagenationButton' onClick={() => setPage(nowPage - i)}>{pageInfo}</span>, pageInfo]);    
                    }
                }
            }
            arrs.push([<span className='noticePagenationButtonNow' onClick={() => setPage(nowPage)}>{nowPage}</span>, nowPage]);
            if (nowPage > pages.length - 4) {
                for (let i = nowPage + 1; i <= pages.length; i++) {
                    if (nowPage === i) {
                        arrs.push([<span className='noticePagenationButtonNow' onClick={() => setPage(i)}>{i}</span>, i]);
                    } else {
                        arrs.push([<span className='noticePagenationButton' onClick={() => setPage(i)}>{i}</span>, i]);
                    }
                }
            } else {
                for (let i = 1; i < 3 ; i++) {
                    if (nowPage + i > pages.length) {
                        continue;
                    } else {
                        pageInfo = nowPage + i;
                        arrs.push([<span className='noticePagenationButton' onClick={() => setPage(nowPage + i)}>{pageInfo}</span>, pageInfo]);
                    }
                }
                arrs.push([<span className='noticePagenationButton'>...</span>, 'k']);
                arrs.push([<span className='noticePagenationButton' onClick={() => setPage(pages.length)}>{pages.length}</span>, "KF"]);
            }
        }
    }

    useEffect(() => {
        buttonCall();
        if(nowPage > pages.length) {
            setPage(nowPage - pages.length);
        }
    }, [pages, nowPage]);

    (function () {
        buttonCall();
    }());

    return (
        arrs.map((data) => (<Fragment key={data[1]}> {data[0]} </Fragment>))
    )
}

export default React.memo(PostPointer);