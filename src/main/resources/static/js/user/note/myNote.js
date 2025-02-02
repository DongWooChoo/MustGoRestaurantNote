// 페이지 로드 시 실행
$(document).ready(function () {

    // 식당 추가 버튼 클릭
    $('#addRestaurantBtn').on('click', function () {
        loadInsertRestaurantModal();
    });

    // 주소 검색 버튼 -> 다음(카카오) 도로명 주소 API
    $('#searchAddressBtn').on('click', function () {
        new daum.Postcode({
            oncomplete: function (data) {
                // 선택된 주소를 restaurantAddress input에 세팅
                $('#restaurantAddress').val(data.address);
            }
        }).open();
    });

    // 닫기 버튼 클릭 (모달)
    $('#topCloseModalBtn, #bottomCloseModalBtn').on('click', function () {
        clearInsertModalData();
        closeInsertRestaurantModal();
    });

    // 등록 버튼 클릭 (모달)
    $('#submitRestaurantBtn').on('click', function () {
        sendInsertRestaurantRequest();
    });
});


// 모달 창 나타내는 함수
function loadInsertRestaurantModal() {
    const $itemContainer = $('.item-container');
    const offset = $itemContainer.offset();
    const width = $itemContainer.outerWidth();
    const height = $itemContainer.outerHeight();

    // 모달 위치와 크기를 item-container와 동일하게 설정
    $('#insertRestaurantModal').css({
        top: offset.top + 'px',
        left: offset.left + 'px',
        width: width + 'px',
        height: height + 'px'
    });

    $('#insertRestaurantModal').fadeIn(200);
}

// 모달 창 숨기는 함수
function closeInsertRestaurantModal() {
    $('#insertRestaurantModal').fadeOut(200);
}

// 모달 창 내용 초기화 시키는 함수
function clearInsertModalData(){
    // 입력 필드 초기화
    $('#restaurantPostIdx').val('');
    $('#restaurantName').val('');
    $('#restaurantAddress').val('');
    $('#restaurantInfo').val('');
}

// 모달 창에서 작성한 식당 데이터를 서버로 전달하는 함수
function sendInsertRestaurantRequest() {
    const restaurantName = $('#restaurantName').val().trim();
    const restaurantInfo = $('#restaurantInfo').val().trim();
    const address = $('#restaurantAddress').val().trim();
    const postIdx = $('#restaurantPostIdx').val().trim();

    if (!restaurantName || !address) {
        alert("식당 이름과 주소는 필수입니다.");
        return;
    }

    if (postIdx) {
        // 수정 요청
        $.ajax({
            type: 'POST',
            url: updateNoteEndpoint,
            data: JSON.stringify({ postIdx, restaurantName, restaurantInfo, address }),
            contentType: 'application/json',
            success: function (response) {
                if (response.status === "Success") {
                    alert("수정 성공");
                    $('#insertRestaurantModal').fadeOut(200);
                    location.reload();
                } else {
                    alert("수정 실패: " + response.message);
                }
            },
            error: function (xhr, status, error) {
                console.error("식당 정보 수정 중 오류 발생 :", error);
                alert("수정 실패: 서버 오류");
            }
        });
    } else {
        $.ajax({
            // 신규 작성 요청
            type: 'POST',
            url: insertNoteEndpoint,
            data: JSON.stringify({restaurantName, restaurantInfo, address}),
            contentType: 'application/json',
            success: function (response) {
                if (response.status === "Success") {
                    alert("등록 성공");
                    $('#inserRestaurantModal').fadeOut(200);
                    location.reload(); // 새로고침하여 리스트 갱신
                } else {
                    alert("등록 실패: " + response.message);
                }
            },
            error: function (xhr, status, error) {
                console.error("식당 추가 중 오류 발생:", error);
                alert("등록 실패: 서버 오류");
            }
        });
    }
}
