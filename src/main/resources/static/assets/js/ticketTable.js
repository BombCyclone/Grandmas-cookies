// call the get mapping from the controller to retrive data from the database
fetch('/tickets', {method: 'GET'})
.then(data=>{return data.json()})
.then(res=>{
    // the response data are the members to be displayed, call the buildtable method
    buildTable(res);
})
.catch(error=>console.log(error))

let arrRes;

fetch('/ticket-resident', {method: 'GET'})
.then(data=>{return data.json()})
.then(res=>{
    arrRes = res;
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
        var ticketNum = ticket.ticketNum;
            var row =   `<tr>
                            <td>${ticket.ticketNum}</td>
                            <td>${arrRes[i]}</td>
                            <td>${ticket.issueDesc}</td>
                            <td>${formattedDate}</td>
                            <td>${formattedTime}</td>
                            <td action="/tickets" method="DELETE" onClick="deleteRow()">
                                <button type="submit">X</button>
                            </td>
                        </tr>`
            table.innerHTML += row
        i++;
    }

    // Hide the loading spinner now that the rows have been added
    var spinner = document.getElementById('spinner');
    spinner.style.visibility = "hidden";
    spinner.style.height = 0;
    addscripts();
}

function deleteRow() {
fetch('/tickets', {method: 'DELETE', body: JSON.stringify(1)})
.then(data=>{return data.json()})
.then(res=>{
    arrRes = res;
})
.catch(error=>console.log(error))
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
