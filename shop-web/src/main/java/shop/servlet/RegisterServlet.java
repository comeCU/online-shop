package shop.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import shop.model.UserInfo;
import shop.service.UserInfoService;
import shop.service.impl.UserInfoServiceImpl;

/**
 * 用户注册
 * 
 * @author dong
 * @date Aug 25, 2019 12:10:06 PM
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserInfoService userInfoService = null;

    public RegisterServlet() {
        super();
        userInfoService = new UserInfoServiceImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action"); // 这里需要处理异步发来的请求
        if (action == null) {
            // 注册
            register(request, response);
        } else {
            // 验证用户名是否存在
            check(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

    /**
     * 用户注册
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    protected void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        UserInfo userInfo = new UserInfo(username, password, email);

        boolean isAdd = userInfoService.addUser(userInfo);
        if (!isAdd) { // 注册失败
            response.setContentType("text/html; charset=utf-8");
            PrintWriter out = response.getWriter();
            out.println("<script type='text/javascript'>");
            out.println("alert(\"注册失败！请联系管理员咨询\")");// 反斜杠转义
            out.println("open(\"register.jsp\", \"_self\");");// 重新打开新的页面, _self在原窗口打开
            out.println("</script>");
            out.close();
        } else { // 注册成功
            response.sendRedirect("register_success.jsp");
        }
    }

    /**
     * 
     * @param request
     * @param response
     * @throws IOException
     */
    protected void check(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        boolean isExist = userInfoService.userExist(username);
        // 回写给到异步请求的结果，用于ajax校验用户名是否已被注册
        if (isExist) {
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("true");
            response.getWriter().close();
        } else {
            response.setCharacterEncoding("utf-8");
            response.getWriter().write("false");
            response.getWriter().close();
        }
    }
}
