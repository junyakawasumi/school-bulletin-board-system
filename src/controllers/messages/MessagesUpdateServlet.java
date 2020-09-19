package controllers.messages;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
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
import validators.MessageValidator;

/**
 * Servlet implementation class MessagesUpdateServlet
 */
@WebServlet("/messages/update")
public class MessagesUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessagesUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //セッションIDをリクエストパラメーターから取得
        String _token = (String)request.getParameter("_token");

        //セッションIDが一致した場合の処理
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            //該当するメッセージIDのデータ一件を取得
            Message m = em.find(Message.class, (Integer)(request.getSession().getAttribute("message_id")));

            m.setMessage_date(Date.valueOf(request.getParameter("message_date"))); //日付
            m.setTitle(request.getParameter("title")); //タイトル
            m.setContent(request.getParameter("content")); //内容
            m.setUpdated_at(new Timestamp(System.currentTimeMillis())); //更新日
            m.setOpen_range(Integer.parseInt(request.getParameter("open_range"))); //公開範囲

            List<String> errors = MessageValidator.validate(m);

            //エラーがあった場合の処理
            if(errors.size() > 0) {
                em.close();

                //リクエストスコープにセッションID, インスタンス, エラー内容を保存
                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("message", m);
                request.setAttribute("errors", errors);

                //フォワード
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/messages/edit.jsp");
                rd.forward(request, response);
            } else {
                //エラーがなかった場合の処理
                em.getTransaction().begin();
                em.getTransaction().commit();
                em.close();

                //フラッシュメッセージの処理
                request.getSession().setAttribute("flush", "更新が完了しました。");

                //セッションスコープからmessage_idを削除
                request.getSession().removeAttribute("message_id");

                //リダイレクト
                response.sendRedirect(request.getContextPath() + "/messages/index");
            }
        }
    }

}
