<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品一覧画面</title>
</head>
<body>
	<h3>商品一覧</h3>
	<%-- DIコンテナで管理しているセッションスコープBeanはSpELを利用して参照する --%>
	<spring:eval var="cart" expression="@cart"/><%-- オブジェクトだけ取得したり --%>
	<h5>カートの数量：　${cart.total}</h5>
	<hr />
	<table>
		<tr>
			<th>商品名</th>
			<th>値段</th>
			<th>数量</th>
		</tr>
		<c:forEach var="item" items="${items}">
			<form method="post" action="${pageContext.request.contextPath}/cart/add">
			<tr>
				<td>${item.name}</td>
				<td>${item.price}円</td>
				<td>
					<select name="quantity">
						<c:forEach var="i" begin="1" end="10">
							<option value="${i}">${i}</option>
						</c:forEach>
					</select>
				</td>
				<td>
					<button type="submit" name="send">カートに追加</button>
					<input type="hidden" name="itemId" value="${item.id}" />
				</td>
			</tr>
			</form>
		</c:forEach>
	</table>
	<br>
	<a href="${pageContext.request.contextPath}/order/">購入確認</a><br>
	<hr />
	<a href="<c:url value='/'/>">TOP</a>
</body>
</html>