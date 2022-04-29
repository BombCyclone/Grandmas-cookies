// call the get mapping from the controller to retrive data from the database
fetch('/member', {method: 'GET'})
.then(data=>{return data.json()})
.then(res=>{
    // the response data are the members to be displayed, call the buildtable method
    populateBio(res);
    getProfilePicture(res);
})
.catch(error=>console.log(error))

// fills the fields on the page with data from the logged in member's bio
function populateBio(data){
    var fullName = data.firstName + ' ' + data.lastName;

    document.getElementById('staticName').innerHTML = fullName;
    document.getElementById('titleName').innerHTML = fullName;

    document.getElementById('firstNameEdit').value = data.firstName;
    document.getElementById('lastNameEdit').value = data.lastName;

    document.getElementById('staticBackground').innerHTML = data.biography.backgroundInfo;
    document.getElementById('about').innerHTML = data.biography.backgroundInfo;

    document.getElementById('staticHometown').innerHTML = data.biography.hometown;
    document.getElementById('hometown').value = data.biography.hometown;

    document.getElementById('staticMajor').innerHTML = data.biography.major;
    document.getElementById('major').value = data.biography.major;

    document.getElementById('staticEmail').innerHTML = data.email;
    document.getElementById('email').value = data.email;
}

function getProfilePicture(res){
  $('#profilePic').attr('src', `${window.location.origin}/bioPicture/${res.biography.bioId}`).width(120).height(120);
  $('#displayPic').attr('src', `${window.location.origin}/bioPicture/${res.biography.bioId}`).width(120).height(120);
}

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

function saveProfile() {
  console.log("in save profile");

  //member properties
  var firstName = document.getElementById('firstNameEdit');
  var lastName = document.getElementById('lastNameEdit');
  var email = document.getElementById('email');
  console.log(firstName);
  //get member id
  //get password

  //bio properties
  var backgroundInfo = document.getElementById('about').value;
  var hometown = document.getElementById('hometown').value;
  var major = document.getElementById('major').value;
  console.log(backgroundInfo, hometown, major);

  var formData = new FormData();
  formData.append('backgroundInfo', backgroundInfo);
  formData.append('hometown', hometown);
  formData.append('major', major);
  fetch('/bio', {
    method: 'PUT',
    body: formData
  })
}