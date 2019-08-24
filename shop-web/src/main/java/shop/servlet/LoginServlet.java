package shop.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import shop.service.UserInfoService;
import shop.service.impl.UserInfoServiceImpl;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    private UserInfoService userInfoService = null;
	
    public LoginServlet() {
        super();
        userInfoService = new UserInfoServiceImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String username = request.getParameter("username");//获取页面传过来的参数
        String password = request.getParameter("password");
        //登录操作
        boolean flag = userInfoService.checkLogin(username, password);
        
        if(flag) {
            /*request.getSession().setAttribute("loginuser", username);
            response.sendRedirect("SearchServlet");*/ //首页直接显示
            
            System.out.println("测试用户登录。。。");
        } else {//登录失败
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println("<script type='text/javascript'>");
            out.println("alert(\"登录失败！请重新登录\")");//反斜杠转义
            out.println("open(\"login.jsp\", \"_self\");");//重新打开新的页面, _self在原窗口打开
            out.println("</script>");
            out.close();
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
