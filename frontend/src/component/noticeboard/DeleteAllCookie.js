import axios from "axios";

export function deleteAllToken () {
    axios({
        method: "POST",
        mode: "cors",
        url: `/logout`,
    });
    localStorage.removeItem("userId");
}