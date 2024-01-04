import { useEffect, useState } from 'react';
import '../../_style/admin/admin.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import AdminMain from './content/main';
import AdminBug from './content/bug';
import ContentReport from './content/contentReport';
import PeopleEdit from './content/people';
import axios from 'axios';

const AdminPage = () => {
    let sessionAdminId = localStorage.getItem("adminId");
    if (sessionAdminId === null) {
        window.location.replace("/admin/login");
    }

    const [component, setComponent] = useState(<AdminMain />);
    const componentArr = [<AdminMain />, < AdminBug />, < ContentReport />, <PeopleEdit />];
    const [render, setRender] = useState(false);
    
    const modifyComponent = (index) => {
        setComponent(componentArr[index]);
    }

    const updateRender = () => {
        setRender(render ? false : true);
    }

    const logout = async () => {
        sessionAdminId = null;
        localStorage.removeItem("adminId");

        await axios({
            method: "POST",
            url: `/user/logout`
        })
            .then(() => {
                updateRender();
                window.location.reload("/admin/login"); 
            })
            .catch((e) => alert(e.response.data.message));
    }

    useEffect(() => {
        // Feather Icons 스크립트를 동적으로 로드합니다.
        const featherScript = document.createElement('script');
        featherScript.src = 'https://cdn.jsdelivr.net/npm/feather-icons@4.28.0/dist/feather.min.js';
        featherScript.integrity = 'sha384-uO3SXW5IuS1ZpFPKugNNWqTZRRglnUJK6UAZ/gxOX80nxEkN9NcGZTftn6RzhGWE';
        featherScript.crossOrigin = 'anonymous';
        featherScript.async = true;

        featherScript.onload = () => {
            // Feather Icons를 초기화합니다.
            window.feather.replace();
        };

        document.body.appendChild(featherScript);

        return () => {
            // 컴포넌트가 언마운트되면 스크립트를 제거합니다.
            document.body.removeChild(featherScript);
        };
    }, []);

    return (
        <div className="adminBody">
            <header className="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0 shadow">
                <a className="navbar-brand col-md-3 col-lg-2 me-0 px-3">admin page</a>
                <button className="navbar-toggler position-absolute d-md-none collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#sidebarMenu" aria-controls="sidebarMenu" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="navbar-nav">
                    <div className="nav-item text-nowrap">
                        <a className="nav-link px-3" onClick={logout}>Sign out</a>
                    </div>
                </div>
            </header>
            <div className="container-fluid">
                <div className="row">
                    <nav id="sidebarMenu" className="col-md-3 col-lg-2 d-md-block bg-light sidebar collapse">
                        <div className="position-sticky pt-3">
                            <ul className="nav flex-column">
                                <h6 className="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
                                    <span>Admin Section</span>
                                </h6>
                                <li className="nav-item">
                                    <a className="nav-link active" aria-current="page" onClick={() => modifyComponent(0)}>
                                        <span data-feather="home"></span>
                                        메인 화면
                                    </a>
                                </li>
                                <li className="nav-item">
                                    <a className="nav-link" onClick={() => modifyComponent(1)}>
                                        <span data-feather="file"></span>
                                        버그 신고 관리
                                    </a>
                                </li>
                                <li className="nav-item">
                                    <a className="nav-link" onClick={() => modifyComponent(2)}>
                                        <span data-feather="file"></span>
                                        컨텐츠 신고 관리
                                    </a>
                                </li>
                                <li className="nav-item">
                                    <a className="nav-link" onClick={() => modifyComponent(3)}>
                                        <span data-feather="users"></span>
                                        사용자 관리
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </nav>
                    {component}
                </div>
            </div>
        </div>
    )
};

export default AdminPage;