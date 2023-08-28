import '../../_style/todo.css'
import check from '../../_image/check.svg';
import addpen from '../../_image/addpen.png';
import line from '../../_image/line-3.svg';
import exit from '../../_image/exit.png';

const Todo = ({dropDownSet}) => {

    return (
        <div className="screen-todo">
          <img src={exit} className='todoList-exit' alt="exit" onClick={dropDownSet}/>
      <div className="div-todo">
        <div className="overlap-group-todo">
          <div className="text-wrapper-12-todo">TO DO LIST</div>
          <div className="text-wrapper-13-todo">TO DO LIST</div>
        </div>
        <div className="overlap-todo">
            <div className="overlap-2-todo">
                <div className="text-wrapper-19-todo">진행중</div>
                <div className="text-wrapper-20-todo">전체</div>
                <div className="text-wrapper-21-todo">진행완료</div>
                <img
                className="line-todo"
                alt="Line"
                src={line}
                />
            </div>
            {/* 진행 중 */}
            <div className="todoArea">
            <div className='processTodo'>
                <div className="ellipse-3-todo" />
                <div className="group-2-todo">
                    <img className="ellipse-todo" src={exit} alt="delete"/>
                </div>
                <span className="text-wrapper-14-todo">대학도서관 운영론</span>
            </div>

            { /* 완료 된 */}
            <div className='processTodo'>
                <div className="ellipse-3-complete-todo" />
                <img
                    className="vector-todo"
                    alt="Vector"
                    src={check}
                />
                <div className="group-2-todo">
                    <img className="ellipse-todo" src={exit} alt="delete"/>
                </div>
                <span className="text-wrapper-14-todo">대학도서관 운영론대충 공부 블라블라~ㅇ22ㅇ2ㅇ2ㅇ</span>
            </div>
            </div>
          <div className="group-wrapper-todo">
            <img
              className="img-todo"
              alt="Group"
              src={addpen}
            />
          </div>
          <input className="rectangle-todo" />
        </div>
      </div>
    </div>
    )
}

export default Todo;