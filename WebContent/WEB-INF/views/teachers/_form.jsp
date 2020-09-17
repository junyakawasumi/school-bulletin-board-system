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
<label for="code">教職員番号</label><br />
<input type="text" name="code" value="${teacher.code}" />
<br /><br />

<label for="name">氏名</label><br />
<input type="text" name="name" value="${teacher.name}" />
<br /><br />

<label for="password">パスワード</label><br />
<input type="password" name="password" />
<br /><br />

<label for="admin_flag">所属</label><br />
<select name="admin_flag">
    <option value="0"<c:if test="${teacher.admin_flag == 0}"> selected</c:if>>一般</option>
    <option value="1"<c:if test="${teacher.admin_flag == 1}"> selected</c:if>>教務部</option>
</select>
<br /><br />

<input type="hidden" name="_token" value="${_token}" /> <%-- セッションID --%>
<button type="submit">登録</button>