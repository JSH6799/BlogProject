<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>

<html>
	<head>
		<title>블로그 프로젝트</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" 
			  integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
		<link href="${pageContext.request.contextPath}/css/common.css" rel="stylesheet">
		<link href="${pageContext.request.contextPath}/css/mystyle.css" rel="stylesheet">
		<!-- context : 프로젝트 이름 -->
		<!-- ${pageContext.request.contextPath}/ 최상위 폴더
			EL 표현식 -> 현재 페이지의 프로젝트 이름을 찾아서 루트 경로(최상위 경로)로 사용하겠다 -->
	</head>
 <header class="site-header">
	<div class="header-inline">
		<h1 class="logo"><a href="${pageContext.request.contextPath}/main/">MY Blog</a></h1>
		<nav class="gnb">
			<ul>
				<li><a href="${pageContext.request.contextPath}/blog/list">글목록</a></li>
				<li><a href="${pageContext.request.contextPath}/blog/popular">인기글</a></li>
			</ul>
		</nav>
		<div class="member-menu">
			<c:choose>
				<c:when test="${empty sessionScope.loginMember}">
						<!-- session 범위 안에서 loginMember 값이 비어있으면 -->
					<a href="${pageContext.request.contextPath}/member/login">로그인</a>
					<a href="${pageContext.request.contextPath}/member/join">회원가입</a>
				</c:when>
				<c:otherwise>
					<span>${sessionScope.loginMember.nickname} 님 </span>
					<a href="${pageContext.request.contextPath}/member/logout">로그아웃</a>
					<a href="${pageContext.request.contextPath}/member/mypage">마이페이지</a>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</header>
<body>
