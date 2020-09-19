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
import utils.DBUtil;

/**
 * Servlet implementation class StudentsTopPageIndexServlet
 */
@WebServlet("/stoppage")
public class StudentsTopPageIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentsTopPageIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        //ページネーション
        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(Exception e) {
            page = 1;
        }

        //全体公開のメッセージを取得
        //マックス20件
        List<Message> messages = em.createNamedQuery("getAllMessagesForStudents", Message.class)
                .setFirstResult(20 * (page - 1))
                .setMaxResults(20)
                .getResultList();

        long messages_count = (long)em.createNamedQuery("getAllMessagesForStudentsCount", Long.class)
                .getSingleResult();

        em.close();

        //リクエストスコープにメッセージデータ、メッセージの件数、ページ情報を保存
        request.setAttribute("messages", messages);
        request.setAttribute("messages_count", messages_count);
        request.setAttribute("page", page);

        //フラッシュメッセージの処理
        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        //フォワード
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/topPage/studentsindex.jsp");
        rd.forward(request, response);
    }

}
