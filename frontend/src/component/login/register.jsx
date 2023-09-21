import axios from "axios";
import { Fragment, useState , useRef } from "react"
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
    const imageInput = useRef();
    const [profileImage, setProfileImage] = useState(); // 이미지
    const [imageName , setImageName] = useState("프로필 이미지를 꼭 선택해주세요. ( 필수 ) "); // 이미지 이름

    const setinfo = (e) => {
        const {name , value} = e.target;
        setIdInfo({...idInfo , [name] : value });
    }

    const showPwBt = () => { showpw === 'password' ? setShowPw('text') : setShowPw('password'); }

    const checkValidImage = (e) => {
             if (e.target.files[0].size > 10 * 1024 * 1024) {
              alert("10M가 넘는 파일은 제외되었습니다.");
              return false;
            } else if (e.target.files[0].name.length > 30) {
              alert("파일명이 30자가 넘는 파일은 제외되었습니다.");
              return false;
            } else if (e.target.files[0].name.split('.')[1] == 'jpg' || e.target.files[0].name.split('.')[1] == 'png') {
              setImageName(e.target.files[0].name);
              return true;
            }

            alert("jpg , png 파일만 업로드할 수 있습니다..");
            return false;
      }

      const uploadImage = (e) => {
        if(checkValidImage(e)) {
            setProfileImage(e.target.files);
        }
      }

    const newRegis = () => {
        if (imageName == "프로필 이미지를 꼭 선택해주세요. ( 필수 ) ") {
            alert('프로필 이미지를 꼭 선택해주세요.');
            return;
        }
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
        const jsonData = {"id" : idInfo.id , "password" : idInfo.pw , "checkPassword" : idInfo.checkpw};
        let requestForm = new FormData();
        
        requestForm.append("data" , new Blob([JSON.stringify(jsonData)], { type: "application/json" }));
        requestForm.append('image', profileImage[0]);

        await axios({
            method : "POST" ,
            mode: "cors" , 
            url : `/registers` , 
            headers : {'Content-Type': 'multipart/form-data'} ,
            data : requestForm})
            .then((response) => { alert(response.data.message); window.location.replace('/login'); }) 
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
                <div className="registerArea">
                    <form>
                        <input type="text" className="idInput" placeholder="Username" name="id" onChange={setinfo} />
                        <input type={showpw} className="pwInput" placeholder="password" name="pw" onChange={setinfo} />
                        <input type={showpw} className="pwInput" placeholder="Confirm password" name="checkpw" onChange={setinfo} /><br /></form>
                    <label className="showPw">
                        <input type="checkbox" className="showPw" onClick={showPwBt} /> 비밀번호 표시
                    </label><br />
                    <span className="pwChecking">아이디는 4자 이상, 비밀번호는 6자 이상 입력해주세요.</span>
                    <button type="submit" onClick={() => imageInput.current.click()} className="sbButton">프로필 사진</button>
                    <input type="file" onChange={(e) => uploadImage(e)} name="file" ref={imageInput} style={{ display: "none" }}/>
                    <span className="imageName">{imageName}</span>
                    <button type="submit" className="sbButton" onClick={newRegis}>회원가입</button>
                </div>
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

export default Register;

