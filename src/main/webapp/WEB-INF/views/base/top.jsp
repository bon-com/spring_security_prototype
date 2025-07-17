<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TOP</title>
</head>
<body>
	<h3>${greeting}</h3>
	<ul>
		<li>カートの利用：　<a href="<c:url value='/items/'/>">こちら</a></li>
	</ul>
	<ul>
		<li>購入履歴：　<a href="<c:url value='/history/'/>">こちら</a></li>
	</ul>
</body>
</html>