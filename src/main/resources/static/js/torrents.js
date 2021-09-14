// Get the modal
var modal = document.getElementById("myModal");
console.log(modal)
// Get the button that opens the modal
var btn = document.getElementById("myBtn");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks on <span> (x), close the modal
//span.onclick = function() {
//    modal.style.display = "none";
//}

$("#closeModalSpan").onclick = function (){
    modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target === modal) {
        modal.style.display = "none";
    }
}

$(window).on('load', function () {
    updateInterface();
});


function bytesHumanReadable(bytes){

    if (!bytes){
        return 'N/A';
    }
    const mega = 1024;

    if (Math.abs(bytes) < mega) {
        return `${bytes}B/s`;
    }

    const units = ['Ko', 'Mo', 'To'];
    let u = -1;
    let r = 10**1;
    do {
        bytes = bytes / mega;
        u = u + 1;
    }while (Math.round(Math.abs(bytes) * r) / r >= mega && u < units.length - 1);

    return `${bytes.toFixed(1)} ${units[u]}/s`;
}

function updateInterface(){
    var settings = {
        "url": "/api/torrents",
        "method": "GET",
        "timeout": 0,
    };

    $.ajax(settings).done(function (response) {
        $("#tbodyTorrent tr").remove();
        var torrents = response;
        for (var i = 0; i < torrents.length; i ++){

            var torrent = torrents[i];
            var status = torrent.progress || 100;
            var seeders = torrent.seeders || 0;
            var speed = bytesHumanReadable(torrent.speed) || 0;
            $("#tableTorrent").append(`<tr>
                    <td>${torrent.filename}</td>
                    <td>${status}%</td>
                    <td th:text="">${seeders}</td>
                    <td th:text="">${speed}</td>
                    <td>
                        <img src="/img/direct-download.png" alt="download"/>
                    </td>               
                </tr>`);
        }

    })
}

setInterval (function () {
    updateInterface()
}, 2*1000);


function acceptAllFile(id){
    let settings = {
        'url': `/api/torrents/accept/${id}`,
        'method': "GET",
        'timeout': 0,
        'processData': false
    }

    $.ajax(settings).done(result => {
        modal.style.display = "none";
        $("#torrentFile").val("");
    });
}

function uploadFile(){
    var form = new FormData();
    var files = $("#torrentFile")[0].files;
    for (var i = 0; i < files.length; i++){
        form.append("file", files[i], files[i].name);

    }

    var settings = {
        "url": "/api/torrent/upload",
        "method": "POST",
        "timeout": 0,
        "processData": false,
        "mimeType": "multipart/form-data",
        "contentType": false,
        "data": form
    };

    $.ajax(settings).done(function (response) {
        let json = JSON.parse(response);
        for (let i = 0; i < json.length; i++){

            let settingsInfo = {
                "url": `/api/torrents/${json[i]['id']}`,
                "method": "GET",
                "timeout": 0,
            }
            $.ajax(settingsInfo).done(function (result){
                console.log(result);
                modal.style.display = "block";
                $("#modalContent").append(`
                    <p id="addingText">Adding ${result.filename} in progress...</p>
                    `);
                acceptAllFile(result.id);
                $("#addingText").remove();

            });



        }

    });
}