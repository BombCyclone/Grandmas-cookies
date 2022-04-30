const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const ticketNum = urlParams.get('number');
let ticketStatus;

document.getElementById("ticketNumber").innerHTML = ticketNum;

Promise.all([
    fetch('/ticket-detail?ticketNumber=' + ticketNum, {method: 'GET'}),
    fetch('/members', {method: 'GET'}),
    fetch('/tags', {method: 'GET'})
]).then(function (responses) {
    return Promise.all(responses.map(function (response) {
        return response.json();
    }));
}).then(function (data) {
    ticketStatus = data[0].ticketStatusActive;
    populateForm(data[0]);
    populateComments(data[0]);
    populateMemberDropdown(data[1], data[0]);
    populateTagDropdown(data[2], data[0]);
}).catch (function (error) {
    console.log(error);
});

function populateForm(data) {
    var ticketDate = new Date(data.timestamp);
    var formattedDate = ticketDate.toLocaleDateString();
    var formattedTime = ticketDate.toLocaleTimeString();
    
    document.getElementById("date").innerHTML = formattedDate;
    document.getElementById("time").innerHTML = formattedTime;
    document.getElementById("issueDesc").value = data.issueDesc;
    document.getElementById("resident").innerHTML = data.resident.firstName + " " + data.resident.lastName;
    addscripts();
}

function populateComments(data){
    for (comment of data.comments) {
        var memberName = comment.member.firstName + " " + comment.member.lastName;
        var commentDate = new Date(comment.timeStamp);
        var formattedDate = commentDate.toLocaleDateString();
        var formattedTime = commentDate.toLocaleTimeString();

        commentCard = `<div class="card">
                            <div class="card-body">
                                <h7>
                                    <span>
                                        <b>${memberName} on ${formattedDate} at ${formattedTime}</b>
                                    </span>
                                </h7>
                                </br>
                                </br>
                                <p>${comment.content}</p>
                                <button style="float: right;" onClick="deleteComment(${comment.commentId})">Delete</button>
                            </div>
                        </div>`
        comments.innerHTML += commentCard;
    }
}

function populateMemberDropdown(members, data) {
    // for all possible members
    for(let member of members){
        var memberName = member.firstName + " " + member.lastName;
        var matchFound = false;
        // check if the current member to be added is one of the currently assigned members to the ticket
        for (const preMember of data.assignedMembers) {
            // if the member to be added is one of the assigned members, mark as true
            if (member.memberId == preMember.memberId){
                matchFound = true;
            }
        }
        // if a match was found, add the member as a currently selected option, otherwise add as an option not currently selected
        if(matchFound){
            $("#memberSelect").append(`<option selected value="${member.memberId}">${memberName}</option>`).trigger("chosen:updated"); 
        } else {
            $("#memberSelect").append(`<option value="${member.memberId}">${memberName}</option>`).trigger("chosen:updated");
        }
    }
}

function populateTagDropdown(tags, data) {
    for (const pretag of data.associatedTags) {
        $("#tagSelect").append(`<option selected value="${pretag.tagString}">${pretag.tagString}</option>`).trigger("chosen:updated"); 
    }
    for(let tag of tags){
        $("#tagSelect").append(`<option value="${tag.tagString}">${tag.tagString}</option>`).trigger("chosen:updated");
    }
}

function addNewComment() {
    if(document.getElementById("newComment")) {
        return;
    }
    commentCard = 
    `
    <button id="saveComment" onClick="saveComment()">Save Comment</button>
    <button id="cancelComment" onClick="deleteNewComment()">Cancel</button>
    <div class="card" id="newComment">
        <div class="card-body">
            <form id="ticket-form" name="ticket-form" action="/ticket" method="POST" >
                <div class="row mb-3">
                    <textarea maxLength="200" placeholder="Add comment here" id="newContent" required></textarea>
                </div>
            </form>
        </div>
    </div>
    `
    comments.innerHTML += commentCard;
}

