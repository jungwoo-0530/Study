<%--
  Created by IntelliJ IDEA.
  User: jungwoo
  Date: 2021/10/05
  Time: 4:12 오전
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>이벤트 목록</h1>
    <h2>${message}</h2>
    <table>
        <tr>
            <th>이름</th>
            <th>시작</th>
        </tr>
        <c:forEach items="${events}" var = "events">
            <tr>
                <td>${events.name}</td>
                <td >${events.starts}</td>
            </tr>

        </c:forEach>
    </table>

</body>
</html>
