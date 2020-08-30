package validators;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import models.Teacher;
import utils.DBUtil;

public class TeacherValidator {
    public static List<String> validate(Teacher t, Boolean code_duplicate_check_flag, Boolean password_check_flag) {
        //エラーを格納するリスト(String型)を作成
        List<String> errors = new ArrayList<String>();

        //_validateCodeメソッドの結果を変数code_errorに格納
        //エラーがあればリストに格納
        String code_error = _validateCode(t.getCode(), code_duplicate_check_flag);
        if(!code_error.equals("")) {
            errors.add(code_error);
        }

        //_validateNameメソッドの結果を変数name_errorに格納
        //エラーがあればリストに格納
        String name_error = _validateName(t.getName());
        if(!name_error.equals("")) {
            errors.add(name_error);
        }

        //_validatePasswordメソッドの結果を変数password_errorに格納
        //エラーがあればリストに格納
        String password_error = _validatePassword(t.getPassword(), password_check_flag);
        if(!password_error.equals("")) {
            errors.add(password_error);
        }

        //リストを返す
        return errors;
    }

    //教職員IDのバリデーションチェック
    //第２引数がtrueであれば教職員IDの重複チェックを行う
    private static String _validateCode(String code, Boolean code_duplicate_check_flag) {
        // 必須入力チェック
        if(code == null || code.equals("")) {
            return "教職員IDを入力してください。";
        }

        // すでに登録されている教職員IDとの重複チェック
        if(code_duplicate_check_flag) {
            EntityManager em = DBUtil.createEntityManager();
            long teachers_count = (long)em.createNamedQuery("checkRegisteredCode", Long.class)
                                           .setParameter("code", code)
                                             .getSingleResult();
            em.close();
            if(teachers_count > 0) {
                return "入力された教職員IDの情報はすでに存在しています。";
            }
        }

        return "";
    }

 // 氏名の必須入力チェック
    private static String _validateName(String name) {
        if(name == null || name.equals("")) {
            return "氏名を入力してください。";
        }

        return "";
    }

    // パスワードの必須入力チェック
    //第２引数がtrueであればパスワードの入力チェックを行う
    private static String _validatePassword(String password, Boolean password_check_flag) {
        // パスワードを変更する場合のみ実行
        if(password_check_flag && (password == null || password.equals(""))) {
            return "パスワードを入力してください。";
        }
        return "";
    }

}
