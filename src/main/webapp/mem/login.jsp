<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/include/header.jsp"%>

<main class="login-wrap">

        <h2 class="login-page-title">로그인</h2>
        
        <c:if test="${not empty loginFail}">
			<p class="login-error-msg">${loginFail}</p>
				<!-- el표현식 : loginFail에 있는 값을 출력 -->
		</c:if>
		
        <form id="loginForm" class="login-form"
              action="${pageContext.request.contextPath}/member/login" method="post">

            <div class="form-row">
                <label for="id">아이디</label>
                <input type="text" id="id" name="id" placeholder="아이디를 입력하세요" class="form-input">
            </div>

            <div class="form-row">
                <label for="pwd">비밀번호</label>
                <input type="password" id="pwd" name="pwd" placeholder="비밀번호를 입력하세요" class="form-input">
            </div>

            <button type="submit" class="btn btn-primary login-submit-btn">로그인</button>

            <p class="login-footer-link">
                아직 계정이 없으신가요?
                <a href="${pageContext.request.contextPath}/member/join">회원가입</a>
            </p>

        </form>

</main>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/login.js"></script>

<%@ include file="/include/footer.jsp"%>
