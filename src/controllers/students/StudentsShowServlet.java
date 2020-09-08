package controllers.students;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Student;
import utils.DBUtil;

/**
 * Servlet implementation class StudentsShowServlet
 */
@WebServlet("/students/show")
public class StudentsShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentsShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //データベースにアクセス
        EntityManager em = DBUtil.createEntityManager();

        //findメソッドで該当IDのデータ一件をTeacherのインスタンスに格納
        Student s = em.find(Student.class, Integer.parseInt(request.getParameter("id")));

        em.close();

        //リクエストスコープに取得したデータ一件を保存
        request.setAttribute("student", s);

        //フォワード
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/students/show.jsp");
        rd.forward(request, response);
    }

}
