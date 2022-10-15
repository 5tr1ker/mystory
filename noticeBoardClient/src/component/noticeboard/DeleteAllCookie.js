import AsyncStorage from "@react-native-async-storage/async-storage";
import axios from "axios";
import Cookies from "universal-cookie";

export function deleteAllToken () {
    axios({
        method: "POST",
        mode: "cors",
        url: `/logout`,
    });
    const cookie = new Cookies();
    cookie.remove('myToken', { path: '/' });
    AsyncStorage.removeItem("accessToken");
}