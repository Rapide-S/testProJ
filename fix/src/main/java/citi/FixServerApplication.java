package citi;

import quickfix.*;
import quickfix.field.MsgType;

public class FixServerApplication extends MessageCracker implements Application {
    @Override
    protected void onMessage(Message message, SessionID sessionID) {
        try {
            //每次接受会话类型，业务会话时都会调用此方法
            System.out.println("onMessage======");
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
        System.out.println("onCreate 服务器启动时候调用此方法创建");

    }

    @Override
    public void onLogon(SessionID sessionId) {
        System.out.println("onLogon客户端登陆成功时候调用此方法");

    }

    @Override
    public void onLogout(SessionID sessionId) {
        System.out.println("onLogout客户端断开连接时候调用此方法");

    }

    @Override
    public void toAdmin(Message message, SessionID sessionId) {
        System.out.println("toAdmin发送会话消息时候调用此方法");

    }

    @Override
    public void toApp(Message message, SessionID sessionId) throws DoNotSend {
        System.out.println("toApp发送业务消息时候调用此方法");

    }

    @Override
    public void fromAdmin(Message message, SessionID sessionId)
            throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
        System.out.println("fromAdmin接收会话类型消息时调用此方法");
        try {
            crack(message, sessionId);
        } catch (UnsupportedMessageType | FieldNotFound | IncorrectTagValue e) {
            e.printStackTrace();
        }

    }

    @Override
    public void fromApp(Message message, SessionID sessionId)
            throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        System.out.println("fromApp接收业务消息时调用此方法");
        System.out.println("fromApp:"+message);
        crack(message, sessionId);

    }

}
