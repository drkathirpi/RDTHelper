function debridLink(link){

    if ($("#link-list").length === 0){
        $("#list-card").append(`
        <div class="card bg-dark text-white" id="list-container" style="width: 50rem;">
          <ul class="bg-dark list-group list-group-flush" id="link-list">

          </ul>
        </div>`);
    }



    var settings = {
      "url": "/api/torrents/debrid",
      "method": "POST",
      "timeout": 0,
      "headers": {
        "Content-Type": "application/json"
      },
      "data": JSON.stringify({
        "link": link
      }),
    };

    $.ajax(settings).done(function (response) {

        $("#link-list").append(
                    `<li class="list-group-item bg-dark text-white">
                    <div class="card">
                      <div class="card-body">
                        <blockquote class="blockquote mb-0">
                            <p style="color: black;">${response.filename} - (${bytesHumanReadable(response.filesize)})</p>
                            <footer><a style="color: black;" href="${response.download}">${response.download}</a></footer>
                        </blockquote>
                      </div>
                    </div>
                    </li>`
                )

    });
}

function bytesHumanReadable(bytes){

    if (!bytes){
        return '0 o';
    }
    const mega = 1024;

    bytes /= 10;

    if (Math.abs(bytes) < mega) {
        return `${bytes}`;
    }

    const units = ['Ko', 'Mo', 'To'];
    let u = -1;
    let r = 10**1;
    do {
        bytes = bytes / mega;
        u = u + 1;
    }while (Math.round(Math.abs(bytes) * r) / r >= mega && u < units.length - 1);

    return `${bytes.toFixed(1)} ${units[u]}`;
}

function sleep(ms){
    return new Promise(resolve => setTimeout(resolve, ms));
}

async function debridClick(event){

    let area = $("#links");
    let links = area.val().split("\n");
    for (let i = 0; i < links.length; i++) {
        debridLink(links[i]);
        await sleep(3000);
    }
}

function resetDebrid(){
    if ($("#list-container").length !== 0) {
        $("#list-container").remove();
    }
}

