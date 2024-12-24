let recognition;
let isRecording = false;

document.addEventListener("DOMContentLoaded", function () {
    getCurrentLocation();

    var alertMessage = document.getElementById("toastMessage") ? document.getElementById("toastMessage").innerText : null;

    if (alertMessage) {
        var toastElement = document.getElementById("liveToast");
        var toast = new bootstrap.Toast(toastElement);
        toast.show();
    }
});

//위치 가져오기
function getCurrentLocation() {
    if ("geolocation" in navigator) {
        navigator.geolocation.getCurrentPosition(
            (position) => {
                const latitude = position.coords.latitude;
                const longitude = position.coords.longitude;

                document.getElementById("latitude").value = latitude;
                document.getElementById("longitude").value = longitude;
            },
            (error) => {
                alert("위치를 가져오는 데 실패했습니다. 위치 권한을 확인하세요."); // 에러 처리
                console.error(error);
            }
        );
    } else {
        alert("브라우저가 위치 정보를 지원하지 않습니다.");
    }
}

//녹음 버튼 이벤트 핸들러
function toggleRecognition() {
    if (isRecording) {
        // 음성 인식 중지
        recognition.stop();
        document.getElementsByClassName("record_lable")[0].innerText = "녹음"; // 버튼 텍스트 변경
        console.log("음성 인식이 종료되었습니다.");
        isRecording = false;
    } else {
        document.getElementsByClassName("record_lable")[0].innerText = "녹음 종료"; // 버튼 텍스트 변경
        startRecognition();
        isRecording = true;
    }
}

// 녹음 시작
function startRecognition() {
    recognition = new (window.SpeechRecognition || window.webkitSpeechRecognition)();
    recognition.lang = "ko-KR";
    console.log("시작");
    recognition.continuous = true;
    recognition.interimResults = true;

    recognition.addEventListener("speechstart", () => {
        console.log("음성을 듣고 있습니다.");
    });

    recognition.addEventListener("result", (e) => {
        const transcript = e.results[0][0].transcript;
        document.getElementById("request").value = transcript;
        console.log("음성 결과: ", transcript);
    });

    recognition.addEventListener("error", (e) => {
        console.error("음성 인식 오류: ", e);
        alert("음성 인식에 오류가 발생했습니다.");
    });

    recognition.addEventListener("end", () => {
        console.log("음성 인식 종료");
    });

    recognition.start();
}
