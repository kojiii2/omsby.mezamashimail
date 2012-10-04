<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/css/style.css" type="text/css" />
<title>彼女からの目覚ましメール</title>
</head>
<body>
	<form action="/index" method="post">
		<input type="hidden" name="currentYear"
			value="<c:out value="${currentYear}" />" />
		<h1>彼女からの目覚ましメール</h1>
		「何時に起こしてほしい？ メアドと呼び方も教えてね」
		<dl>
			<dt>
				メールアドレス：
				<c:if test="${email_error}">
					<span class="error">メアドを教えてくれないと、起こせないよ</span>
				</c:if>
			</dt>
			<dd>
				<input type="text" name="email" value="<c:out value="${email}" />"
					class="email" />
			</dd>
			<dt>
				呼び方：
				<c:if test="${nickname_error}">
					<span class="error">なんて呼べばいい？</span>
				</c:if>
			</dt>
			<dd>
				<input type="text" name="nickname"
					value="<c:out value="${nickname}" />" />
			</dd>
			<dt>
				起こしてもらう時間：
				<c:if test="${wakeupDate_error}">
					<span class="error">そんな日はないと思うな</span>
				</c:if>
			</dt>
			<dd>
				<%-- 年 --%>
				<select name="year">
					<c:forEach begin="0" end="１" var="i">
						<option <c:if test="${i + currentYear == year}">selected</c:if>>
							<c:out value="${i + currentYear}" />
						</option>
					</c:forEach>
				</select>年

				<%-- 月 --%>
				<select name="month">
					<c:forEach begin="0" end="11" var="i">
						<option value="<c:out value="${i}" />"
							<c:if test="${i == month}">selected</c:if>>
							<c:out value="${i + 1}" />
						</option>
					</c:forEach>
				</select>月

				<%-- 日 --%>
				<select name="date">
					<c:forEach begin="1" end="31" var="i">
						<option <c:if test="${i == date}">selected</c:if>>
							<c:out value="${i}" />
						</option>
					</c:forEach>
				</select>日

				<%-- 時間 --%>
				<select name="hourOfDay">
					<c:forEach begin="0" end="23" var="i">
						<option <c:if test="${i == hourOfDay}">selected</c:if>>
							<c:out value="${i}" />
						</option>
					</c:forEach>
				</select>時

				<%-- 分（５分おき） --%>
				<select name="minute">
					<c:forEach begin="0" end="59" step="5" var="i">
						<option <c:if test="${i == minute}">selected</c:if>>
							<c:out value="${i}" />
						</option>
					</c:forEach>
				</select>分
			</dd>
		</dl>
		<input type="submit" value="この時間に起こしてね" />
	</form>
</body>
</html>