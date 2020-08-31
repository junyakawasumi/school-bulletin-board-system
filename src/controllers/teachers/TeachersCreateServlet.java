package controllers.teachers;

import java.io.IOException;
import java.sql.Timestamp;
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
import utils.EncryptUtil;
import validators.TeacherValidator;

/**
 * Servlet implementation class TeachersCreateServlet
 */
@WebServlet("/teachers/create")
public class TeachersCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TeachersCreateServlet() {
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
        if(_token != null && _token.equals(request.getSession().getId())){
            //データベースにアクセス
            EntityManager em = DBUtil.createEntityManager();

            Teacher t = new Teacher();

            //setterを利用してgetParameterで取得した値をそれぞれのプロパティに格納
            t.setCode(request.getParameter("code")); //教職員ID
            t.setName(request.getParameter("name")); //氏名
            //パスワードはハッシュ化してから格納
            t.setPassword(
                    EncryptUtil.getPasswordEncrypt(
                            request.getParameter("password"),
                            (String)this.getServletContext().getAttribute("pepper")
                            )
                    );
            t.setAdmin_flag(Integer.parseInt(request.getParameter("admin_flag"))); //一般or教務

            Timestamp currentTime = new Timestamp(System.currentTimeMillis()); //現在時を取得
            t.setCreated_at(currentTime); //登録日時
            t.setUpdated_at(currentTime); //変更日時
            t.setDelete_flag(0); //現役: 0 /  削除済み: 1

            //バリデーションチェックを行い、エラーが内容をリストに格納
            //第２引数がtrueの場合、教職員IDの重複チェック, 第３引数がtrueの場合、パスワードの入力値チェック
            List<String> errors = TeacherValidator.validate(t, true, true);

            //エラーがあった場合の処理
            if(errors.size() > 0) {
                em.close();

                //リクエストスコープにセッションID, Teacher型のインスタンス, エラー内容(List)
                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("teacher", t);
                request.setAttribute("errors", errors);

                //teachers/new.jspにフォワード
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/teachers/new.jsp");
                rd.forward(request, response);
            } else {
                //エラーがなかった場合の処理(値を確定してデータベースに保存)
                em.getTransaction().begin(); //トランザクション処理
                em.persist(t);
                em.getTransaction().commit();
                em.close();

                //セッションスコープにフラッシュメッセージを保存
                request.getSession().setAttribute("flush", "登録が完了しました。");

                //teachers/index.jspにリダイレクト
                response.sendRedirect(request.getContextPath() + "/teachers/index");
            }

        }
    }

}
