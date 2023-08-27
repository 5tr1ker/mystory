
import '../../_style/meeting/chat.css'
import '../../_style/meeting/chatData.css'
import line from '../../_image/line-3.svg'
import exit from '../../_image/exit.png'
import { Fragment } from 'react'

const Chat = ({ dropDownSet }) => {

    return (
        <div className="element-chat">
      <div className="div-chat">
        <div className="text-wrapper-4-chat">모임 채팅</div>
        <div className="chatContentArea">
            { true ?
            <Fragment>
            <div className="chatContent">
                <div className="ellipse-chat" />
                <div className="text-wrapper-chat">회기 꽃술 6인팟!!</div>
                <div className="text-wrapper-chat-last">마지막 대화 : 16시 20분</div>
            </div>
            <div className="chatContent">
                <div className="ellipse-chat" />
                <div className="text-wrapper-chat">회기 꽃술 6인팟!!</div>
                <div className="text-wrapper-chat-last">마지막 대화 : 16시 20분</div>
            </div>
            <div className="chatContent">
                <div className="ellipse-chat" />
                <div className="text-wrapper-chat">회기 꽃술 6인팟!!</div>
                <div className="text-wrapper-chat-last">마지막 대화 : 16시 20분</div>
            </div>
            <div className="chatContent">
                <div className="ellipse-chat" />
                <div className="text-wrapper-chat">회기 꽃술 6인팟!!</div>
                <div className="text-wrapper-chat-last">마지막 대화 : 16시 20분</div>
                </div>
            </Fragment> :
            <Fragment>
                <div className="element-chatData">
      <div className="div-chatData">
        <div className="new-chatData">
          <p className="p-chatData">
            <span className="span-chatData">콧물흘리는 맹구</span>
            <span className="text-wrapper-2-chatData"> 님이 모임채팅을 시작하였습니다.</span>
          </p>
        </div>
        <div className="overlap-group-chatData">
          <img
          className="mask-group-chatData"
          alt="Mask group"
          src="https://generation-sessions.s3.amazonaws.com/3d73c7ae9ccc9618cff009f94c868a41/img/mask-group@2x.png"
            />
          <div className="text-wrapper-7-chatData">와구와구 토끼</div>
          <span className="rectangle-chatData">
            안녕하세요안녕하세요안녕하세요안녕하세요
          </span>
          <div className="text-wrapper-4-chatData">오후 02:25</div>
        </div>
        <div className="overlap-group-chatData">
          <img
          className="mask-group-chatData"
          alt="Mask group"
          src="https://generation-sessions.s3.amazonaws.com/3d73c7ae9ccc9618cff009f94c868a41/img/mask-group@2x.png"
            />
          <div className="text-wrapper-7-chatData">와구와구 토끼</div>
          <span className="rectangle-chatData">
            야이 싀 ㅋㅋㅋ
          </span>
          <div className="text-wrapper-4-chatData">오후 02:25</div>
        </div>
        </div>
        
                    <div className="rectangle-3-chatData">
                        <input className="chattingInput"/>
                    </div>
                </div>
            </Fragment>
            }
        </div>
        <img className="light-s-chat" alt="Light s" src={exit} onClick={dropDownSet} />
        <img className="line-chat" alt="Line" src={line} />
      </div>
    </div>
    )

}

export default Chat;