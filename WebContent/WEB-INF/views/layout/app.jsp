<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
    <head>
        <meta charset="UTF-8">
        <title>学校掲示板</title>
        <link rel="stylesheet" href="<c:url value='/css/reset.css' />">
        <link rel="stylesheet" href="<c:url value='/css/style.css' />">
    </head>
    <body>
        <div id="wrapper">
            <div id="header">
                <h1>●●高等学校掲示板</h1>
            </div>
            <div id="content">
                ${param.content}
            </div>
            <div id="footer">
                <h3>今日の名言: ●●●</h3>
                by Junya Kawasumi.
            </div>
        </div>
    </body>
</html>