$(function () {
    $('#loginForm').on('submit', function (e) {
        if ($('#id').val().trim() === '') {
            alert('아이디를 입력하세요.');
            e.preventDefault();
            return;
        }
        if ($('#pwd').val().trim() === '') {
            alert('비밀번호를 입력하세요.');
            e.preventDefault();
            return;
        }
        // 여기까지 통과하면 실제 폼이 /member/login 으로 제출됨
    });
});