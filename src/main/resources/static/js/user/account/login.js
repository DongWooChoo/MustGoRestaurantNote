$(document).ready(function () {
    // 버튼 클릭 이벤트 처리
    $('#login-form').on('submit', function (event) {
        event.preventDefault(); // 기본 form 제출 방지
        processLogin();
    });
});

function processLogin(){

    // 입력된 데이터 가져오기
    const userId = $('#userId').val();
    const userPw = $('#userPw').val();

    if(!validateInputs(userId,userPw)){
        return;
    }

    // AJAX 요청 보내기
    $.ajax({
        url: '/user/processLogin', // 서버 요청 URL
        method: 'POST',
        contentType: 'application/json', // 요청 데이터 타입
        data: JSON.stringify({ userId: userId, userPw: userPw }), // 전송 데이터
        success: function (response) {
            // 성공 처리
            if(response.status === "success"){
                window.location.href = response.redirectUrl;
            }
            // 실패 처리
            else{
                alert("로그인 실패: " + response.message)
            }
        },
        error: function (xhr, status, error) {
            // 오류 처리
            alert('로그인 실패: ' + xhr);
            console.error(error);
        }
    });
}

function validateInputs(userId, userPw) {
    if (!userId) {
        alert('아이디를 입력하세요.');
        return false;
    }
    if (!userPw) {
        alert('비밀번호를 입력하세요.');
        return false;
    }
    return true;
}
