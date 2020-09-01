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
 * Servlet implementation class TeachersUpdateServlet
 */
@WebServlet("/teachers/update")
public class TeachersUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TeachersUpdateServlet() {
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
            //findメソッドで対応するIDのデータを取得しインスタンスtに格納
            Teacher t = em.find(Teacher.class, (Integer)(request.getSession().getAttribute("teacher_id")));

            // 教職員番号に現在の値と異なる値が入力されていたら重複チェックを行う
            Boolean code_duplicate_check = true;
            if(t.getCode().equals(request.getParameter("code"))) {
                code_duplicate_check = false;
            } else {
                t.setCode(request.getParameter("code"));
            }

            // パスワード欄に入力があったらパスワードの入力値チェックを行う
            Boolean password_check_flag = true;
            String password = request.getParameter("password");
            if(password == null || password.equals("")) {
                password_check_flag = false;
            } else {
                t.setPassword(
                        EncryptUtil.getPasswordEncrypt( //ハッシュ化
                                password,
                                (String)this.getServletContext().getAttribute("pepper")
                                )
                        );
            }

            //各データをセッターでtに格納
            t.setName(request.getParameter("name"));
            t.setAdmin_flag(Integer.parseInt(request.getParameter("admin_flag")));
            t.setUpdated_at(new Timestamp(System.currentTimeMillis()));
            t.setDelete_flag(0);

            //バリデーションチェックを行いエラー内容をリストに格納
            List<String> errors = TeacherValidator.validate(t, code_duplicate_check, password_check_flag);

            if(errors.size() > 0) { //エラーがあった場合の処理
                em.close();

                //セッションID、インスタンス、エラー内容をリクエストスコープに保存
                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("teacher", t);
                request.setAttribute("errors", errors);

                //teachers/efit.jspにフォワード
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/teachers/edit.jsp");
                rd.forward(request, response);
            } else { //エラーがなかった場合の処理
                //データを確定
                em.getTransaction().begin();
                em.getTransaction().commit();
                em.close();

                //フラッシュメッセージをセッションスコープに登録
                request.getSession().setAttribute("flush", "更新が完了しました。");
                //セッションスコープからIDを削除
                request.getSession().removeAttribute("teacher_id");
                //teachers/index.jspにリダイレクト
                response.sendRedirect(request.getContextPath() + "/teachers/index");
            }

        }
    }

}
