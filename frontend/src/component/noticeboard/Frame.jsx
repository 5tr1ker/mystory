/* eslint-disable react-hooks/exhaustive-deps */
import { Link, Route, Routes } from 'react-router-dom';
import React, { Fragment, useEffect, useState } from "react";
import '../../_style/noticeFrame.css';
import NewPost from "./newPost";
import NoticeList from "./postlist";
import PostView from "./postview";
import Profile from './Profile';
import Notificate from './notificate';
import qs from 'qs';
import axios from 'axios';
import { deleteAllToken } from './DeleteAllCookie';
import NewMeeting from '../meeting/newmetting';
import Chat from '../meeting/chat';
import MeetingList from '../meeting/meetingList';
import MeetingView from '../meeting/meetingView';
import NewReservation from '../meeting/newReservation';
import ModifyMeeting from '../meeting/modifyMeeting';
import defaultALT from '../../_image/defaultALT.png'

const NoticeFrame = () => {
    let sessionUserId = localStorage.getItem("userId");

    const [dropBoxs, setDropBox] = useState(false);
    const [notified, setNotified] = useState(false);
    const [chatDrop , setChatDrop] = useState(false);
    const [profileImage , setProfileImage] = useState("");

    const [userOption, setUserOption] = useState();
    const urlInfo = window.location.pathname.split('/')[1];
    
    const query = qs.parse(window.location.search, { // ?tag=데이터 로 찾음 query.tag
        ignoreQueryPrefix: true
    });

    const noticeInfo = window.location.pathname.split('/')[1];
    if ((noticeInfo === undefined || noticeInfo === '') && urlInfo !== 'profile' && urlInfo !== 'newpost') { // 비정상적인 경로 확인
        window.location.replace("/noticelist");
    }

    const logout = () => {
        sessionUserId = null;
        localStorage.removeItem("userId");
        setUserOption(); // 강제 리렌더링
    }

    const getInitData = async () => {
        await axios({
            method: "GET",
            mode: "cors",
            url: `/users`,
        })
        .then((response) => { 
            localStorage.setItem("userId" , response.data.data.id);
            setUserOption({notified: response.data.data.options});
            setProfileImage(response.data.data.profileImage);
        }) 
        .catch((e) => {console.log(e.response)});
    }

    const setNotifiedPops = () => {
        setDropBox(false);
        notified ? setNotified(false) : setNotified(true);
    }

    const setChatPops = () => {
        setDropBox(false);
        chatDrop ? setChatDrop(false) : setChatDrop(true);
    }

    const setDropers = () => { dropBoxs ? setDropBox(false) : setDropBox(true); }
    
    useEffect(async () => {
        if(query.code != undefined) {
            window.location.replace("/noticelist");
        }

    });
    if(sessionUserId == null || userOption == undefined) {
        getInitData();
    }

    return (
        <Fragment>
            <div className="noticeFrame">
                <header className="noticeFrameHeader">
                    <div className="myStory">
                        <span className="noticeFrameName"><a href="https://github.com/5tr1ker" target="_blank" id="noticeName">myStory.</a></span>
                    </div>
                    <div id="buttonArea">
                        {notified ? <Notificate dropDownSet={setNotified} notifiedMode={userOption.notified} userId={sessionUserId} /> : null}
                        {chatDrop ? <Chat dropDownSet={setChatPops}/> : null}
                        <span><Link to="/noticelist" id="notDecor">게시판</Link></span>
                        <span><Link to="/meeting" id="notDecor">모임</Link></span>
                        <span><a href="https://velog.io/@tjseocld" target="_blank" id="notDecor">블로그</a></span>
                        <span><a href="https://github.com/5tr1ker" target="_blank" id="notDecor">GitHub</a></span>
                    </div>
                    <div id="loginStatus">
                        <span id="loginUser">
                            {sessionUserId != undefined ?
                                <Fragment>
                                    <div className='noticeLogin' onClick={setDropers}>
                                        <div className="profileimage">
                                            <img src={profileImage || defaultALT} alt="img"className="profileImageData"/>
                                        </div>
                                        <span style={{ borderRight: "none" }}>{sessionUserId} 님
                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-caret-down-fill profilepointer" viewBox="0 0 16 16">
                                                <path d="M7.247 11.14 2.451 5.658C1.885 5.013 2.345 4 3.204 4h9.592a1 1 0 0 1 .753 1.659l-4.796 5.48a1 1 0 0 1-1.506 0z" />
                                            </svg>
                                        </span>
                                    </div>
                                    {dropBoxs ?
                                        <div className='LoginDropBox'>
                                            <div className="profileIcon">
                                                <svg xmlns="http://www.w3.org/2000/svg" onClick={setDropers} width="20" height="20" fill="currentColor" className="bi bi-x-lg" viewBox="0 0 16 16">
                                                    <path fillRule="evenodd" d="M13.854 2.146a.5.5 0 0 1 0 .708l-11 11a.5.5 0 0 1-.708-.708l11-11a.5.5 0 0 1 .708 0Z" />
                                                    <path fillRule="evenodd" d="M2.146 2.146a.5.5 0 0 0 0 .708l11 11a.5.5 0 0 0 .708-.708l-11-11a.5.5 0 0 0-.708 0Z" />
                                                </svg>
                                            </div>
                                            <ul>
                                                <Link to="/profile" id="textDesign">
                                                    <li className='DropBoxContent' onClick={setDropers} style={{ marginLeft: "-2px" }}>
                                                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" className="bi bi-person" viewBox="0 0 16 16">
                                                            <path d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm2-3a2 2 0 1 1-4 0 2 2 0 0 1 4 0zm4 8c0 1-1 1-1 1H3s-1 0-1-1 1-4 6-4 6 3 6 4zm-1-.004c-.001-.246-.154-.986-.832-1.664C11.516 10.68 10.289 10 8 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10z" />
                                                        </svg>
                                                        My Profile
                                                    </li>
                                                </Link>
                                                <li className='DropBoxContent' onClick={setNotifiedPops} style={{ marginLeft: "-1px" }}>
                                                    <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="currentColor" className="bi bi-bell" viewBox="0 0 16 16">
                                                        <path d="M8 16a2 2 0 0 0 2-2H6a2 2 0 0 0 2 2zM8 1.918l-.797.161A4.002 4.002 0 0 0 4 6c0 .628-.134 2.197-.459 3.742-.16.767-.376 1.566-.663 2.258h10.244c-.287-.692-.502-1.49-.663-2.258C12.134 8.197 12 6.628 12 6a4.002 4.002 0 0 0-3.203-3.92L8 1.917zM14.22 12c.223.447.481.801.78 1H1c.299-.199.557-.553.78-1C2.68 10.2 3 6.88 3 6c0-2.42 1.72-4.44 4.005-4.901a1 1 0 1 1 1.99 0A5.002 5.002 0 0 1 13 6c0 .88.32 4.2 1.22 6z" />
                                                    </svg>
                                                    Notification
                                                </li>
                                                <li className='DropBoxContent' onClick={setChatPops} style={{ marginLeft: "-1px" }}>
                                                    <svg width="19" height="19" viewBox="0 0 26 23" fill="currentColor" className="bi bi-bell" xmlns="http://www.w3.org/2000/svg">
                                                        <path d="M7.42102 10.4827C7.72824 10.4827 7.99311 10.3839 8.21563 10.1865C8.43815 9.9891 8.54942 9.75098 8.54942 9.47216C8.54942 9.19332 8.44067 8.95292 8.22317 8.75095C8.00564 8.54898 7.74328 8.44799 7.43609 8.44799C7.12888 8.44799 6.86401 8.5467 6.64148 8.74411C6.41896 8.94154 6.3077 9.17966 6.3077 9.45848C6.3077 9.73732 6.41645 9.97773 6.63395 10.1797C6.85147 10.3817 7.11383 10.4827 7.42102 10.4827ZM12.9925 10.4827C13.2997 10.4827 13.5645 10.3839 13.7871 10.1865C14.0096 9.9891 14.1209 9.75098 14.1209 9.47216C14.1209 9.19332 14.0121 8.95292 13.7946 8.75095C13.5771 8.54898 13.3147 8.44799 13.0075 8.44799C12.7003 8.44799 12.4355 8.5467 12.2129 8.74411C11.9904 8.94154 11.8791 9.17966 11.8791 9.45848C11.8791 9.73732 11.9879 9.97773 12.2054 10.1797C12.4229 10.3817 12.6853 10.4827 12.9925 10.4827ZM18.5639 10.4827C18.8711 10.4827 19.136 10.3839 19.3585 10.1865C19.581 9.9891 19.6923 9.75098 19.6923 9.47216C19.6923 9.19332 19.5836 8.95292 19.3661 8.75095C19.1485 8.54898 18.8862 8.44799 18.579 8.44799C18.2718 8.44799 18.0069 8.5467 17.7844 8.74411C17.5618 8.94154 17.4506 9.17966 17.4506 9.45848C17.4506 9.73732 17.5593 9.97773 17.7768 10.1797C17.9944 10.3817 18.2567 10.4827 18.5639 10.4827ZM0 23V2.08458C0 1.50093 0.222024 1.00758 0.666073 0.604551C1.11012 0.201517 1.65367 0 2.29672 0H23.7033C24.3463 0 24.8899 0.201517 25.3339 0.604551C25.778 1.00758 26 1.50093 26 2.08458V16.8461C26 17.4297 25.778 17.9231 25.3339 18.3261C24.8899 18.7291 24.3463 18.9306 23.7033 18.9306H4.48351L0 23ZM3.7143 17.2451H23.7033C23.8132 17.2451 23.9139 17.2035 24.0055 17.1204C24.0971 17.0373 24.1429 16.9458 24.1429 16.8461V2.08458C24.1429 1.98483 24.0971 1.89338 24.0055 1.81025C23.9139 1.72714 23.8132 1.68558 23.7033 1.68558H2.29672C2.18682 1.68558 2.08606 1.72714 1.99447 1.81025C1.9029 1.89338 1.85711 1.98483 1.85711 2.08458V18.9432L3.7143 17.2451Z" fill="black"/>
                                                    </svg>
                                                    Chat
                                                </li>
                                                <Link to="/profile" id="textDesign">
                                                    <li className='DropBoxContent' onClick={setDropers} style={{ marginLeft: "-1px" }}>
                                                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" className="bi bi-gear" viewBox="0 0 16 16">
                                                            <path d="M8 4.754a3.246 3.246 0 1 0 0 6.492 3.246 3.246 0 0 0 0-6.492zM5.754 8a2.246 2.246 0 1 1 4.492 0 2.246 2.246 0 0 1-4.492 0z" />
                                                            <path d="M9.796 1.343c-.527-1.79-3.065-1.79-3.592 0l-.094.319a.873.873 0 0 1-1.255.52l-.292-.16c-1.64-.892-3.433.902-2.54 2.541l.159.292a.873.873 0 0 1-.52 1.255l-.319.094c-1.79.527-1.79 3.065 0 3.592l.319.094a.873.873 0 0 1 .52 1.255l-.16.292c-.892 1.64.901 3.434 2.541 2.54l.292-.159a.873.873 0 0 1 1.255.52l.094.319c.527 1.79 3.065 1.79 3.592 0l.094-.319a.873.873 0 0 1 1.255-.52l.292.16c1.64.893 3.434-.902 2.54-2.541l-.159-.292a.873.873 0 0 1 .52-1.255l.319-.094c1.79-.527 1.79-3.065 0-3.592l-.319-.094a.873.873 0 0 1-.52-1.255l.16-.292c.893-1.64-.902-3.433-2.541-2.54l-.292.159a.873.873 0 0 1-1.255-.52l-.094-.319zm-2.633.283c.246-.835 1.428-.835 1.674 0l.094.319a1.873 1.873 0 0 0 2.693 1.115l.291-.16c.764-.415 1.6.42 1.184 1.185l-.159.292a1.873 1.873 0 0 0 1.116 2.692l.318.094c.835.246.835 1.428 0 1.674l-.319.094a1.873 1.873 0 0 0-1.115 2.693l.16.291c.415.764-.42 1.6-1.185 1.184l-.291-.159a1.873 1.873 0 0 0-2.693 1.116l-.094.318c-.246.835-1.428.835-1.674 0l-.094-.319a1.873 1.873 0 0 0-2.692-1.115l-.292.16c-.764.415-1.6-.42-1.184-1.185l.159-.291A1.873 1.873 0 0 0 1.945 8.93l-.319-.094c-.835-.246-.835-1.428 0-1.674l.319-.094A1.873 1.873 0 0 0 3.06 4.377l-.16-.292c-.415-.764.42-1.6 1.185-1.184l.292.159a1.873 1.873 0 0 0 2.692-1.115l.094-.319z" />
                                                        </svg>
                                                        Settings
                                                    </li>
                                                </Link>
                                                <Link to="/login" id="textDesign" onClick={() => deleteAllToken()}>
                                                    <li className='DropBoxContent'>
                                                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" className="bi bi-box-arrow-right" viewBox="0 0 16 16">
                                                            <path fillRule="evenodd" d="M10 12.5a.5.5 0 0 1-.5.5h-8a.5.5 0 0 1-.5-.5v-9a.5.5 0 0 1 .5-.5h8a.5.5 0 0 1 .5.5v2a.5.5 0 0 0 1 0v-2A1.5 1.5 0 0 0 9.5 2h-8A1.5 1.5 0 0 0 0 3.5v9A1.5 1.5 0 0 0 1.5 14h8a1.5 1.5 0 0 0 1.5-1.5v-2a.5.5 0 0 0-1 0v2z" />
                                                            <path fillRule="evenodd" d="M15.854 8.354a.5.5 0 0 0 0-.708l-3-3a.5.5 0 0 0-.708.708L14.293 7.5H5.5a.5.5 0 0 0 0 1h8.793l-2.147 2.146a.5.5 0 0 0 .708.708l3-3z" />
                                                        </svg>
                                                        Logout
                                                    </li>
                                                </Link>
                                            </ul>
                                        </div> : null}
                                </Fragment>
                                :
                                <div className="logincommitsetting"><Link to="/login" className='noticeLogin'>Login</Link><Link to="/Register" className='noticeRegister'>Register</Link></div>
                            }
                        </span>
                    </div>
                </header>
                <Routes>
                    <Route path='/noticelist' element={<NoticeList/>}></Route>
                    <Route path='/newpost' element={<NewPost idStatus={sessionUserId} />}></Route>
                    <Route path='/modified/:id' element={<NewPost idStatus={sessionUserId} />}></Route>
                    <Route path='/viewpost/:id' element={<PostView idStatus={sessionUserId} />}></Route>
                    <Route path='/profile' element={<Profile idStatus={sessionUserId} rerenders={logout} profileImageUrl={profileImage} />}></Route>
                    <Route path="/meeting" element={<MeetingList idStatus={sessionUserId} />}></Route>
                    <Route path="/newmeeting" element={<NewMeeting idStatus={sessionUserId}/>}></Route>
                    <Route path="/modify/meeting/:id" element={<ModifyMeeting idStatus={sessionUserId} />}></Route>
                    <Route path="/meeting/:id" element={<MeetingView />}></Route>
                    <Route path="/newmeeting/:id" element={<NewReservation idStatus={sessionUserId} />}></Route>
                </Routes>
            </div>
        </Fragment>
    )
};

export default NoticeFrame;