package controllers.messages;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Message;
import models.Teacher;
import utils.DBUtil;

/**
 * Servlet implementation class MessagesShowServlet
 */
@WebServlet("/messages/show")
public class MessagesShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessagesShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        //該当のIDのデータ一件を取得
        Message m = em.find(Message.class, Integer.parseInt(request.getParameter("id")));

        em.close();

        //該当データとセッションIDをリクエストスコープに保存
        request.setAttribute("message", m);
        request.setAttribute("_token", request.getSession().getId());

        // セッションスコープに保存されたログインユーザ情報を取得
        HttpSession session = ((HttpServletRequest)request).getSession();
        Teacher t = (Teacher)session.getAttribute("login_teacher");

        if(t != null) {
          //教職員用show.jspへフォワード
          RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/messages/show.jsp");
          rd.forward(request, response);
        } else {
          //生徒用show.jspへフォワード
          RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/messages/showstudents.jsp");
          rd.forward(request, response);
        }
    }

}
