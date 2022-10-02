import axios from 'axios';
import { Fragment , useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import '../../_style/loginScreen.css'
import Cookies from 'universal-cookie';
import img01 from '../../_image/pic1.png';
import { setAccessToken } from '../noticeboard/RefreshToken';
import img02 from '../../_image/googleLoginButton.png';
import img03 from '../../_image/kakaoLogInButton.png';


const Logins = () => {
    const cookies = new Cookies();
    const [idInfo , setInfo] = useState({
        id : '' ,
        pw : ''
    });
    
    const kakaoLogin = () => {
        window.location.replace('http://localhost:8080/oauth2/authorization/kakao');
    }
    const googleLogin = () => {
        window.location.replace('http://localhost:8080/oauth2/authorization/google');
    }

    const jsonData = JSON.stringify(idInfo);
    const signUp = async () => {
        const signupResult = await axios({
            method : "POST" ,
            mode: "cors" , 
            url : `/login` , 
            headers : {"Content-Type": "application/json"} ,
            data : jsonData
        });

        axios.defaults.headers.common['Authorization'] = `${signupResult.data.accessToken}`;
        if (signupResult.data === -1) {
            alert('아이디 또는 비밀번호를 잘못 입력했습니다.');
        } else {
            
            cookies.set('refreshToken', signupResult.data.refreshToken , {
                path: '/',
                secure: true ,
                maxAge: 1209600
            });
            
            setAccessToken(signupResult.data.accessToken);
            // cookies.set('AccessToken', signupResult.data.accessToken , {
            //     path: '/',
            //     secure: true ,
            //     maxAge: 1209600
            // });

            const getUserData = await axios({
                method : "GET" ,
                mode: "cors" ,
                url : `/auth/getProfileData/${idInfo.id}`
            });

            if (getUserData.data.option1 === 1) {
                cookies.set('myToken', idInfo.id , {
                    path: '/',
                    secure: true,
                    maxAge: 3600
                });
            } else if (getUserData.data.option1 === 2) {
                cookies.set('myToken', idInfo.id , {
                    path: '/',
                    secure: true,
                    maxAge: 21600
                });
            } else if (getUserData.data.option1 === 3) {
                cookies.set('myToken', idInfo.id , {
                    path: '/',
                    secure: true
                });
            }
            window.location.replace('/noticelist');
        }
    }

    useEffect(async () => {
        const getCookieStat = cookies.get('myToken');
        if(getCookieStat !== undefined) {
            window.location.replace('/noticelist');
        }
    }, []);

    const changeIdPw = (e) => {
        const {name , value} = e.target;
        setInfo({...idInfo , [name] : value});
    }
    
    return (
        <Fragment>
            <div className="loginFrame">
                <Link to="/noticelist">
                    <svg xmlns="http://www.w3.org/2000/svg" width="26" height="26" fill="currentColor" className="bi bi-arrow-left Arrows" viewBox="0 0 16 16">
                        <path fillRule="evenodd" d="M15 8a.5.5 0 0 0-.5-.5H2.707l3.147-3.146a.5.5 0 1 0-.708-.708l-4 4a.5.5 0 0 0 0 .708l4 4a.5.5 0 0 0 .708-.708L2.707 8.5H14.5A.5.5 0 0 0 15 8z" />
                    </svg>
                </Link>
                <Link to="/register"><span className='registers'>Register</span></Link>
                <div className="loginArea">
                    <form>
                        <input type="text" className="idInput" placeholder="Username" name="id" onChange={changeIdPw} />
                        <input type="password" className="pwInput" placeholder="password" name="pw" onChange={changeIdPw} /><br />
                        <Link to="/findid"><span className="findId">Forgot your Password?</span></Link>
                    </form>
                    <div className="loginbuttonarea">
                        <button type="submit" className="sbButton" onClick={signUp}>로그인</button>
                        <Link to="/noticelist"><button type="submit" className="sbButton sbButton2" onClick={() => window.location.replace('/noticelist')}>로그인 없이 보기</button></Link>
                    </div>
                    <img src={img02} className = "socialLoginButton" onClick={googleLogin}/>
                    <img src={img03} className = "socialLoginButton" onClick={kakaoLogin}/>
                </div>
            </div>
            <div className="platform">
                <span className='platformTextbox'>Sign Up Now</span>
                <span className='platformTextbox2'>Create a new account and connect with others.</span>
                <img src={img01} alt='img' className="loginImg" />
            </div>
        </Fragment>
    )
}

export default Logins;