package Servlet;

import Http.MyHttpRequest;
import Http.MyHttpResponse;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

/**
 * 抽象模板类
 */
public abstract class MyHttpServlet implements Myservlet {
    //对配置文件里的Servlet进行初始化
    //对传入的参数进行判断
    public void service(MyHttpRequest myHttpRequest, MyHttpResponse myHttpResponse){
        if ("get".equalsIgnoreCase(myHttpRequest.getMethod())){
            //方便后面转型
            this.doGet(myHttpRequest, myHttpResponse);
        }else if("post".equalsIgnoreCase(myHttpRequest.getMethod())){
            this.doPost(myHttpRequest, myHttpResponse);
        }
    }
    //抽象模板设计模式，让业务层来实现
    public abstract void doGet(MyHttpRequest myHttpRequest, MyHttpResponse myHttpResponse);
    public abstract void doPost(MyHttpRequest myHttpRequest, MyHttpResponse myHttpResponse);
}
