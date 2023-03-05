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
 * 先规定要几个基本方法
 */
public interface Myservlet {
    //对配置文件里的Servlet进行初始化
    public void init();
    public void service(MyHttpRequest myHttpRequest, MyHttpResponse myHttpResponse);

    public void destroy();
}
