<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>注文内容確認画面</title>
</head>
<body>
	<h3>注文内容確認</h3>
	<hr />
	<table>
		<tr>
			<th>商品名</th>
			<th>数量</th>
			<th>値段</th>
		</tr>
		<c:forEach var="cartItem" items="${cartitems}">
		<tr>
			<td>${cartItem.item.name}　</td>
			<td>${cartItem.quantity}個　</td>
			<td>${cartItem.total}円　</td>
			<td><a href="${pageContext.request.contextPath}/cart/delete/${cartItem.item.id}">削除</td>
		</tr>
		</c:forEach>
	</table>
	<h5>${totalPriceMsg}</h5>
	<hr />
	<c:choose>
	<c:when test="${totalPrice != 0}">
		<a href="${pageContext.request.contextPath}/order/complete">購入する</a>
	</c:when>
	<c:otherwise>
		<a href="${pageContext.request.contextPath}/items/">商品一覧に戻る</a>
	</c:otherwise>
	</c:choose>
</body>
</html>