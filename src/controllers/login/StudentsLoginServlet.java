package controllers.login;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Student;
import utils.DBUtil;
import utils.EncryptUtil;

/**
 * Servlet implementation class StudentsLoginServlet
 */
@WebServlet("/slogin")
public class StudentsLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentsLoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    //ログイン画面を表示
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //リクエストスコープにセッションID, false(エラーの有無)を保存
        request.setAttribute("_token", request.getSession().getId());
        request.setAttribute("hasError", false);

        //フラッシュメッセージがあった場合の処理
        //セッションスコープからフラッシュメッセージを取得し、リクエストスコープに格納後、セッションスコープからは削除
        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }
        //フォワード
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login/studentslogin.jsp");
        rd.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    //ログイン処理を実行
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      //認証結果を格納する変数
        Boolean check_result = false;

        //教職員番号, パスワードを受け取る
        String code = request.getParameter("code");
        String plain_pass = request.getParameter("password");

        Student s = null;

        //教職員番号, パスワードがnullではなかった場合の処理
        if(code != null && !code.equals("") && plain_pass != null && !plain_pass.equals("")) {

            EntityManager em = DBUtil.createEntityManager();

            String password = EncryptUtil.getPasswordEncrypt(
                    plain_pass,
                    (String)this.getServletContext().getAttribute("pepper")
                    );

            //教職員番号とパスワードが正しいかチェック
            try {
                s = em.createNamedQuery("checkLoginStudentCodeAndPassword", Student.class)
                      .setParameter("code", code)
                      .setParameter("pass", password)
                      .getSingleResult();
            } catch(NoResultException ex) {}

            em.close();

            if(s != null) {
                check_result = true;
            }
        }

        if(!check_result) {
            //認証できなかったらログイン画面に戻る
            request.setAttribute("_token", request.getSession().getId());
            request.setAttribute("hasError", true);
            request.setAttribute("code", code);

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login/studentslogin.jsp");
            rd.forward(request, response);
        } else {

            //認証できたらログイン状態にしてトップページへリダイレクト
            //セッションスコープにtを保存
            request.getSession().setAttribute("login_student", s);
            //フラッシュメッセージ
            request.getSession().setAttribute("flush", "ログインしました。");

            //リダイレクト
            response.sendRedirect(request.getContextPath() + "/stoppage");
        }
    }

}
