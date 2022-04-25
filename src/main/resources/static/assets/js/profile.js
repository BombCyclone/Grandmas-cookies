// call the get mapping from the controller to retrive data from the database
fetch('/bio', {method: 'GET'})
.then(data=>{return data.json()})
.then(res=>{
    // the response data are the members to be displayed, call the buildtable method
    populateBio(res);
})
.catch(error=>console.log(error))

// fills the fields on the page with data from the logged in member's bio
function populateBio(data){
    
    var fullName = data.member.firstName + ' ' + data.member.lastName;
    document.getElementById('staticName').innerHTML = fullName;
    document.getElementById('fullName').value = fullName.toString();
    document.getElementById('titleName').innerHTML = fullName;

    document.getElementById('staticBackground').innerHTML = data.backgroundInfo;
    document.getElementById('about').innerHTML = data.backgroundInfo;

    document.getElementById('staticHometown').innerHTML = data.hometown;
    document.getElementById('hometown').value = data.hometown;

    document.getElementById('staticMajor').innerHTML = data.major;
    document.getElementById('major').value = data.major;

    document.getElementById('staticEmail').innerHTML = data.member.email;
    document.getElementById('email').value = data.member.email;
}