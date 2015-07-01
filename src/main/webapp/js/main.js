
// The root URL for the RESTful services
var rootURL = "http://localhost:8080/CarRestExample/webresources/cars";
var dropUrl = "http://localhost:8080/CarRestExample/webresources/makes";
var currentCar;
var chkbox = null;


//Call functions

//Load all cars into table
findAll();

//load data into form on selection of car in table.
$('#carList td').live('click', function() {
	findById($(this).data('identity'));
});


$('#drop').click(function(){
	region_id  = $('#drop').val();
        console.log("?????????????????"+region_id);
	//$.post('dropUrl',{reg:region_id},function(res){
	//$("#list").html(region_id);
        $('#list').empty();
        $('#list').append('<option value="'+region_id+'">'+region_id+'</option>');
        $('#list').append('<option value="'+region_id+'">'+region_id+'</option>');
        $('#list').append('<option value="'+region_id+'">'+region_id+'</option>');
        $('#list').append('<option value="'+region_id+'">'+region_id+'</option>');
    
        listDropdown2();
    
    
        $('#drop').empty();
        $('#drop').prepend('<option value="'+region_id+'">'+region_id+'</option>');
    

   //});
});


// Register listeners
$('#btnSearch').click(function() {
	search($('#searchKey').val());
	return false;
});



$('#btnAdd').click(function() {
	newCar();
	return false;
});

$('#drop').click(function() {
    
	listDropdown();
	return false;
});

$('#btnSave').click(function() {
	if ($('#carId').val() === ''){       
	    addCar();
        }
	else {
	    updateCar();
        }
	return false;
});



// Trigger search when pressing 'Return' on search key input field
$('#searchKey').keypress(function(e){
	if (e.which === 13) {
		search($('#searchKey').val());
		e.preventDefault();
		return false;
    }
});




//Functions being called
function newCar() {
	
    currentCar = {};
    renderDetails(currentCar); // Display empty form
    
}


//Load table on search
function search(searchKey) {
	if (searchKey === '') {
            
            $('#carList').empty();
	    findAll();
        }
	else {
	    findByName(searchKey);
        }
}

// Load by id
function findById(id) {
    
    console.log('findById: ' +id);
    $.ajax({
	type: 'GET',
	url: rootURL + '/' +id,
	dataType: "json",
	success: function(data){
	   // $('#btnDelete').show();
	    console.log('findById success: ' + data.id);
	    //currentCar = data;
	    //renderDetails(currentCar);
            renderDetails(data);
	}
    });
}




 
function findAll() {
	//console.log('Get all cars.');
	$.ajax({
		type: 'GET',
		url: rootURL,
		dataType: "json", // data type of response
		success: renderList // Call renderList function
	});
}



function findByName(searchKey) {
	console.log('findByName: ' + searchKey);
	$.ajax({
		type: 'GET',
		url: rootURL + '/search/' + searchKey,
		dataType: "json",
		success: renderSearchList 
	});
}


function listDropdown() {
	
	$.ajax({
		type: 'GET',
		url: dropUrl,
		dataType: "json",
		success: renderDropdownList 
	});
        
}

function listDropdown2() {
	
	$.ajax({
		type: 'GET',
		url: dropUrl,
		dataType: "json",
		success: renderDropdownList2
	});
        
}


function renderList(data) {
    
    // JAX-RS serializes an empty list as null, and a 'collection of one' 
    // as an object (not an 'array of one')
    var list = data === null ? [] : (data instanceof Array ? data : [data]);
    //$('#carList').remove();
    //jquery version of for each.
    $.each(list, function(index, car) {
            
        var ck_id = car.id;
        var ck = "chk"+ck_id;
          //Can use prepend also
        $('#carList').append('<tr id=' +car.id+ '><td data-identity=' + car.id +  '>' + car.id + '</td><td id="'+ car.make +'"  data-identity=' + car.id +  '   contenteditable="true">'+ car.make +'</td><td id="' + car.model +'"  name="Accord" data-identity='+ car.id +' contenteditable="true" >' + car.model + '</td><td><input type="checkbox" name="'+car.model+'" id="'+ck+'"/></td></tr>');	
    });
}

function renderDropdownList(data) {
    
   // $('#drop').prepend('<option value="Select">Select</option>');
    
    
    // JAX-RS serializes an empty list as null, and a 'collection of one' 
    // as an object (not an 'array of one')
    var list = data === null ? [] : (data instanceof Array ? data : [data]);
    //$('#carList').remove();
    //jquery version of for each.
    
    $.each(list, function(index, car) {
            
        var car_make = car.make;
        var ck = "sel"+car_make;
       
          //Can use prepend also
       // $('#carList').append('<tr id=' +car.id+ '><td data-identity=' + car.id +  '>' + car.id + '</td><td id="'+ car.make +'"  data-identity=' + car.id +  '   contenteditable="true">'+ car.make +'</td><td id="' + car.model +'"  name="Accord" data-identity='+ car.id +' contenteditable="true" >' + car.model + '</td><td><input type="checkbox" name="'+car.model+'" id="'+ck+'"/></td></tr>');	
        $('#drop').append('<option id=' +car.make+ ' data-identity=' + car.id + ' value="'+car.make+'">'+ car.make +'</option>');
    });
}

