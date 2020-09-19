<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${message != null}"><%-- messageがnullではなかった場合の処理 --%>
                <h2>メッセージ 詳細ページ</h2>

                <table> <%-- テーブル --%>
                    <tbody>
                        <tr>
                            <th>氏名</th>
                            <td><c:out value="${message.teacher.name}" /></td>
                        </tr>
                        <tr>
                            <th>日付</th>
                            <td><fmt:formatDate value="${message.message_date}" pattern="yyyy-MM-dd" /></td>
                        </tr>
                        <tr>
                            <th>内容</th>
                            <td>
                                <pre><c:out value="${message.content}" /></pre>
                            </td>
                        </tr>
                        <tr>
                            <th>登録日時</th>
                            <td>
                                <fmt:formatDate value="${message.created_at}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                        </tr>
                        <tr>
                            <th>更新日時</th>
                            <td>
                                <fmt:formatDate value="${message.updated_at}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                        </tr>
                        <tr>
                            <th>公開範囲</th>
                            <td>
                                <c:choose>
                                    <c:when test="${message.open_range == 0}">全体</c:when>
                                    <c:otherwise>教職員のみ</c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </tbody>
                </table>

                <c:if test="${sessionScope.login_teacher.id == message.teacher.id}"> <%-- ログインユーザーとメッセージの作成者が一致した場合はメッセージの編集が可能 --%>
                    <p><a href="<c:url value="/messages/edit?id=${message.id}" />">このメッセージを編集する</a></p>
                </c:if>

            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>

        <p><a href="<c:url value="/messages/index" />">一覧に戻る</a></p>
    </c:param>
</c:import>