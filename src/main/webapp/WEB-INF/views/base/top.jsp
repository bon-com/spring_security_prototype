<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TOP</title>
</head>
<body>
	<h5>こんにちは、<sec:authentication property="name" />さん</h5>
	<form action="<c:url value='/logout' />" method="post">
		<sec:csrfInput/>
		<input type="submit" value="ログアウト" />
	</form>
	<hr />
	<h3>${greeting}</h3>
	<ul>
		<li>カートの利用：　<a href="<c:url value='/items/'/>">こちら</a></li>
	</ul>
	<ul>
		<li>購入履歴：　<a href="<c:url value='/history/'/>">こちら</a></li>
	</ul>
</body>
</html>