// call the get mapping to add the data to members
fetch('/member', {method: 'GET'})
.then(data=>{return data.json()})
.then(res=>{
    // the response data is the members to be displayed, call the buildtable method
    buildTable(res);
})
.catch(error=>console.log)

// used to display the rows from members to the webpage
function buildTable(data){
    // get the element from the webpage to output data to
    var table = document.getElementById('memberTable');
    // for each object in the data array, return a table row
    for(let member of data){
        console.log(member);
        var row =   `<tr>
                        <td>${member.memberid}</td>
                        <td>${member.firstname}</td>
                        <td>${member.lastname}</td>
                        <td>${member.email}</td>
                    </tr>`
        table.innerHTML += row
    }
    // loadScript("assets/vendor/simple-datatables/simple-datatables.js");
}

// function loadScript(src){
//     let script = document.createElement('script');
//     script.src = src;
//     script.async = false;
//     document.body.append(script);
//     console.log("script was added");
// }