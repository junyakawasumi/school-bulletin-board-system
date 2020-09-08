package controllers.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TeachersLogoutServlet
 */
@WebServlet("/teacherslogout")
public class TeachersLogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TeachersLogoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //ログイン情報を削除
        request.getSession().removeAttribute("login_teacher");

        //フラッシュメッセージ
        request.getSession().setAttribute("flush", "ログアウトしました。");

        //リダイレクト
        response.sendRedirect(request.getContextPath() + "/login");
    }

}
