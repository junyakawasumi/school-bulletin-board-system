<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}"> <%-- フラッシュメッセージがある場合の処理 --%>
            <div id="flush_success">
                <c:out value="${flush}"></c:out> <%-- フラッシュメッセージを出力 --%>
            </div>
        </c:if>
        <h2>生徒 一覧</h2>
        <table id="student_list">
            <tbody>
                <tr>
                    <th>学生証番号</th>
                    <th>氏名</th>
                    <th>操作</th>
                </tr>
                <c:forEach var="student" items="${students}" varStatus="status"> <%-- 教職員のデータを繰り返し表示(最大20件) --%>
                    <tr class="row${status.count % 2}">
                        <td><c:out value="${student.code}" /></td> <%-- 教職員番号 --%>
                        <td><c:out value="${student.name}" /></td>　<%-- 氏名 --%>
                        <td>
                            <c:choose>
                                <c:when test="${student.delete_flag == 1}"> <%-- delete_flagが1の場合(削除済)の処理 --%>
                                    [削除済み]
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value='/students/show?id=${student.id}' />">詳細を表示</a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination"> <%-- ページネーション --%>
            (全 ${students_count} 件)<br />
            <c:forEach var="i" begin="1" end="${((students_count - 1) / 20) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/students/index?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value='/students/new' />">新規生徒の登録</a></p>
        <p><a href="<c:url value='/teachers/index' />">教職員一覧に戻る</a></p>
    </c:param>
</c:import>