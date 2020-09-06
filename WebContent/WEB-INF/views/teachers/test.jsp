<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>教職員 一覧</h2>
        <table id="teacher_list">
            <tbody>
                <tr>
                    <th>教職員番号</th>
                    <th>氏名</th>
                    <th>操作</th>
                </tr>
                <c:forEach var="teacher" items="${teachers}" varStatus="status"> <%-- 教職員のデータを繰り返し表示(最大20件) --%>
                    <tr class="row${status.count % 2}">
                        <td><c:out value="${teacher.code}" /></td> <%-- 教職員番号 --%>
                        <td><c:out value="${teacher.name}" /></td>　<%-- 氏名 --%>
                        <td>
                            <c:choose>
                                <c:when test="${teacher.delete_flag == 1}"> <%-- delete_flagが1の場合(削除済)の処理 --%>
                                    [削除済み]
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value='/teachers/show?id=${teacher.id}' />">詳細を表示</a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:param>
</c:import>