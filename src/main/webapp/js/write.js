$(function(){	// jQuery 시작을 알리는 함수
	// 이미지 선택 시 미리보기
	const defaultImg = $("#imgPreview").attr('src');
	// #imgPreview 태그의 src attr(속성)을 가져와 defaultImg 변수에 저장하라
	$("#imgFile").on("change",function(){
		// on("change" : 이미지 변경 이벤트 발생 = 값이 변경이 되면
		const file = this.files[0];
		// this.files[0] : 첨부시킨 0번째 방에 저장된 파일
		// input type="file"은 배열의 형태로 저장됨
		if(!file) return;
		// 첨부파일이 없으면 함수를 실행하지 않고 종료
		// 브라우저 메모리 안에서 임시 URL을 만들어 미리보기에 반영
		const previewUrl = URL.createObjectURL(file);
		// 첨부한 파일을 브라우저의 임시기억장소에 저장 후 임시기억장소의 주소를 불러와라
		$("#imgPreview").attr("src",previewUrl);
		// attr()에 2개 입력 시 대치. previewUrl에 저장된 주소를 src에 바꿔 넣어라
		$("#removeImg").val("false");
		// 제거 누르고 다시 새 파일 선택하면 제거 요청 취소
	})
	// 이미지 제거
	$("#imgRemoveBtn").on("click",function(){
		$("#imgFile").val('');
		$("#imgPreview").attr("src",defaultImg);
		$("#removeImg").val('true');
		// modify.jsp에만 있는 hidden. write.jsp엔 없다.
	})
	
	$("#writeForm").on('submit',function(e){
		const title = $("#title").val().trim();
		const content = $("#content").val().trim();
		if(title === ''){	// === : 완전히 같으면
			alert("제목을 입력하세요");
			e.preventDefault();
			// 화면 이동 금지
			$("#title").focus();
			return;
		}
		if(content == ''){	// == : 같으면
			alert("내용을 입력하세요");
			e.preventDefault();
			$("#content").focus();
			return;
		}
	})
	
	
	
})

