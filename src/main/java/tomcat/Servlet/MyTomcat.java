package tomcat.Servlet;

import Servlet.MyHttpServlet;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 第二代系统
 */
public class MyTomcat{
    //创建两个map，来存放对应的servletClass 和servletUrl

    //这个用来存放class类 , 用MyHttpServlet来接受其他Servlet (面向对象思想)
    public static final ConcurrentHashMap<String, MyHttpServlet> servletMapping = new ConcurrentHashMap<>();
    //这个用来存放对应的Servlet地址
    public static final ConcurrentHashMap<String, String> servletUrlMapping = new ConcurrentHashMap<>();
    public static void main(String[] args) throws IOException {
        new MyTomcat().init();
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("服务端等待中");
        while (!serverSocket.isClosed()){
            Socket socket = serverSocket.accept();
            socketThread socketThread = new socketThread(socket);
            //因为是实现Runnable接口，所以启动线程时，要借助Thread类
            new Thread(socketThread).start();
        }
    }

    //对配置文件里的Servlet进行初始化
    public static void init(){
        //同样先死后活
        String path = MyHttpServlet.class.getResource("/").getPath();
        System.out.println(path);
        SAXReader saxReader = new SAXReader();
        try {
            Document read = saxReader.read(new File(path+"web.xml"));
            //读取Xml里面的文件
            Element rootElement = read.getRootElement();

            List<Element> servletMappings = rootElement.elements("servlet-mapping");
            List<Element> servlets = rootElement.elements("servlet");
            Element className ;
            Element urlPattern;
            for (Element servlet : servlets) {
                className = servlet.element("servlet-name");
                Element servletClass = servlet.element("servlet-class");
                if (servletMapping.contains(className.getText())){
                    System.out.println("类已经存在");
                }else {
                    Class<?> myHttpServlet = Class.forName(servletClass.getText());
                    Object o = myHttpServlet.newInstance();

                    MyHttpServlet httpServlet = (MyHttpServlet) o;
                    servletMapping.put(className.getText(),httpServlet);
                }
            }

            //mapping的作用是，检测浏览器输入的servlet类是否存在。并用来查找对应的servlet类
            for (Element mapping : servletMappings) {
                className = mapping.element("servlet-name");
                urlPattern = mapping.element("url-pattern");
                //将读取出来的元素放在集合中
                if (servletUrlMapping.contains(className.getText())){
                    System.out.println("类已经存在");
                }else {
                    servletUrlMapping.put(urlPattern.getText(), className.getText());
                    //把配置的类通过反射出来
                }
            }
//            for (Map.Entry<String, MyHttpServlet> stringMyHttpServletEntry : servletMapping.entrySet()) {
//                System.out.println(stringMyHttpServletEntry.getKey()+"======"+stringMyHttpServletEntry.getValue());
//            }
        } catch (DocumentException | InstantiationException |
                MalformedURLException | ClassNotFoundException
                | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
