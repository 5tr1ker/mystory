/* eslint-disable react-hooks/exhaustive-deps */
import { useEffect, useState } from "react";
import Chart from 'chart.js';
import axios from "axios";

const MainChartList = ({chart}) => {
    if(chart === undefined || Array.isArray(chart.map)) {
        return null;
    }

    return (
        chart.map(list => (
            <tr key={list.visitedDate}>
                <td>{list.visitedDate}</td>
                <td>{list.visitorsCount}</td>
            </tr>
        ))
    );
}

const AdminMain = () => {

    const [chart, setChart] = useState();
    let chartLabel_date = [];
    let chartLabel_value = [];

    const setChartLabel = (chartData) => {
        for (let i = 0; i < 7 && chartData.length > i; i++) {
            chartLabel_date.push(chartData[i].visitedDate);
            chartLabel_value.push(chartData[i].visitorsCount);
        }
    }

    useEffect(async () => {
        let ctx = document.getElementById('myChart')

        await axios({
            method: "GET",
            url: `/admin/visitant/count`
        })
            .then((response) => {
                setChart(response.data);
                setChartLabel(response.data);
            })
            .catch((e) => alert(e.response.data.message));



        new Chart(ctx, {
            type: 'line',
            data: {
                labels: chartLabel_date,
                datasets: [{
                    data: chartLabel_value,
                    lineTension: 0,
                    backgroundColor: 'transparent',
                    borderColor: '#007bff',
                    borderWidth: 4,
                    pointBackgroundColor: '#007bff'
                }]
            },
            options: {
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: false
                        }
                    }]
                },
                legend: {
                    display: false
                }
            }
        })
    }, []);


    return (
        <main className="col-md-9 ms-sm-auto col-lg-10 px-md-4">
            <div className="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                <h1 className="h2">일주일 간 방문자 그래프</h1>
                <div className="btn-toolbar mb-2 mb-md-0">
                </div>
            </div>

            <canvas className="my-4 w-100" id="myChart" width="900" height="380"></canvas>

            <h2>방문자 기록</h2>
            <div className="table-responsive">
                <table className="table table-striped table-sm">
                    <thead>
                        <tr>
                            <th scope="col">날짜</th>
                            <th scope="col">방문자 수</th>
                        </tr>
                    </thead>
                    <tbody>
                        <MainChartList chart={chart}/>
                    </tbody>
                </table>
            </div>
        </main>
    )
}

export default AdminMain;
