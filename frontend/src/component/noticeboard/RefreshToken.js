import AsyncStorage from "@react-native-async-storage/async-storage";
import axios from "axios";
import { deleteAllToken } from "./DeleteAllCookie";

export async function expireTokenTrans(func) {
    const getAccessToken = await axios({
        method: "GET",
        mode: "cors",
        url: `/refresh`
    });

    //console.log(getAccessToken.data);
    if (getAccessToken.data.status === "200") {
        setAccessToken(getAccessToken.data.accessToken);
        func(); // 해당 함수 다시 요청
    } else if (getAccessToken.data.status == "402") { // RefreshToken 만료
        alert("세션이 종료되었습니다. 다시 로그인해주세요.");
        deleteAllToken();
        window.location.replace('/login');
    }
};

export function setAccessToken(access) {
    AsyncStorage.removeItem("accessToken");
    AsyncStorage.setItem("accessToken", access);
    axios.defaults.headers.common['Authorization'] = access;

};