<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品の登録・更新画面</title>
</head>
<body>
	<h5>
		こんにちは、
		<sec:authentication property="name" />
		さん
	</h5>
	<form action="${pageContext.request.contextPath}/logout" method="post">
		<sec:csrfInput />
		<input type="submit" value="ログアウト" />
	</form>
	<hr />
	<h3>商品の登録・更新</h3>
	<hr />
	<h5 style="color: red;">
		<c:out value="${message}" />
	</h5>
	<table>
		<tr>
			<th>商品ID</th>
			<th>商品名</th>
			<th>価格</th>
			<th>操作</th>
		</tr>
		<c:forEach var="item" items="${items}">
			<tr>
				<td>${item.id}</td>
				<td>${item.name}</td>
				<td>${item.price}円</td>
				<td>
					<form method="get" action="${pageContext.request.contextPath}/admin/items/update-deleted/${item.id}">
						<sec:csrfInput />
						<label>
							<input type="radio" name="deleted" value="false" ${!item.deleted ? 'checked' : ''} />
							有効
						</label>
						<label>
							<input type="radio" name="deleted" value="true" ${item.deleted ? 'checked' : ''} />
							削除済
						</label>
						　<button type="submit">更新</button>
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>
	<hr />
	<a href="<c:url value='/'/>">TOP</a>
</body>
</html>