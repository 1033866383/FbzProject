package script;


import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class TomcatRun {
    public static void main(String[] args) throws LifecycleException, ServletException {
        String userDir = System.getProperty("user.dir"); // 项目目录
        String tomcatBaseDir = userDir + File.separatorChar + "/src/main/java/tomcat";
        String webappDir = userDir + File.separatorChar + "target" + File.separatorChar + "classes";

        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(tomcatBaseDir);

        Connector connector = new Connector();
        connector.setPort(8081); // 端口号
        connector.setURIEncoding("UTF-8");
        tomcat.getService().addConnector(connector);

        tomcat.addWebapp("/", webappDir);

        tomcat.start();
        tomcat.getServer().await();
    }
}

class RunServletTomcat extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getOutputStream().write("Hello".getBytes());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        List ls;
//        ls.stream().forEach();
    }
}