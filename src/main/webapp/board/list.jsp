<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/include/header.jsp" %>
	
	<!-- 검색창, 글쓰기 버튼 -->
	<main class="list-wrap">
		<div class="list-top">
			<form class="search-form" action="${pageContext.request.contextPath}/blog/list" method="get">
				<select name="searchType" class="search-select">
					<option value="">선택</option>
					<option value="title" ${searchType == 'title' ? 'selected' : ''}>제목</option>
					<option value="content" ${searchType == 'content' ? 'selected' : ''}>내용</option>
					<option value="writer" ${searchType == 'writer' ? 'selected' : ''}>글쓴이</option>
				</select>
				<input type="text" name="keyword" value="${keyword}" placeholder="검색어를 입력하세요" class="search-input">
				<button type="submit" class="btn btn-outline">검색</button>
			</form>
				<a href="${pageContext.request.contextPath}/blog/write" class="btn btn-primary">글쓰기</a>
		</div>
		
		<!-- 게시글 카드 목록 -->
		<ul class="board-grid">
		<c:forEach var="b" items="${boardList}">
			<li class="board-card">
				<c:choose>
					<c:when test="${not empty b.imgPath}">
						<img src="/imgs/${b.imgPath}" alt="게시글 썸네일" class="board-thumb">
					</c:when>
					<c:otherwise>
						<img src="${pageContext.request.contextPath}/images/default-thumb.jpg" alt="게시글 썸네일" class="board-thumb">
					</c:otherwise>
				</c:choose>
					<div class="board-info">
						<p class="board-title"><a href="${pageContext.request.contextPath}/blog/view?bno=${b.bno}">${b.title}</a></p>
						<p class="board-meta">작성자 ${b.writerNickname} | ${b.regDate}</p>
						<p class="board-stat">조회수 ${b.viewCnt} | 좋아요 ${b.likeCnt}</p>
					</div>
			</li>
		</c:forEach>
		</ul>
		
		<!-- 페이지 처리. 어려움!! -->
		<div class="pagination">
		<!-- 이전 버튼 -->
		<c:choose>
			<c:when test="${startPage > 1}">
				<c:url var="preUrl" value="/blog/list">
					<c:param name="page" value="${startPage - 1}"/>
					<c:param name="searchType" value="${searchType}"/>
					<c:param name="keyword" value="${keyword}"/>
				</c:url>
				<a href="${preUrl}" class="page-nav">이전</a>
			</c:when>
			<c:otherwise>
				<span class="page-nav disabled">이전</span>
			</c:otherwise>
		</c:choose>
		<!-- 페이지 번호 -->
		<c:forEach var="p" begin="${startPage}" end="${endPage}">
			<c:url var="pageUrl" value="/blog/list">
				<c:param name="page" value="${p}"/>
				<c:param name="searchType" value="${searchType}"/>
				<c:param name="keyword" value="${keyword}"/>
			</c:url>
			<a href="${pageUrl}" class="page-num ${p==currentPage?'active':''}">${p}</a>
		</c:forEach>
		<!-- 다음 버튼 -->
		<c:choose>
			<c:when test="${endPage < totalPage}">
				<c:url var="nextUrl" value="/blog/list">
					<c:param name="page" value="${endPage + 1}"/>
					<c:param name="searchType" value="${searchType}"/>
					<c:param name="keyword" value="${keyword}"/>
				</c:url>
				<a href="${nextUrl}" class="page-nav">다음</a>
			</c:when>
			<c:otherwise>
				<span class="page-nav disabled">다음</span>
			</c:otherwise>
		</c:choose>
		</div>
	</main>
	
	<%@ include file="/include/footer.jsp" %>
	