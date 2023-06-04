import axios from "axios";
import { Fragment, useState } from "react"
import { Link } from "react-router-dom";
import '../../_style/loginScreen.css'
import img01 from '../../_image/pic1.png';

const Register = () => {
    const [idInfo , setIdInfo] = useState({
        id :'' ,
        pw :'' ,
        checkpw : ''
    });
    const [showpw, setShowPw] = useState('password');

    const setinfo = (e) => {
        const {name , value} = e.target;
        setIdInfo({...idInfo , [name] : value });
    }

    const showPwBt = () => { showpw === 'password' ? setShowPw('text') : setShowPw('password'); }

    const newRegis = () => {
        if (idInfo.id.length <= 3) {
            alert('아이디는 4자 이상입력해주세요.');
            return;
        }
        if (idInfo.pw.length <= 5) {
            alert('비밀번호는 6자 이상입력해주세요.');
            return;
        }
        if (idInfo.pw !== idInfo.checkpw) {
            alert('비밀번호가 일치하지 않습니다.');
            return;
        }
        regist();
    }

    const regist = async () => {
        const jsonData = JSON.stringify({"id" : idInfo.id , "password" : idInfo.pw , "checkPassword" : idInfo.checkpw});
        await axios({
            method : "POST" ,
            mode: "cors" , 
            url : `/registers` , 
            headers : {"Content-Type": "application/json"} ,
            data : jsonData})
            .then((response) => { alert(response.data.message); window.location.replace('/login'); }) 
            .catch((e) => alert(e.response.data.message));
    }

    return (
        <Fragment>
            <div className="loginFrame">
                <Link to="/login">
                    <svg xmlns="http://www.w3.org/2000/svg" width="26" height="26" fill="currentColor" className="bi bi-arrow-left Arrows" viewBox="0 0 16 16">
                        <path fillRule="evenodd" d="M15 8a.5.5 0 0 0-.5-.5H2.707l3.147-3.146a.5.5 0 1 0-.708-.708l-4 4a.5.5 0 0 0 0 .708l4 4a.5.5 0 0 0 .708-.708L2.707 8.5H14.5A.5.5 0 0 0 15 8z" />
                    </svg>
                </Link>
                <div className="registerArea">
                    <form>
                        <input type="text" className="idInput" placeholder="Username" name="id" onChange={setinfo} />
                        <input type={showpw} className="pwInput" placeholder="password" name="pw" onChange={setinfo} />
                        <input type={showpw} className="pwInput" placeholder="Confirm password" name="checkpw" onChange={setinfo} /><br /></form>
                    <label className="showPw">
                        <input type="checkbox" className="showPw" onClick={showPwBt} /> 비밀번호 표시
                    </label><br />
                    <span className="pwChecking">아이디는 4자 이상, 비밀번호는 6자 이상 입력해주세요.</span>
                    <button type="submit" className="sbButton" onClick={newRegis}>회원가입</button>
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

export default Register;

