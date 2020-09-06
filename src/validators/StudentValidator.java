package validators;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import models.Student;
import utils.DBUtil;

public class StudentValidator {
    public static List<String> validate(Student s, Boolean code_duplicate_check_flag, Boolean password_check_flag) {
        //エラーを格納するリスト(String型)を作成
        List<String> errors = new ArrayList<String>();

        //_validateCodeメソッドの結果を変数code_errorに格納
        //エラーがあればリストに格納
        String code_error = _validateCode(s.getCode(), code_duplicate_check_flag);
        if(!code_error.equals("")) {
            errors.add(code_error);
        }

        //_validateNameメソッドの結果を変数name_errorに格納
        //エラーがあればリストに格納
        String name_error = _validateName(s.getName());
        if(!name_error.equals("")) {
            errors.add(name_error);
        }

        //_validateGradeメソッドの結果を変数grade_errorに格納
        //エラーがあればリストに格納
        String grade_error = _validateGrade(s.getGrade());
        if(!grade_error.equals("")) {
            errors.add(grade_error);
        }

        //_validateGroupメソッドの結果を変数group_errorに格納
        //エラーがあればリストに格納
        String group_error = _validateTeam(s.getTeam());
        if(!group_error.equals("")) {
            errors.add(group_error);
        }

        //_validatePasswordメソッドの結果を変数password_errorに格納
        //エラーがあればリストに格納
        String password_error = _validatePassword(s.getPassword(), password_check_flag);
        if(!password_error.equals("")) {
            errors.add(password_error);
        }

        //リストを返す
        return errors;
    }

    //学生証番号のバリデーションチェック
    //第２引数がtrueであれば学生証番号の重複チェックを行う
    private static String _validateCode(String code, Boolean code_duplicate_check_flag) {
        // 必須入力チェック
        if(code == null || code.equals("")) {
            return "学生証番号を入力してください。";
        }

        // すでに登録されている学生証番号との重複チェック
        if(code_duplicate_check_flag) {
            EntityManager em = DBUtil.createEntityManager();
            long students_count = (long)em.createNamedQuery("checkRegisteredStudentCode", Long.class)
                                           .setParameter("code", code)
                                           .getSingleResult(); //データを１件取得(件数)
            em.close();
            if(students_count > 0) {
                return "入力された学生証番号の情報はすでに存在しています。";
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

    // 学年の必須入力チェック
    private static String _validateGrade(Integer grade) {
        if(grade == null || grade.equals("")) {
            return "学年を選択してください。";
        }

        return "";
    }

    // クラスの必須入力チェック
    private static String _validateTeam(Integer team) {
        if(team == null || team.equals("")) {
            return "クラスを選択してください。";
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
