package boot.jungwoo.tomcat;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@SpringBootApplication
public class TomcatApplication {

    public static void main(String[] args) throws LifecycleException {
// 굳이 안따라해도됨. 어차피 이렇게 톰캣을 만들어서 할 일이 없다.
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        Context context = tomcat.addContext("/", "/");

        //서블릿 만들기
        HttpServlet servlet = new HttpServlet() {
            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                PrintWriter writer = resp.getWriter();
                writer.println("<html><head><title>");
                writer.println("hey, Tomcat");
                writer.println("</title></head>");
                writer.println("<body><h1>Hello Tomcat</h1></body>");
                writer.print("</html>");
            }
        };

        String ServletName = "HelloServlet";
        tomcat.addServlet("/", ServletName, servlet);
        context.addServletMappingDecoded("/hello", ServletName);// /hello가 들어오면 ServletName을 보여

        tomcat.start();

    }


}
