// get the data needed for this page by fetching posts and tags from the database
Promise.all([
	fetch('/kbpost', {method: 'GET'}),
	fetch('/tags', {method: 'GET'})
]).then(function (responses) {
	// Get a JSON object from each of the responses
	return Promise.all(responses.map(function (response) {
		return response.json();
	}));
}).then(function (data) {
    // data[0] contains all kbposts
    buildTable(data[0]);
    // data[1] contains all tags
    populateTagDrowdown(data[1]);

}).catch(function (error) {
	// if there's an error, log it
	console.log(error);
});


// used to display the rows from members to the webpage
function buildTable(posts){
    // get the element from the webpage to output data to
    var table = document.getElementById('postTable');
    // for each object in the data array, return a table row
    for(let kbpost of posts){
        var memberName = kbpost.member.firstName + " " + kbpost.member.lastName;
        // parse the tags that are applied to the kb post
        var appliedTags = '';
        for(let tag of kbpost.postTags){
            appliedTags += tag.tagString + '   ';
        }
        if('' == appliedTags){
            appliedTags = 'none';
        }
        // for each kbpost, a new row is added to the table containing an accordion with the kbpost's information
        var row =   `<tr>
                        <td>
                        <div class="accordion-item">
                            <h5 class="accordion-header">
                                <div class="d-flex justify-content-center">
                                    ${kbpost.title}
                                </div>
                                <button class="accordion-button collapsed" data-bs-target="#kbpost${kbpost.postId}" type="button" data-bs-toggle="collapse">
                                    Tags: ${appliedTags}
                                </button>
                            </h5>
                            <div id="kbpost${kbpost.postId}" class="accordion-collapse collapse">
                                <div class="accordion-body">
                                    <div id="poster">
                                        <b>Posted by: ${memberName}</b>
                                    </div>
                                    </br>
                                    <div id="postContent">
                                        ${kbpost.content}
                                    </div>
                                </div>
                            </div>
                        </div>
                        </td>
                    </tr>`
        table.innerHTML += row
    }

    // Hide the loading spinner now that the rows have been added
    var spinner = document.getElementById('spinner');
    spinner.style.visibility = "hidden";
    spinner.style.height = 0;
    addscripts();
}

// add other NiceAdmin Scripts to the page after the table content has been rendered
function addscripts(){
    loadScript("assets/vendor/apexcharts/apexcharts.min.js");
    loadScript("assets/vendor/bootstrap/js/bootstrap.bundle.min.js");
    loadScript("assets/vendor/chart.js/chart.min.js");
    loadScript("assets/vendor/echarts/echarts.min.js")
    loadScript("assets/vendor/quill/quill.min.js");
    loadScript("assets/vendor/simple-datatables/simple-datatables.js");
    loadScript("assets/vendor/tinymce/tinymce.min.js");
    loadScript("assets/js/main.js");
}

// loads the script and add to the window
function loadScript(src){
    let script = document.createElement('script');
    script.src = src;
    script.async = false;
    document.body.append(script);
}

// adds all available tags to the dropdown menu in the add kbpost form
function populateTagDrowdown(tags){
    for(let tag of tags){
        $("#tagSelect").append(`<option value=${tag.tagString}>${tag.tagString}</option>`).trigger("chosen:updated");
    }
}

// used with the tagSelect dropdown menu to allow the selection of multiple options 
$(".chosen-select").chosen({
    width: '100%',
    max_selected_options: 3,
    no_results_text: ""
})
