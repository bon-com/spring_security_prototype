<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>注文完了画面</title>
</head>
<body>
	<h5>
		<c:out value="${greeting}" />
		<sec:authentication var="user" property="principal" />
		<c:out value="${user.username}" />
		さん
	</h5>
	<form action="${pageContext.request.contextPath}/logout" method="post">
		<sec:csrfInput/>
		<input type="submit" value="ログアウト" />
	</form>
	<hr />
	<h3>注文完了</h3>
	<hr />
	<h2>ご購入ありがとうございました！</h2>
	<hr />
	<a href="<c:url value='/'/>">TOP</a>
</body>
</html>