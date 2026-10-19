$(function () {
    $('.mypage-tab-btn').on('click', function () {
        const target = $(this).data('tab'); // data-tab 속성값 (myPosts, myReplies, myLikes)

        // 버튼 active 상태 전환
        $('.mypage-tab-btn').removeClass('active');
        $(this).addClass('active');

        // 패널 전환
        $('.mypage-panel').removeClass('active');
        $('#' + target).addClass('active');
    });
});