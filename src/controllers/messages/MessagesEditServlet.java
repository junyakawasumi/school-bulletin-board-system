package controllers.messages;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Message;
import models.Teacher;
import utils.DBUtil;

/**
 * Servlet implementation class MessagesEditServlet
 */
@WebServlet("/messages/edit")
public class MessagesEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessagesEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        //該当するIDのデータ一件を取得
        Message m = em.find(Message.class, Integer.parseInt(request.getParameter("id")));

        em.close();

        //セッションスコープからログインユーザー情報を取得
        Teacher login_teacher = (Teacher)request.getSession().getAttribute("login_teacher");

        //メッセージ情報がnullではなく、ログインユーザーのIDとメッセージのユーザーIDが一致した場合の処理
        //一致すればメッセージの編集が可能
        if(m != null && login_teacher.getId() == m.getTeacher().getId()) {
            request.setAttribute("message", m);
            request.setAttribute("_token", request.getSession().getId());
            request.getSession().setAttribute("message_id", m.getId());
        }

        //フォワード
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/messages/edit.jsp");
        rd.forward(request, response);
    }

}
