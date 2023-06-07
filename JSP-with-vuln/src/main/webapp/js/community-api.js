function location_function(){
    location.href="/hackthebox/community/write";
}

function modify(id) {
    location.href="/hackthebox/community/modify?id="+id;
}

function delete_post(id){
    location.href="/hackthebox/community/delete?id="+id;
}

function community(){
    location.href="/hackthebox/community";
}

function current_date(){
    var date = new Date().toLocaleDateString();
    document.getElementById("current_date").value = date;
}