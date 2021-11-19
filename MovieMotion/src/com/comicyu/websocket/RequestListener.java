/**
 * 
 */
package com.comicyu.websocket;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

/**   
* 事件监听器
* 由于HTTP协议与websocket协议的不同，导致没法直接从websocket中获取协议，然后在3.1中我们已经
* 写了获取HttpSession的代码，但是如果真的放出去执行，那么会报空指值异常，因为这个HttpSession并没有设置进去。 
* 设置HttpSession。这时候我们需要写一个监听器
* @version        
*/
@WebListener
@Component
public class RequestListener implements ServletRequestListener {
 
    public void requestInitialized(ServletRequestEvent sre)  {
        //将所有request请求都携带上httpSession
        ((HttpServletRequest) sre.getServletRequest()).getSession();
 
    }
    public RequestListener() {
    }
 
    public void requestDestroyed(ServletRequestEvent arg0)  {
    }
}