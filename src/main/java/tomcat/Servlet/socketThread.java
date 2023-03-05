package tomcat.Servlet;

import Http.MyHttpRequest;
import Http.MyHttpResponse;
import Servlet.MyHttpServlet;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 通信线程类，当发出请求时就创建一个线程
 */
public class socketThread implements Runnable{
    Socket socket;

    public socketThread(Socket socket) {
        this.socket = socket;
    }

    //当请求发过来时，启动一个线程，由线程读取请求信息并启动io流
    @Override
    public void run(){
        try {
            MyHttpRequest myHttpRequest = new MyHttpRequest(socket.getInputStream());
            //返回数据
            MyHttpResponse myHttpResponse = new MyHttpResponse(socket.getOutputStream());

            //先写死掉用myCalServlet,之后使用XML配置文件来调用指定的Servlet
//            MyCalServlet myCalServlet = new MyCalServlet();
//            myCalServlet.doGet(myHttpRequest,myHttpResponse);
// 获取浏览器输入的servlet，进行判断看是否有这个类
            String uri = myHttpRequest.getUri();
            //在之前创建的容器中取出对应是servlet类
            if(MyTomcat.servletUrlMapping.get(uri)!=null){
                String servletName = MyTomcat.servletUrlMapping.get(uri);
                MyHttpServlet httpServlet = MyTomcat.servletMapping.get(servletName);
//                httpServlet.doGet(myHttpRequest,myHttpResponse);
                //应该调用的是service方法,让他判断是doget还是都post。
                //每一层做每一层的任务，体现分层思想
                httpServlet.service(myHttpRequest,myHttpResponse);
            }else {
                //返回时要加响应头不然不能正常显示
                OutputStream outputStream = myHttpResponse.getOutputStream();
                String resp = MyHttpResponse.respHeader +"<h1>404NotFind</h1>";
                outputStream.write(resp.getBytes());
                outputStream.flush();
                outputStream.close();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
