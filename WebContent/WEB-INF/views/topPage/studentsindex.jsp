<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../layout/studentsapp.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>●●高等学校掲示板へようこそ！</h2>
        <h3>【メッセージ一覧】</h3>

        <table id="message_list"> <%-- テーブル --%>
            <tbody>
                <tr>
                    <th class="message_name">氏名</th>
                    <th class="message_date">日付</th>
                    <th class="message_title">タイトル</th>
                    <th class="message_action">操作</th>
                </tr>
                <c:forEach var="message" items="${messages}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="message_name"><c:out value="${message.teacher.name}" /> 先生</td>
                        <td class="message_date"><fmt:formatDate value='${message.message_date}' pattern='yyyy-MM-dd' /></td>
                        <td class="message_title">${message.title}</td>
                        <td class="message_action"><a href="<c:url value='/messages/show?id=${message.id}' />">詳細を見る</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination"> <%-- ページネーション --%>
            （全 ${messages_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((messages_count - 1) / 20) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>

    </c:param>
</c:import>