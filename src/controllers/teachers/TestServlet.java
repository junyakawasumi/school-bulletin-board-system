package controllers.teachers;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Teacher;
import utils.DBUtil;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/teachers/test")
public class TestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        List<Teacher> teachers = em.createNamedQuery("test", Teacher.class)
                .setParameter("admin_flag", 1)
                .setParameter("delete_flag", 0)
                .getResultList();

        em.close();

        request.setAttribute("teachers", teachers);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/teachers/test.jsp");
        rd.forward(request, response);
    }

}
