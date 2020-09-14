<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/WEB-INF/views/layout/studentsapp.jsp">
    <c:param name="content">
        <c:if test="${hasError}">
            <div id="flush_error">
                学生証番号かパスワードが間違っています。
            </div>
        </c:if>
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>生徒用ログインページ</h2>
        <form method="POST" action="<c:url value='/slogin' />">
            <label for="code">学生証番号</label><br />
            <input type="text" name="code" value="${code}" />
            <br /><br />

            <label for="password">パスワード</label><br />
            <input type="password" name="password" />
            <br /><br />

            <input type="hidden" name="_token" value="${_token}" />
            <button type="submit">ログイン</button>
        </form>
        <p><a href="<c:url value='/tlogin' />">先生用ログインはこちら</a></p>
    </c:param>
</c:import>
