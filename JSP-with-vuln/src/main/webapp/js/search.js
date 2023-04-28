function inputHTML() {
    const buttons = document.querySelectorAll("#search-contents button");
    // input 태그 선택
    const input = document.querySelector("#address");
    // 각 버튼에 클릭 이벤트 리스너 등록
    buttons.forEach((button) => {
        button.addEventListener("click", () => {
            // 클릭된 버튼의 innerHTML 값을 가져와 input 태그에 반영
            input.value = button.innerHTML;
        });
    });
}

function search_form() {
    // form
    const form = document.getElementById("search-form");

    form.addEventListener("submit", (event) => {
        event.preventDefault(); // 기본 제출 동작 막기

        const formData = new FormData(form);
        const address = formData.get("address");
        const encodedURL = encodeURIComponent(address);
        const url = "/hackthebox/searchprocess?address=" + encodedURL;
        fetch(url, {
            method: "GET",
        })
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then((result) => {
                if (result.search === "success") {
                    const searchContents = document.getElementById("search-contents");
                    const dialog = document.getElementById("modal-dialog");
                    const count = Object.values(result.address).length; // 생성할 버튼 갯수
                    const button_check = document.querySelectorAll(".check-button");
                    if (button_check.length > 0) {
                        // 이미 버튼이 존재하면 기존 버튼 삭제
                        for (let i = 0; i < button_check.length; i++) {
                            button_check[i].parentNode.removeChild(button_check[i]);
                        }
                    }

                    for (let i = 0; i < count; i++) {
                        const button = document.createElement("button");
                        button.classList.add("btn", "btn-success", "btn-block", "mt-3", "check-button");
                        button.dataset.dismiss = "modal";
                        button.dataset.toggle = "modal";

                        if (i === 0) {
                            button.innerHTML = result.address[i];
                        } else if (i === count - 1) {
                            button.innerHTML = result.address[i];
                        } else if (i % 2 === 0) {
                            button.innerHTML = result.address[i];
                        } else if (i % 2 !== 0) {
                            button.innerHTML = result.address[i];
                        }
                        searchContents.appendChild(button);
                    }

                    if (count > 5) {
                        dialog.style.marginTop = "2%"; // modal 위치 조정
                    } else {
                        dialog.style.marginTop = "-11%"; // modal 위치 조정
                    }

                    inputHTML();
                } else if (result.search === "fail") {
                    alert("검색 결과가 없습니다.");
                }
            })
            .catch((error) => {
                console.error(error);
                alert('"강남구 선릉로 10길"과 같은 도로명 주소로 검색해주세요.');
            });
    });
}

search_form();
