<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
	<body>
	
	<%@ include file="/include/header.jsp" %>
	
	<section class="visual">
		<div id="carouselExampleIndicators" class="carousel slide">
			<div class="carousel-indicators">
			  <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
			  <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
			  <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
			</div>
			<div class="carousel-inner">
			  <div class="carousel-item active">
			    <img src="${pageContext.request.contextPath}/images/visual.jpg" class="d-block w-100" alt="...">
			  </div>
			  <div class="carousel-item">
			    <img src="${pageContext.request.contextPath}/images/visual2.jpg" class="d-block w-100" alt="...">
			  </div>
			  <div class="carousel-item">
			    <img src="${pageContext.request.contextPath}/images/visual3.jpg" class="d-block w-100" alt="...">
			  </div>
			</div>
			<button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
			  <span class="carousel-control-prev-icon" aria-hidden="true"></span>
			  <span class="visually-hidden">Previous</span>
			</button>
			<button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
			  <span class="carousel-control-next-icon" aria-hidden="true"></span>
			  <span class="visually-hidden">Next</span>
			</button>
		</div>
	</section>
	
	<!-- 컨텐츠 영역 : 최신글 인기글 들어갈 자리 -->
	<main class="content">
		<section class="content-box">
			<h2 class="sectionin-title">최신글</h2>
			<ul class="post-card-list">
			<c:forEach var="n" items="${newList}" begin="0" end="2">
				<li class="post-card">
					<c:choose>
                	<c:when test="${not empty n.imgPath}">
                		<img src="/imgs/${n.imgPath}" alt="게시글 썸네일" class="post-thumb">
                	</c:when>
                	<c:otherwise>
                   		<img src="${pageContext.request.contextPath}/images/default-thumb.jpg" alt="게시글 썸네일" class="post-thumb">
                	</c:otherwise>
               		</c:choose>
					<p class="post-title"><a href="${pageContext.request.contextPath}/blog/view?bno=${n.bno}">${n.title}</a></p>
					<p class="post-date">${n.regDate}</p>
				</li>
			</c:forEach>
			</ul>
		</section>
		<section class="content-box">
			<h2 class="sectionin-title">인기글</h2>
			<ul class="post-card-list">
			<c:forEach var="b" items="${poplist}" begin="0" end="2">
				<li class="post-card">
					<c:choose>
                	<c:when test="${not empty b.imgPath}">
                		<img src="/imgs/${b.imgPath}" alt="게시글 썸네일" class="post-thumb">
                	</c:when>
                	<c:otherwise>
                   		<img src="${pageContext.request.contextPath}/images/default-thumb.jpg" alt="게시글 썸네일" class="post-thumb">
                	</c:otherwise>
               		</c:choose>
					<p class="post-title"><a href="${pageContext.request.contextPath}/blog/view?bno=${b.bno}">${b.title}</a></p>
					<p class="post-date">${b.regDate}</p>
				</li>
			</c:forEach>
			</ul>
		</section>
	</main>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
	
<%@ include file="/include/footer.jsp" %>
