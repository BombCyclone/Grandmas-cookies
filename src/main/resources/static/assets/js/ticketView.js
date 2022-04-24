const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const ticketNum = urlParams.get('number');
var residentName;

document.getElementById("ticketNumber").innerHTML = ticketNum;

Promise.all([
    fetch('/ticket-detail?ticketNumber=' + ticketNum, {method: 'GET'}),
    fetch('/member-names', {method: 'GET'}),
    fetch('/tags', {method: 'GET'})
]).then(function (responses) {
    return Promise.all(responses.map(function (response) {
        return response.json();
    }));
}).then(function (data) {
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
    document.getElementById("resident").value = data.resident.firstName + " " + data.resident.lastName;
    addscripts();
}

function populateComments(data){
    for (comment of data.comments) {
        var memberName = comment.member.firstName + " " + comment.member.lastName;
        var timestamp = new Date(comment.timeStamp);
        commentCard = `<div class="card"><div class="card-body">
                        <h7>${timestamp}</h7><br>
                        <h7>${memberName}</h7>
                        <p>${comment.content}</p>
                        <button onClick="deleteComment(${comment.commentId})">Delete</button>
                        </div></div>`
        comments.innerHTML += commentCard;
    }
}

function populateMemberDropdown(members, data) {
    for (const preMember of data.assignedMembers) {
        var memberName = preMember.firstName + " " + preMember.lastName;
        $("#memberSelect").append(`<option selected value=${memberName}>${memberName}</option>`).trigger("chosen:updated"); 
    }
    for(let member of members){
        var memberName = member.firstName + " " + member.lastName;
        $("#memberSelect").append(`<option value=${memberName}>${memberName}</option>`).trigger("chosen:updated");
    }
}

function populateTagDropdown(tags, data) {
    for (const pretag of data.associatedTags) {
        $("#tagSelect").append(`<option selected value=${pretag.tagString}>${pretag.tagString}</option>`).trigger("chosen:updated"); 
    }
    for(let tag of tags){
        $("#tagSelect").append(`<option value=${tag.tagString}>${tag.tagString}</option>`).trigger("chosen:updated");
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
    <div class="card" id="newComment"><div class="card-body">
    <form id="ticket-form" name="ticket-form" action="/ticket" method="POST" >
    <input placeholder="Add comment here" id="newContent"></input>
    </form>
    </div></div>`
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

//inspo for delete ticket
// formData.append('ticketNum', id);
// await fetch('/ticket/', {
//     method: 'DELETE', body: formData,
//     })
//     .then(() => {window.location.reload()})
// }

//source: http://jsfiddle.net/d7TeL/
(function (W) {
    var D, form, bts, ipt;

    function init() {
        D = W.document, previous = [];
        form = D.getElementsByTagName('form')[0];
        bts = form.getElementsByTagName('button');
        ipt = form.getElementsByTagName('input');
        form.addEventListener('submit', save, false);
        bts[1].addEventListener('click', cancel, false);
        bts[2].addEventListener('click', edit, false);
    }

    function save(e) {
        e.preventDefault();
        form.classList.remove('invert');
        var l = ipt.length;
        while (l--) {
            ipt[l].readOnly = true;
        };
        previous = [];
        //send your info here 
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
    }

    function cancel(e) {
        form.classList.remove('invert');
        e.preventDefault();
        var l = ipt.length;
        while (l--) {
            ipt[l].value = previous[l];
            ipt[l].readOnly = true;
        }
    }
    init();
})(window)

$(".chosen-select").chosen({
    width: '100%',
    max_selected_options: 3,
    no_results_text: ""
})
