// call the get mapping from the controller to retrive data from the database
fetch('/kbpost', {method: 'GET'})
.then(data=>{return data.json()})
.then(res=>{
    // the response data are the members to be displayed, call the buildtable method
    buildTable(res);
})
.catch(error=>console.log(error))

// used to display the rows from members to the webpage
function buildTable(data){
    // get the element from the webpage to output data to
    var table = document.getElementById('postTable');
    // for each object in the data array, return a table row
    for(let kbpost of data){
        var memberName = kbpost.member.firstName + " " + kbpost.member.lastName;
        var allTags = '';
        for(let tag of kbpost.postTags){
            allTags += tag.tagString + '   ';
        }
        if('' == allTags){
            allTags = 'none';
        }
        var row =   `<tr>
                        <td>
                        <div class="accordion-item">
                            <h5 class="accordion-header">
                                <div class="d-flex justify-content-center">
                                    ${kbpost.title}
                                </div>
                                <button class="accordion-button collapsed" data-bs-target="#kbpost${kbpost.postId}" type="button" data-bs-toggle="collapse">
                                    Tags: ${allTags}
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

$(".chosen-select").chosen({
    no_results_text: ""
})
