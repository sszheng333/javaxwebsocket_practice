/**
 * 
 */
package com.comicyu.controller;



import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.comicyu.websocket.SessionConfigurator;
import com.comicyu.websocket.TestWebsocket;

/**
 * 
 * 项目名称：UploadVideoTransfer 类名称：ActionTransferController 类描述： 动作迁移类 创建人：zhengss
 * 创建时间：2021年6月11日 上午9:41:25
 * 
 * @version
 */
@Controller
@RequestMapping(value = "/motion")
public class MotionTransferController {

    
    /**
     * 任务执行方法
    *Title: execute
    *author:liuxuli
    *Description: 
    　 * @param request
    　 * @return
   */

    @RequestMapping(value = "/execute")
    @ResponseBody
    public String execute(HttpServletRequest request) {
        for (int i = 0; i <= 100; i++) {
            //for循环只是个例子，在实际业务中百分比要进行实际的计算，这里的i就当是百分比
            //推送给用户
            TestWebsocket.sendMessage(request.getSession(), String.valueOf(i));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("执行完成...");
        return "success";
    }  

}
