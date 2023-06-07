// script.js 파일

function validateAccess() {
    alert("Hack The Box 회원만 접근 가능합니다!");
    location.href = "/hackthebox/login";
}

function writeAcess() {
    alert("잘못된 요청입니다.");
    location.href = "/hackthebox/login";
}
