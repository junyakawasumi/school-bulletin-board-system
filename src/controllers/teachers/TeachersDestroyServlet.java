package controllers.teachers;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Teacher;
import utils.DBUtil;

/**
 * Servlet implementation class TeachersDestroyServlet
 */
@WebServlet("/teachers/destroy")
public class TeachersDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TeachersDestroyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      //セッションIDをgetParameterから取得し変数_tokenに格納
        String _token = (String)request.getParameter("_token");

        //セッションIDを照合し、一致すれば処理を行う
        if(_token != null && _token.equals(request.getSession().getId())) {
            //データベースにアクセス
            EntityManager em = DBUtil.createEntityManager();

            //findメソッドでIDに該当するデータ一件を取得しインスタンスtに格納
            Teacher t = em.find(Teacher.class, (Integer)(request.getSession().getAttribute("teacher_id")));
            t.setDelete_flag(1); //delete_flagに1を格納(=削除済)
            t.setUpdated_at(new Timestamp(System.currentTimeMillis())); //変更日時を変更

            //データを確定
            em.getTransaction().begin();
            em.getTransaction().commit();
            em.close();

            //セッションスコープにフラッシュメッセージを保存
            request.getSession().setAttribute("flush", "削除が完了しました。");

            //リダイレクト
            response.sendRedirect(request.getContextPath() + "/teachers/index");
        }
    }

}
