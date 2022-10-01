import Cookies from "universal-cookie";

export function deleteAllToken() {
    const cookie = new Cookies();
    cookie.remove('AccessToken', { path: '/' });
    cookie.remove('myToken', { path: '/' });
    cookie.remove('refreshToken', { path: '/' });
}