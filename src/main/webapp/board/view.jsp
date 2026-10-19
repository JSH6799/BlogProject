<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

	<!-- header.jsp -->
	<%@ include file="/include/header.jsp" %>
	<!-- 동적 jsp 파일을 불러올 때에는 EL 표현식을 사용할 수 없다 -->
	<!-- / : root 밑에 있는 = 최상위 경로에 있는 -->
	
	<main class="view-wrap">
		<div class="view-header">
		<!-- 여백 조정 등을 위해 모두 그룹으로 나눔. 나누지 않아도 상관은 없음 -->
			<h2 class="view-title">${view.title}</h2>
			<p class="view-meta">작성자 ${view.writerNickname} | ${view.regDate} | 조회수 ${view.viewCnt}</p>
		</div>
		<c:if test="${not empty view.imgPath}">
		<div class="view-image">
			<img src="/imgs/${view.imgPath}" alt="" class="view-thumb">
		</div>
		</c:if>
		<div class="view-content">
			${view.content}
		</div>
		<div class="view-actions">
			<button type="button" id="likeBtn" class="btn btn-outline like-btn ${liked ? 'liked' : ''}">
										<!-- "btn btn-outline like-btn" : classList -->
				♥ 좋아요 <span id="likeCount">${view.likeCnt}</span>
			</button>
			<div class="view-actions-right">
				<a href="${pageContext.request.contextPath}/blog/update?bno=${view.bno}" class="btn btn-outline">수정</a>
				<a href="${pageContext.request.contextPath}/blog/delete?bno=${view.bno}" class="btn btn-outline" onclick="return confirm('삭제 하시겠습니까?')">삭제</a>
				<a href="${pageContext.request.contextPath}/blog/list" class="btn btn-primary">목록</a>
			</div>
		</div>
			<!-- 댓글 영역 -->
			<div class="reply-section">
				<h3 class="reply-title">댓글<span id="replyCount">${fn:length(replylist)}</span></h3>
				<ul class="reply-list" id="replylist">
				<c:forEach var="r" items="${replylist}">
					<li class="reply-item" data-rno="${r.rno}">
						<div class="reply-info">
							<span class="reply-writer">${r.nickName}</span>
							<span class="reply-date">${r.regDate}</span>
						</div>
						<p class="reply-content">${r.content}</p>
						<div class="reply-btns">
							<button type="button" class="reply-edit-btn">수정</button>
							<button type="button" class="reply-delete-btn">삭제</button>
						</div>
					</li>
				</c:forEach>
				</ul>
				
				<div class="reply-form">
					<input type="text" id="replyContent" placeholder="댓글을 입력하세요" class="reply-input">
					<!-- placeholder : 입력창에 설명글 삽입 -->
					<button type="button" id="replySubmitBtn" class="btn btn-primary">등록</button>
				</div>
				
			</div>
			
			<!-- Modal -->
			<div class="modal fade" id="replyModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
			  <div class="modal-dialog">
			    <div class="modal-content">
			      <div class="modal-header">
			        <h1 class="modal-title fs-5" id="exampleModalLabel">댓글 수정</h1>
			        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			      </div>
			      <div class="modal-body">
			        <input type="hidden" id="modalRno">
			        <textarea id="modalContent" class="form-control" row="4"></textarea>
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
			        <button type="button" class="btn btn-primary" id="modalSaveBtn">Save changes</button>
			      </div>
			    </div>
			  </div>
			</div>
			
	</main>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
	
	<script>
		// jQuery : 자바스크립트 언어로 만들어진 함수를 모아놓은 라이브러리
		// -- 좋아요 버튼 처리 --
		$("#likeBtn").on("click",function() {
			const bno = ${view.bno}
			$.ajax({
				url:"${pageContext.request.contextPath}/blog/like",
				type:"post",
				data:{bno:bno},
				dataType:"json",
				success:function(result){
					if(!result.success){
						alert(result.message);
						return;
					}
					$("#likeCount").text(result.likeCount);
					$("#likeBtn").toggleClass("liked",result.liked);
				},error:function(){
					alert("통신 실패");
				}
			})
			
		})
		
   //---댓글 등록---
   $("#replySubmitBtn").on("click", function() {
      const content = $("#replyContent").val().trim();
      if(content == '') {
         alert("댓글 내용을 입력하세요");
         return;
      }
      
      $.ajax({
    	  url:"${pageContext.request.contextPath}/reply/write",
    	  type:'post',
    	  data:{bno:"${view.bno}", content:content},
    	  dataType:'json',
    	  success:function(result){
    		  if(!result.success){
    			  alert(result.message || '댓글 등록 실패')
    			  return
    		  }
    		  
		      // '' 가 아니라 `벡틱`
		      // 벡틱 : jsp 언어와 자바 스크립트 언어를 혼용해서 사용할 수 있음
		      // .js 파일에서 사용해야 함
		      // .jsp에서 사용하기 위해서는 \
		      
		      const newItem=`
		            <li class="reply-item" data-rno="\${result.rno}">
		               <div class="reply-info">
		                  <span class="reply-writer">\${result.writerNickname}</span>
		                  <span class="reply-date">방금전</span>
		               </div>
		               <p class="reply-content">\${result.content}</p>
		               <div class="reply-btns">
		                  <button type="button" class="reply-edit-btn">수정</button>
		                  <button type="button" class="reply-delete-btn">삭제</button>
		               </div>
		            </li>
		      `;
		      
		      $("#replylist").append(newItem);
		      $("#replyContent").val('');
		      
    	  },error:function(){
    		  alert("댓글 등록 중 오류 발생");
    	  }
      })
      
   });
   
   //---댓글 삭제 ---
   $("#replylist").on("click", '.reply-delete-btn', function() {
      if(confirm('댓글을 삭제하시겠습니까?')) {
         // 실제 연동시 rno값을 data-rno에서 꺼내서 비동기식으로 처리
         // 임시로 어떤 값을 저장해둘 때 태그 옆에 data- 속성을 사용
         // .closest() : 클래스명이 ()안에 들어간 것과 같은 태그 전체
         const $item = $(this).closest('.reply-item');	// 클릭한 li 요소 전체
         const rno = $item.data('rno');	// li 태그의 data-rno 속성 값
         
         $.ajax({
        	 url:"${pageContext.request.contextPath}/reply/delete",
        	 type:'post',
        	 data:{rno:rno},	// 서버로 보내는 데이터
        	 dataType:'json',	// 서버에서 보내는 데이터는 반드시 json형식으로 보내라
        	 success:function(result){
        		 if(result.success){
        			 $item.remove();	// 서버에서 실제로 지워졌으면 화면에서 제거
        		 }else{
        			 alert(result.message);
        		 }
        	 },error:function(){
        		 alert("댓글 삭제 중 통신 오류");
        	 }
         })
         
      }
   })
   
   //---댓글 수정 ---
   const replyModal = new bootstrap.Modal(document.getElementById('replyModal'));
   document.getElementById('replyModal').addEventListener('show.bs.modal',function(){
	   $('#modalContent').focus();
   });
   
   $("#replylist").on("click", '.reply-edit-btn', function() {
      const $item = $(this).closest('.reply-item');
      $('#modalRno').val($item.data('rno'));
      $('#modalContent').val($item.find('.reply-content').text());
      replyModal.show();
   });
   
   $('#modalSaveBtn').on("click", function(){
	   const rno = $('#modalRno').val();
	   const newContent = $('#modalContent').val().trim();
	   if(newContent == ''){
		   alert('댓글 내용을 입력하세요');
		   return;
	   }
	   
	   $.ajax({
		   	url:'${pageContext.request.contextPath}/reply/modify',
		   	type:'post',
		   	data:{rno:rno,content:newContent},
		   	dataType:'json',
		   	success:function(result){
		   		if(result.success){
		   			$('#replylist .reply-item[data-rno="'+rno+'"]')
		   				.find(".reply-content").text(result.content);
		   			replyModal.hide();
		   		}else{
		   			alert(result.message);
		   		}
		   	},error:function(){
		   		alert("통신 실패");
		   	}
	   })
   })
		
		
	// 자바스크립트
		/* document.getElementById("likeBtn").addEventListener("click",function(){
			// getElementById(" ") : 해당 id를 가진 태그를 가져와라
			// addEventListener : 마우스와 관련된 이벤트(->click)가 발생했을 때 함수{}를 실행하라
			// 변수선언 : const, var, let
			
			let likeBtn = this;
			// 현재 자기 자신(this)이 클릭한 데이터를 likeBtn이라는 변수에 저장하라
			
			likeBtn.classList.toggle("liked");
			// classList : likeBtn이라는 버튼 태그의 클래스 항목
			// toggle() : 한영키와 같은 기능을 가진 함수
			// 클릭 이벤트가 발생하면 likeBtn이라는 클래스 항목에 liked라는 클래스를 추가하고, 재클릭 시 liked를 삭제하라
			// => liked 클래스가 있으면 제거하고, 없으면 추가된다
			
			let count = parseInt(document.getElementById("likeCount").textContent);
			// textContent : 해당 id를 가진 태그에 있는 텍스트를 가져옴. 숫자도 모두 문자열로 가져옴
			// parseInt : 문자를 숫자로 형변환
			
			if(likeBtn.classList.contains("liked")){
				// contains() : ()안에 있는 것을 포함 -> liked 클래스를 포함하고 있으면
				count = count + 1;
				document.getElementById("likeCount").textContent = count;
			}else{
				document.getElementById("likeCount").textContent = count - 1;
			}
		}) */
		
		
		
	</script>
	
	<!-- footer.jsp -->
	<%@ include file="/include/footer.jsp" %>