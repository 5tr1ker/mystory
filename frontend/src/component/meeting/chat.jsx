/* eslint-disable react-hooks/exhaustive-deps */
/* eslint-disable react/jsx-no-undef */
import '../../_style/meeting/chat.css'
import '../../_style/meeting/chatData.css'
import line from '../../_image/line-3.svg'
import exit from '../../_image/exit.png'
import defaultALT from '../../_image/defaultALT.png'
import React, { Fragment, useEffect, useRef, useState } from 'react'
import axios from "axios";

const Chat = ({ dropDownSet }) => {
  const [chatRoom, setChatRoom] = useState([]); // 방 번호
  const [meetingId, setMeetingId] = useState(0); // 채팅방이 속한 채팅 방 번호
  const [toogle, setToogle] = useState(true);
  const [chatData, setChatData] = useState([]); // 전송된 데이터 목록
  const [userInfo, setUserInfo] = useState({ userKey: 0, userId: "", profileImage: "" });

  // Chat Scroll
  const messagesEndRef = useRef(null);

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" })
  }

  useEffect(() => {
    scrollToBottom();
  }, [chatData]);

  // Chatting 관련 끝
  const webSocketUrl = `wss://server-springBoot:8080/chat`;
  let ws = useRef(null);

  const [socketConnected, setSocketConnected] = useState(false);
  // Chatting 관련 종료

  const [inputData, setInputData] = useState(""); // 입력 데이터
  const onChangeInputData = (e) => {
    setInputData(e.target.value);
  }
  const onPushEnter = (e) => {
    if (e.key == 'Enter') {
      if(inputData == "") {
        alert("메세지를 입력해 주세요.");

        return;
      }
      if (socketConnected) {
        ws.current.send(
          JSON.stringify({
            sender: userInfo.userId,
            message: inputData,
            meetingId: meetingId,
            senderImage: userInfo.profileImage,
            messageType: "SEND"
          })
        );
        setInputData("");
      }
    }
  }

  useEffect(() => {
    if (toogle == false) {
      // Socket
      if (!ws.current) {
        ws.current = new WebSocket(webSocketUrl);
        ws.current.onopen = () => {
          console.log("connected to " + webSocketUrl);
          setSocketConnected(true);
        };
        ws.current.onclose = (error) => {
          console.log("disconnect from " + webSocketUrl);
          console.log(error);
        };
        ws.current.onerror = (error) => {
          console.log("connection error " + webSocketUrl);
          console.log(error);
        };
        ws.current.onmessage = (evt) => {
          const data = JSON.parse(evt.data);
          setChatData((prevItems) => [...prevItems, data]);
        };
      }

      return () => {
        console.log("clean up");
        ws.current.close();
      };
    }
  }, [toogle]);

  useEffect(() => {
    if (socketConnected) {
      ws.current.send(
        JSON.stringify({
          sender: userInfo.userId,
          message: "",
          meetingId: meetingId,
          senderImage: "",
          messageType: "ENTER"
        })
      );
    }
  }, [socketConnected]);


  useEffect(async () => {
    await axios({
      method: "GET",
      mode: "cors",
      url: `/chat/participants`
    }).then((response) => {
      setChatRoom(response.data);
    }).catch((err) => {
      console.log(err.response.data)
    });

    await axios({ // 유저의 개인 정보
      method: "GET",
      mode: "cors",
      url: `/users`
    }).then((response) => {
      setUserInfo({ userKey: response.data.data.userKey, userId: response.data.data.id, profileImage: response.data.data.profileImage });
    }).catch((err) => {
      console.log(err.response.data)
    });
  }, []);

  const getChatData = async (roomId, meetingId) => {
    setMeetingId(meetingId);
    setToogle(false);
    await axios({ // 데이터 가져오기
      method: "GET",
      mode: "cors",
      url: `/chat/${meetingId}`
    }).then((response) => {
      setChatData(response.data);
    }).catch((err) => {
      console.log(err.response.data)
    });
  }

  const ChatRoomContent = ({ room }) => {
    return (
      room.map(data => (
        <div className="chatContent" key={data.chatId} onClick={() => getChatData(data.chatId, data.meetingId)}>
          <img className="ellipse-chat" alt="img" src={data.meetingImage} />
          <div className="text-wrapper-chat">{data.meetingTitle}</div>
          <div className="text-wrapper-chat-last">모임 개설일 : {data.createDate}</div>
        </div>
      ))
    )
  }

  const ChatDataContent = ({ data }) => {
    return (
      data.map(detail => (
        <div className="overlap-group-chatData" key={detail.chatId == 0 ? Math.floor(Math.random() * 10_000_000_001) : detail.chatId}>
          <img
            className="mask-group-chatData"
            alt="img"
            src={detail.senderImage || defaultALT}
          />
          <div className="text-wrapper-7-chatData">{detail.sender}</div>
          <span className="rectangle-chatData">
            {detail.message}
          </span>
          <div className="text-wrapper-4-chatData">{detail.sendTime}</div>
          <div ref={messagesEndRef} />
        </div>
      ))
    )
  }

  return (
    <div className="element-chat">
      <div className="div-chat">
        <div className="text-wrapper-4-chat">모임 채팅</div>
        <div className="chatContentArea">
          {toogle ?
            <Fragment>
              <ChatRoomContent room={chatRoom} />
            </Fragment> :
            <Fragment>
              <div className="element-chatData">
                <div className="div-chatData">
                  <ChatDataContent data={chatData}/>
                </div>
                <div className="rectangle-3-chatData">
                  <input className="chattingInput" onChange={onChangeInputData} onKeyDown={onPushEnter} value={inputData} />
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