package controllers.toppage;

import java.io.IOException;
import java.util.List;

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
 * Servlet implementation class TopPageIndexServlet
 */
@WebServlet("/index.html")
public class TopPageIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TopPageIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        //セッションスコープからログインユーザー情報を取得
        Teacher login_teacher = (Teacher)request.getSession().getAttribute("login_teacher");

        //ページネーション
        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(Exception e) {
            page = 1;
        }

        //JPQLでログインユーザーのIDと一致するメッセージデータを取得
        //マックスは15件
        List<Message> messages = em.createNamedQuery("getMyAllMessages", Message.class)
                .setParameter("teacher", login_teacher)
                .setFirstResult(15 * (page - 1))
                .setMaxResults(15)
                .getResultList();

        //JPQLでログインユーザーのIDと一致するメッセージの件数を取得
        long messages_count = (long)em.createNamedQuery("getMyMessagesCount", Long.class)
                .setParameter("teacher", login_teacher)
                .getSingleResult();

        em.close();

        //リクエストスコープに, メッセージデータ、メッセージの件数、ページ情報を保存
        request.setAttribute("messages", messages);
        request.setAttribute("messages_count", messages_count);
        request.setAttribute("page", page);
        request.setAttribute("teacher", login_teacher);

        //フラッシュメッセージの処理
        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/topPage/index.jsp");
        rd.forward(request, response);
    }

}
