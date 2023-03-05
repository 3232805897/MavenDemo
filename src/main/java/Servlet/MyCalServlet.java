package Servlet;

import Http.MyHttpRequest;
import Http.MyHttpResponse;
import untils.WebUntils;

import java.io.OutputStream;

/**
 * 业务层
 */
public class MyCalServlet extends MyHttpServlet{
    @Override
    public void doGet(MyHttpRequest myHttpRequest, MyHttpResponse myHttpResponse) {
        doPost(myHttpRequest,myHttpResponse);
    }

    @Override
    public void doPost(MyHttpRequest myHttpRequest, MyHttpResponse myHttpResponse) {
        //先读取浏览器输入的参数
        String n1 = myHttpRequest.getParameter("n1");
        String n2 = myHttpRequest.getParameter("n2");
        String resp = myHttpResponse.respHeader+
                "<h1>服务端返回数据="+n1+"+"+n2+"= "+ WebUntils.change(n1,n2) +"</h1>";
        OutputStream outputStream = myHttpResponse.getOutputStream();
        try{
            outputStream.write(resp.getBytes());
            //对数据进行关流
            outputStream.flush();
            outputStream.close();
        }catch (Exception e){
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void destroy() {

    }
}
