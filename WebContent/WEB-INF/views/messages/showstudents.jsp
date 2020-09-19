<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/studentsapp.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${message != null}"><%-- messageがnullではなかった場合の処理 --%>
                <h2>メッセージ 詳細ページ</h2>

                <table> <%-- テーブル --%>
                    <tbody>
                        <tr>
                            <th>氏名</th>
                            <td><c:out value="${message.teacher.name}" /> 先生</td>
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
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>

        <p><a href="<c:url value="/stoppage" />">トップページに戻る</a></p>
    </c:param>
</c:import>