function saveComment() {
    var commentContent = document.getElementById("newContent").value;

    fetch('/comment?content=' + commentContent + '&ticketNum=' + ticketNum, {method: 'POST'})
    .then(data=>{return data.json()})
    .catch(error=>console.log(error))

    deleteNewComment();
    window.location.reload();

}

function deleteNewComment() {
    document.getElementById("newComment").remove();
    document.getElementById("saveComment").remove();
    document.getElementById("cancelComment").remove();
}

function deleteComment(id) {
const formData = new FormData();
formData.append('commentId', id);

fetch('/comment/', {
    method: 'DELETE', body: formData,
    })
    .then(() => {window.location.reload()})

window.location.reload();
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

function updateTicket() {
    var tags = $('#tagSelect').serializeArray();
    console.log(tags);
    var formattedTags = [];
    for (tag of tags) {
        formattedTags.push(tag.value);
    }
    console.log(formattedTags);
    var members = $('#memberSelect').serializeArray();
    var formattedMembers = [];
    for (member of members) {
        formattedMembers.push(member.value);
    }
    console.log(formattedMembers);
    var issueDesc = document.getElementById("issueDesc").value;

    const formData = new FormData();
    formData.append('ticketId', ticketNum);
    formData.append('ticketStatus', ticketStatus);
    formData.append('issueDesc', issueDesc);
    formData.append('tags', formattedTags);
    console.log(formattedTags);

    const formData2 = new FormData();
    formData2.append('ticketId', ticketNum);
    formData2.append('memberId', formattedMembers);

    fetch('/ticket-update', {
        method: 'PUT', 
        body: formData
    }).catch(error=>console.log(error))

    fetch('/ticket-assign', {
        method: 'PATCH', 
        body: formData2,
        })
        .then(() => {window.location.reload()})
    .catch(error=>console.log(error))
    
    //refresh the page
    window.location.reload();

}

function enable() {
    console.log("In enable");
    $(".chosen-select").prop("disabled", false);
}

async function deleteTicket() {
    var result = confirm("Are you sure you want to permanently delete this ticket?");
    if (result) {
    const formData = new FormData();
    formData.append('ticketNum', ticketNum);
    await fetch('/ticket', {
    method: 'DELETE',
    body: formData,
    })
    .catch(error=>console.log(error));
    window.location.replace("/index");
    }
    }

//source: http://jsfiddle.net/d7TeL/
(function (W) {
    var D, form, bts, ipt;

    function init() {
        D = W.document, previous = [], selectPrev = [];
        form = D.getElementsByTagName('form')[0];
        bts = form.getElementsByTagName('button');
        ipt = form.getElementsByTagName('textarea');
        // select = form.getElementsByTagName('select');
        form.addEventListener('submit', save, false);
        bts[1].addEventListener('click', cancel, false);
        bts[2].addEventListener('click', edit, false);
        $(".chosen-select").prop("disabled", true);
    }

    function save(e) {
        e.preventDefault();
        form.classList.remove('invert');
        var l = ipt.length;
        while (l--) {
            ipt[l].readOnly = true;
        }
        previous = [];
        updateTicket();
    }

    function edit(e) {
        e.preventDefault();
        form.classList.add('invert');
        var l = ipt.length;
        while (l--) {
            previous[l] = ipt[l].value;
            ipt[l].readOnly = false;
            ipt[l].disabled = false;
        }
        $(".chosen-select").prop("disabled", false).trigger("chosen:updated");
    }

    function cancel(e) {
        $(".chosen-select").prop("disabled", true).trigger("chosen:updated");
        form.classList.remove('invert');
        e.preventDefault();
        var l = ipt.length;
        while (l--) {
            ipt[l].value = previous[l];
            ipt[l].readOnly = true;
        }
        window.location.reload();
    }
    init();
})(window)

$(".chosen-select").chosen({
    width: '100%',
    max_selected_options: 3,
    no_results_text: ""
})
