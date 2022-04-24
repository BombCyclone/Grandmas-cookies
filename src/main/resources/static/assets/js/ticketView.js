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
    populateMemberDropDown(res)
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

function populateMemberDropDown(memberArray) {
    var select1 = document.getElementById("member1");
    var select2 = document.getElementById("member2");
    var select3 = document.getElementById("member3");

    for(var i = 0; i < memberArray.length; i++)
    {
        var option = document.createElement("OPTION");
        var txt = document.createTextNode(memberArray[i]);
        option.appendChild(txt);
        select1.insertBefore(option, select1.lastChild);
        select2.insertBefore(option, select2.lastChild);
        select3.insertBefore(option, select3.lastChild);
    }
}

function populateComments(data){
    for (comment of data.comments) {
        var timestamp = new Date(comment.timeStamp);
        commentCard = `<div class="card"><div class="card-body">
                        <h7>${timestamp}</h7><br>
                        <p>${comment.content}</p>
                        </div></div>`
        comments.innerHTML += commentCard;
    }
}

function addNewComment() {
    if(document.getElementById("newComment")) {
        return;
    }
    commentCard = 
    `<button id="saveComment" onClick="saveComment()">Save Comment</button>
    <button id="cancelComment" onClick="deleteNewComment()">Cancel</button>
    <div class="card" id="newComment"><div class="card-body">
    <form>
    <input placeholder="Add comment here" id="newContent"></input>
    </form>
    </div></div>`
    comments.innerHTML += commentCard;
}

//currently not working - need to run with debugger
function saveComment() {
    var commentContent = document.getElementById("newContent").value;

    fetch('/comment', {method: 'POST', body: JSON.stringify ({
        content: commentContent,
        ticketId: ticketNum
        })
    })
    .then(data=>{return data.json()})
    .catch(error=>console.log(error))

    deleteNewComment();

}

function deleteNewComment() {
    document.getElementById("newComment").remove();
    document.getElementById("saveComment").remove();
    document.getElementById("cancelComment").remove();
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