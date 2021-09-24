function debridLink(link){

    if ($("#link-list").length === 0){
        $("body").append(`
        <div class="card bg-dark text-white" id="list-container" style="width: 50rem;">
          <ul class="list-group list-group-flush" id="link-list">

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
                    `<li class="list-group-item bg-dark text-white"><a href="${response.download}">${response.filename}</a></li>`
                )

    });
}

function debridClick(event){

    let area = $("#links");
    let links = area.val().split("\n");
    for (let i = 0; i < links.length; i++) {
        debridLink(links[i]);
    }
}

function resetDebrid(){
    if ($("#list-container").length !== 0) {
        $("#list-container").remove();
    }
}

