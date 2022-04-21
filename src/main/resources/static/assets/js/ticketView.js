const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const ticketNum = urlParams.get('number');
var residentName;

document.getElementById("ticketNumber").value = ticketNum;

fetch('/ticket-detail?ticketNumber=' + ticketNum, {method: 'GET'})
.then(data=>{return data.json()})
.then(res=>{
    populateForm(res);
})
.catch(error=>console.log(error))

//currently not working
fetch('/resident-ticket?ticketNumber=' + ticketNum, {method: 'GET'})
.then(data=>{return data.json()})
.then(res=>{
    residentName = res;
})
.catch(error=>console.log(error))


function populateForm(data) {
    var ticketDate = new Date(data.timestamp);
    var formattedDate = ticketDate.toLocaleDateString();
    var formattedTime = ticketDate.toLocaleTimeString();
    
    document.getElementById("date").value = formattedDate;
    document.getElementById("time").value = formattedTime;
    document.getElementById("issueDesc").value = data.issueDesc;
}

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