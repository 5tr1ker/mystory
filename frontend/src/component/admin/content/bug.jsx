

const AdminBug = () => {

    return (
        <main className="col-md-9 ms-sm-auto col-lg-10 px-md-4">
            <div className="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 className="h2">버그 신고 관리</h1>
                <div className="btn-toolbar mb-2 mb-md-0">
                    <div className="btn-group me-2">
                        <button type="button" className="btn btn-sm btn-outline-secondary">Share</button>
                        <button type="button" className="btn btn-sm btn-outline-secondary">Export</button>
                    </div>
                    <button type="button" className="btn btn-sm btn-outline-secondary dropdown-toggle">
                        <span data-feather="calendar"></span>
                        This week
                    </button>
                </div>
            </div>

            <div className="table-responsive">
                <table className="table table-striped table-sm">
                    <thead>
                        <tr>
                            <th scope="col" width="5%">#</th>
                            <th scope="col" width="5%">보고자</th>
                            <th scope="col" width="5%">보고 시간</th>
                            <th scope="col" width="80%">보고 내용</th>
                            <th scope="col" width="5%">처리 상태</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>1,001</td>
                            <td>random</td>
                            <td>data</td>
                            <td>그것도 아주 아주 아주 많이 긴 보고 내용이 올라오면 어떻게 될까요오오오,그것도 아주 아주 아주 많이 긴 보고 내용이 올라오면 어떻게 될까요오오오,그것도 아주 아주 아주 많이 긴 보고 내용이 올라오면 어떻게 될까요오오오그것도 아주 아주 아주 많이 긴 보고 내용이 올라오면 어떻게 될까요오오오,그것도 아주 아주 아주 많이 긴 보고 내용이 올라오면 어떻게 될까요오오오,그것도 아주 아주 아주 많이 긴 보고 내용이 올라오면 어떻게 될까요오오오,그것도 아주 아주 아주 많이 긴 보고 내용이 올라오면 어떻게 될까요오오오</td>
                        </tr>
                        <tr>
                            <td>1,002</td>
                            <td>placeholder</td>
                            <td>irrelevant</td>
                            <td>visual</td>
                        </tr>
                        <tr>
                            <td>1,003</td>
                            <td>data</td>
                            <td>rich</td>
                            <td>dashboard</td>
                        </tr>
                        <tr>
                            <td>1,003</td>
                            <td>information</td>
                            <td>placeholder</td>
                            <td>illustrative</td>
                        </tr>
                        <tr>
                            <td>1,004</td>
                            <td>text</td>
                            <td>random</td>
                            <td>layout</td>
                        </tr>
                        <tr>
                            <td>1,005</td>
                            <td>dashboard</td>
                            <td>irrelevant</td>
                            <td>text</td>
                        </tr>
                        <tr>
                            <td>1,006</td>
                            <td>dashboard</td>
                            <td>illustrative</td>
                            <td>rich</td>
                        </tr>
                        <tr>
                            <td>1,007</td>
                            <td>placeholder</td>
                            <td>tabular</td>
                            <td>information</td>
                        </tr>
                        <tr>
                            <td>1,008</td>
                            <td>random</td>
                            <td>data</td>
                            <td>placeholder</td>
                        </tr>
                        <tr>
                            <td>1,009</td>
                            <td>placeholder</td>
                            <td>irrelevant</td>
                            <td>visual</td>
                        </tr>
                        <tr>
                            <td>1,010</td>
                            <td>data</td>
                            <td>rich</td>
                            <td>dashboard</td>
                        </tr>
                        <tr>
                            <td>1,011</td>
                            <td>information</td>
                            <td>placeholder</td>
                            <td>illustrative</td>
                        </tr>
                        <tr>
                            <td>1,012</td>
                            <td>text</td>
                            <td>placeholder</td>
                            <td>layout</td>
                        </tr>
                        <tr>
                            <td>1,013</td>
                            <td>dashboard</td>
                            <td>irrelevant</td>
                            <td>text</td>
                        </tr>
                        <tr>
                            <td>1,014</td>
                            <td>dashboard</td>
                            <td>illustrative</td>
                            <td>rich</td>
                        </tr>
                        <tr>
                            <td>1,015</td>
                            <td>random</td>
                            <td>tabular</td>
                            <td>information</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div className="pagenationPosition">
                <nav aria-label="Page navigation example">
                    <ul class="pagination">
                        <li class="page-item">
                            <a class="page-link" href="#" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                        <li class="page-item"><a class="page-link" href="#">1</a></li>
                        <li class="page-item"><a class="page-link" href="#">2</a></li>
                        <li class="page-item"><a class="page-link" href="#">3</a></li>
                        <li class="page-item">
                            <a class="page-link" href="#" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
        </main>
    )
}

export default AdminBug;