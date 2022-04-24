// call the get mapping from the controller to retrive data from the database
fetch('/tickets', {method: 'GET'})
.then(data=>{return data.json()})
.then(res=>{
    // the response data are the members to be displayed, call the buildtable method
    buildTable(res);
})
.catch(error=>console.log(error))

let arrRes;
let arrRmNum;
let arrMemNames;

fetch('/ticket-resident', {method: 'GET'})
.then(data=>{return data.json()})
.then(res=>{
    arrRes = res;
})
.catch(error=>console.log(error))

fetch('/ticket-resident-room', {method: 'GET'})
.then(data=>{return data.json()})
.then(res=>{
    arrRmNum = res;
})
.catch(error=>console.log(error))

fetch('/member-names', {method: 'GET'})
.then(data=>{return data.json()})
.then(res=>{
    arrMemNames = res;
})
.catch(error=>console.log(error))

// used to display the rows from members to the webpage
function buildTable(data){
    // get the element from the webpage to output data to
    var table = document.getElementById('ticketTable');
    // for each object in the data array, return a table row
    var i = 0;
    for(let ticket of data){
        var ticketDate = new Date(ticket.timestamp);
        var formattedDate = ticketDate.toLocaleDateString();
        var formattedTime = ticketDate.toLocaleTimeString();
        ticketNumber = ticket.ticketNum;
        //     var row = `
        //     <div class="card">
        //     <div class="card-body">
        //       <form>
        //         <h5 id="ticketNum">Ticket #: ${ticket.ticketNum}</h5>
        //         <h5 id="date">Date: ${formattedDate}</h5>
        //         <h5 id="time">Time: ${formattedTime}</h5>
        //         <h6 id="resident">Resident: ${arrRes[i]}</h6>
        //         <h6 id="roomNum">Room #: ${arrRmNum[i]}</h6>
        //           <label for="members">Assigned Members:</label>
        //           <select name="assignedMembers" id="select" onClick="populateSelect()">
        //             <option value=""></option>
        //           </select><br>
        //           <label for="tags">Tags:</label>
        //           <select name="tags" id="tags">
        //           </select><br>
        //           <label for="issueDesc">Issue Description:</label><br>
        //           <textarea readonly rows="3" cols="75" id="issueDesc">${ticket.issueDesc}</textarea><br>
        //           <button>Save</button>
        //           <button>Cancel</button>
        //           <button onclick="test()">Edit</button>
        //           <label>Comments</label>
        //           <div class="icon">
        //             <em class="ri-arrow-down-s-line"></em>
        //         </div>
        //         </form>
        //     </div>
        //   </div>
        //     `
        
            var row =   ` <div class="table">
                        <div class="card body">
                            <tr onClick="test(${ticket.ticketNum})">
                            <td>${arrRes[i]}</td>
                            <td>${ticket.issueDesc}</td>
                            <td>${formattedDate}</td>
                            <td>${formattedTime}</td>
                        </div>
                        </td>
                        </tr>`
            table.innerHTML += row;
            loadComments(ticket.ticketNum, ticket.comments);
        i++;
    }

    // Hide the loading spinner now that the rows have been added
    var spinner = document.getElementById('spinner');
    spinner.style.visibility = "hidden";
    spinner.style.height = 0;
    addscripts();
}

async function test(id) {
    window.location.href = "/ticket-view?number=" + id;
}

function loadComments(id, comments){
    // if there are comments
    if(Object.entries(comments).length !== 0){
        console.log(id, JSON.stringify(comments));
    }
    // if no comments exist
    else{

    }
}
 
function showComments(id){
    console.log("Show Comments")
}

async function deleteRow(id) {
console.log(id);
const formData = new FormData();
formData.append('ticketNum', id);
await fetch('/ticket/', {
    method: 'DELETE', body: formData,
    })
    .then(() => {window.location.reload()})
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

 
// $(document).ready(function() {
//     console.log("function executed")
//     var select = document.getElementById("select");
//     for(var i = 0; i < arrMemNames.length; i++)
//     {
//         var option = document.createElement("OPTION");
//         var txt = document.createTextNode(arrMemNames[i]);
//         option.appendChild(txt);
//         select.insertBefore(option, select.lastChild);
//     }
// });

var executed = false;
function populateSelect() {
    if (!executed) {
        console.log("test");
        var select = document.getElementById("select");
        for(var i = 0; i < arrMemNames.length; i++)
        {
            var option = document.createElement("OPTION");
            var txt = document.createTextNode(arrMemNames[i]);
            option.appendChild(txt);
            select.insertBefore(option, select.lastChild);
        }
        executed = true;
    }
}
