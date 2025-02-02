$(document).ready(function () {
    // 현재 위치를 기반으로 지도 초기화
    getCurrentLocation(
        function (position) {
            const userLat = position.coords.latitude;
            const userLng = position.coords.longitude;
            initializeMap(userLat, userLng); // 지도 초기화
            loadRestaurantData(userLat, userLng); // 데이터 로드
        },
        function () {
            const defaultLat = 37.5665; // 서울 시청 기준
            const defaultLng = 126.9780;
            initializeMap(defaultLat, defaultLng); // 기본 위치 지도 초기화
            loadRestaurantData(defaultLat, defaultLng); // 데이터 로드
        }
    );
    // 내 위치 버튼 이벤트 등록
    $('#findMyLocation').on('click', function () {
        getCurrentLocation(
            function (position) {
                const userLat = position.coords.latitude;
                const userLng = position.coords.longitude;
                initializeMap(userLat, userLng); // 지도 초기화
                loadRestaurantData(userLat, userLng); // 데이터 로드
            },
            function () {
                alert("현재 위치를 가져올 수 없습니다.");
            }
        );
    });
});

// 지도 객체 전역 변수
let map;
//현재 마커 정보창 전역 변수
let currentInfoWindow = null;
//마커를 관리하는 객체
let markers = {};

// 지도 초기화 함수
function initializeMap(lat, lng) {
    map = new naver.maps.Map("map", {
        center: new naver.maps.LatLng(lat, lng),
        zoom: 15,
    });

    // 현재 위치에 마커 추가
    new naver.maps.Marker({
        position: new naver.maps.LatLng(lat, lng),
        map: map,
        title: "현재 위치",
        icon: {
            content: '<div style="width: 20px; height: 20px; background-color: red; border-radius: 50%; border: 2px solid white;"></div>',
            anchor: new naver.maps.Point(10, 10), // 중심점 조정
        }
    });
}

// 식당 데이터 로드 함수
function loadRestaurantData(userLat, userLng) {
    $.ajax({
        type: 'POST',
        url: getNoteEndpoint,
        contentType: 'application/json',
        success: function (data) {
            const restaurantWithDistance = [];
            const promises = data.map(restaurant => {
                return new Promise(resolve => {
                    getCoordinates(restaurant.address, (lat, lng) => {
                        if (lat && lng) {
                            const distance = calculateDistance(userLat, userLng, lat, lng);
                            restaurantWithDistance.push({
                                ...restaurant,
                                lat,
                                lng,
                                distance,
                            });
                        }
                        resolve();
                    });
                });
            });

            Promise.all(promises).then(() => {
                // 거리순 정렬
                restaurantWithDistance.sort((a, b) => a.distance - b.distance);

                // 왼쪽의 식당 리스트와 지도 속 마커 업데이트
                updateRestaurantList(restaurantWithDistance);
                restaurantWithDistance.forEach(restaurant => {
                    addMarker(restaurant.lat, restaurant.lng, restaurant.restaurantName, restaurant.address, restaurant.restaurantInfo, restaurant.distance);
                });
            });
        },
        error: function (xhr, status, error) {
            console.error("식당 정보를 가져오던 중 오류가 발생했습니다. :", error);
        }
    });
}

// 주소를 경위도로 변환하는 함수
function getCoordinates(address, callback) {
    naver.maps.Service.geocode({ query: address }, function(status, response) {
        if (status === naver.maps.Service.Status.OK) {
            const result = response.v2.addresses[0];
            callback(parseFloat(result.y), parseFloat(result.x));
        } else {
            console.error(`주소 정보를 가져오던 중 오류가 발생했습니다. : ${address}`);
            callback(null, null);
        }
    });
}

// 거리 계산 함수 (Haversine Formula)
function calculateDistance(lat1, lng1, lat2, lng2) {
    const R = 6371; // 지구 반지름 (km)
    const dLat = toRad(lat2 - lat1);
    const dLng = toRad(lng2 - lng1);
    const a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
        Math.sin(dLng / 2) * Math.sin(dLng / 2);
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return R * c;
}

// 라디안 변환 함수
function toRad(value) {
    return value * Math.PI / 180;
}

