
var url = "/CarRestExample/webresources/broadcast";

if(typeof(EventSource)!=="undefined") {
     
var source = new EventSource(url); 

source.addEventListener('Add', function(event) {
    var d = JSON.parse(event.data);
    addRow2(d);
}, false);
     
     
 source.addEventListener('Delete', function(event) {
     
    var d = JSON.parse(event.data);
    console.log("Delete notification recieved");
      deleteRow2(d);
 }, false); 
   
 source.addEventListener('Update', function(event) {
    var d = JSON.parse(event.data);
    console.log("Update notification recieved");
        //location.reload(false); 
       editRow2(d);
}, false); 
    
    
   if (window.XMLHttpRequest) {
    
    // code for IE7+, Firefox, Chrome, Opera, Safari
    xmlhttp=new XMLHttpRequest();
   }
   else {
    // code for IE6, IE5
    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
   }  
}
else {
  document.getElementById("carList").innerHTML="Sorry, your browser does not support server-sent events...";
}



function addRow2(d) {
    
    var make = d.make;
    var model = d.model;
    var id = d.id;
    var table = document.getElementById('carList');
    var row = table.rows;
    var len = table.rows.length;
    var row = document.getElementById(id);
    
    if (!row) {
        
        $('#carList').prepend('<tr id=' +id+ '><td data-identity=' + id + '>' + id + '</td><td id="' + make + '" data-identity=' + id + '>'+ make +'</td><td id="' + model + '" data-identity=' + id + '>' + model + '</td><td><input type="checkbox" name="'+model+'"/></td></tr>');
   
    }
}



function deleteRow2(d)  
{   
    var row = document.getElementById(d.id);
    
    if(row) {
        row.parentNode.removeChild(row);
    }
}       
       

function editRow2(val) {
    
    var row = document.getElementById(val.id);
    
    if(row) {
          if (val.make){
             row.cells[1].innerHTML=val.make;
          }
          if (val.model) {
            row.cells[2].innerHTML=val.model;
           
          }
    }
}


function getTimeStamp() {
       var now = new Date();
       return ((now.getMonth() + 1) + '/' + (now.getDate()) + '/' + now.getFullYear() + " " + now.getHours() + ':'
                     + ((now.getMinutes() < 10) ? ("0" + now.getMinutes()) : (now.getMinutes())) + ':' + ((now.getSeconds() < 10) ? ("0" + now
                     .getSeconds()) : (now.getSeconds())));
}

      
