<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>利用者情報の一覧画面</title>
</head>
<body>
	<h5>
		<c:out value="${greeting}" />
		<sec:authentication var="user" property="principal" />
		<c:out value="${user.username}" />
		さん
	</h5>
	<form action="${pageContext.request.contextPath}/logout" method="post">
		<sec:csrfInput />
		<input type="submit" value="ログアウト" />
	</form>
	<hr />
	<h3>利用者情報の一覧</h3>
	<hr />
    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>利用者氏名</th>
                <th>アカウント有効可否</th>
                <th>アカウント有効期限切れ可否</th>
                <th>パスワード有効期限切れ可否</th>
                <th>アカウントロック可否</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="exUser" items="${userList}">
                <tr>
                    <td><c:out value="${exUser.loginId}" /></td>
                    <td><c:out value="${exUser.username}" /></td>
                    <td><c:out value="${exUser.enabled ? '有効' : '無効'}" /></td>
                    <td><c:out value="${exUser.accountNonExpired ? '有効' : '無効'}" /></td>
                    <td><c:out value="${exUser.credentialsNonExpired ? '有効' : '無効'}" /></td>
                    <td><c:out value="${exUser.accountNonLocked ? 'ロックなし' : 'ロックあり' }" /></td>
                    <td>
                        <form method="get" action="${pageContext.request.contextPath}/admin/users/${user.loginId}/edit">
                            <input type="submit" value="変更" />
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
	<hr />
	<a href="<c:url value='/'/>">TOP</a>
</body>
</html>