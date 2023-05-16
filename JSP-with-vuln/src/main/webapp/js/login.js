const form = document.getElementById("login-form");

form.addEventListener("submit", (event) => {
    event.preventDefault(); // 기본 제출 동작 막기

    const formData = new FormData(form);
    const username = formData.get("user_id");
    const password = formData.get("user_pw");

    fetch("/hackthebox/loginprocess", {
        method: "POST",
        headers: [["Content-Type", "application/json; charset=utf-8"]],
        body: JSON.stringify({
            user_id: username,
            user_pw: password,
        }),
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json();
        })
        .then((result) => {
            if (result.login === "success") {
                alert("로그인에 성공했습니다.");
                location.href = "/hackthebox/community";
                // sessionStorage.setItem("id", result.sessionId);
            } else if (result.login === "fail") {
                alert("로그인에 실패했습니다. 아이디 또는 패스워드를 확인해주세요.");
                location.href = "/hackthebox/api/login.jsp";
            }
        })
        .catch((error) => {
            console.error(error);
            alert("로그인 중 오류가 발생했습니다. 다시 시도해주세요.");
        });
});
