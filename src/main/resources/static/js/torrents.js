


$(window).on('load', function () {
    updateInterface();
    changeTableView();
});

function bytesHumanReadable(bytes){

    if (!bytes){
        return '0 o/s';
    }
    const mega = 1024;

    if (Math.abs(bytes) < mega) {
        return `${bytes}o/s`;
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

$(window).resize((selector) => {
    changeTableView();
});

function changeTableView(){
    // if (window.innerWidth > 600) return false;
    const tableEl = document.querySelector("table");
    const thEls = tableEl.querySelectorAll('thead th');
    const tdLabels = Array.from(thEls).map(el => el.innerText);
    tableEl.querySelectorAll('tbody tr').forEach( tr => {
    Array.from(tr.children).forEach(
        (td, ndx) =>  {
            td.setAttribute('label', tdLabels[ndx]);
        }
    );
    });
}

function makeRequest(settings, callback){
    $.ajax(settings).done(callback).fail((jqXHR, textStatus, errorThrown) => {
    console.log(jqXHR)
        $("#error_connection").text(`Error: ${jqXHR.responseJSON.error}`);
    });
}

function deleteTorrents(){
    let allCheckBox = $(".torrentCheckbox:checkbox:checked");
    for (let i = 0; i < allCheckBox.length; i++){
        let settings = {
            "url": `/api/torrents/${$(allCheckBox[i]).attr('name')}`,
            "method": "DELETE",
            "timeout": 0,
        };

        makeRequest(settings, (response) => {
            $(allCheckBox[i]).parents("tr").remove();
        });

    }
}

function updateInterface(){
    var settings = {
        "url": "http://localhost:8080/api/torrents",
        "method": "GET",
        "timeout": 0,
    };

    makeRequest(settings, (response) => {
        var torrents = response;
        if ($("#tbodyTorrent tr").length === 0 || $(`#filename_${torrents[0].id}`).length === 0 ){
            $("#tbodyTorrent tr").remove();
            for (var i = 0; i < torrents.length; i ++){

                var torrent = torrents[i];
                var progress = torrent.progress || 100;
                var seeders = torrent.seeders || 0;
                var speed = bytesHumanReadable(torrent.speed) || 0;
                console.log(torrent.status);

                    $("#tableTorrent").append(`<tr>
                                        <td id="filename_${torrent.id}"><a class="filenameTorrent" href="http://localhost:8080/info/${torrent.id}">${torrent.filename}</a></td>
                                        <td><img src=""/><img id="status_${torrent.id}" src="/img/${torrent.status}.png"/><span  id="progress_${torrent.id}">${progress}%</span></td>
                                        <td id="seeders_${torrent.id}" th:text="">${seeders}</td>
                                        <td id="speed_${torrent.id}" th:text="">${speed}</td>
                                        <td>
                                        <label for="download"><input style="margin-left: 85px;" name="${torrent.id}" class="torrentCheckbox" type="checkbox" name="${torrent.id}"/></label>
                                        </td>
                                    </tr>`);
            }
        }else {

            for (let i = 0; i < torrents.length; i++) {
                let torrent = torrents[i];
                let progress = $(`#progress_${torrent.id}`);
                let speed = $(`#speed_${torrent.id}`);
                let seeders = $(`#seeders_${torrent.id}`);
                let status = $(`#status_${torrent.id}`);

                status.attr("src", `/img/${torrent.status}.png`);
                progress.text(`${torrent.progress}%`);
                speed.text(bytesHumanReadable(torrent.speed) || 0);
                seeders.text(torrent.seeders || 0);
            }

        }
    });

    
}


setInterval (function () {
    updateInterface()
}, 2*1000);

function showLinks(links){
    console.log(links)
}

function showInfo(id){
    showModal();
}

function acceptAllFile(id){
    let settings = {
        'url': `http://localhost:8080/api/torrents/accept/${id}`,
        'method': "GET",
        'timeout': 0,
        'processData': false
    }

    $.ajax(settings).done(result => {
        $("#error_connection").text("Torrent added");
    });
}

function showModal(){
    this.blur();
    $.get(this.href, function(html){
        $(html).appendTo('body').modal();
    });
}

function closeModal(){
    modal.style.display = "none";
}

function uploadFile(){
    let form = new FormData();
    let files = $("#torrentFile")[0].files;
    for (var i = 0; i < files.length; i++){
        form.append("file", files[i], files[i].name);
    }

    var settings = {
        "url": "http://localhost:8080/api/torrent/upload",
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
                "url": `http://localhost:8080/api/torrents/${json[i]['id']}`,
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