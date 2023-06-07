function uploadFiles() {
    let url = "/hackthebox/community/upload";
    let file = document.getElementById("attachment").files[0];
    let data = new FormData();
    data.append('file', file);

    fetch(url, {
        method: "POST",
        body: data,
    })
        .then((response) => {
            if (response.ok) {
                response.json();
            }
        })
        .catch((error) => console.log(error));
}
