<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>利用者新規登録画面</title>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<h3>利用者新規登録</h3>
	<p style="color: red;">
		<c:out value="${warning}" />
	</p>
	<hr />
	<form:form modelAttribute="usersForm" method="post" action="${pageContext.request.contextPath}/admin/users/register">
		<sec:csrfInput />
		<table>
			<tr>
				<th>ログインID</th>
				<td>
					<form:input path="loginId" />
				</td>
				<td>
					<form:errors path="loginId" cssStyle="color: red;" />
				</td>
			</tr>
			<tr>
				<th>利用者氏名</th>
				<td>
					<form:input path="username" />
				</td>
				<td>
					<form:errors path="username" cssStyle="color: red;" />
				</td>
			</tr>
			<tr>
				<th>パスワード</th>
				<td>
					<form:password path="password" />
				</td>
				<td>
					<form:errors path="password" cssStyle="color: red;" />
				</td>
			</tr>
			<tr>
				<th>確認用パスワード</th>
				<td>
					<form:password path="confirmPassword" />
				</td>
				<td>
					<form:errors path="confirmPassword" cssStyle="color: red;" />
				</td>
			</tr>
			<tr>
				<th>アカウント有効状態</th>
				<td>
					<label>
						<form:radiobutton path="enabled" value="true" />
						有効
					</label>
					<label>
						<form:radiobutton path="enabled" value="false" />
						無効
					</label>
				</td>
				<td>
					<form:errors path="enabled" cssStyle="color: red;" />
				</td>
			</tr>
			<tr>
				<th>アカウント有効期限</th>
				<td>
					<form:input path="accountExpiryAt" type="datetime-local" />
				</td>
				<td>
					<form:errors path="accountExpiryAt" cssStyle="color: red;" />
				</td>
			</tr>
			<tr>
				<th>パスワード有効期限</th>
				<td>
					<form:input path="passwordExpiryAt" type="datetime-local" />
				</td>
				<td>
					<form:errors path="passwordExpiryAt" cssStyle="color: red;" />
				</td>
			</tr>
			<tr>
				<th>アカウントロック状態</th>
				<td>
					<label>
						<form:radiobutton path="accountNonLocked" value="true" />
						ロックなし
					</label>
					<label>
						<form:radiobutton path="accountNonLocked" value="false" />
						ロックあり
					</label>
				</td>
				<td>
					<form:errors path="accountNonLocked" cssStyle="color: red;" />
				</td>
			</tr>
			<tr>
				<th>利用者権限</th>
				<td>
					<c:forEach var="authority" items="${authorityList}">
						<label>
							<form:checkbox path="authorityIds" value="${authority.authorityId}" />
							${authority.authorityName}
						</label>
						<br />
					</c:forEach>
				</td>
				<td>
					<form:errors path="authorityIds" cssStyle="color: red;" />
				</td>
			</tr>
		</table>
		<br>
		<input type="submit" value="登録" />
	</form:form>
	<br>
	<form method="get" action="${pageContext.request.contextPath}/admin/users">
		<input type="submit" value="戻る">
	</form>
	<hr />
	<a href="<c:url value='/'/>">TOP</a>
</body>
</html>