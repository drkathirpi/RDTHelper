function performLogin(){
    const username = $("#username").text();
    const password = $("#password").text();



    const settings = {
        url: "/api/login",
        data: JSON.stringify({ "username": username, "password": password}),
        timeout: 0,
    }


    $.ajax(settings).done(response => {
        console.log(response);
    }).fail(response => {
        console.log(response);
    });
}