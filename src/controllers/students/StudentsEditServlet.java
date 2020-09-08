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
 * Servlet implementation class StudentsEditServlet
 */
@WebServlet("/students/edit")
public class StudentsEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentsEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      //データベースにアクセス
        EntityManager em = DBUtil.createEntityManager();

        //findメソッドを用いて該当IDのデータ一件をStudent型のインスタンスに格納
        Student s = em.find(Student.class, Integer.parseInt(request.getParameter("id")));

        em.close();

        request.setAttribute("student", s); //取得したデータ一件(Student型)をリクエストスコープに保存
        request.setAttribute("_token", request.getSession().getId()); //セッションIDをリクエストスコープに保存
        request.getSession().setAttribute("student_id", s.getId()); //IDをセッションスコープに保存

        //フォワード
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/students/edit.jsp");
        rd.forward(request, response);
    }

}
