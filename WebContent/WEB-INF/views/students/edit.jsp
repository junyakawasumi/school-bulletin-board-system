<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${student != null}"> <%-- リクエストスコープに保存されたstudentがnullではなかった場合の処理 --%>
                <h2>${student.name} の生徒情報 編集ページ</h2>
                <p>（※パスワードは変更する場合のみ入力してください）</p>
                <form method="POST" action="<c:url value='/students/update' />">
                    <c:import url="_form.jsp" />
                </form>

                <p><a href="#" onclick="confirmDestroy();">この生徒情報を削除する</a></p>
                <form method="POST" action="<c:url value='/students/destroy' />">
                    <input type="hidden" name="_token" value="${_token}" /> <%-- セッションID --%>
                </form>
                <script> <%-- JavaScript --%>
                    function confirmDestroy() {
                        if(confirm("本当に削除してよろしいですか？")) {
                            document.forms[1].submit();
                        }
                    }
                </script>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>

        <p><a href="<c:url value='/students/index' />">生徒一覧に戻る</a></p>
    </c:param>
</c:import>