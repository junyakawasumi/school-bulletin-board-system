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
 * Servlet implementation class StudentsCreateServlet
 */
@WebServlet("/students/create")
public class StudentsCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentsCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //セッションIDを変数_tokenに格納
        String _token = (String)request.getParameter("_token");

        //セッションIDの照合
        if(_token != null && _token.equals(request.getSession().getId())){
            //データベースにアクセス
            EntityManager em = DBUtil.createEntityManager();

            //インスタンスの作成
            Student s = new Student();

            //setterを利用して値をそれぞれのプロパティに格納
            s.setCode(request.getParameter("code")); //学生証番号
            s.setName(request.getParameter("name")); //氏名
            s.setGrade(Integer.parseInt(request.getParameter("grade"))); //学年
            s.setTeam(Integer.parseInt(request.getParameter("team"))); //クラス

            //パスワードはハッシュ化してから格納
            s.setPassword(
                    EncryptUtil.getPasswordEncrypt(
                            request.getParameter("password"),
                            (String)this.getServletContext().getAttribute("pepper")
                            )
                    );

            Timestamp currentTime = new Timestamp(System.currentTimeMillis()); //現在時を取得
            s.setCreated_at(currentTime); //登録日時
            s.setUpdated_at(currentTime); //変更日時
            s.setDelete_flag(0); //現役: 0 /  削除済み: 1

            //バリデーションチェック
            //第２引数がtrueの場合、教職員番号の重複チェック, 第３引数がtrueの場合、パスワードの入力値チェック
            List<String> errors = StudentValidator.validate(s, true, true);

            //エラーがあった場合の処理
            if(errors.size() > 0) {
                em.close();

                //リクエストスコープにセッションID, Student型のインスタンス, エラー内容(List)を保存
                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("student", s);
                request.setAttribute("errors", errors);

                //フォワード
                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/students/new.jsp");
                rd.forward(request, response);
            } else {
                //エラーがなかった場合の処理(値を確定してデータベースに保存)
                em.getTransaction().begin(); //トランザクション処理
                em.persist(s);
                em.getTransaction().commit();
                em.close();

                //セッションスコープにフラッシュメッセージを保存
                request.getSession().setAttribute("flush", "登録が完了しました。");

                //リダイレクト
                response.sendRedirect(request.getContextPath() + "/students/index");
            }

        }
    }

}
