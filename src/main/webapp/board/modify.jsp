<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/include/header.jsp"%>

<main class="write-wrap">
	
	<h2 class="write-page-title">글 수정</h2>
	<form id="modifyForm" class="write-form"
		  method="post"
		  enctype="multipart/form-data"
		  action="${pageContext.request.contextPath}/blog/update">
		  <input type="hidden" name="bno" value="${board.bno}">
		  
		  <div class="form-row">
		  	<label>작성자</label>
		  	<p class="write-author">${board.writerNickname}</p>
		  </div>
		  
		  <div class="form-row">
		  	<label for="title">제목</label>	<!-- 제목을 클릭했을 때 입력칸으로 커서가 넘어감-->
		  	<input type="text" id="title" name="title" class="form-input" value="${board.title}">
		  </div>
		  
		  <div class="form-row">
		  	<label>대표 이미지<span class="form-option">(선택)</span></label>
		  	<div class="image-upload-box">
		  		<img id="imgPreview" src="${not empty board.imgPath ? '/imgs/'.concat(board.imgPath) : pageContext.request.contextPath.concat('/images/default-thumb.jpg')}"
		  		alt="이미지 미리보기" class="img-preview">
		  		<div class="image-upload-btns">
		  			<label for="imgFile" class="btn btn-outline file-label">이미지 선택</label>
		  				<!-- file : 첨부파일 -->
		  			<input type="file" id="imgFile" name="imgFile" accept="image/*" class="file-input">
		  			<button type="button" id="imgRemoveBtn" class="btn btn-outline">이미지 제거</button>
		  		</div>
		  	</div>
		  </div>
		  
		  <div class="form-row">
		  	<label for="content">내용</label>
		  	<textarea rows="12" id="content" name="content" class="form-textarea">${board.content}</textarea>
		  	<!-- <textarea></textarea>는 줄 변경을 했을 시 공백이 생기므로 붙여서 작성. textarea는 value 속성이 없으므로 태그 사이에 입력-->
		  </div>
		  
		  <div class="write-btns">
		  	<a href="${pageContext.request.contextPath}/blog/view?bno=${board.bno}" class="btn btn-outline">취소</a>
		  	<button type="submit" class="btn btn-primary">수정 완료</button>
		  </div>
		  
	</form>

</main>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	<!-- 한 번만 코딩할 경우 헤더에 -->
	<!-- 반드시 위를 먼저 작성한 뒤 로딩해야 함 -->
<script src="${pageContext.request.contextPath}/js/write.js"></script>

<%@ include file="/include/footer.jsp"%>
