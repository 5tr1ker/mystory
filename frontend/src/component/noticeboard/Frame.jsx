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
import Cookies from 'universal-cookie';
import { expireTokenTrans, setAccessToken } from './RefreshToken';
import { deleteAllToken } from './DeleteAllCookie';
import AsyncStorage from '@react-native-async-storage/async-storage';

const NoticeFrame = () => {
    const cookies = new Cookies();
    const getCookieStat = cookies.get('myToken');

    const [dropBoxs, setDropBox] = useState(false);
    const [notified, setNotified] = useState(false);

    const [mobileMenu, setMobileMenu] = useState(false);

    const [userOption, setUserOption] = useState({ session: 0, notified: 3 });
    const urlInfo = window.location.pathname.split('/')[1];
    
    const query = qs.parse(window.location.search, { // ?tag=데이터 로 찾음 query.tag
        ignoreQueryPrefix: true
    });

    const noticeInfo = window.location.pathname.split('/')[1];
    if ((noticeInfo === undefined || noticeInfo === '') && urlInfo !== 'profile' && urlInfo !== 'newpost') { // 비정상적인 경로 확인
        window.location.replace("/noticelist");
    }

    const getInitData = async () => {
        if(getCookieStat === undefined) {
            return;
        }
        
        const getProfileDatas = await axios({
            method: "GET",
            mode: "cors",
            url: `/auth/profileData/${getCookieStat}`,
        });

        if(getProfileDatas.data === "AccessTokenExpire") {
            expireTokenTrans(getInitData)
        }

        setUserOption({
            session: getProfileDatas.data.option1,
            notified: getProfileDatas.data.option2
        });
    }

    const setMobileMenuBar = () => { mobileMenu ? setMobileMenu(false) : setMobileMenu(true); }

    const setNotifiedPops = () => {
        setDropBox(false);
        notified ? setNotified(false) : setNotified(true);
    }

    const setDropers = () => { dropBoxs ? setDropBox(false) : setDropBox(true); }
    
    useEffect(async () => {
        const accessToken =  await AsyncStorage.getItem("accessToken");
        axios.defaults.headers.common['Authorization'] = accessToken;
        if(query.code !== undefined) {
            let result = null;

            if(query.scope !== undefined) { // 구글 로그인
                result = await axios({
                    method: "GET",
                    mode: "cors",
                    url: `/googleLogin?code=${query.code}`,
                });

            } else {    // 카카오 로그인
                result = await axios({
                    method: "GET",
                    mode: "cors",
                    url: `/kakaoLogin?code=${query.code}`,
                });
            }

            cookies.set('refreshToken', result.data.refreshToken , {
                path: '/',
                secure: true ,
                maxAge: 1209600
            });

            cookies.set('myToken', result.data.key , {
                path: '/',
                secure: true
            });
            setAccessToken(result.data.accessToken);
            window.location.replace("/noticelist");
        }

        if (getCookieStat !== '') {
            getInitData();
        }
    }, []);

    return (
        <Fragment>
            <div className="noticeFrame">
                <header className="noticeFrameHeader">
                    <div className="myStory">
                        <span className="noticeFrameName"><a href="https://github.com/5tr1ker" target="_blank" id="noticeName">myStory.</a></span>
                    </div>
                    {getCookieStat !== undefined ?
                        <div className="MobiledropBox" onClick={setMobileMenuBar}>
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="gray" className="bi bi-chevron-double-down mobileDropBox" viewBox="0 0 16 16">
                                <path fillRule="evenodd" d="M1.646 6.646a.5.5 0 0 1 .708 0L8 12.293l5.646-5.647a.5.5 0 0 1 .708.708l-6 6a.5.5 0 0 1-.708 0l-6-6a.5.5 0 0 1 0-.708z" />
                                <path fillRule="evenodd" d="M1.646 2.646a.5.5 0 0 1 .708 0L8 8.293l5.646-5.647a.5.5 0 0 1 .708.708l-6 6a.5.5 0 0 1-.708 0l-6-6a.5.5 0 0 1 0-.708z" />
                            </svg>
                        </div> :
                        <div className="logincommitsettingMobile"><Link to="/login" id='notDecor'>Login</Link></div>}

                    {mobileMenu ?
                        <div className="mobileDropBoxContent">
                            <span className='menuMobile' onClick={setMobileMenuBar}><Link to="/noticelist" id="notDecor">게시판</Link></span>
                            <span className='menuMobile' onClick={setMobileMenuBar}><a href="https://velog.io/@tjseocld" target="_blank" id="notDecor">블로그</a></span>
                            <span className='menuMobile' onClick={setMobileMenuBar}><a href="https://github.com/5tr1ker" target="_blank" id="notDecor">GitHub</a></span>
                            <ul>
                                <Link to='/newpost' id="textDesign"><li className='menuMobile'>New Post</li></Link>
                                <Link to="/profile" id="textDesign">
                                    <li className='menuMobile' onClick={setMobileMenuBar}>
                                        My Profile
                                    </li>
                                </Link>
                                <Link to="/profile" id="textDesign">
                                    <li className='menuMobile' onClick={setMobileMenuBar}>
                                        Settings
                                    </li>
                                </Link>
                                <Link to="/login" id="textDesign" onClick={() => deleteAllToken()}>
                                    <li className='menuMobile'>
                                        Logout
                                    </li>
                                </Link>
                            </ul>
                        </div>
                        : null}


                    <div id="buttonArea">
                        {notified ? <Notificate dropDownSet={setNotified} notifiedMode={userOption.notified} userId={getCookieStat} /> : null}
                        <span><Link to="/noticelist" id="notDecor">게시판</Link></span>
                        <span><a href="https://velog.io/@tjseocld" target="_blank" id="notDecor">블로그</a></span>
                        <span><a href="https://github.com/5tr1ker" target="_blank" id="notDecor">GitHub</a></span>
                    </div>
                    <div id="loginStatus">
                        <span id="loginUser">
                            {getCookieStat !== undefined ?
                                <Fragment>
                                    <div className='noticeLogin' onClick={setDropers}>
                                        <div className="profileimage">
                                            <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" className="bi bi-person-circle peopleicon" viewBox="0 0 16 16">
                                                <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z" />
                                                <path fillRule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z" />
                                            </svg>
                                        </div>
                                        <span style={{ borderRight: "none" }}>{getCookieStat} 님
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
                    <Route path='/newpost' element={<NewPost idStatus={getCookieStat} />}></Route>
                    <Route path='/modified/:id' element={<NewPost idStatus={getCookieStat} />}></Route>
                    <Route path='/viewpost' element={<PostView idStatus={getCookieStat} />}></Route>
                    <Route path='/profile' element={<Profile idStatus={getCookieStat} rerenders={getInitData} />}></Route>
                </Routes>
            </div>
        </Fragment>
    )
};

export default NoticeFrame;