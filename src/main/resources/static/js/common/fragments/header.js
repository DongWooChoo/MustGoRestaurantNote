$(document).ready(function () {
    let currentPath = window.location.pathname; // 현재 URL 경로 가져오기

    $(".menu-item").each(function () {
        if ($(this).attr("href") === currentPath) {
            $(this).addClass("active"); // 현재 페이지와 일치하면 'active' 클래스 추가
        }
    });
});
