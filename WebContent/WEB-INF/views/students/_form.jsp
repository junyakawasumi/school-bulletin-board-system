<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${errors != null}"> <%-- 入力内容のエラーチェック --%>
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>

<%-- データ入力部分 --%>
<label for="code">学生証番号</label><br />
<input type="text" name="code" value="${student.code}" />
<br /><br />

<label for="name">氏名</label><br />
<input type="text" name="name" value="${student.name}" />
<br /><br />

<label for="grade">学年</label><br />
<select name="grade">
    <option value="1"<c:if test="${student.grade == 1}"> selected</c:if>>1</option>
    <option value="2"<c:if test="${student.grade == 2}"> selected</c:if>>2</option>
    <option value="3"<c:if test="${student.grade == 3}"> selected</c:if>>3</option>
</select>
<br /><br />

<label for="team">クラス</label><br />
<select name="team">
    <option value="1"<c:if test="${student.team == 1}"> selected</c:if>>1</option>
    <option value="2"<c:if test="${student.team == 2}"> selected</c:if>>2</option>
    <option value="3"<c:if test="${student.team == 3}"> selected</c:if>>3</option>
</select>
<br /><br />

<label for="password">パスワード</label><br />
<input type="password" name="password" />
<br /><br />

<input type="hidden" name="_token" value="${_token}" /> <%-- セッションID --%>
<button type="submit">登録</button>