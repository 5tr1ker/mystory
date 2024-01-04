import 'bootstrap/dist/css/bootstrap.min.css';
import '../../_style/admin/adminLogin.css';
import { useEffect, useState } from 'react';
import axios from 'axios';

const AdminLogin = () => {
    const [idInfo, setInfo] = useState({
        id: '',
        password: ''
    });

    const changeIdPw = (e) => {
        const { name, value } = e.target;
        setInfo({ ...idInfo, [name]: value });
    }

    const signUp = async () => {
        await axios({
            method: "POST",
            url: `/admin/login`,
            headers: { "Content-Type": "application/json" },
            data: JSON.stringify({ "id": idInfo.id, "password": idInfo.password })
        })
            .then(async (response) => {
                localStorage.setItem("adminId", response.data.id);
                window.location.reload("/admin");
            })
            .catch((e) => alert(e.response.data.message));
    }

    useEffect(() => {
        let sessionAdminId = localStorage.getItem("adminId");
        if (sessionAdminId !== null) {
            window.location.replace("/admin");
        }
    }, []);


    return (
        <div className="bodyData">
            <section className="ftco-section">
                <div className="container">
                    <div className="row justify-content-center">
                        <div className="col-md-6 text-center mb-5">
                            <h2 className="heading-section">Admin Login</h2>
                        </div>
                    </div>
                    <div className="row justify-content-center">
                        <div className="col-md-7 col-lg-5">
                            <div className="login-wrap p-4 p-md-5">
                                <div className="icon d-flex align-items-center justify-content-center">
                                    <span className="fa fa-user-o"></span>
                                </div>
                                <h3 className="text-center mb-4">Sign In</h3>
                                <div className="form-group">
                                    <input type="text" className="form-control rounded-left" placeholder="Username" required name="id" onChange={changeIdPw} />
                                </div>
                                <div className="form-group d-flex">
                                    <input type="password" className="form-control rounded-left" placeholder="Password" required name="password" onChange={changeIdPw} />
                                </div>
                                <div className="form-group">
                                    <button className="form-control btn btn-primary rounded submit px-3" onClick={signUp}>Login</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    )
}

export default AdminLogin;