
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
      deleteRow2(d.id);
 }, false); 
   
 source.addEventListener('Edit', function(event) {
    var d = JSON.parse(event.data);
    console.log("Edit notification recieved");
        //location.reload(false); 
       editRow(d);
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
        
        $('#carList').append('<tr id=' +id+ ' data-identity=' + id + '><td>' + id + '</td><td>'+ make +'</td><td>' + model + '</td><td><input type="checkbox" name="chk"/></td></tr>');
   
    }
}


function addRow(val) {
    
    //Get length or number of rows in table in client   
     var len = document.getElementById("carList").rows.length;
     //load table from DOM
     var table = document.getElementById('carList');
     //Get rows
     
     var row = document.getElementById('carList').rows;
     var cell = document.getElementById('carList').rows.cells;
    // var colCount = table.rows[0].cells.length;   
       
 
  //Check if row exists.
  if (!row[val.id]){
      //alert(val.id);
   // if ( val.id === len + 1 ) {
       var newrow = table.insertRow(val.id);
       newrow.id = val.id;
       
       var newcell = newrow.insertCell(0);
       newcell.innerHTML = val.data1;
       
       var newcell2 = newrow.insertCell(1);
       newcell2.innerHTML = val.data2;
       
       var newcell3 = newrow.insertCell(2);
       newcell3.innerHTML = val.data3;
       
       var newcell4 = newrow.insertCell(3);
       newcell4.innerHTML = val.data5;
       
       var newcell5 = newrow.insertCell(4);
       newcell5.innerHTML = val.data6;
       
       document.getElementById('carList');
       //document.write(newcell5.innerHTML);
       //Row does not exist, therefore we can add it.         
       // var j =  table.insertRow(val).insertCell(0).appendChild(document.createElement('result'));
       // j.innerHTML = val.id;
   // }
    //  var len = document.getElementById("result").rows.length;
  }       
 
}
    
   // catch(e) {
          //      alert(e);
          //  }        
 //}
       
function deleteRow2(rowid)  
{   
    var row = document.getElementById(rowid);
    
    if(row) {
        row.parentNode.removeChild(row);
    }
}       
       
       
       
 function deleteRow(val)  
{   
    var table=document.getElementById('carList');
    var rowCount=table.rows.length;
    console.log(val);
    for(var i=0;i<table.rows.length;i++) {
 
        var row=table.rows[i];
        //loop through table and search for id or ids
        var id=row.cells[0].childNodes[0];
       // var id=row.cells[0];
        console.log("id:");
        console.log(id);
        console.log("val:");
        console.log(val);
       if(id.data===val){
          console.log("Row found. Deleting Row.");
          table.deleteRow(i);
          
       }
     }
}

function editRow(val)  
{   
    var table=document.getElementById('carList');
    var rowCount=table.rows.length;
    console.log(val);
    for(var i=0;i<rowCount;i++) {
 
        var row=table.rows[i];
        //loop through table and search for id or ids
        var id=row.cells[0].childNodes[0];
       // var id=row.cells[0];
        console.log("id:");
        console.log(id);
        console.log("val:");
        console.log(val.data1);
       if(id.data===val.id){
          console.log("Row found. Editing Row.");
          //table.deleteRow(i);
          
          if (val.make){
             row.cells[1].innerHTML=val.make;
          }
          if (val.model) {
            row.cells[2].innerHTML=val.model;
          }
       // }
       }
     }
}


function getTimeStamp() {
       var now = new Date();
       return ((now.getMonth() + 1) + '/' + (now.getDate()) + '/' + now.getFullYear() + " " + now.getHours() + ':'
                     + ((now.getMinutes() < 10) ? ("0" + now.getMinutes()) : (now.getMinutes())) + ':' + ((now.getSeconds() < 10) ? ("0" + now
                     .getSeconds()) : (now.getSeconds())));
}

      
