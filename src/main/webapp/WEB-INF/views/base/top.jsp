<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TOP</title>
</head>
<body>
	<h5>
		<c:out value="${greeting}" />
		<sec:authentication property="name" />
		さん
	</h5>
	<form action="${pageContext.request.contextPath}/logout" method="post">
		<sec:csrfInput />
		<input type="submit" value="ログアウト" />
	</form>
	<hr />
	<h3>利用者メニュー</h3>
	<p style="color: red;">
		<c:out value="${warning}" />
	</p>
	<ul>
		<li>パスワードの更新： <a href="${pageContext.request.contextPath}/user/password-update">こちら</a></li>
		<li>商品の購入： <a href="${pageContext.request.contextPath}/items/">こちら</a></li>
		<li>購入履歴： <a href="${pageContext.request.contextPath}/history/">こちら</a></li>
	</ul>
	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<hr />
		<h3>管理者メニュー</h3>
		<ul>
			<li>商品の登録・更新： <a href="${pageContext.request.contextPath}/admin/items">こちら</a></li>
			<li>利用者情報の登録・更新： <a href="${pageContext.request.contextPath}/admin/users/">こちら</a></li>
		</ul>
	</sec:authorize>
</body>
</html>