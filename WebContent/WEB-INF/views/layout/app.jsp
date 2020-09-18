<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
    <head>
        <meta charset="UTF-8">
        <title>●●高等学校掲示板</title>
        <link rel="stylesheet" href="<c:url value='/css/reset.css' />">
        <link rel="stylesheet" href="<c:url value='/css/style.css' />">
    </head>
    <body>
        <div id="wrapper">
            <div id="header">
                <div id="header_menu">
                    <h1><a href="<c:url value='/' />">●●高等学校掲示板</a></h1>&nbsp;&nbsp;&nbsp;
                    <c:if test="${sessionScope.login_teacher != null}">
                        <c:if test="${sessionScope.login_teacher.admin_flag == 1}">
                            <a href="<c:url value='/teachers/index' />">教職員管理</a>&nbsp;
                            <a href="<c:url value='/students/index' />">生徒管理</a>&nbsp;
                        </c:if>
                        <a href="<c:url value='/messages/index' />">メッセージ管理</a>&nbsp;
                    </c:if>
                </div>
                <c:if test="${sessionScope.login_teacher != null}">
                    <div id="teacher_name">
                        <c:out value="${sessionScope.login_teacher.name}" />&nbsp;先生&nbsp;&nbsp;&nbsp;
                        <a href="<c:url value='/tlogout' />">ログアウト</a>
                    </div>
                </c:if>
            </div>
            <div id="content">
                ${param.content}
            </div>
            <div id="footer">
                by Junya Kawasumi.
            </div>
        </div>
    </body>
</html>