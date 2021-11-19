/**
 * 
 */
package com.comicyu.websocket;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

/**   
* 在websocket中获取httpsession对象
* 类名称：SessionConfigurator   
*/
public class SessionConfigurator extends Configurator{
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        //获取到客户端的session
        HttpSession session = (HttpSession) request.getHttpSession();
        //将session当如到websocket的用户属性中
        sec.getUserProperties().put(HttpSession.class.getName(), session);
        super.modifyHandshake(sec, request, response);
    }
}
