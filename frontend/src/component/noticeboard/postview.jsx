import NoticeboardCommit from "./noticeboardcommit";
import React ,  { useEffect , useRef } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import { useState , Fragment } from "react";
import FileDownload from "js-file-download";
import AsyncStorage from "@react-native-async-storage/async-storage";

const PostView = ({ idStatus }) => {
    const params = new URLSearchParams(window.location.search).get('page');
    const [postContentArr, setContentArr] = useState([]); // 현재 보여지는 페이지 
    const commit = useRef(''); // 커밋 내용
    const [nowPageNumbers, setNowPages] = useState(0); // 현재 페이지 번호
    const [commitData, setCommitData] = useState([]); // 댓글 데이터
    const [tagData , setTagData] = useState([]); // 포스트 해시태그 데이터
    const [attachment , setAttachment] = useState([]); // 첨부파일 데이터


    const PostViewTag = (({data}) => { // 태그
        const tagarr = [];
        data.forEach(item => tagarr.push(item));
        return(tagarr.map(item => <span key={item} onClick={() => window.location.replace(`/noticelist?tag=${item}`)}>{item}</span>));
    });

    const PostViewAttachment = (({ data }) => { // 첨부파일
        return (data.map(item => (
            <Fragment key={item.fileName}>
                <div className="downloadBlock" onClick={() => onDownload(item.changedFile , item.fileName)}>
                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" className="bi bi-paperclip" viewBox="0 0 16 16">
                        <path d="M4.5 3a2.5 2.5 0 0 1 5 0v9a1.5 1.5 0 0 1-3 0V5a.5.5 0 0 1 1 0v7a.5.5 0 0 0 1 0V3a1.5 1.5 0 1 0-3 0v9a2.5 2.5 0 0 0 5 0V5a.5.5 0 0 1 1 0v7a3.5 3.5 0 1 1-7 0V3z" />
                    </svg>
                    <span>{item.fileName}</span>
                </div><br />
            </Fragment>
        ))
        )
    });

    const onDownload = async (changeName , fileName) => {
    const result = await axios({
      url:`/onDownload/${changeName}` ,
      method:"GET" ,
      mode : "cors" ,
      responseType: 'blob'
    });
    FileDownload(result.data , fileName);
    }
    
    const getPost = async (mode) => { // 게시글 정보 가져오기
        if(isNaN(parseInt(params))) {
            alert("비정상적인 접근입니다.");
            window.location.replace(`/noticelist`);
        }
        const result = await axios({ // POST 정보 가져오기
            method: "GET" ,
            mode: "cors" , 
            url: `/selectPost/${parseInt(params) + 1}`
        });

       const findPost = result.data.postData;
        setAttachment(result.data.attachment || []);
        setNowPages(result.data.postData.numbers || []);
        setTagData(result.data.tagData || []);

        if(findPost === '') {
            alert("해당 게시물을 찾을 수 없습니다.");
            window.location.replace(`/noticelist`);
        }

        if(findPost.privates === true && findPost.writer !== idStatus) {
            alert("비공개글입니다.");
            window.location.replace(`/noticelist`);
        } else {
            if (mode === 'views') { // 조회수 증가
                setContentArr(findPost); // findindex 로 해당 키값이 어떤 배열에 저장되어있는지 확인 후 해당 배열 반환+
            }
        }

        if (mode === 'likes') { // 추천 기능
            if (idStatus === postContentArr.writer) {
                alert('자신의 글에 좋아요를 할 수 없습니다.');
                return;
            } else if (idStatus === undefined) {
                alert('로그인 후 이용해주세요.');
                return;
            } else {
                const jsondata = JSON.stringify({"idStatus" : idStatus});
                const likeresult = await axios({
                    method : "PATCH" ,
                    mode : "cors" ,
                    url: `/auth/likes/${parseInt(params) + 1}`, 
                    data : jsondata , 
                    headers : {"Content-Type": "application/json"}
                });

                if (likeresult.data === -2) {
                    alert('이미 좋아요를 눌렀습니다.');
                    return;
                }
            }
            window.location.reload();
        }
    }

    const postDelete = async (data) => { // 게시글 삭제
        if (idStatus === postContentArr.writer) {
            if (window.confirm("게시글을 삭제하시겠습니까?")) {
                const result = await axios({
                    method : "DELETE" ,
                    url : `/auth/post/${parseInt(data)}` ,
                    mode : "cors"
                });

                if (result.data === -1) {
                    alert('오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
                } else if (result.data === 0) {
                    window.location.replace(`/noticelist`);
                }
            }
        } else {
            alert('내용 삭제는 작성자만 할 수 있습니다.');
        }
    }

    const postModifie = () => { // 글 수정
        if (idStatus === postContentArr.writer) {
            window.location.href = `/modified/${postContentArr.numbers}`;
        } else {
            alert('글 수정은 작성자만 할 수 있습니다.');
        }
    }

    const addCommit = async () => { // 댓글 추가
        if (idStatus === undefined) {
            alert('로그인 후에 이용해주세요.');
        } else if (commit.current.value === '') {
            alert('입력값이 없습니다.');
        } else {
            const data = JSON.stringify({"data" : commit.current.value , "writter" : idStatus , "postNum" : nowPageNumbers , "postNumber" : parseInt(params) + 1 , "postType" : "게시판"});
            const commitResult = await axios({
                method : "POST" ,
                url : "/auth/commit" ,
                data : data ,
                mode : "cors" ,
                headers : {"Content-Type": "application/json"}
            });

            if (commitResult.data === -1) {
                alert('댓글 작성중 오류가 발생했습니다. 잠시후 다시 시도해주세요.');
            } else {
                document.getElementById('commitinput').value = '';
                getCommit();
            }
        }
    }

    const delCommit = async (numbers) => { // 댓글 삭제
        if (window.confirm('댓글을 삭제하시겠습니까?')) {
            const result = await axios({
                method : "DELETE" ,
                url : `/auth/commit/${numbers}` ,
                mode : "cors"
            });

            getCommit();
        }
    }

    const getCommit = async () => { // 댓글 출력
        const getCommit = await axios({
            method: "GET" ,
            url: `/commit/${parseInt(params) + 1}` ,
            mode : "cors"
        });

        setCommitData(getCommit.data);
    }

    useEffect(async () => {
        getPost('views');
        getCommit();
        const accessToken =  await AsyncStorage.getItem("accessToken");
        axios.defaults.headers.common['Authorization'] = accessToken;
    }, []);

    return (
        <section className="postViewFrame">
            <div className="postViewSection">
                <header className="postViewName">
                    <div className="postsettingIcon">
                     <Link to={`/noticelist`}><svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" className="bi bi-arrow-bar-left" viewBox="0 0 16 16">
                            <path fillRule="evenodd" d="M12.5 15a.5.5 0 0 1-.5-.5v-13a.5.5 0 0 1 1 0v13a.5.5 0 0 1-.5.5zM10 8a.5.5 0 0 1-.5.5H3.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L3.707 7.5H9.5a.5.5 0 0 1 .5.5z" />
                        </svg></Link>
                        <svg xmlns="http://www.w3.org/2000/svg" onClick={() => postDelete(postContentArr.numbers)} width="20" height="20" fill="currentColor" className="bi bi-trash" viewBox="0 0 16 16">
                            <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z" />
                            <path fillRule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z" />
                        </svg>
                        <svg xmlns="http://www.w3.org/2000/svg" onClick={() => postModifie()} width="20" height="20" fill="currentColor" className="bi bi-pen" viewBox="0 0 16 16">
                            <path d="m13.498.795.149-.149a1.207 1.207 0 1 1 1.707 1.708l-.149.148a1.5 1.5 0 0 1-.059 2.059L4.854 14.854a.5.5 0 0 1-.233.131l-4 1a.5.5 0 0 1-.606-.606l1-4a.5.5 0 0 1 .131-.232l9.642-9.642a.5.5 0 0 0-.642.056L6.854 4.854a.5.5 0 1 1-.708-.708L9.44.854A1.5 1.5 0 0 1 11.5.796a1.5 1.5 0 0 1 1.998-.001zm-.644.766a.5.5 0 0 0-.707 0L1.95 11.756l-.764 3.057 3.057-.764L14.44 3.854a.5.5 0 0 0 0-.708l-1.585-1.585z" />
                        </svg>
                        <svg xmlns="http://www.w3.org/2000/svg" onClick={() => getPost('likes')} width="20" height="20" fill="currentColor" className="bi bi-hand-thumbs-up" viewBox="0 0 16 16">
                            <path d="M8.864.046C7.908-.193 7.02.53 6.956 1.466c-.072 1.051-.23 2.016-.428 2.59-.125.36-.479 1.013-1.04 1.639-.557.623-1.282 1.178-2.131 1.41C2.685 7.288 2 7.87 2 8.72v4.001c0 .845.682 1.464 1.448 1.545 1.07.114 1.564.415 2.068.723l.048.03c.272.165.578.348.97.484.397.136.861.217 1.466.217h3.5c.937 0 1.599-.477 1.934-1.064a1.86 1.86 0 0 0 .254-.912c0-.152-.023-.312-.077-.464.201-.263.38-.578.488-.901.11-.33.172-.762.004-1.149.069-.13.12-.269.159-.403.077-.27.113-.568.113-.857 0-.288-.036-.585-.113-.856a2.144 2.144 0 0 0-.138-.362 1.9 1.9 0 0 0 .234-1.734c-.206-.592-.682-1.1-1.2-1.272-.847-.282-1.803-.276-2.516-.211a9.84 9.84 0 0 0-.443.05 9.365 9.365 0 0 0-.062-4.509A1.38 1.38 0 0 0 9.125.111L8.864.046zM11.5 14.721H8c-.51 0-.863-.069-1.14-.164-.281-.097-.506-.228-.776-.393l-.04-.024c-.555-.339-1.198-.731-2.49-.868-.333-.036-.554-.29-.554-.55V8.72c0-.254.226-.543.62-.65 1.095-.3 1.977-.996 2.614-1.708.635-.71 1.064-1.475 1.238-1.978.243-.7.407-1.768.482-2.85.025-.362.36-.594.667-.518l.262.066c.16.04.258.143.288.255a8.34 8.34 0 0 1-.145 4.725.5.5 0 0 0 .595.644l.003-.001.014-.003.058-.014a8.908 8.908 0 0 1 1.036-.157c.663-.06 1.457-.054 2.11.164.175.058.45.3.57.65.107.308.087.67-.266 1.022l-.353.353.353.354c.043.043.105.141.154.315.048.167.075.37.075.581 0 .212-.027.414-.075.582-.05.174-.111.272-.154.315l-.353.353.353.354c.047.047.109.177.005.488a2.224 2.224 0 0 1-.505.805l-.353.353.353.354c.006.005.041.05.041.17a.866.866 0 0 1-.121.416c-.165.288-.503.56-1.066.56z" />
                        </svg>
                    </div>
                    <span className="postWriter">Written <span style={{color:"gray"}}>by</span> {postContentArr.writer}</span><br/>
                    <span className="postTitle">{postContentArr.title}</span>
                    <div className="postView">
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="gray" className="bi bi-eye" viewBox="0 0 16 16">
                            <path d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8zM1.173 8a13.133 13.133 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.133 13.133 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5c-2.12 0-3.879-1.168-5.168-2.457A13.134 13.134 0 0 1 1.172 8z" />
                            <path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5zM4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0z" />
                        </svg>
                        <span>{postContentArr.views}</span>
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" className="bi bi-hand-thumbs-up" viewBox="0 0 16 16">
                            <path d="M8.864.046C7.908-.193 7.02.53 6.956 1.466c-.072 1.051-.23 2.016-.428 2.59-.125.36-.479 1.013-1.04 1.639-.557.623-1.282 1.178-2.131 1.41C2.685 7.288 2 7.87 2 8.72v4.001c0 .845.682 1.464 1.448 1.545 1.07.114 1.564.415 2.068.723l.048.03c.272.165.578.348.97.484.397.136.861.217 1.466.217h3.5c.937 0 1.599-.477 1.934-1.064a1.86 1.86 0 0 0 .254-.912c0-.152-.023-.312-.077-.464.201-.263.38-.578.488-.901.11-.33.172-.762.004-1.149.069-.13.12-.269.159-.403.077-.27.113-.568.113-.857 0-.288-.036-.585-.113-.856a2.144 2.144 0 0 0-.138-.362 1.9 1.9 0 0 0 .234-1.734c-.206-.592-.682-1.1-1.2-1.272-.847-.282-1.803-.276-2.516-.211a9.84 9.84 0 0 0-.443.05 9.365 9.365 0 0 0-.062-4.509A1.38 1.38 0 0 0 9.125.111L8.864.046zM11.5 14.721H8c-.51 0-.863-.069-1.14-.164-.281-.097-.506-.228-.776-.393l-.04-.024c-.555-.339-1.198-.731-2.49-.868-.333-.036-.554-.29-.554-.55V8.72c0-.254.226-.543.62-.65 1.095-.3 1.977-.996 2.614-1.708.635-.71 1.064-1.475 1.238-1.978.243-.7.407-1.768.482-2.85.025-.362.36-.594.667-.518l.262.066c.16.04.258.143.288.255a8.34 8.34 0 0 1-.145 4.725.5.5 0 0 0 .595.644l.003-.001.014-.003.058-.014a8.908 8.908 0 0 1 1.036-.157c.663-.06 1.457-.054 2.11.164.175.058.45.3.57.65.107.308.087.67-.266 1.022l-.353.353.353.354c.043.043.105.141.154.315.048.167.075.37.075.581 0 .212-.027.414-.075.582-.05.174-.111.272-.154.315l-.353.353.353.354c.047.047.109.177.005.488a2.224 2.224 0 0 1-.505.805l-.353.353.353.354c.006.005.041.05.041.17a.866.866 0 0 1-.121.416c-.165.288-.503.56-1.066.56z" />
                        </svg>
                        <span>{postContentArr.likes}</span>
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" className="bi bi-chat-left" viewBox="0 0 16 16">
                            <path d="M14 1a1 1 0 0 1 1 1v8a1 1 0 0 1-1 1H4.414A2 2 0 0 0 3 11.586l-2 2V2a1 1 0 0 1 1-1h12zM2 0a2 2 0 0 0-2 2v12.793a.5.5 0 0 0 .854.353l2.853-2.853A1 1 0 0 1 4.414 12H14a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2z" />
                        </svg>
                        <span>{commitData.length}</span>
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" className="bi bi-clock" viewBox="0 0 16 16">
                            <path d="M8 3.5a.5.5 0 0 0-1 0V9a.5.5 0 0 0 .252.434l3.5 2a.5.5 0 0 0 .496-.868L8 8.71V3.5z" />
                            <path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16zm7-8A7 7 0 1 1 1 8a7 7 0 0 1 14 0z" />
                        </svg>
                        <span>{ postContentArr.postTime}</span>
                    </div>
                </header>
                <aside className="postViewContent">
                    <div dangerouslySetInnerHTML={{ __html: postContentArr.content }} />
                </aside>
                {tagData.length === 0 ? null :
                    <div className="postattachment">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-tags" viewBox="0 0 16 16">
                            <path d="M3 2v4.586l7 7L14.586 9l-7-7H3zM2 2a1 1 0 0 1 1-1h4.586a1 1 0 0 1 .707.293l7 7a1 1 0 0 1 0 1.414l-4.586 4.586a1 1 0 0 1-1.414 0l-7-7A1 1 0 0 1 2 6.586V2z" />
                            <path d="M5.5 5a.5.5 0 1 1 0-1 .5.5 0 0 1 0 1zm0 1a1.5 1.5 0 1 0 0-3 1.5 1.5 0 0 0 0 3zM1 7.086a1 1 0 0 0 .293.707L8.75 15.25l-.043.043a1 1 0 0 1-1.414 0l-7-7A1 1 0 0 1 0 7.586V3a1 1 0 0 1 1-1v5.086z" />
                        </svg>
                        <span>Tags</span>
                        <div className="postviewTagscontent"> {/* 태그 */}
                            <PostViewTag data={tagData} />
                        </div>
                    </div>
                }
                {attachment.length === 0 ? null :
                    <div className="postattachment2">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-download" viewBox="0 0 16 16">
                            <path d="M.5 9.9a.5.5 0 0 1 .5.5v2.5a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1v-2.5a.5.5 0 0 1 1 0v2.5a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2v-2.5a.5.5 0 0 1 .5-.5z" />
                            <path d="M7.646 11.854a.5.5 0 0 0 .708 0l3-3a.5.5 0 0 0-.708-.708L8.5 10.293V1.5a.5.5 0 0 0-1 0v8.793L5.354 8.146a.5.5 0 1 0-.708.708l3 3z" />
                        </svg>
                        <span>Attachment</span>
                        <div className="postviewTagscontent2"> {/* 첨부파일 */}
                            <PostViewAttachment data={attachment} />
                        </div>
                    </div> }

                <div className="postComment">
                    <div className="commitmiddle">
                        {postContentArr.blockComm ? null : <div className="commitInput">
                            <textarea id="commitinput" className="postCommitArea" ref={commit} maxLength={200} />
                            <button className="commitCommit" onClick={() => addCommit()}>Commit</button>
                        </div> }
                        {/* 댓글 <NoticeboardCommit/> 과 덧글 <NoticeboardReply/> 은 여기에 넣자*/}
                        <NoticeboardCommit data={commitData} delButton={delCommit} idStats={idStatus}/>
                        {/* <NoticeboardreplyText/> 덧글 값 입력 창 */}
                    </div>
                    <div className="heightBug" />
                </div>
            </div>
        </section>
    )
}

export default React.memo(PostView);