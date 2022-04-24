const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const ticketNum = urlParams.get('number');
var residentName;

document.getElementById("ticketNumber").value = ticketNum;

fetch('/ticket-detail?ticketNumber=' + ticketNum, {method: 'GET'})
.then(data=>{return data.json()})
.then(res=>{
    populateForm(res);
    populateComments(res);
})
.catch(error=>console.log(error))


fetch('/member-names', {method: 'GET'})
.then(data=>{return data.json()})
.then(res=>{
    populateMemberDropDown1(res);
    populateMemberDropDown2(res);
    populateMemberDropDown3(res);
})
.catch(error=>console.log(error))


function populateForm(data) {
    var ticketDate = new Date(data.timestamp);
    var formattedDate = ticketDate.toLocaleDateString();
    var formattedTime = ticketDate.toLocaleTimeString();
    
    document.getElementById("date").value = formattedDate;
    document.getElementById("time").value = formattedTime;
    document.getElementById("issueDesc").value = data.issueDesc;
    document.getElementById("resident").value = data.resident.firstName + " " + data.resident.lastName;
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
        var timestamp = new Date(comment.timeStamp);
        commentCard = `<div class="card"><div class="card-body">
                        <h7>${timestamp}</h7><br>
                        <p>${comment.content}</p>
                        <button onClick="deleteComment(${comment.commentId})">Delete</button>
                        </div></div>`
        comments.innerHTML += commentCard;
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