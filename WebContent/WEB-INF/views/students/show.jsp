<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${student != null}"> <%-- "student"がnullではなかった場合の処理 --%>
                <h2>${student.name} の生徒情報 詳細ページ</h2>

                <table>
                    <tbody>
                        <tr>
                            <th>学生証番号</th>
                            <td><c:out value="${student.code}" /></td>
                        </tr>
                        <tr>
                            <th>学年</th>
                            <td><c:out value="${student.grade}" /></td>
                        </tr>
                        <tr>
                            <th>クラス</th>
                            <td><c:out value="${student.team}" /></td>
                        </tr>
                        <tr>
                            <th>氏名</th>
                            <td><c:out value="${student.name}" /></td>
                        </tr>
                        <tr>
                            <th>登録日時</th>
                            <td>
                                <fmt:formatDate value="${student.created_at}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                        </tr>
                        <tr>
                            <th>更新日時</th>
                            <td>
                                <fmt:formatDate value="${student.updated_at}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                        </tr>
                    </tbody>
                </table>

                <p><a href="<c:url value='/students/edit?id=${student.id}' />">情報を編集する</a></p>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>

        <p><a href="<c:url value='/students/index' />">生徒一覧に戻る</a></p>
        <p><a href="<c:url value='/teachers/index' />">教職員一覧に戻る</a></p>
    </c:param>
</c:import>