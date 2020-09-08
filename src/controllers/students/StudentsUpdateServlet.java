package controllers.students;

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

import models.Student;
import utils.DBUtil;
import utils.EncryptUtil;
import validators.StudentValidator;

/**
 * Servlet implementation class StudentsUpdateServlet
 */
@WebServlet("/students/update")
public class StudentsUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentsUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //セッションIDを変数_tokenに格納
        String _token = (String)request.getParameter("_token");

        //セッションIDを照合し、一致すれば処理を行う
        if(_token != null && _token.equals(request.getSession().getId())) {

            //データベースにアクセス
            EntityManager em = DBUtil.createEntityManager();
            //findメソッドで対応するIDのデータを取得しインスタンスsに格納
            Student s = em.find(Student.class, (Integer)(request.getSession().getAttribute("student_id")));

            // 学生証番号に現在の値と異なる値が入力されていたら重複チェックを行う
            Boolean code_duplicate_check = true;
            if(s.getCode().equals(request.getParameter("code"))) {
                code_duplicate_check = false;
            } else {
                s.setCode(request.getParameter("code"));
            }

            // パスワード欄に入力があったらパスワードの入力値チェックを行う
            Boolean password_check_flag = true;
            String password = request.getParameter("password");
            if(password == null || password.equals("")) {
                password_check_flag = false;
            } else {
                s.setPassword(
                        EncryptUtil.getPasswordEncrypt( //ハッシュ化
                                password,
                                (String)this.getServletContext().getAttribute("pepper")
                                )
                        );
            }

            //各データをセッターでsに格納
            s.setName(request.getParameter("name")); //氏名
            s.setGrade(Integer.parseInt(request.getParameter("grade"))); //学年
            s.setTeam(Integer.parseInt(request.getParameter("team"))); //クラス
            s.setUpdated_at(new Timestamp(System.currentTimeMillis())); //更新日時
            s.setDelete_flag(0);

            //バリデーションチェックを行いエラー内容をリストに格納
            List<String> errors = StudentValidator.validate(s, code_duplicate_check, password_check_flag);

            if(errors.size() > 0) { //エラーがあった場合の処理
                em.close();

                //セッションID、インスタンス、エラー内容をリクエストスコープに保存
                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("student", s);
                request.setAttribute("errors", errors);

                //フォワード
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/students/edit.jsp");
                rd.forward(request, response);

            } else { //エラーがなかった場合の処理
                //データを確定
                em.getTransaction().begin();
                em.getTransaction().commit();
                em.close();

                //フラッシュメッセージをセッションスコープに登録
                request.getSession().setAttribute("flush", "更新が完了しました。");
                //セッションスコープからIDを削除
                request.getSession().removeAttribute("student_id");

                //リダイレクト
                response.sendRedirect(request.getContextPath() + "/students/index");
            }

        }
    }

}
