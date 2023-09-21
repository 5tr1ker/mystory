import axios from "axios";

export function deleteAllToken () {
    axios({
        method: "POST",
        mode: "cors",
        url: `/user/logout`,
    });
    localStorage.removeItem("userId");
}