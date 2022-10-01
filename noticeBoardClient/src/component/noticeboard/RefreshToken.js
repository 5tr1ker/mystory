import axios from "axios";
import Cookies from "universal-cookie";
import { deleteAllToken } from "./DeleteAllCookie";

export async function expireTokenTrans(func) {

    const cookie = new Cookies();
    const refreshToken = cookie.get('refreshToken');

    const jsonObj = JSON.stringify({"refreshToken" : refreshToken});
    const getAccessToken = await axios({
        method: "POST",
        mode: "cors",
        url: `/refresh`,
        headers : {"Content-Type": "application/json"} ,
        data: jsonObj
    });

    console.log(getAccessToken.data);
    if(getAccessToken.data.status === "200") {
        cookie.remove('AccessToken', { path: '/' });
        cookie.set('AccessToken', getAccessToken.data.accessToken , {
            path: '/',
            secure: true ,
            maxAge: 1800
        });
        axios.defaults.headers.common['Authorization'] = cookie.get('AccessToken');
        func(); // 해당 함수 다시 요청
    } else if (getAccessToken.data.status == "402") { // RefreshToken 만료
        alert("세션이 종료되었습니다. 다시 로그인해주세요.");
        deleteAllToken();
        window.location.replace('/login');
    }
} 
