<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${errors != null}"> <%-- フラッシュメッセージ --%>
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>
<label for="message_date">日付</label><br />
<input type="date" name="message_date" value="<fmt:formatDate value='${message.message_date}' pattern='yyyy-MM-dd' />" />
<br /><br />

<label for="name">氏名</label><br />
<c:out value="${sessionScope.login_teacher.name}" />
<br /><br />

<label for="title">タイトル</label><br />
<input type="text" name="title" value="${message.title}" />
<br /><br />

<label for="content">内容</label><br />
<textarea name="content" rows="10" cols="50">${message.content}</textarea>
<br /><br />

<label for="open_range">公開範囲</label><br />
<select name="open_range">
    <option value="0"<c:if test="${message.open_range == 0}"> selected</c:if>>全体</option>
    <option value="1"<c:if test="${message.open_range == 1}"> selected</c:if>>教職員のみ</option>
</select>
<br /><br />

<input type="hidden" name="_token" value="${_token}" />
<button type="submit">投稿</button>