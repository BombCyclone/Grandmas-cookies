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
    populateMemberDropDown1(data[1]);
    populateMemberDropDown2(data[1]);
    populateMemberDropDown3(data[1]);
    populateTagDropdown(data[2], data[0]);
}).catch (function (error) {
    console.log(error);
});

// fetch('/ticket-detail?ticketNumber=' + ticketNum, {method: 'GET'})
// .then(data=>{return data.json()})
// .then(res=>{
//     populateForm(res);
//     populateComments(res);
// })
// .catch(error=>console.log(error))


// fetch('/member-names', {method: 'GET'})
// .then(data=>{return data.json()})
// .then(res=>{
//     populateMemberDropDown1(res);
//     populateMemberDropDown2(res);
//     populateMemberDropDown3(res);
// })
// .catch(error=>console.log(error))

// fetch('/tags', {method: 'GET'})
// .then(data=>{return data.json()})
// .then(res=>{
//     console.log("test 2");
//     populateTagDropdown(res);
// })
// .catch(error=>console.log(error))


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

function populateMemberDropDown1(memberArray) {
    var select1 = document.getElementById("member1");

    for(var i = 0; i < memberArray.length; i++)
    {
        var option = document.createElement("OPTION");
        var txt = document.createTextNode(memberArray[i]);
        option.appendChild(txt);
        select1.insertBefore(option, select1.lastChild);
    }
}

function populateMemberDropDown2(memberArray) {
    var select1 = document.getElementById("member2");

    for(var i = 0; i < memberArray.length; i++)
    {
        var option = document.createElement("OPTION");
        var txt = document.createTextNode(memberArray[i]);
        option.appendChild(txt);
        select1.insertBefore(option, select1.lastChild);
    }
}

function populateMemberDropDown3(memberArray) {
    var select1 = document.getElementById("member3");

    for(var i = 0; i < memberArray.length; i++)
    {
        var option = document.createElement("OPTION");
        var txt = document.createTextNode(memberArray[i]);
        option.appendChild(txt);
        select1.insertBefore(option, select1.lastChild);
    }
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

function populateTagDropdown(tags, data) {
    var preSelectedTags = data.associatedTags;
    console.log(tags);
    console.log(preSelectedTags);
    var dropdown = document.getElementById('tagSelect');
    for (const pretag of preSelectedTags) {
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
