import axios from 'axios';
import { Fragment , useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import '../../_style/loginScreen.css'
import Cookies from 'universal-cookie';
import img01 from '../../_image/pic1.png';
import img02 from '../../_image/googleLoginButton.png';
import img03 from '../../_image/kakaoLogInButton.png';
import img04 from '../../_image/naverLoginButton.png';


const Logins = () => {
    const cookies = new Cookies();
    const [idInfo , setInfo] = useState({
        id : '' ,
        pw : ''
    });

    const headers = {
        "Content-Type": "application/json" ,
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Credentials': true
    }
    
    const kakaoLogin = () => {
        window.location.replace('http://localhost:8080/oauth2/authorization/kakao');
    }
    const googleLogin = () => {
        window.location.replace('http://localhost:8080/oauth2/authorization/google');
    }
    const naverLogin = () => {
        window.location.replace('http://localhost:8080/oauth2/authorization/naver');
    }

    
    const signUp = async () => {
        await axios({
            method : "POST" ,
            url : `/logins` , 
            headers : headers ,
            data : JSON.stringify({"id" : idInfo.id , "password" : idInfo.pw})
        })
        .then((response) => { alert(response.data.message); localStorage.setItem("userId" , idInfo.id); window.location.replace('/noticelist'); }) 
        .catch((e) => alert(e.response.data.message));
    }

    useEffect(async () => {
        const getCookieStat = localStorage.getItem('userId');
        if(getCookieStat != undefined) {
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
                    <div className="socialLogin">
                        <img src={img03} className = "socialLoginButton" onClick={kakaoLogin}/>
                        <img src={img02} className = "socialLoginButton" onClick={googleLogin}/>
                        <img src={img04} className = "socialLoginButton" onClick={naverLogin}/>
                    </div>
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