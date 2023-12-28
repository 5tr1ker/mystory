import 'bootstrap/dist/css/bootstrap.min.css';
import '../../_style/admin/adminLogin.css';

const AdminLogin = () => {

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
                                <form action="#" className="login-form">
                                    <div className="form-group">
                                        <input type="text" className="form-control rounded-left" placeholder="Username" required />
                                    </div>
                                    <div className="form-group d-flex">
                                        <input type="password" className="form-control rounded-left" placeholder="Password" required />
                                    </div>
                                    <div className="form-group">
                                        <button type="submit" className="form-control btn btn-primary rounded submit px-3">Login</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    )
}

export default AdminLogin;