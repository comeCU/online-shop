package shop.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import shop.model.Book;
import shop.model.PageBean;
import shop.service.BookService;
import shop.service.impl.BookServiceImpl;

/**
 * 分页展示所有图书
 * @author dong
 * @date Aug 25, 2019 7:05:06 PM
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private BookService bookService = null;
	
    public SearchServlet() {
        super();
        bookService = new BookServiceImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		findAll(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
         *1.获取页面传递的pc
         * 2.给定pr的值
         * 3.使用pc和pr调用service方法，得到pageBean，保存到request域
         * 4.转发到list.jsp
         */
        /*
         * 1.得到pc
         *   如果pc参数不存在，说明pc＝1
         *   如果pc参数存在，需要转换成int类型
         */
         int pc = getPc(request);
         int pr = 3;//给定pr的值，每页10行纪录
         
         System.out.println("pc:" + pc);
         System.out.println("pr:" + pr);
         PageBean<Book> pb = bookService.findAll(pc, pr);
         pb.setUrl(getUrl(request));
         request.setAttribute("pb", pb);
         request.getRequestDispatcher("/main.jsp").forward(request, response);
    }

	private int getPc(HttpServletRequest request) {
        String value = request.getParameter("pc");
        if (value == null || value.trim().isEmpty()) {
            return 1;
        }
        return Integer.parseInt(value);
    }
    
    private String getUrl(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();
        String queryString = request.getQueryString();
        
        System.out.println(contextPath + ":" + servletPath + ":" + queryString);
        if (queryString.contains("&pc=")) {
            int index = queryString.lastIndexOf("&pc=");
            queryString = queryString.substring(0, index);
        }
        
        System.out.println("路径：" + contextPath + servletPath + "?" + queryString);
        return contextPath + servletPath + "?" + queryString;
    }

}
