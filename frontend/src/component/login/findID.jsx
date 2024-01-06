import axios from 'axios';
import { useState } from 'react';
import { Link } from 'react-router-dom';
import { Fragment } from 'react';
import '../../_style/loginScreen.css'
import img01 from '../../_image/pic1.png';

const FindID = () => {
    const [email, setEmail] = useState('');
    const [codeVisible, setVisible] = useState(false);
    const [passwordInputVisible, setPasswordInputVisible] = useState(false);
    const [verificationCode, setVerificationCode] = useState('');
    const [isProcess, setProcess] = useState(false);
    const [newPassword, setPassword] = useState({
        'password': '',
        'checkPassword': ''
    });

    const changePassword = (e) => {
        const { name, value } = e.target;
        setPassword({ ...newPassword, [name]: value });
    }

    const requestChangePassword = async () => {
        if (newPassword.password.length <= 5) {
            alert('비밀번호는 6자 이상입력해주세요.');
            
            return;
        }

        if (newPassword.password !== newPassword.checkPassword) {
            alert("비밀번호가 일치하지 않습니다.");

            return;
        }

        await axios({
            method: "PATCH",
            mode: "cors",
            headers: { "Content-Type": "application/json" },
            url: `/password`,
            data: JSON.stringify({ "email" : email , "password": newPassword.password })
        })
            .then((response) => {
                alert("비밀번호가 변경되었습니다.");

                window.location.replace("/login");
            })
            .catch((e) => {
                alert(e.response.data.message);
            });
    }

    const setEmails = (e) => {
        setEmail(e.target.value)
    }

    const inputVerificationCode = (e) => {
        setVerificationCode(e.target.value);
    }

    const findId = async () => {
        if (!codeVisible) {
            if (!isProcess) {
                setProcess(true);
            }
            else if (isProcess) {
                alert("처리 중입니다.");

                return;
            }

            await axios({
                method: "POST",
                mode: "cors",
                headers: { "Content-Type": "application/json" },
                url: `/mail/send`,
                data: JSON.stringify({ "email": email })
            })
                .then((response) => {
                    setVisible(true);
                    alert("해당 이메일로 인증 번호가 전송되었습니다.");
                })
                .catch((e) => {
                    alert(e.response.data.message);

                    setProcess(false);
                });

            return;
        }

        await axios({
            method: "POST",
            mode: "cors",
            headers: { "Content-Type": "application/json" },
            url: `/mail/check`,
            data: JSON.stringify({ "email": email, "code": verificationCode })
        })
            .then((response) => {
                setVisible(false);
                setPasswordInputVisible(true);
            })
            .catch((e) => alert(e.response.data.message));

    }
    return (
        <Fragment>
            <div className="loginBackGround">
                <div className="loginFrame">
                    <Link to="/login">
                        <svg xmlns="http://www.w3.org/2000/svg" width="26" height="26" fill="currentColor" className="bi bi-arrow-left Arrows" viewBox="0 0 16 16">
                            <path fillRule="evenodd" d="M15 8a.5.5 0 0 0-.5-.5H2.707l3.147-3.146a.5.5 0 1 0-.708-.708l-4 4a.5.5 0 0 0 0 .708l4 4a.5.5 0 0 0 .708-.708L2.707 8.5H14.5A.5.5 0 0 0 15 8z" />
                        </svg>
                    </Link>
                    {passwordInputVisible ?
                        <div className="findIDArea">
                            <span className="findIddescription">please enter the id you use to sign in to website</span>
                            <input type="password" className="idInput forgotNameInput" placeholder="새로운 password" name="password" style={{ display: "none" }} /><br />
                            <input type="password" className="idInput forgotNameInput" placeholder="새로운 password" name="password" onChange={changePassword} style={{ marginBottom: "0px" }} /><br />
                            <input type="password" className="idInput forgotNameInput" placeholder="password 확인" name="checkPassword" onChange={changePassword} style={{ marginBottom: "0px" }} /><br />
                            <button type="submit" className="forgotButton" onClick={requestChangePassword}>비밀번호 재설정</button>
                            <Link to="/login" style={{ textDecoration: "none" }}><span className="backtosignin">Back to Sign in</span></Link>
                        </div>
                        :
                        <div className="findIDArea">
                            <span className="findIddescription">please enter the id you use to sign in to website</span>
                            <input type="text" className="idInput forgotNameInput" placeholder="Email 입력" onChange={setEmails} style={{ marginBottom: "0px" }} /><br />
                            {codeVisible ? <input type="text" className="idInput forgotNameInput" placeholder="인증 번호" onChange={inputVerificationCode} style={{ marginBottom: "30px" }} /> : null}
                            <button type="submit" className="forgotButton" onClick={findId}>비밀번호 재설정</button>
                            <Link to="/login" style={{ textDecoration: "none" }}><span className="backtosignin">Back to Sign in</span></Link>
                        </div>
                    }

                </div>
                <div className="platform">
                    <span className='platformTextbox'>Sign Up Now</span>
                    <span className='platformTextbox2'>Create a new account and connect with others.</span>
                    <img src={img01} alt='img' className="loginImg" />
                </div>
            </div>
        </Fragment>
    )
}

export default FindID;