// call the get mapping from the controller to retrive data from the database
fetch('/bio', {method: 'GET'})
.then(data=>{return data.json()})
.then(res=>{
    // the response data are the members to be displayed, call the buildtable method
    populateBio(res);
    getProfilePicture(res);
})
.catch(error=>console.log(error))

// fills the fields on the page with data from the logged in member's bio
function populateBio(data){
    var fullName = data.member.firstName + ' ' + data.member.lastName;

    document.getElementById('staticName').innerHTML = fullName;
    document.getElementById('titleName').innerHTML = fullName;

    document.getElementById('firstNameEdit').value = data.member.firstName;
    document.getElementById('lastNameEdit').value = data.member.lastName;

    document.getElementById('staticBackground').innerHTML = data.backgroundInfo;
    document.getElementById('about').innerHTML = data.backgroundInfo;

    document.getElementById('staticHometown').innerHTML = data.hometown;
    document.getElementById('hometown').value = data.hometown;

    document.getElementById('staticMajor').innerHTML = data.major;
    document.getElementById('major').value = data.major;

    document.getElementById('staticEmail').innerHTML = data.member.email;
    document.getElementById('email').value = data.member.email;
}

function getProfilePicture(res){
  $('#profilePic').attr('src', `${window.location.origin}/bioPicture/${res.bioId}`).width(120).height(120);
  $('#displayPic').attr('src', `${window.location.origin}/bioPicture/${res.bioId}`).width(120).height(120);
}


// function saveChanges() {
//     var queryString = $('#profileEdit').serialize();
// }

function readURL(input) {
    if (input.files && input.files[0]) {
      var reader = new FileReader();
  
      reader.onload = function (e) {
        $('#profilePic').attr('src', e.target.result).width(120).height(120);
        $('#displayPic').attr('src', e.target.result).width(120).height(120);
      };
  
      reader.readAsDataURL(input.files[0]);
    }

    // // submit the new image to the bio controller to be saved
    var photoData = new FormData();
    photoData.append('imageFile', input.files[0], input.files[0].name);

    fetch('/bioPicture', {
      method: 'PATCH',
      body: photoData
    })
  }