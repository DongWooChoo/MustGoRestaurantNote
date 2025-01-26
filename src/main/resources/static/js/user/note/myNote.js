$(document).ready(function () {
    // 현재 위치를 기반으로 지도 초기화
    getCurrentLocation(
        function (position) {
            const userLat = position.coords.latitude;
            const userLng = position.coords.longitude;
            const map = initializeMap(userLat, userLng);
            loadRestaurants(map);
        },
        function () {
            const map = initializeDefaultMap();
            loadRestaurants(map);
        }
    );

    // 식당 추가 버튼 클릭
    $('#addRestaurantBtn').on('click', function() {
        // 모달 창 나타내기
        loadInsertRestaurantModal();
    });

    // 주소 검색 버튼 -> 다음(카카오) 도로명 주소 API
    $('#searchAddressBtn').on('click', function() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 선택된 주소를 restaurantAddress input에 세팅
                $('#restaurantAddress').val(data.address);
            }
        }).open();
    });

    // 닫기 버튼 클릭 (모달)
    $('#topCloseModalBtn').on('click', function() {
        // 모달 창 숨기기
        closeInsertRestaurantModal();
    });

    // 닫기 버튼 클릭 (모달)
    $('#bottomCloseModalBtn').on('click', function() {
        // 모달 창 숨기기
        closeInsertRestaurantModal();
    });

    // 등록 버튼 클릭 (모달)
    $('#submitRestaurantBtn').on('click', function() {
        sendInsertRestaurantRequest();
    });
});

// 모달 창 나타내는 함수
function loadInsertRestaurantModal(){

    const $itemContainer = $('.item-container');
    const offset = $itemContainer.offset();
    const width = $itemContainer.outerWidth();
    const height = $itemContainer.outerHeight();

    // 모달 위치와 크기를 item-container와 동일하게 설정
    $('#inserRestaurantModal').css({
        top: offset.top + 'px',
        left: offset.left + 'px',
        width: width + 'px',
        height: height + 'px'
    });

    $('#inserRestaurantModal').fadeIn(200);
}

// 모달 창 숨기는 함수
function closeInsertRestaurantModal(){
    $('#inserRestaurantModal').fadeOut(200);
}

function sendInsertRestaurantRequest() {
    const restaurantName = $('#restaurantName').val();
    const restaurantInfo = $('#restaurantInfo').val();
    const address = $('#restaurantAddress').val();

    if (!restaurantName || !address) {
        alert("식당 이름과 주소는 필수입니다.");
        return;
    }

    $.ajax({
        type: 'POST',
        url: '/note/insertMyNote',
        data: JSON.stringify({restaurantName, restaurantInfo, address}),
        contentType: 'application/json',
        success: function (response) {
            console.log(response.status);
            if(response.status === "Success"){
                alert("등록 성공");
                $('#inserRestaurantModal').hide();
            }
            // 실패 처리
            else{
                alert("등록 실패");
            }
        },
        error: function (xhr, status, error) {
            // 오류 처리
            const response = JSON.parse(xhr.responseText);
            console.log(response);
            if(response.status === "Fail") {
                alert("등록 실패 : " + response.message);
            }
            else {
                alert("등록 실패");
            }
        }
    });
}

// 현재 위치를 가져오는 함수
function getCurrentLocation(successCallback, errorCallback) {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(successCallback, errorCallback);
    } else {
        console.error("Geolocation is not supported by this browser.");
        errorCallback();
    }
}

// 네이버 지도를 초기화하는 함수
function initializeMap(lat, lng) {
    const map = new naver.maps.Map("map", {
        center: new naver.maps.LatLng(lat, lng),
        zoom: 15,
    });

    // 현재 위치에 마커 추가
    new naver.maps.Marker({
        position: new naver.maps.LatLng(lat, lng),
        map: map,
        title: "현재 위치",
    });

    return map;
}

// 기본 위치로 지도 초기화
function initializeDefaultMap() {
    console.warn("Using default location (Seoul City Hall).");
    return initializeMap(37.5665, 126.9780);
}

// 식당 리스트와 마커를 로드하는 함수
function loadRestaurants(map) {
    const restaurants = [
        { name: "00 식당", address: "경기도 파주 OO동", distance: 300, lat: 37.567, lng: 126.978 },
        { name: "00 밥상", address: "경기도 파주 OO동", distance: 350, lat: 37.568, lng: 126.979 },
    ];

    const $listContainer = $("#listContainer");
    $listContainer.empty(); // 기존 리스트 초기화

    $.each(restaurants, function (index, restaurant) {
        // 리스트에 추가
        const $listItem = $(`
            <div class="list-item">
                <h3>${restaurant.name}</h3>
                <p>${restaurant.address}</p>
                <p>거리: ${restaurant.distance}m</p>
            </div>
        `);
        $listContainer.append($listItem);

        // 마커 추가
        const marker = new naver.maps.Marker({
            position: new naver.maps.LatLng(restaurant.lat, restaurant.lng),
            map: map,
            title: restaurant.name,
        });

        // 마커 클릭 시 정보창 표시
        const infoWindow = new naver.maps.InfoWindow({
            content: `<div style="padding:10px;"><h4>${restaurant.name}</h4><p>${restaurant.address}</p></div>`,
        });

        naver.maps.Event.addListener(marker, "click", function () {
            infoWindow.open(map, marker);
        });
    });
}