function renderDropdownList2(data) {
    
  
    var list = data === null ? [] : (data instanceof Array ? data : [data]);
   
    $.each(list, function(index, car) {
            
        var car_make = car.make;
        var ck = "sel"+car_make;
       
          //Can use prepend also
       // $('#carList').append('<tr id=' +car.id+ '><td data-identity=' + car.id +  '>' + car.id + '</td><td id="'+ car.make +'"  data-identity=' + car.id +  '   contenteditable="true">'+ car.make +'</td><td id="' + car.model +'"  name="Accord" data-identity='+ car.id +' contenteditable="true" >' + car.model + '</td><td><input type="checkbox" name="'+car.model+'" id="'+ck+'"/></td></tr>');	
        $('#list').append('<option id=' +car.make+ ' data-identity=' + car.id + ' value="'+car.make+'">'+ car.make +'</option>');
    });
}


function renderSearchList(data) {
    
    // JAX-RS serializes an empty list as null, and a 'collection of one' 
    // as an object (not an 'array of one')
    var list = data === null ? [] : (data instanceof Array ? data : [data]);
    $('#carList').empty();
    
    
    
    //jquery version of for each.
    $.each(list, function(index, car) {
            
        var ck_id = car.id;
        var ck = "chk"+ck_id;
          console.log("Search:" +car.make);
        $('#carList').prepend('<tr id=' +car.id+ '><td data-identity=' + car.id +  '>' + car.id + '</td><td id="' + car.make + '" data-identity=' + car.id +  '   contenteditable="true">'+ car.make +'</td><td  id="' + car.model +'" data-identity='+ car.id +' contenteditable="true" >' + car.model + '</td><td><input type="checkbox" name="chk" id="'+ck+'"/></td></tr>');	
        
    });
}



function renderDetails(car) {
    
	$('#carId').val(car.id);
	$('#make').val(car.make);
	$('#model').val(car.model);
}



function addCar() {
    
    var json = formToJSON();
      
    console.log('addCar');
     $.ajax({
	type: 'POST',
	contentType: 'application/json',
	url: rootURL,
	dataType: "json",
	data: json,
        
	success: function(data, textStatus, jqXHR) {   
	     // alert('Car added to Database successfully');	
	      $('#carId').val(data.Id);               
	},
        
	error: function(jqXHR, textStatus, errorThrown){
			alert('addCar error: ' + textStatus);
	}
    });   
}






function updateCar() {
	console.log('updateCar');
	$.ajax({
		type: 'PUT',
		contentType: 'application/json',
		url: rootURL + '/' + $('#carId').val(),
		dataType: "json",
		data: formToJSON(),
		success: function(data, textStatus, jqXHR){
			alert('Car updated successfully');
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('updateCar error: ' + textStatus);
		}
	});
}

function checkAll(bx) {
  var cbs = document.getElementsByTagName('input');
  for(var i=0; i < cbs.length; i++) {
    if(cbs[i].type == 'checkbox') {
      cbs[i].checked = bx.checked;
    }
  }
}


function deleteCar(id) {
    
    console.log('deleting Car... ');
    $.ajax({
        type: 'DELETE',
	url: rootURL + '/' + id,
	success: function(data, textStatus, jqXHR){ },
	error: function(jqXHR, textStatus, errorThrown){
	    alert('deleteWineCar delete error');
	}
    });
}


// Helper function to serialize all the form fields into a JSON string
function formToJSON() {
    
    var carId = $('#carId').val();
    var make = $('#make').val();
    var model = $('#model').val();
        
    return JSON.stringify({
	"id": carId === "" ? null : carId, 
	"make": make, 
	"model": model
    });       
    
}

function deleteRow() {
            
    var table = document.getElementById('carTable');
    var tbody = document.getElementById('carList');
    var inputs = table.getElementsByTagName('input');
    var rowCount = inputs.length;
           
    for(var i=1; i<=rowCount; i++) {
        
        var row = table.rows[i];
        var id = row.cells[0].childNodes[0].data;
        var j = i-1;
        console.log("-----" +id);
        console.log(inputs[j].type);
        var chkbox = inputs[j].checked;
                 
        if (inputs[j].type == "checkbox") {
                        
            if (inputs[j].checked) {
              
                console.log("Deleting row...   "+id);
                   deleteCar(id);
                }            
            }     
        }
}
       
       

    
