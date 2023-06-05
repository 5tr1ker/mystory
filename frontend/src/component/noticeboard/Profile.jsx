import axios from "axios";
import { Fragment , useState } from "react"
import { useEffect } from "react";
import { useNavigate } from 'react-router-dom';
import React from "react";
import { deleteAllToken } from "./DeleteAllCookie";
import AsyncStorage from "@react-native-async-storage/async-storage";

const Profile = ({idStatus , rerenders}) => {
    const [profileEdits , setEdits] = useState(false);
    const [profileData , getProfileData] = useState([]) // 내 profile 데이터
    const [postStatistics , setPostStatistics] = useState([]); // 통계
    const [inputProfileData , setInputProfileData] = useState({ // inputs
        userId : idStatus , 
        email : '' ,
        phone : '' ,
        options : 0 ,
        postid : 0
    });
    const nav = useNavigate();
    const getData = async () => {
        if(idStatus == undefined) return -1; 
        const result = await axios({
            method: "GET" ,
            url : `/profiles/statistics` ,
            mode : "cors"
        });

        const getProfileDatas = await axios({
            method : "GET" ,
            mode: "cors" , 
            url : `/profiles` ,
        });

        const datas = [];
        datas.push(result.data.data.totalPostView , result.data.data.totalPost, result.data.data.totalComment , result.data.data.joinData.replaceAll("-" , "/"));
        getProfileData(getProfileDatas.data.data);
        setInputProfileData({userId : idStatus , email: getProfileDatas.data.data.email , phone: getProfileDatas.data.data.phone , options: getProfileDatas.data.data.options});
        setPostStatistics(datas); // 통계
    }

    const changeGetData = (e) => {
        const {name , value} = e.target;
        setInputProfileData({...inputProfileData , [name] : value});
    }

    const setProfileEdits = () => {
        setEdits(profileEdits ? false : true);
    }

    const saveProfile = () => {
        setEdits(false);
        getProfileData({userId: inputProfileData.userid , email: inputProfileData.email , phone: inputProfileData.phone , options :inputProfileData.option2});
    }

    const deleteId = async() => {
        const deleteConfirm = window.confirm("정말로 삭제하시겠습니까?");
        if (deleteConfirm) {
             await axios({
                url : `/users` ,
                method : "DELETE" ,
                mode : "cors"
            })
            .then(async (response) =>{ 
                alert("계정이 삭제되었습니다."); 
                await axios({
                    url : `/logout` ,
                    method : "GET" ,
                    mode : "cors"
                });
                localStorage.removeItem("userId")
                nav("/login", { replace: true }); }) 
            .catch((e) => alert(e.response.data.message));
        }
    }

    const doneChange = async () => {
        const jsonParse = JSON.stringify({"userId" : inputProfileData.userId, "email" : inputProfileData.email, "phone" : inputProfileData.phone, "options" : inputProfileData.options});

            if (idStatus !== inputProfileData.userId) {
                 await axios({
                    url : "/profiles" ,
                    mode : "cors" ,
                    method : "PUT" ,
                    data : jsonParse ,
                    headers : {"Content-Type": "application/json"}
                })
                .then(async (response) => { 
                    alert(response.data.message); 
                    alert("User Name이 변경되어 다시 로그인을 시도해주세요."); 
                    await axios({
                        url : `/logout` ,
                        method : "GET" ,
                        mode : "cors"
                    });
                    localStorage.removeItem("userId")
                    nav("/login", { replace: false }); }) 
                .catch((e) => alert(e.response.data.message));
            } else {
                await axios({
                    url : "/profiles" ,
                    mode : "cors" ,
                    method : "PUT" ,
                    data : jsonParse ,
                    headers : {"Content-Type": "application/json"}
                })
                .then((response) => { alert(response.data.message); nav("/noticelist", { replace: false }); }) 
                .catch((e) => alert(e.response.data.message));
            }
       }

    useEffect(async () => {
        getData();
    },[idStatus]);

    return (
        <section className="profileArea">
            <div className="profilestatistics">
                <div className="viewdesign">
                    <span id="total1">{postStatistics[0]?? 0}</span>
                    <span id="total1">Total views</span>
                    <svg  id="total1" xmlns="http://www.w3.org/2000/svg" fill="currentColor" className="bi bi-eye" viewBox="0 0 16 16">
                        <path d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8zM1.173 8a13.133 13.133 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.133 13.133 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5c-2.12 0-3.879-1.168-5.168-2.457A13.134 13.134 0 0 1 1.172 8z" />
                        <path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5zM4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0z" />
                    </svg>
                </div>
                <div className="viewdesign">
                    <span id="view1">{postStatistics[1]}</span>
                    <span id="view1">Total Posts</span>
                    <svg  id="view1" xmlns="http://www.w3.org/2000/svg" fill="currentColor" className="bi bi-layout-text-window" viewBox="0 0 16 16">
                        <path d="M3 6.5a.5.5 0 0 1 .5-.5h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1-.5-.5zm0 3a.5.5 0 0 1 .5-.5h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1-.5-.5zm.5 2.5a.5.5 0 0 0 0 1h5a.5.5 0 0 0 0-1h-5z" />
                        <path d="M2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2zm12 1a1 1 0 0 1 1 1v1H1V2a1 1 0 0 1 1-1h12zm1 3v10a1 1 0 0 1-1 1h-2V4h3zm-4 0v11H2a1 1 0 0 1-1-1V4h10z" />
                    </svg>
                </div>
                <div className="viewdesign">
                    <span id="comment1">{postStatistics[2]}</span>
                    <span id="comment1">Total Comments</span>
                    <svg id="comment1" xmlns="http://www.w3.org/2000/svg" fill="currentColor" className="bi bi-chat-quote cmtRSA" viewBox="0 0 16 16">
                        <path d="M2.678 11.894a1 1 0 0 1 .287.801 10.97 10.97 0 0 1-.398 2c1.395-.323 2.247-.697 2.634-.893a1 1 0 0 1 .71-.074A8.06 8.06 0 0 0 8 14c3.996 0 7-2.807 7-6 0-3.192-3.004-6-7-6S1 4.808 1 8c0 1.468.617 2.83 1.678 3.894zm-.493 3.905a21.682 21.682 0 0 1-.713.129c-.2.032-.352-.176-.273-.362a9.68 9.68 0 0 0 .244-.637l.003-.01c.248-.72.45-1.548.524-2.319C.743 11.37 0 9.76 0 8c0-3.866 3.582-7 8-7s8 3.134 8 7-3.582 7-8 7a9.06 9.06 0 0 1-2.347-.306c-.52.263-1.639.742-3.468 1.105z" />
                        <path d="M7.066 6.76A1.665 1.665 0 0 0 4 7.668a1.667 1.667 0 0 0 2.561 1.406c-.131.389-.375.804-.777 1.22a.417.417 0 0 0 .6.58c1.486-1.54 1.293-3.214.682-4.112zm4 0A1.665 1.665 0 0 0 8 7.668a1.667 1.667 0 0 0 2.561 1.406c-.131.389-.375.804-.777 1.22a.417.417 0 0 0 .6.58c1.486-1.54 1.293-3.214.682-4.112z" />
                    </svg>
                </div>
                <div className="viewdesign">
                    <span id="design1" style={{fontSize : 28 +"px" , position : "relative" , top : 30}}>{postStatistics[3]}</span>
                    <span id="design1" style={{position : "relative" , top : 30}}>Join Date</span>
                    <svg id="design1" style={{position : "relative" , top : -5}} xmlns="http://www.w3.org/2000/svg" fill="currentColor" className="bi bi-easel2" viewBox="0 0 16 16">
                        <path fillRule="evenodd" d="M8 0a.5.5 0 0 1 .447.276L8.81 1h4.69A1.5 1.5 0 0 1 15 2.5V11h.5a.5.5 0 0 1 0 1h-2.86l.845 3.379a.5.5 0 0 1-.97.242L12.11 14H3.89l-.405 1.621a.5.5 0 0 1-.97-.242L3.36 12H.5a.5.5 0 0 1 0-1H1V2.5A1.5 1.5 0 0 1 2.5 1h4.691l.362-.724A.5.5 0 0 1 8 0ZM2 11h12V2.5a.5.5 0 0 0-.5-.5h-11a.5.5 0 0 0-.5.5V11Zm9.61 1H4.39l-.25 1h7.72l-.25-1Z" />
                    </svg>
                </div>
            </div>
            <div className="profileArea_under">
                <div className="profiledesc">
                    <header>Profile</header>
                    <div className="profileEdits-area">
                        <div className="profile-pic">
                            <div className="userprofilepic">
                                <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" className="bi bi-person-circle peopleicon" viewBox="0 0 16 16">
                                    <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z" />
                                    <path fillRule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z" />
                                </svg>
                            </div>
                            <button className="saveandreturn" onClick={doneChange}>
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-house" viewBox="0 0 16 16">
                                    <path fillRule="evenodd" d="M2 13.5V7h1v6.5a.5.5 0 0 0 .5.5h9a.5.5 0 0 0 .5-.5V7h1v6.5a1.5 1.5 0 0 1-1.5 1.5h-9A1.5 1.5 0 0 1 2 13.5zm11-11V6l-2-2V2.5a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 .5.5z" />
                                    <path fillRule="evenodd" d="M7.293 1.5a1 1 0 0 1 1.414 0l6.647 6.646a.5.5 0 0 1-.708.708L8 2.207 1.354 8.854a.5.5 0 1 1-.708-.708L7.293 1.5z" />
                                </svg>저장 및 돌아가기
                            </button>
                        </div>
                        <div className="profile-data">
                            {profileEdits ?
                                <div className="profile-editmode">
                                    <span>Name  : </span><input type="text" placeholder="name" defaultValue={inputProfileData.userId} onChange={changeGetData} name="userId"/><br/>
                                    <span>Email : </span><input type="text" placeholder="Email" defaultValue={profileData.email} onChange={changeGetData} name="email"/><br/>
                                    <span>Phone : </span><input type="text" placeholder="Phone" defaultValue={profileData.phone} onChange={changeGetData} name="phone"/><br/>
                                    <button className="editprofileButton" onClick={saveProfile}>
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-pencil" viewBox="0 0 16 16">
                                            <path d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168l10-10zM11.207 2.5 13.5 4.793 14.793 3.5 12.5 1.207 11.207 2.5zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293l6.5-6.5zm-9.761 5.175-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325z" />
                                    </svg>Save</button>
                                    <button className="editprofileButton-cancel" onClick={setProfileEdits}>
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-arrow-bar-left" viewBox="0 0 16 16">
                                            <path fillRule="evenodd" d="M12.5 15a.5.5 0 0 1-.5-.5v-13a.5.5 0 0 1 1 0v13a.5.5 0 0 1-.5.5zM10 8a.5.5 0 0 1-.5.5H3.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L3.707 7.5H9.5a.5.5 0 0 1 .5.5z" />
                                        </svg>Cancel</button>
                                </div> : 
                                <Fragment>
                                    <span>Name  : </span><span id="profiledata">{inputProfileData.userId ?? "None"}</span>
                                    <span>Email : </span><span id="profiledata">{profileData.email || "None"}</span>
                                    <span>Phone : </span><span id="profiledata">{String(profileData.phone) || "None"}</span>
                                    <span id="seteditbox" onClick={setProfileEdits}>
                                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" className="bi bi-pencil" viewBox="0 0 16 16">
                                            <path d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168l10-10zM11.207 2.5 13.5 4.793 14.793 3.5 12.5 1.207 11.207 2.5zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293l6.5-6.5zm-9.761 5.175-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325z" />
                                        </svg>Edit Profile
                                    </span>
                                </Fragment>}
                            
                        </div>
                    </div>
                </div>
                <div className="profile_settings">
                    <header>Settings</header>
                    <div className="settings-contents">
                        <span>알람 설정</span>
                        <select name="options" key={profileData.options} defaultValue={profileData.options} style={{ marginTop: "5px", fontWeight: "600", color: "black", cursor: "pointer", boxShadow: "none" }} className="form-select form-select-sm" aria-label=".form-select-sm example" onChange={changeGetData}>
                            <option value={1} style={{ fontWeight: "600" }}>알람 켜기</option>
                            <option value={0} style={{ fontWeight: "600" }}>알람 끄기</option>
                        </select>
                        <span>계정 탈퇴</span>  
                        <button className="accountdelete" onClick={deleteId}>
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-door-open" viewBox="0 0 16 16">
                                <path d="M8.5 10c-.276 0-.5-.448-.5-1s.224-1 .5-1 .5.448.5 1-.224 1-.5 1z" />
                                <path d="M10.828.122A.5.5 0 0 1 11 .5V1h.5A1.5 1.5 0 0 1 13 2.5V15h1.5a.5.5 0 0 1 0 1h-13a.5.5 0 0 1 0-1H3V1.5a.5.5 0 0 1 .43-.495l7-1a.5.5 0 0 1 .398.117zM11.5 2H11v13h1V2.5a.5.5 0 0 0-.5-.5zM4 1.934V15h6V1.077l-6 .857z" />
                            </svg>Delete account
                        </button>
                    </div>
                </div>
            </div>
        </section>
        )
}

export default React.memo(Profile);