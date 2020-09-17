package controllers.messages;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Message;

/**
 * Servlet implementation class MessagesNewServlet
 */
@WebServlet("/messages/new")
public class MessagesNewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessagesNewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //セッションIDをリクエストスコープに保存
        request.setAttribute("_token", request.getSession().getId());

        //インスタンス作成
        //現在時をmのmessage_dateに保存
        Message m = new Message();
        m.setMessage_date(new Date(System.currentTimeMillis()));
        request.setAttribute("message", m);

        //フォワード
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/messages/new.jsp");
        rd.forward(request, response);
    }

}
