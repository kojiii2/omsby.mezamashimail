<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/css/style.css" type="text/css" />
<title>彼女からの目覚ましメール - 登録完了画面</title>
</head>
<body>
<h1>彼女からの目覚ましメール</h1>

<p>「<c:out value="${alarm.nickname}" />、確かに目覚まし依頼を預かったよ。<br/>
<fmt:formatDate value="${alarm.wakeupDate}" pattern="d日のH時mm分" timeZone="Asia/Tokyo" />に<c:out value="${alarm.email}" />あてに、<br/>
目覚ましメールを送るね。</p>
<p>それじゃあ私、これから寝るからね。メール出したら、絶対起きてよ！」</p>

</body>
</html>