// 리스트 UI 업데이트
function updateRestaurantList(restaurants) {
    const listContainer = $('#listContainer');
    listContainer.empty();

    restaurants.forEach(restaurant => {
        const listItem = $(`
            <div class="list-item" data-lat="${restaurant.lat}" data-lng="${restaurant.lng} data-postIdx="${restaurant.postIdx}">
                <h3>${restaurant.restaurantName}</h3>
                <p>${restaurant.address}</p>
                <p>${restaurant.distance.toFixed(2)} km</p>
                <div class="list-button-group">
                    <button class="btn btn-success edit-btn">수정</button>
                    <button class="btn btn-danger delete-btn">삭제</button>
                </div>
            </div>`
        );

        // 수정 버튼 클릭 이벤트
        listItem.find('.edit-btn').on('click', function () {
            // 식당 정보 삽입할 수 잇는 모달 창 로드시, 기존 데이터 표시
            $('#restaurantName').val(restaurant.restaurantName);
            $('#restaurantAddress').val(restaurant.address);
            $('#restaurantInfo').val(restaurant.restaurantInfo);
            $('#restaurantPostIdx').val(restaurant.postIdx);

            loadInsertRestaurantModal();
        });

        // 삭제 버튼 클릭 이벤트
        listItem.find('.delete-btn').on('click', function () {
            if (confirm(`${restaurant.restaurantName}을(를) 삭제하시겠습니까?`)) {
                deleteRestaurantInfo(restaurant.postIdx, restaurant.restaurantName);
            }
        });
        listContainer.append(listItem);
    });

    // 리스트 아이템에 마우스 이벤트 바인딩
    bindMouseEvents();
}

// 식당 정보를 삭제 요청을 보내는 메서드
function deleteRestaurantInfo(postIdx, restaurantName){
    $.ajax({
        type: 'POST',
        url: deleteNoteEndpoint,
        contentType: 'application/json',
        data: JSON.stringify({ postIdx }),
        success: function (response) {
            if(response.status === "Success") {
                alert(`${restaurantName} 삭제 완료`);
                location.reload();
            }
            else {
                alert(`${restaurantName} 삭제 중 오류 발생`);
            }
        },
        error: function (xhr, status, error) {
            console.error("식당 정보 삭제 중 오류 발생 :", error);
        }
    });
}

// 마우스 이벤트 처리
function bindMouseEvents() {
    $('.list-item').on('click', function () {
        const lat = parseFloat($(this).data('lat'));
        const lng = parseFloat($(this).data('lng'));
        const id = `${lat}-${lng}`; // 고유 식별자로 경위도를 사용

        if (!isNaN(lat) && !isNaN(lng) && markers[id]) {
            const { marker, infoWindow } = markers[id];
            moveMapCenter(lat, lng); // 위치 이동
            // 기존 열려 있는 정보창 닫기
            if (currentInfoWindow) {
                currentInfoWindow.close();
            }

            // 현재 정보창 열기
            infoWindow.open(map, marker);
            currentInfoWindow = infoWindow;
        }
    });
}

// 지도 중심 이동 함수
function moveMapCenter(lat, lng) {
    if (map) {
        map.setCenter(new naver.maps.LatLng(lat, lng));
    } else {
        console.error("지도 객체가 초기화되지 않았습니다.");
    }
}

// 마커 추가 메서드
function addMarker(lat, lng, name, address, info, distance) {
    const id = `${lat}-${lng}`; // 고유 식별자 생성

    const marker = new naver.maps.Marker({
        position: new naver.maps.LatLng(lat, lng),
        map: map,
        title: name,
    });

    // InfoWindow 내용에 닫기 버튼 추가
    const infoWindowContent = `
        <div class="info-window">
            <button class="close-info-window">✖</button>
            <h4>식당명 : ${name}</h4>
            <p>주소 : ${address}</p>
            <p>정보 : ${info}</p>
            <p>현재위치로부터 거리 : ${distance.toFixed(2)} km</p>
        </div>
    `;

    const infoWindow = new naver.maps.InfoWindow({
        content: infoWindowContent,
    });

    // 마커 클릭 이벤트
    naver.maps.Event.addListener(marker, "click", function () {
        // 기존 열려 있는 정보창 닫기
        if (currentInfoWindow) {
            currentInfoWindow.close();
        }

        // 현재 정보창 열기
        infoWindow.open(map, marker);
        currentInfoWindow = infoWindow;
    });

    // 닫기 버튼 이벤트 처리
    setTimeout(() => {
        const closeButton = document.querySelector(".close-info-window");
        if (closeButton) {
            closeButton.addEventListener("click", function () {
                infoWindow.close();
                currentInfoWindow = null; // 현재 열린 정보창 상태 초기화
            });
        }
    }, 0);

    // 지도 클릭 시 정보창 닫기
    naver.maps.Event.addListener(map, "click", function () {
        if (currentInfoWindow) {
            currentInfoWindow.close();
            currentInfoWindow = null; // 열린 정보창 상태 초기화
        }
    });

    // 마커와 정보창을 markers 객체에 저장
    markers[id] = { marker, infoWindow };
}

// 현재 위치를 가져오는 함수
function getCurrentLocation(successCallback, errorCallback) {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(successCallback, errorCallback);
    } else {
        console.error("이 브라우저에서는 GeoLocation이 지원되지 않습니다.");
        errorCallback();
    }
}
