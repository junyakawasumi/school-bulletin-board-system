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
import models.Teacher;
import utils.DBUtil;
import validators.MessageValidator;

/**
 * Servlet implementation class MessagesCreateServlet
 */
@WebServlet("/messages/create")
public class MessagesCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessagesCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //セッションIDをリクエストパラメータとして取得
        String _token = (String)request.getParameter("_token");

        //セッションIDの照合
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Message m = new Message();

            //mのsetTeacherにloginアカウントの情報を格納
            m.setTeacher((Teacher)request.getSession().getAttribute("login_teacher"));

            //日付を格納
            //日付を入力しなかった場合の処理
            Date message_date = new Date(System.currentTimeMillis());
            String md_str = request.getParameter("message_date");
            if(md_str != null && !md_str.equals("")) {
                message_date = Date.valueOf(request.getParameter("message_date"));
            }
            m.setMessage_date(message_date);

            //タイトル及び内容
            m.setTitle(request.getParameter("title"));
            m.setContent(request.getParameter("content"));

            //登録日時
            //更新日時
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            m.setCreated_at(currentTime);
            m.setUpdated_at(currentTime);

            //公開権限
            m.setOpen_range(Integer.parseInt(request.getParameter("open_range")));

            //バリデーションチェック
            List<String> errors = MessageValidator.validate(m);
            if(errors.size() > 0) {
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("message", m);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/messages/new.jsp");
                rd.forward(request, response);
            } else {
                //トランザクション処理
                em.getTransaction().begin();
                em.persist(m);
                em.getTransaction().commit();

                em.close();

                //フラッシュメッセージ
                request.getSession().setAttribute("flush", "登録が完了しました。");

                //リダイレクト
                response.sendRedirect(request.getContextPath() + "/messages/index");
            }
        }
    }

}
