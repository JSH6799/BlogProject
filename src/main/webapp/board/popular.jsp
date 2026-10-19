<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="/include/header.jsp"%>

 <main class="popular-wrap">

        <h2 class="popular-page-title">인기글</h2>
        <p class="popular-sub">좋아요를 가장 많이 받은 글 순서로 보여줍니다</p>

        <ul class="popular-grid">
        <c:forEach var="b" items="${poplist}" varStatus="status">
            <li class="popular-card">
                <span class="rank-badge rank-${status.count}">${status.count}</span>
                <a href="${pageContext.request.contextPath}/blog/view?bno=${b.bno}">
                <c:choose>
                	<c:when test="${not empty b.imgPath}">
                		<img src="/imgs/${b.imgPath}" alt="게시글 썸네일" class="popular-thumb">
                	</c:when>
                	<c:otherwise>
                   		<img src="${pageContext.request.contextPath}/images/default-thumb.jpg" alt="게시글 썸네일" class="popular-thumb">
                	</c:otherwise>
                </c:choose>
                    <div class="popular-info">
                        <p class="popular-title">${b.title}</p>
                        <p class="popular-meta">작성자 ${b.writerNickname} · 좋아요 ${b.likeCnt}</p>
                    </div>
                </a>
            </li>
        </c:forEach>
        </ul>

</main>

<%@ include file = "/include/footer.jsp"%>