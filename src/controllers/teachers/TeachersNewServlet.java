package controllers.teachers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Teacher;

/**
 * Servlet implementation class TeachersNewServlet
 */
@WebServlet("/teachers/new")
public class TeachersNewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TeachersNewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("_token", request.getSession().getId()); //セッションIDをリクエストスコープに保存
        request.setAttribute("teacher", new Teacher()); //Teacherのインスタンスを作成しリクエストスコープに保存

        //teachers/new.jspにフォワード
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/teachers/new.jsp");
        rd.forward(request, response);
    }

}
