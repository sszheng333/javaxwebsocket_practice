package com.comicyu.websocket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
/**
 *
 * 被监听对象(事件源)TestWebsocket
 *
 * 事件源要提供方法注册监听器(即在事件源上关联监听器对象)
 */
@ServerEndpoint(value = "/testwebsocket", configurator = SessionConfigurator.class)
public class TestWebsocket {
    /**
     * 存储所有在线的用户（观察者模式）
     *concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
     */
    private static Map<String, Session> sessions = new ConcurrentHashMap<String, Session>();

    //在成员变量定义一个监听器对象
    private static RequestListener requestListener ;
    
   
    
  //注册监听器，该类没有监听器对象啊，那么就传递进来吧。
    public void registerLister(RequestListener requestListener) {
        this.requestListener = requestListener;
    }
    

    /**
     * 当用户连接到websocket，将该用户进行记录 Title: onopen author:liuxuli Description: @param
     * session @param config
     */
    @OnOpen
    public static void onOpen(Session session, EndpointConfig config) {
        HttpSession httpsession = (HttpSession) session.getUserProperties().get(HttpSession.class.getName());
        System.out.println("连接成功");
        sessions.put(httpsession.getId(), session);
    }

    /**
     * 当用户退出连接后，将该用户进行删除 Title: onClose author:liuxuli Description: @param
     * session
     */
    @OnClose
    public static  void onClose(Session session) {
        HttpSession httpsession = (HttpSession) session.getUserProperties().get(HttpSession.class.getName());
        System.out.println("连接关闭");
        sessions.remove(httpsession.getId());
    }

    /**
     * 发生错误时触发的方法
     */
    @OnError
    public  static void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    /**
     * 接收到客户端消息时触发的方法
     */
    @OnMessage
    public static void onMessage(String params, Session session) throws Exception {
        // 获取服务端到客户端的通道
        HttpSession httpsession = (HttpSession) session.getUserProperties().get(HttpSession.class.getName());
        System.out.println("收到来自" + session.getId() + "的消息" + params);
        String result = "收到来自" + session.getId() + "的消息" + params;
        // 返回消息给Web Socket客户端（浏览器）
        sendMessage(httpsession, params);
    }

    /**
     * 向指定的用户发送消息 Title: sendMessage author:liuxuli Description: @param
     * httpsession 必须使用这个参数，如果不使用此参数来区分接收的用户，既浪费服务器资源，还能使所有的用户都能收到消息 @param text
     * 发送的消息
     */
    public static  void sendMessage(HttpSession httpsession, String text)  {
        String id = httpsession.getId();
        Session session = sessions.get(httpsession.getId());
        if (text != null && text.length() > 0) {
            //大部分情况下，推荐使用getAsyncRemote();getAsyncRemote是非阻塞式的，getBasicRemote是阻塞式的
           session.getAsyncRemote().sendText(text);
        }
      

    }
}
