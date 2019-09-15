package com.citi;

import quickfix.*;
import quickfix.field.MsgType;

public class FixServerApplication extends MessageCracker implements Application {
    @Override
    protected void onMessage(Message message, SessionID sessionID) {
        try {
            String msgType = message.getHeader().getString(35);
            Session session = Session.lookupSession(sessionID);
            switch (msgType) {
                case MsgType.LOGON: // 登陆
                    session.logon();
                    session.sentLogon();
                    break;
                case MsgType.HEARTBEAT: // 心跳
                    session.generateHeartbeat();
                    break;
            }

        } catch (FieldNotFound e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onCreate(SessionID sessionId) {
        System.out.println(" 服务器启动时候调用此方法创建");

    }

    @Override
    public void onLogon(SessionID sessionId) {
        System.out.println("客户端登陆成功时候调用此方法");

    }

    @Override
    public void onLogout(SessionID sessionId) {
        System.out.println("客户端断开连接时候调用此方法");

    }

    @Override
    public void toAdmin(Message message, SessionID sessionId) {
        System.out.println("发送会话消息时候调用此方法");

    }

    @Override
    public void toApp(Message message, SessionID sessionId) throws DoNotSend {
        System.out.println("发送业务消息时候调用此方法");

    }

    @Override
    public void fromAdmin(Message message, SessionID sessionId)
            throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
        System.out.println("接收会话类型消息时调用此方法");
        try {
            crack(message, sessionId);
        } catch (UnsupportedMessageType | FieldNotFound | IncorrectTagValue e) {
            e.printStackTrace();
        }

    }

    @Override
    public void fromApp(Message message, SessionID sessionId)
            throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        System.out.println("接收业务消息时调用此方法");
        crack(message, sessionId);

    }

}
