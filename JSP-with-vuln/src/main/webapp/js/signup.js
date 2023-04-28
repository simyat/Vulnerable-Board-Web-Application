const form = document.getElementById("signup-form");

form.addEventListener("submit", (event) => {
    event.preventDefault(); // 기본 제출 동작 막기

    const formData = new FormData(form);
    const username = formData.get("user_id");
    const name = formData.get("name");
    const password = formData.get("user_pw");
    const address = formData.get("address");

    fetch("/hackthebox/signupprocess", {
        method: "POST",
        headers: [["Content-Type", "application/json; charset=utf-8"]],
        body: JSON.stringify({
            user_id: username,
            name: name,
            user_pw: password,
            address: address,
        }),
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json();
        })
        .then((result) => {
            if (result.signup === "success") {
                location.href = "/hackthebox/board/board.jsp";
                sessionStorage.setItem("id", result.sessionId);
            } else if (result.signup === "duplication") {
                alert("이미 가입한 아이디입니다.");
            }
        })
        .catch((error) => {
            console.error(error);
            alert("회원가입 중 오류가 발생했습니다. 다시 시도해주세요.");
        });
});
